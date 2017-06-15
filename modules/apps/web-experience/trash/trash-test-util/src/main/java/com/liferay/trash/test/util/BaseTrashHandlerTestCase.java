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

package com.liferay.trash.test.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SystemEventLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.trash.exception.RestoreEntryException;
import com.liferay.trash.exception.TrashEntryException;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.trash.service.TrashEntryServiceUtil;
import com.liferay.trash.service.TrashVersionLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialTrashVersionsCount =
			TrashVersionLocalServiceUtil.getTrashVersionsCount();

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

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

		baseModel = addBaseModel(parentBaseModel, serviceContext);

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
		Assume.assumeFalse(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testMoveBaseModelToTrashIndexableAndReindex() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeFalse(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		reindex(baseModel);

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testMoveBaseModelToTrashIndexableWithEqualsParentAndBaseModelClass()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testMoveBaseModelToTrashIndexableWithEqualsParentAndBaseModelClassAndReindex()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		reindex(baseModel);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testMoveBaseModelToTrashIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenIsAssetableBaseModel whenIsAssetableBaseModel =
			(WhenIsAssetableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertFalse(
			whenIsAssetableBaseModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testMoveBaseModelToTrashStatusIsInTrash() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

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

		baseModel = addBaseModel(parentBaseModel, serviceContext);

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

		baseModel = addBaseModel(parentBaseModel, serviceContext);

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
		Assume.assumeFalse(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndDeleteWithApprovedStatusIndexableAndEqualsParentAndBaseModelClass()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndDeleteWithApprovedStatusIsNotFound()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenIsAssetableBaseModel whenIsAssetableBaseModel =
			(WhenIsAssetableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertNull(whenIsAssetableBaseModel.fetchAssetEntry(baseModel));
	}

	@Test
	public void testTrashAndDeleteWithDraftStatus() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasDraftStatus);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		baseModel = addDraftBaseModel(parentBaseModel, serviceContext);

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
		Assume.assumeTrue(this instanceof WhenHasDraftStatus);
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addDraftBaseModel(parentBaseModel, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndDeleteWithDraftStatusIsNotFound() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasDraftStatus);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenIsAssetableBaseModel whenIsAssetableBaseModel =
			(WhenIsAssetableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addDraftBaseModel(parentBaseModel, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertNull(whenIsAssetableBaseModel.fetchAssetEntry(baseModel));
	}

	@Test
	public void testTrashAndRestoreWithApprovedStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

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
		Assume.assumeFalse(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndRestoreWithApprovedStatusIndexableAndEqualsParentAndBaseModelClass()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndRestoreWithApprovedStatusIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenIsAssetableBaseModel whenIsAssetableBaseModel =
			(WhenIsAssetableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertTrue(
			whenIsAssetableBaseModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashAndRestoreWithApprovedStatusRestoreStatus()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

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

		baseModel = addBaseModel(parentBaseModel, serviceContext);

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
		Assume.assumeTrue(this instanceof WhenHasDraftStatus);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		baseModel = addDraftBaseModel(parentBaseModel, serviceContext);

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
		Assume.assumeTrue(this instanceof WhenHasDraftStatus);
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addDraftBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashAndRestoreWithDraftStatusIsNotVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasDraftStatus);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenIsAssetableBaseModel whenIsAssetableBaseModel =
			(WhenIsAssetableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addDraftBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertFalse(
			whenIsAssetableBaseModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreStatus()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasDraftStatus);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addDraftBaseModel(parentBaseModel, serviceContext);

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

		Assume.assumeTrue(this instanceof WhenHasDraftStatus);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addDraftBaseModel(parentBaseModel, serviceContext);

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

		Assume.assumeTrue(this instanceof WhenIsRestorableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		deleteParentBaseModel(parentBaseModel, false);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		boolean restorable = trashHandler.isRestorable(
			getAssetClassPK(baseModel));

		Assert.assertFalse(restorable);
	}

	@Test
	public void testTrashBaseModelAndParentAndDeleteGroupTrashEntries()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

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
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsMoveableFromTrashBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

		parentTrashHandler.deleteTrashEntry(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashBaseModelAndParentAndDeleteParentNoMoveableFromTrash()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeFalse(this instanceof WhenIsMoveableFromTrashBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

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
	public void testTrashBaseModelAndParentAndRestore() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

		if (Objects.equals(
				getBaseModelClassName(),
				whenHasParent.getParentBaseModelClassName())) {

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
	public void testTrashBaseModelAndParentAndRestoreModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsMoveableFromTrashBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsMoveableFromTrashBaseModel whenIsMoveableFromTrashBaseModel =
			(WhenIsMoveableFromTrashBaseModel)this;

		BaseModel<?> newParentBaseModel =
			whenIsMoveableFromTrashBaseModel.moveBaseModelFromTrash(
				baseModel, group, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(newParentBaseModel));

		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashBaseModelAndParentAndRestoreModelIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsMoveableFromTrashBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsMoveableFromTrashBaseModel whenIsMoveableFromTrashBaseModel =
			(WhenIsMoveableFromTrashBaseModel)this;

		whenIsMoveableFromTrashBaseModel.moveBaseModelFromTrash(
			baseModel, group, serviceContext);

		WhenIsAssetableBaseModel whenIsAssetableBaseModel =
			(WhenIsAssetableBaseModel)this;

		Assert.assertTrue(
			whenIsAssetableBaseModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashBaseModelAndParentIsInContainerBaseModel()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertTrue(isInTrashContainer(baseModel));
	}

	@Test
	public void testTrashBaseModelAndParentIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsAssetableBaseModel whenIsAssetableBaseModel =
			(WhenIsAssetableBaseModel)this;

		Assert.assertFalse(
			whenIsAssetableBaseModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashBaseModelAndTrashParentAndDeleteParentIsNotRestorable()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsRestorableBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

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

		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsRestorableBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

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

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertFalse(isInTrashContainer(baseModel));
	}

	@Test
	public void testTrashBaseModelWithParentIsRestorable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsRestorableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		boolean restorable = trashHandler.isRestorable(
			getAssetClassPK(baseModel));

		Assert.assertTrue(restorable);
	}

	@Test
	public void testTrashDuplicate() throws Exception {
		Assume.assumeTrue(this instanceof WhenCanBeDuplicatedInTrash);

		WhenCanBeDuplicatedInTrash whenCanBeDuplicatedInTrash =
			(WhenCanBeDuplicatedInTrash)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		String baseModelName = whenCanBeDuplicatedInTrash.getBaseModelName(
			baseModel);

		Assert.assertTrue(isValidTrashTitle(baseModelName));

		BaseModel<?> duplicateBaseModel = addBaseModel(
			parentBaseModel, serviceContext);

		moveBaseModelToTrash((Long)duplicateBaseModel.getPrimaryKeyObj());

		duplicateBaseModel = getBaseModel(
			(Long)duplicateBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));

		String duplicateBaseModelName =
			whenCanBeDuplicatedInTrash.getBaseModelName(duplicateBaseModel);

		Assert.assertTrue(isValidTrashTitle(duplicateBaseModelName));
	}

	@Test
	public void testTrashGrandparentBaseModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasGrandParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

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

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		if (Objects.equals(
				getBaseModelClassName(),
				whenHasParent.getParentBaseModelClassName())) {

			Assert.assertEquals(
				initialBaseModelsCount + 1,
				getNotInTrashBaseModelsCount(grandparentBaseModel));
		}
		else {
			Assert.assertEquals(
				initialBaseModelsCount,
				getNotInTrashBaseModelsCount(grandparentBaseModel));
		}

		whenHasParent.moveParentBaseModelToTrash(
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

		Assume.assumeTrue(this instanceof WhenHasGrandParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsNotInTrashContainer()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasGrandParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		Assert.assertFalse(isInTrashContainer(baseModel));
		Assert.assertFalse(isInTrashContainer(parentBaseModel));
	}

	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasGrandParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		WhenIsAssetableBaseModel whenIsAssetableBaseModel =
			(WhenIsAssetableBaseModel)this;

		Assert.assertTrue(
			whenIsAssetableBaseModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsVisibleParent()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasGrandParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableParentModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		WhenIsAssetableParentModel whenIsAssetableParentModel =
			(WhenIsAssetableParentModel)this;

		Assert.assertTrue(
			whenIsAssetableParentModel.isAssetEntryVisible(
				parentBaseModel, getAssetClassPK(parentBaseModel)));
	}

	@Test
	public void testTrashGrandparentBaseModelIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasGrandParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		WhenIsAssetableBaseModel whenIsAssetableModel =
			(WhenIsAssetableBaseModel)this;

		Assert.assertFalse(
			whenIsAssetableModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashGrandparentBaseModelIsNotVisibleParent()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasGrandParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableParentModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> grandparentBaseModel = getParentBaseModel(
			group, serviceContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, (Long)grandparentBaseModel.getPrimaryKeyObj(),
			serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)grandparentBaseModel.getPrimaryKeyObj());

		WhenIsAssetableParentModel whenIsAssetableParentModel =
			(WhenIsAssetableParentModel)this;

		Assert.assertFalse(
			whenIsAssetableParentModel.isAssetEntryVisible(
				parentBaseModel, getAssetClassPK(parentBaseModel)));
	}

	@Test
	public void testTrashIsRestorableBaseModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsRestorableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		baseModel = addBaseModelWithWorkflow(serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		boolean restorable = trashHandler.isRestorable(
			getAssetClassPK(baseModel));

		Assert.assertTrue(restorable);
	}

	@Test
	public void testTrashMyBaseModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasMyBaseModel);
		Assume.assumeTrue(this instanceof WhenHasParent);

		WhenHasMyBaseModel whenHasMyBaseModel = (WhenHasMyBaseModel)this;

		int initialBaseModelsCount = whenHasMyBaseModel.getMineBaseModelsCount(
			group.getGroupId(), TestPropsValues.getUserId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		addBaseModel(parentBaseModel, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			whenHasMyBaseModel.getMineBaseModelsCount(
				group.getGroupId(), TestPropsValues.getUserId()));

		WhenHasParent whenHasParent = (WhenHasParent)this;

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			whenHasMyBaseModel.getMineBaseModelsCount(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testTrashParent() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

		if (Objects.equals(
				getBaseModelClassName(),
				whenHasParent.getParentBaseModelClassName())) {

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

	@Test(expected = TrashEntryException.class)
	public void testTrashParentAndBaseModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsRestorableBaseModel);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());
	}

	@Test
	public void testTrashParentAndDeleteGroupTrashEntries() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

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
		Assume.assumeTrue(this instanceof WhenHasParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				whenHasParent.getParentBaseModelClassName());

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
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsMoveableFromTrashBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsMoveableFromTrashBaseModel whenIsMoveableFromTrashBaseModel =
			(WhenIsMoveableFromTrashBaseModel)this;

		whenIsMoveableFromTrashBaseModel.moveBaseModelFromTrash(
			baseModel, group, serviceContext);

		WhenIsAssetableBaseModel whenIsAssetableModel =
			(WhenIsAssetableBaseModel)this;

		Assert.assertTrue(
			whenIsAssetableModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashParentAndRestoreIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsMoveableFromTrashBaseModel);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsMoveableFromTrashBaseModel whenIsMoveableFromTrashBaseModel =
			(WhenIsMoveableFromTrashBaseModel)this;

		whenIsMoveableFromTrashBaseModel.moveBaseModelFromTrash(
			baseModel, group, serviceContext);

		if (isBaseModelContainerModel()) {
			Assert.assertEquals(
				initialBaseModelsSearchCount + 2,
				whenIsIndexableBaseModel.searchBaseModelsCount(
					getBaseModelClass(), group.getGroupId()));
		}
		else {
			Assert.assertEquals(
				initialBaseModelsSearchCount + 1,
				whenIsIndexableBaseModel.searchBaseModelsCount(
					getBaseModelClass(), group.getGroupId()));
		}
	}

	@Test(expected = RestoreEntryException.class)
	public void testTrashParentAndRestoreParentAndBaseModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsRestorableBaseModel);
		Assume.assumeTrue(
			this instanceof WhenIsRestorableParentBaseModelFromTrash);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsRestorableParentBaseModelFromTrash
			whenIsRestorableParentBaseModelFromTrash =
				(WhenIsRestorableParentBaseModelFromTrash)this;

		whenIsRestorableParentBaseModelFromTrash.
			restoreParentBaseModelFromTrash(
				(Long)parentBaseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));
	}

	@Test
	public void testTrashParentIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
	}

	@Test
	public void testTrashParentIndexableAndReindex() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		reindex(baseModel);

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
	}

	@Test
	public void testTrashParentIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsAssetableBaseModel whenIsAssetableModel =
			(WhenIsAssetableBaseModel)this;

		Assert.assertFalse(
			whenIsAssetableModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashParentWithBaseModelIsInTrashContainer()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasParent);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertTrue(isInTrashContainer(baseModel));
	}

	@Test
	public void testTrashParentWithBaseModelIsIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsAssetableBaseModel whenIsAssetableModel =
			(WhenIsAssetableBaseModel)this;

		Assert.assertFalse(
			whenIsAssetableModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashRecentBaseModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenHasRecentBaseModelCount);

		WhenHasRecentBaseModelCount whenHasRecentBaseModelCount =
			(WhenHasRecentBaseModelCount)this;

		int initialBaseModelsCount =
			whenHasRecentBaseModelCount.getRecentBaseModelsCount(
				group.getGroupId());

		WhenHasParent whenHasParent = (WhenHasParent)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		addBaseModel(parentBaseModel, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			whenHasRecentBaseModelCount.getRecentBaseModelsCount(
				group.getGroupId()));

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			whenHasRecentBaseModelCount.getRecentBaseModelsCount(
				group.getGroupId()));
	}

	@Test
	public void testTrashVersionBaseModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
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
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
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
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeFalse(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelAndDeleteIndexableWithEqualsParentAndBaseModelClass()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelAndDeleteIsNotFound()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

		WhenIsAssetableBaseModel whenIsAssetableBaseModel =
			(WhenIsAssetableBaseModel)this;

		Assert.assertNull(whenIsAssetableBaseModel.fetchAssetEntry(baseModel));
	}

	@Test
	public void testTrashVersionBaseModelAndRestore() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
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
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeFalse(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelAndRestoreIndexableWithEqualsParentAndBaseModelClass()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelAndRestoreIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		WhenIsAssetableBaseModel whenIsAssetableModel =
			(WhenIsAssetableBaseModel)this;

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		trashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

		Assert.assertTrue(
			whenIsAssetableModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashVersionBaseModelIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeFalse(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelIndexableAndReindex()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeFalse(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		reindex(baseModel);

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelIndexableWithEqualsParentAndBaseModelClass()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelIndexableWithEqualsParentAndBaseModelClassAndReindex()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenParentModelIsSameType);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		reindex(baseModel);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount + 1,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionBaseModelIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		WhenIsAssetableBaseModel whenIsAssetableModel =
			(WhenIsAssetableBaseModel)this;

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertFalse(
			whenIsAssetableModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashVersionParentBaseModel() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsVersionableBaseModel);

		WhenIsVersionableBaseModel whenIsVersionableBaseModel =
			(WhenIsVersionableBaseModel)this;

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsVersionableBaseModel.expireBaseModel(
			baseModel, serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		List<Integer> originalStatuses = new ArrayList<>();

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
		Assert.assertTrue(isInTrashContainer(baseModel));
	}

	@Test
	public void testTrashVersionParentBaseModelAndCustomRestore()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeFalse(this instanceof WhenIsMoveableFromTrashBaseModel);
		Assume.assumeTrue(
			this instanceof WhenIsRestorableParentBaseModelFromTrash);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsVersionableBaseModel);

		WhenIsVersionableBaseModel whenIsVersionableBaseModel =
			(WhenIsVersionableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsVersionableBaseModel.expireBaseModel(
			baseModel, serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		List<Integer> originalStatuses = new ArrayList<>();

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsRestorableParentBaseModelFromTrash
			whenIsRestorableParentBaseModelFromTrash =
				(WhenIsRestorableParentBaseModelFromTrash)this;

		whenIsRestorableParentBaseModelFromTrash.
			restoreParentBaseModelFromTrash(
				(Long)parentBaseModel.getPrimaryKeyObj());

		List<? extends WorkflowedModel> childrenWorkflowedModels =
			whenIsRestorableParentBaseModelFromTrash.
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

	@Test
	public void testTrashVersionParentBaseModelAndRestore() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsMoveableFromTrashBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsVersionableBaseModel);

		WhenIsVersionableBaseModel whenIsVersionableBaseModel =
			(WhenIsVersionableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsVersionableBaseModel.expireBaseModel(
			baseModel, serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		List<Integer> originalStatuses = new ArrayList<>();

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsMoveableFromTrashBaseModel whenIsMoveableFromTrashBaseModel =
			(WhenIsMoveableFromTrashBaseModel)this;

		BaseModel<?> newParentBaseModel =
			whenIsMoveableFromTrashBaseModel.moveBaseModelFromTrash(
				baseModel, group, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(newParentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));
	}

	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsNotInTrashContainer()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsMoveableFromTrashBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsVersionableBaseModel);

		WhenIsVersionableBaseModel whenIsVersionableBaseModel =
			(WhenIsVersionableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsVersionableBaseModel.expireBaseModel(
			baseModel, serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		List<Integer> originalStatuses = new ArrayList<>();

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsMoveableFromTrashBaseModel whenIsMoveableFromTrashBaseModel =
			(WhenIsMoveableFromTrashBaseModel)this;

		whenIsMoveableFromTrashBaseModel.moveBaseModelFromTrash(
			baseModel, group, serviceContext);

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertFalse(isInTrashContainer(baseModel));
	}

	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsVisible()
		throws Exception {

		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsMoveableFromTrashBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsVersionableBaseModel);

		WhenIsVersionableBaseModel whenIsVersionableBaseModel =
			(WhenIsVersionableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsVersionableBaseModel.expireBaseModel(
			baseModel, serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		List<Integer> originalStatuses = new ArrayList<>();

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsMoveableFromTrashBaseModel whenIsMoveableFromTrashBaseModel =
			(WhenIsMoveableFromTrashBaseModel)this;

		whenIsMoveableFromTrashBaseModel.moveBaseModelFromTrash(
			baseModel, group, serviceContext);

		WhenIsAssetableBaseModel whenIsAssetableModel =
			(WhenIsAssetableBaseModel)this;

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertTrue(
			whenIsAssetableModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	@Test
	public void testTrashVersionParentBaseModelIndexable() throws Exception {
		Assume.assumeTrue(this instanceof WhenIsIndexableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsVersionableBaseModel);

		WhenIsIndexableBaseModel whenIsIndexableBaseModel =
			(WhenIsIndexableBaseModel)this;

		int initialBaseModelsSearchCount =
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		int initialTrashEntriesSearchCount =
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext);

		WhenIsVersionableBaseModel whenIsVersionableBaseModel =
			(WhenIsVersionableBaseModel)this;

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsVersionableBaseModel.expireBaseModel(
			baseModel, serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		List<Integer> originalStatuses = new ArrayList<>();

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			whenIsIndexableBaseModel.searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId()));
		Assert.assertEquals(
			initialTrashEntriesSearchCount,
			whenIsIndexableBaseModel.searchTrashEntriesCount(
				whenIsIndexableBaseModel.getSearchKeywords(), serviceContext));
	}

	@Test
	public void testTrashVersionParentBaseModelIsNotVisible() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasParent);
		Assume.assumeTrue(this instanceof WhenIsAssetableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsUpdatableBaseModel);
		Assume.assumeTrue(this instanceof WhenIsVersionableBaseModel);

		WhenIsVersionableBaseModel whenIsVersionableBaseModel =
			(WhenIsVersionableBaseModel)this;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, serviceContext);

		baseModel = whenIsVersionableBaseModel.expireBaseModel(
			baseModel, serviceContext);

		WhenIsUpdatableBaseModel whenIsUpdatableBaseModel =
			(WhenIsUpdatableBaseModel)this;

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		List<Integer> originalStatuses = new ArrayList<>();

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = whenIsUpdatableBaseModel.updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		WhenHasParent whenHasParent = (WhenHasParent)this;

		workflowedModel = getWorkflowedModel(baseModel);

		originalStatuses.add(workflowedModel.getStatus());

		whenHasParent.moveParentBaseModelToTrash(
			(Long)parentBaseModel.getPrimaryKeyObj());

		WhenIsAssetableBaseModel whenIsAssetableModel =
			(WhenIsAssetableBaseModel)this;

		Assert.assertFalse(
			whenIsAssetableModel.isAssetEntryVisible(
				baseModel, getAssetClassPK(baseModel)));
	}

	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			BaseModel<?> baseModel = addBaseModelWithWorkflow(
				parentBaseModel, serviceContext);

			return baseModel;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	protected abstract BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception;

	protected BaseModel<?> addBaseModelWithWorkflow(
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		return addBaseModelWithWorkflow(parentBaseModel, serviceContext);
	}

	protected BaseModel<?> addDraftBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			Assume.assumeTrue(this instanceof WhenHasDraftStatus);

			WhenHasDraftStatus whenHasDraftStatus = (WhenHasDraftStatus)this;

			BaseModel<?> baseModel =
				whenHasDraftStatus.addDraftBaseModelWithWorkflow(
					parentBaseModel, serviceContext);

			return baseModel;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {
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

	protected boolean isBaseModelContainerModel() {
		if (baseModel instanceof ContainerModel) {
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

	protected boolean isValidTrashTitle(String title) {
		if (title.startsWith(_TRASH_PREFIX)) {
			return true;
		}

		return false;
	}

	protected abstract void moveBaseModelToTrash(long primaryKey)
		throws Exception;

	protected void reindex(ClassedModel classedModel) throws Exception {
		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			classedModel.getModelClassName());

		long classPK = (Long)classedModel.getPrimaryKeyObj();

		indexer.reindex(classedModel.getModelClassName(), classPK);
	}

	protected BaseModel<?> baseModel;

	@DeleteAfterTestRun
	protected Group group;

	private static final String _TRASH_PREFIX = StringPool.SLASH;

}