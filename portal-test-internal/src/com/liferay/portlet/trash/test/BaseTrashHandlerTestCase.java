/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.trash.test;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.model.WorkflowedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.SystemEventLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;
import com.liferay.portlet.trash.service.TrashVersionLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author Eudaldo Alonso
 * @author Manuel de la Peña
 * @author Cristina González
 */
public abstract class BaseTrashHandlerTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteTrashVersions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialTrashVersionsCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialTrashVersionsCount,
			TrashVersionLocalServiceUtil.getTrashVersionsCount());
	}

	@Test
	public void testMoveBaseModelToTrash() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
			getBaseModelClassName(), getTrashEntryClassPK(baseModel));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		Assert.assertEquals(
			1,
			getDeletionSystemEventCount(
				trashHandler, trashEntry.getSystemEventSetKey()));
	}

	@Test
	public void testMoveBaseModelToTrashIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			getSearchKeywords(), serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			searchTrashEntriesCount(getSearchKeywords(), serviceContext));
	}

	@Test
	public void testMoveBaseModelToTrashIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertFalse(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testMoveBaseModelToTrashStatusIsInTrash() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		workflowedModel = getWorkflowedModel(
			getBaseModel((Long)baseModel.getPrimaryKeyObj()));

		Assert.assertEquals(
			WorkflowConstants.STATUS_IN_TRASH, workflowedModel.getStatus());
	}

	@Test
	public void testMoveBaseModelToTrashUniqueTitleNotChange()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		String uniqueTitle = getUniqueTitle(baseModel);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		if (uniqueTitle != null) {
			Assert.assertEquals(uniqueTitle, getUniqueTitle(baseModel));
		}
	}

	@Test
	public void testTrashAndDeleteWithApprovedStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));

		Assert.assertEquals(1, getDeletionSystemEventCount(trashHandler, -1));
	}

	@Test
	public void testTrashAndDeleteWithApprovedStatusIndexable()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			getSearchKeywords(), serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			searchTrashEntriesCount(getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndDeleteWithApprovedStatusIsNotFound()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertNull(fetchAssetEntry(baseModel));
	}

	@Test
	public void testTrashAndDeleteWithDraftStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		baseModel = addBaseModel(parentBaseModel, false, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));

		Assert.assertEquals(1, getDeletionSystemEventCount(trashHandler, -1));
	}

	@Test
	public void testTrashAndDeleteWithDraftStatusIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			getSearchKeywords(), serviceContext);

		baseModel = addBaseModel(parentBaseModel, false, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			searchTrashEntriesCount(getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndDeleteWithDraftStatusIsNotFound() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, false, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertNull(fetchAssetEntry(baseModel));
	}

	@Test
	public void testTrashAndRestoreWithApprovedStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(0, getDeletionSystemEventCount(trashHandler, -1));
	}

	@Test
	public void testTrashAndRestoreWithApprovedStatusIndexable()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			getSearchKeywords(), serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));

		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			searchTrashEntriesCount(getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndRestoreWithApprovedStatusIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertTrue(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testTrashAndRestoreWithApprovedStatusRestoreStatus()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		int oldStatus = workflowedModel.getStatus();

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		workflowedModel = getWorkflowedModel(baseModel);

		Assert.assertEquals(oldStatus, workflowedModel.getStatus());
	}

	@Test
	public void testTrashAndRestoreWithApprovedStatusRestoreUniqueTitle()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		String uniqueTitle = getUniqueTitle(baseModel);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		if (uniqueTitle != null) {
			Assert.assertEquals(uniqueTitle, getUniqueTitle(baseModel));
		}
	}

	@Test
	public void testTrashAndRestoreWithDraftStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		baseModel = addBaseModel(parentBaseModel, false, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));

		Assert.assertEquals(0, getDeletionSystemEventCount(trashHandler, -1));
	}

	@Test
	public void testTrashAndRestoreWithDraftStatusIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			getSearchKeywords(), serviceContext);

		baseModel = addBaseModel(parentBaseModel, false, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));

		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			searchTrashEntriesCount(getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndRestoreWithDraftStatusIsNotVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, false, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertFalse(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreStatus()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, false, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		int oldStatus = workflowedModel.getStatus();

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		workflowedModel = getWorkflowedModel(baseModel);

		Assert.assertEquals(oldStatus, workflowedModel.getStatus());
	}

	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreUniqueTitle()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, false, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		String uniqueTitle = getUniqueTitle(baseModel);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		if (uniqueTitle != null) {
			Assert.assertEquals(uniqueTitle, getUniqueTitle(baseModel));
		}
	}

	@Test
	public void testTrashBaseModelAndDeleteWithParentIsNotRestorable()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		deleteParentBaseModel(parentBaseModel, false);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		boolean restorable = trashHandler.isRestorable(
			getAssetClassPK(baseModel));

		Assert.assertFalse(restorable);
	}

	@Test
	public void testTrashBaseModelAndParent() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		if (getBaseModelClassName().equals(getParentBaseModelClassName())) {
			Assert.assertEquals(
				0,
				parentTrashHandler.getTrashContainedModelsCount(
					(Long)parentBaseModel.getPrimaryKeyObj()));
			Assert.assertEquals(
				1,
				parentTrashHandler.getTrashContainerModelsCount(
					(Long)parentBaseModel.getPrimaryKeyObj()));
		}
		else {
			Assert.assertEquals(
				1,
				parentTrashHandler.getTrashContainedModelsCount(
					(Long)parentBaseModel.getPrimaryKeyObj()));
			Assert.assertEquals(
				0,
				parentTrashHandler.getTrashContainerModelsCount(
					(Long)parentBaseModel.getPrimaryKeyObj()));
		}
	}

	@Test
	public void testTrashBaseModelAndParentAndDeleteGroupTrashEntries()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		TrashEntryServiceUtil.deleteEntries(group.getGroupId());

		Assert.assertEquals(0, getTrashEntriesCount(group.getGroupId()));

		try {
			getBaseModel((Long)baseModel.getPrimaryKeyObj());

			Assert.fail();
		}
		catch (NoSuchModelException nsme) {
		}
	}

	@Test
	public void testTrashBaseModelAndParentAndDeleteParent() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.deleteTrashEntry(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));

		if (isBaseModelMoveableFromTrash()) {
			Assert.assertEquals(
				initialTrashEntriesCount + 1,
				getTrashEntriesCount(group.getGroupId()));

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					getBaseModelClassName());

			trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

			Assert.assertEquals(
				initialTrashEntriesCount,
				getTrashEntriesCount(group.getGroupId()));
		}
	}

	@Test
	public void testTrashBaseModelAndParentAndDeleteParentNoMoveableFromTrash()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.deleteTrashEntry(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));

		if (!isBaseModelMoveableFromTrash()) {
			try {
				getBaseModel((Long)baseModel.getPrimaryKeyObj());

				Assert.fail();
			}
			catch (NoSuchModelException nsme) {
			}

			Assert.assertEquals(
				initialTrashEntriesCount,
				getTrashEntriesCount(group.getGroupId()));
		}
	}

	@Test
	public void testTrashBaseModelAndParentAndRestoreModel() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		if (isBaseModelMoveableFromTrash()) {
			BaseModel<?> newParentBaseModel = moveBaseModelFromTrash(
				baseModel, group, serviceContext);

			Assert.assertEquals(
				initialBaseModelsCount + 1,
				getNotInTrashBaseModelsCount(newParentBaseModel));
			Assert.assertEquals(
				initialTrashEntriesCount + 1,
				getTrashEntriesCount(group.getGroupId()));
		}
	}

	@Test
	public void testTrashBaseModelAndParentAndRestoreModelIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		if (isBaseModelMoveableFromTrash()) {
			moveBaseModelFromTrash(baseModel, group, serviceContext);

			Assert.assertTrue(isAssetEntryVisible(baseModel));
		}
	}

	@Test
	public void testTrashBaseModelAndParentIsInContainerBaseModel()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertTrue(isInTrashContainer(baseModel));
	}

	@Test
	public void testTrashBaseModelAndParentIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertFalse(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testTrashBaseModelAndTrashParentAndDeleteParentIsNotRestorable()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.deleteTrashEntry(
			(Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		boolean restorable = trashHandler.isRestorable(
			getAssetClassPK(baseModel));

		Assert.assertFalse(restorable);
	}

	@Test
	public void testTrashBaseModelAndTrashParentIsNotRestorable()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		boolean restorable = trashHandler.isRestorable(
			getAssetClassPK(baseModel));

		Assert.assertFalse(restorable);
	}

	@Test
	public void testTrashBaseModelIsInTrashContainer() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertFalse(isInTrashContainer(baseModel));
	}

	@Test
	public void testTrashBaseModelWithParentIsRestorable() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		boolean restorable = trashHandler.isRestorable(
			getAssetClassPK(baseModel));

		Assert.assertTrue(restorable);
	}

	@Test
	public void testTrashDuplicate() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertTrue(isBaseModelTrashName(baseModel));

		BaseModel<?> duplicateBaseModel = addBaseModel(
			parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)duplicateBaseModel.getPrimaryKeyObj());

		duplicateBaseModel = getBaseModel(
			(Long)duplicateBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));

		Assert.assertTrue(isBaseModelTrashName(duplicateBaseModel));
	}

	@Test
	public void testTrashGrandparentBaseModel() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			grandparentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		if (getBaseModelClassName().equals(getParentBaseModelClassName())) {
			Assert.assertEquals(
				initialBaseModelsCount + 1,
				getNotInTrashBaseModelsCount(grandparentBaseModel));
		}
		else {
			Assert.assertEquals(
				initialBaseModelsCount,
				getNotInTrashBaseModelsCount(grandparentBaseModel));
		}

		moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		Assert.assertTrue(isInTrashContainer(baseModel));
		Assert.assertTrue(isInTrashContainer(parentBaseModel));
		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(grandparentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModel()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsNotInTrashContainer()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		Assert.assertFalse(isInTrashContainer(baseModel));
		Assert.assertFalse(isInTrashContainer(parentBaseModel));
	}

	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		Assert.assertTrue(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsVisibleParent()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableParentModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		Assert.assertTrue(isAssetEntryVisible(parentBaseModel));
	}

	@Test
	public void testTrashGrandparentBaseModelIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		Assert.assertFalse(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testTrashGrandparentBaseModelIsNotVisibleParent()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableParentModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		Assert.assertFalse(isAssetEntryVisible(parentBaseModel));
	}

	@Test
	public void testTrashIsRestorableBaseModel() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		baseModel = addBaseModelWithWorkflow(true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		boolean restorable = trashHandler.isRestorable(
			getAssetClassPK(baseModel));

		Assert.assertTrue(restorable);
	}

	@Test
	public void testTrashMyBaseModel() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getMineBaseModelsCount(
			group.getGroupId(), TestPropsValues.getUserId());

		addBaseModel(parentBaseModel, true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getMineBaseModelsCount(
				group.getGroupId(), TestPropsValues.getUserId()));

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getMineBaseModelsCount(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testTrashParent() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		if (getBaseModelClassName().equals(getParentBaseModelClassName())) {
			Assert.assertEquals(
				0,
				parentTrashHandler.getTrashContainedModelsCount(
					(Long)parentBaseModel.getPrimaryKeyObj()));
			Assert.assertEquals(
				1,
				parentTrashHandler.getTrashContainerModelsCount(
					(Long)parentBaseModel.getPrimaryKeyObj()));
		}
		else {
			Assert.assertEquals(
				1,
				parentTrashHandler.getTrashContainedModelsCount(
					(Long)parentBaseModel.getPrimaryKeyObj()));
			Assert.assertEquals(
				0,
				parentTrashHandler.getTrashContainerModelsCount(
					(Long)parentBaseModel.getPrimaryKeyObj()));
		}
	}

	@Test
	public void testTrashParentAndDeleteGroupTrashEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		TrashEntryServiceUtil.deleteEntries(group.getGroupId());

		Assert.assertEquals(0, getTrashEntriesCount(group.getGroupId()));

		try {
			getBaseModel((Long)baseModel.getPrimaryKeyObj());

			Assert.fail();
		}
		catch (NoSuchModelException nsme) {
		}
	}

	@Test
	public void testTrashParentAndDeleteParent() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.deleteTrashEntry(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));

		try {
			getBaseModel((Long)baseModel.getPrimaryKeyObj());

			Assert.fail();
		}
		catch (NoSuchModelException nsme) {
		}

		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashParentAndRestoreBaseModelIsVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		if (isBaseModelMoveableFromTrash()) {
			moveBaseModelFromTrash(baseModel, group, serviceContext);

			Assert.assertTrue(isAssetEntryVisible(baseModel));
		}
	}

	@Test
	public void testTrashParentAndRestoreIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		if (isBaseModelMoveableFromTrash()) {
			moveBaseModelFromTrash(baseModel, group, serviceContext);

			if (isBaseModelContainerModel()) {
				Assert.assertEquals(
					initialBaseModelsSearchCount + 2,
					searchBaseModelsCount(
						getBaseModelClass(), group.getGroupId()));
			}
			else {
				Assert.assertEquals(
					initialBaseModelsSearchCount + 1,
					searchBaseModelsCount(
						getBaseModelClass(), group.getGroupId()));
			}
		}
	}

	@Test
	public void testTrashParentIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
	}

	@Test
	public void testTrashParentIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertFalse(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testTrashParentWithBaseModelIsInTrashContainer()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertTrue(isInTrashContainer(baseModel));
	}

	@Test
	public void testTrashParentWithBaseModelIsIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertFalse(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testTrashRecentBaseModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasRecentBaseModelCount);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		WhenHasRecentBaseModelCount whenHasRecentBaseModelCount =
			(WhenHasRecentBaseModelCount)this;

		int initialBaseModelsCount =
			whenHasRecentBaseModelCount.getRecentBaseModelsCount(
				group.getGroupId());

		addBaseModel(parentBaseModel, true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			whenHasRecentBaseModelCount.getRecentBaseModelsCount(
				group.getGroupId()));

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			whenHasRecentBaseModelCount.getRecentBaseModelsCount(
				group.getGroupId()));
	}

	@Test
	public void testTrashVersionBaseModel() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashVersionBaseModelAndDelete() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashVersionBaseModelAndDeleteIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			getSearchKeywords(), serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			searchTrashEntriesCount(getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelAndDeleteIsNotFound()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertNull(fetchAssetEntry(baseModel));
	}

	@Test
	public void testTrashVersionBaseModelAndRestore() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashVersionBaseModelAndRestoreIndexable()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			getSearchKeywords(), serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			searchTrashEntriesCount(getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelAndRestoreIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertTrue(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testTrashVersionBaseModelIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			getSearchKeywords(), serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			searchTrashEntriesCount(getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertFalse(isAssetEntryVisible(baseModel));
	}

	@Test
	public void testTrashVersionParentBaseModel() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		List<Integer> originalStatuses = new ArrayList<>();

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = expireBaseModel(baseModel, serviceContext);

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
		Assert.assertTrue(isInTrashContainer(baseModel));
	}

	@Test
	public void testTrashVersionParentBaseModelAndCustomRestore()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		List<Integer> originalStatuses = new ArrayList<>();

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = expireBaseModel(baseModel, serviceContext);

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		if (!isBaseModelMoveableFromTrash()) {
			restoreParentBaseModelFromTrash(
				(Long)parentBaseModel.getPrimaryKeyObj());

			List<? extends WorkflowedModel> childrenWorkflowedModels =
				getChildrenWorkflowedModels(parentBaseModel);

			for (int i = 1; i <= childrenWorkflowedModels.size(); i++) {
				WorkflowedModel childrenWorkflowedModel =
					childrenWorkflowedModels.get(i - 1);

				int originalStatus = originalStatuses.get(
					childrenWorkflowedModels.size() - i);

				Assert.assertEquals(
					originalStatus, childrenWorkflowedModel.getStatus());
			}
		}
	}

	@Test
	public void testTrashVersionParentBaseModelAndRestore() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		List<Integer> originalStatuses = new ArrayList<>();

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = expireBaseModel(baseModel, serviceContext);

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		if (isBaseModelMoveableFromTrash()) {
			BaseModel<?> newParentBaseModel = moveBaseModelFromTrash(
				baseModel, group, serviceContext);

			baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

			Assert.assertEquals(
				initialBaseModelsCount + 1,
				getNotInTrashBaseModelsCount(newParentBaseModel));
			Assert.assertEquals(
				initialTrashEntriesCount + 1,
				getTrashEntriesCount(group.getGroupId()));
		}
	}

	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsNotInTrashContainer()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		List<Integer> originalStatuses = new ArrayList<>();

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = expireBaseModel(baseModel, serviceContext);

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		if (isBaseModelMoveableFromTrash()) {
			moveBaseModelFromTrash(baseModel, group, serviceContext);

			baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

			Assert.assertFalse(isInTrashContainer(baseModel));
		}
	}

	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		List<Integer> originalStatuses = new ArrayList<>();

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = expireBaseModel(baseModel, serviceContext);

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		if (isBaseModelMoveableFromTrash()) {
			moveBaseModelFromTrash(baseModel, group, serviceContext);

			baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

			Assert.assertTrue(isAssetEntryVisible(baseModel));
		}
	}

	@Test
	public void testTrashVersionParentBaseModelIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId());
		int initialTrashEntriesSearchCount = searchTrashEntriesCount(
			getSearchKeywords(), serviceContext);

		List<Integer> originalStatuses = new ArrayList<>();

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = expireBaseModel(baseModel, serviceContext);

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			searchTrashEntriesCount(getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionParentBaseModelIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		List<Integer> originalStatuses = new ArrayList<>();

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = expireBaseModel(baseModel, serviceContext);

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertFalse(isAssetEntryVisible(baseModel));
	}

	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			BaseModel<?> baseModel = addBaseModelWithWorkflow(
				parentBaseModel, approved, serviceContext);

			return baseModel;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	protected abstract BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception;

	protected BaseModel<?> addBaseModelWithWorkflow(
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		return addBaseModelWithWorkflow(
			parentBaseModel, approved, serviceContext);
	}

	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {
	}

	protected BaseModel<?> expireBaseModel(
			BaseModel<?> baseModel, ServiceContext serviceContext)
		throws Exception {

		return baseModel;
	}

	protected AssetEntry fetchAssetEntry(Class<?> clazz, long classPK)
		throws Exception {

		return AssetEntryLocalServiceUtil.fetchEntry(clazz.getName(), classPK);
	}

	protected AssetEntry fetchAssetEntry(ClassedModel classedModel)
		throws Exception {

		return fetchAssetEntry(
			classedModel.getModelClass(),
			(Long)classedModel.getPrimaryKeyObj());
	}

	protected Long getAssetClassPK(ClassedModel classedModel) {
		return (Long)classedModel.getPrimaryKeyObj();
	}

	protected abstract BaseModel<?> getBaseModel(long primaryKey)
		throws Exception;

	protected abstract Class<?> getBaseModelClass();

	protected String getBaseModelClassName() {
		Class<?> clazz = getBaseModelClass();

		return clazz.getName();
	}

	protected String getBaseModelName(ClassedModel classedModel) {
		return StringPool.BLANK;
	}

	protected List<? extends WorkflowedModel> getChildrenWorkflowedModels(
			BaseModel<?> parentBaseModel)
		throws Exception {

		return Collections.emptyList();
	}

	protected long getDeletionSystemEventCount(
			TrashHandler trashHandler, final long systemEventSetKey)
		throws Exception {

		final long systemEventClassNameId = PortalUtil.getClassNameId(
			trashHandler.getSystemEventClassName());

		ActionableDynamicQuery actionableDynamicQuery =
			SystemEventLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(
						classNameIdProperty.eq(systemEventClassNameId));

					if (systemEventSetKey > 0) {
						Property systemEventSetKeyProperty =
							PropertyFactoryUtil.forName("systemEventSetKey");

						dynamicQuery.add(
							systemEventSetKeyProperty.eq(systemEventSetKey));
					}

					Property typeProperty = PropertyFactoryUtil.forName("type");

					dynamicQuery.add(
						typeProperty.eq(SystemEventConstants.TYPE_DELETE));
				}

			});
		actionableDynamicQuery.setGroupId(group.getGroupId());

		return actionableDynamicQuery.performCount();
	}

	protected int getMineBaseModelsCount(long groupId, long userId)
		throws Exception {

		return 0;
	}

	protected abstract int getNotInTrashBaseModelsCount(
			BaseModel<?> parentBaseModel)
		throws Exception;

	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		return group;
	}

	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return group;
	}

	protected Class<?> getParentBaseModelClass() {
		return getBaseModelClass();
	}

	protected String getParentBaseModelClassName() {
		Class<?> clazz = getParentBaseModelClass();

		return clazz.getName();
	}

	protected abstract String getSearchKeywords();

	protected int getTrashEntriesCount(long groupId) throws Exception {
		return TrashEntryLocalServiceUtil.getEntriesCount(groupId);
	}

	protected long getTrashEntryClassPK(ClassedModel classedModel) {
		return (Long)classedModel.getPrimaryKeyObj();
	}

	protected abstract String getUniqueTitle(BaseModel<?> baseModel);

	protected WorkflowedModel getWorkflowedModel(ClassedModel baseModel)
		throws Exception {

		return (WorkflowedModel)baseModel;
	}

	protected boolean isAssetEntryVisible(ClassedModel classedModel)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			classedModel.getModelClassName(), getAssetClassPK(classedModel));

		return assetEntry.isVisible();
	}

	protected boolean isBaseModelContainerModel() {
		if (baseModel instanceof ContainerModel) {
			return true;
		}

		return false;
	}

	protected boolean isBaseModelMoveableFromTrash() {
		return true;
	}

	protected boolean isBaseModelTrashName(ClassedModel classedModel) {
		String baseModelName = getBaseModelName(classedModel);

		if (baseModelName.startsWith(StringPool.SLASH)) {
			return true;
		}

		return false;
	}

	protected boolean isInTrashContainer(ClassedModel classedModel)
		throws Exception {

		if (classedModel instanceof TrashedModel) {
			TrashedModel trashedModel = (TrashedModel)classedModel;

			return trashedModel.isInTrashContainer();
		}

		return false;
	}

	protected BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		return getParentBaseModel(group, serviceContext);
	}

	protected abstract void moveBaseModelToTrash(long primaryKey)
		throws Exception;

	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {
	}

	protected void restoreParentBaseModelFromTrash(long primaryKey)
		throws Exception {
	}

	protected int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception {

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(clazz);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[] {groupId});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	protected int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception {

		Hits results = TrashEntryLocalServiceUtil.search(
			serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
			serviceContext.getUserId(), keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return results.getLength();
	}

	protected BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		return getBaseModel(primaryKey);
	}

	protected BaseModel<?> baseModel;

	@DeleteAfterTestRun
	protected Group group;

}