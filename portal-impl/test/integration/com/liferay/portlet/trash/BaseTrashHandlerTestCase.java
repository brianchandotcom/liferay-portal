/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.WorkflowedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author Eudaldo Alonso
 * @author Manuel de la Peña
 */
public abstract class BaseTrashHandlerTestCase {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	@Transactional
	public void testTrashAndDeleteApproved() throws Exception {
		trashBaseModel(true, true);
	}

	@Test
	@Transactional
	public void testTrashAndDeleteDraft() throws Exception {
		trashBaseModel(false, true);
	}

	@Test
	@Transactional
	public void testTrashAndRestoreApproved() throws Exception {
		trashBaseModel(true, false);
	}

	@Test
	@Transactional
	public void testTrashAndRestoreDraft() throws Exception {
		trashBaseModel(false, false);
	}

	@Test
	@Transactional
	public void testTrashDuplicate() throws Exception {
		trashDuplicateBaseModel();
	}

	@Test
	@Transactional
	public void testTrashMoveBaseModel() throws Exception {
		trashMoveBaseModel();
	}

	@Test
	@Transactional
	public void testTrashMyBaseModel() throws Exception {
		trashMyBaseModel();
	}

	@Test
	@Transactional
	public void testTrashParentAndDeleteParent() throws Exception {
		trashParentBaseModel(true, false);
	}

	@Test
	@Transactional
	public void testTrashParentAndDeleteTrashEntries() throws Exception {
		trashParentBaseModel(false, true);
	}

	@Test
	@Transactional
	public void testTrashParentAndRestoreModel() throws Exception {
		trashParentBaseModel(false, false);
	}

	@Test
	@Transactional
	public void testTrashRecentBaseModel() throws Exception {
		trashRecentBaseModel();
	}

	@Test
	@Transactional
	public void testTrashVersionBaseModelAndDelete() throws Exception {
		trashVersionBaseModel(true);
	}

	@Test
	@Transactional
	public void testTrashVersionBaseModelAndRestore() throws Exception {
		trashVersionBaseModel(false);
	}

	@Test
	@Transactional
	public void testTrashVersionParentBaseModel() throws Exception {
		trashVersionParentBaseModel();
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

	protected int getMineBaseModelsCount(long groupId, long userId)
		throws Exception {

		return 0;
	}

	protected abstract int getNotInTrashBaseModelsCount(
			BaseModel<?> parentBaseModel)
		throws Exception;

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

	protected int getRecentBaseModelsCount(long groupId) throws Exception {
		return 0;
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

	protected boolean isAssetableModel() {
		return true;
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

	protected boolean isIndexableBaseModel() {
		return true;
	}

	protected boolean isInTrashContainer(ClassedModel classedModel)
		throws Exception {

		Class<?> clazz = classedModel.getModelClass();

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			clazz.getName());

		return trashHandler.isInTrashContainer(getAssetClassPK(baseModel));
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

	protected int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(clazz);

		SearchContext searchContext = ServiceTestUtil.getSearchContext();

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

	protected void trashBaseModel(boolean approved, boolean delete)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialBaseModelsSearchCount = 0;
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());
		int initialTrashEntriesSearchCount = 0;

		if (isIndexableBaseModel()) {
			initialBaseModelsSearchCount = searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());
			initialTrashEntriesSearchCount = searchTrashEntriesCount(
				getSearchKeywords(), serviceContext);
		}

		baseModel = addBaseModel(parentBaseModel, approved, serviceContext);

		String uniqueTitle = getUniqueTitle(baseModel);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));

		if (isIndexableBaseModel()) {
			if (approved) {
				Assert.assertEquals(
					initialBaseModelsSearchCount + 1,
					searchBaseModelsCount(
						getBaseModelClass(), group.getGroupId()));
			}
			else {
				Assert.assertEquals(
					initialBaseModelsSearchCount,
					searchBaseModelsCount(
						getBaseModelClass(), group.getGroupId()));
			}

			Assert.assertEquals(
				initialTrashEntriesSearchCount,
				searchTrashEntriesCount(getSearchKeywords(), serviceContext));
		}

		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		WorkflowedModel workflowedModel = getWorkflowedModel(baseModel);

		int oldStatus = workflowedModel.getStatus();

		if (isAssetableModel()) {
			Assert.assertEquals(approved, isAssetEntryVisible(baseModel));
		}

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		if (isIndexableBaseModel()) {
			Assert.assertEquals(
				initialBaseModelsSearchCount,
				searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesSearchCount + 1,
				searchTrashEntriesCount(getSearchKeywords(), serviceContext));
		}

		if (uniqueTitle != null) {
			Assert.assertEquals(uniqueTitle, getUniqueTitle(baseModel));
		}

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
			getBaseModelClassName(), getTrashEntryClassPK(baseModel));

		Assert.assertEquals(
			getTrashEntryClassPK(baseModel),
			GetterUtil.getLong(trashEntry.getClassPK()));

		workflowedModel = getWorkflowedModel(
			getBaseModel((Long)baseModel.getPrimaryKeyObj()));

		Assert.assertEquals(
			WorkflowConstants.STATUS_IN_TRASH, workflowedModel.getStatus());

		if (isAssetableModel()) {
			Assert.assertFalse(isAssetEntryVisible(baseModel));
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		if (delete) {
			trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

			Assert.assertEquals(
				initialBaseModelsCount,
				getNotInTrashBaseModelsCount(parentBaseModel));

			if (isIndexableBaseModel()) {
				Assert.assertEquals(
					initialBaseModelsSearchCount,
					searchBaseModelsCount(
						getBaseModelClass(), group.getGroupId()));
				Assert.assertEquals(
					initialTrashEntriesSearchCount,
					searchTrashEntriesCount(
						getSearchKeywords(), serviceContext));
			}

			if (isAssetableModel()) {
				Assert.assertNull(fetchAssetEntry(baseModel));
			}
		}
		else {
			trashHandler.restoreTrashEntry(
				TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

			Assert.assertEquals(
				initialBaseModelsCount + 1,
				getNotInTrashBaseModelsCount(parentBaseModel));

			if (isIndexableBaseModel()) {
				if (approved) {
					Assert.assertEquals(
						initialBaseModelsSearchCount + 1,
						searchBaseModelsCount(
							getBaseModelClass(), group.getGroupId()));
				}
				else {
					Assert.assertEquals(
						initialBaseModelsSearchCount,
						searchBaseModelsCount(
							getBaseModelClass(), group.getGroupId()));
				}

				Assert.assertEquals(
					initialTrashEntriesSearchCount,
					searchTrashEntriesCount(
						getSearchKeywords(), serviceContext));
			}

			baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

			workflowedModel = getWorkflowedModel(baseModel);

			Assert.assertEquals(oldStatus, workflowedModel.getStatus());

			if (isAssetableModel()) {
				Assert.assertEquals(approved, isAssetEntryVisible(baseModel));
			}

			if (uniqueTitle != null) {
				Assert.assertEquals(uniqueTitle, getUniqueTitle(baseModel));
			}
		}
	}

	protected void trashDuplicateBaseModel() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		baseModel = getBaseModel((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

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

	protected void trashMoveBaseModel() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		int initialBaseModelsSearchCount = 0;

		if (isIndexableBaseModel()) {
			initialBaseModelsSearchCount = searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());
		}

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertTrue(isInTrashContainer(baseModel));

		if (isIndexableBaseModel()) {
			Assert.assertEquals(
				initialBaseModelsSearchCount,
				searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
		}

		if (isAssetableModel()) {
			Assert.assertFalse(isAssetEntryVisible(baseModel));
		}

		if (!isBaseModelMoveableFromTrash()) {
			return;
		}

		moveBaseModelFromTrash(baseModel, group, serviceContext);

		if (isIndexableBaseModel()) {
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

		if (isAssetableModel()) {
			Assert.assertTrue(isAssetEntryVisible(baseModel));
		}
	}

	protected void trashMyBaseModel() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getMineBaseModelsCount(
			group.getGroupId(), TestPropsValues.getUserId());

		addBaseModel(parentBaseModel, true, serviceContext);
		addBaseModel(parentBaseModel, true, serviceContext);
		addBaseModel(parentBaseModel, true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 3,
			getMineBaseModelsCount(
				group.getGroupId(), TestPropsValues.getUserId()));

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getMineBaseModelsCount(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	protected void trashParentBaseModel(
			boolean delete, boolean deleteTrashEntries)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		Assert.assertFalse(isInTrashContainer(baseModel));

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialTrashEntriesCount + 2,
			getTrashEntriesCount(group.getGroupId()));

		Assert.assertTrue(isInTrashContainer(baseModel));

		if (isAssetableModel()) {
			Assert.assertFalse(isAssetEntryVisible(baseModel));
		}

		if (deleteTrashEntries) {
			TrashEntryServiceUtil.deleteEntries(group.getGroupId());

			Assert.assertEquals(0, getTrashEntriesCount(group.getGroupId()));
		}
		else if (isBaseModelMoveableFromTrash()) {
			if (delete) {
				TrashHandler parentTrashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(
						getParentBaseModelClassName());

				parentTrashHandler.deleteTrashEntry(
					(Long)parentBaseModel.getPrimaryKeyObj());

				Assert.assertEquals(
					initialBaseModelsCount,
					getNotInTrashBaseModelsCount(parentBaseModel));
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
			else {
				BaseModel<?> newParentBaseModel = moveBaseModelFromTrash(
					baseModel, group, serviceContext);

				Assert.assertEquals(
					initialBaseModelsCount + 1,
					getNotInTrashBaseModelsCount(newParentBaseModel));
				Assert.assertEquals(
					initialTrashEntriesCount + 1,
					getTrashEntriesCount(group.getGroupId()));

				if (isAssetableModel()) {
					Assert.assertTrue(isAssetEntryVisible(baseModel));
				}
			}
		}
	}

	protected void trashRecentBaseModel() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getRecentBaseModelsCount(
			group.getGroupId());

		addBaseModel(parentBaseModel, true, serviceContext);
		addBaseModel(parentBaseModel, true, serviceContext);
		addBaseModel(parentBaseModel, true, serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 3,
			getRecentBaseModelsCount(group.getGroupId()));

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getRecentBaseModelsCount(group.getGroupId()));
	}

	protected void trashVersionBaseModel(boolean delete) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialBaseModelsSearchCount = 0;
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());
		int initialTrashEntriesSearchCount = 0;

		if (isIndexableBaseModel()) {
			initialBaseModelsSearchCount = searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());
			initialTrashEntriesSearchCount = searchTrashEntriesCount(
				getSearchKeywords(), serviceContext);
		}

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		if (isIndexableBaseModel()) {
			Assert.assertEquals(
				initialBaseModelsSearchCount + 1,
				searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesSearchCount,
				searchTrashEntriesCount(getSearchKeywords(), serviceContext));
		}

		if (isAssetableModel()) {
			Assert.assertTrue(isAssetEntryVisible(baseModel));
		}

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		if (isIndexableBaseModel()) {
			Assert.assertEquals(
				initialBaseModelsSearchCount,
				searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesSearchCount + 1,
				searchTrashEntriesCount(getSearchKeywords(), serviceContext));
		}

		if (isAssetableModel()) {
			Assert.assertFalse(isAssetEntryVisible(baseModel));
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getBaseModelClassName());

		if (delete) {
			trashHandler.deleteTrashEntry(getTrashEntryClassPK(baseModel));

			Assert.assertEquals(
				initialBaseModelsCount,
				getNotInTrashBaseModelsCount(parentBaseModel));
			Assert.assertEquals(
				initialTrashEntriesCount,
				getTrashEntriesCount(group.getGroupId()));

			if (isIndexableBaseModel()) {
				Assert.assertEquals(
					initialBaseModelsSearchCount,
					searchBaseModelsCount(
						getBaseModelClass(), group.getGroupId()));
				Assert.assertEquals(
					initialTrashEntriesSearchCount,
					searchTrashEntriesCount(
						getSearchKeywords(), serviceContext));
			}

			if (isAssetableModel()) {
				Assert.assertNull(fetchAssetEntry(baseModel));
			}
		}
		else {
			trashHandler.restoreTrashEntry(
				TestPropsValues.getUserId(), getTrashEntryClassPK(baseModel));

			Assert.assertEquals(
				initialBaseModelsCount + 1,
				getNotInTrashBaseModelsCount(parentBaseModel));
			Assert.assertEquals(
				initialTrashEntriesCount,
				getTrashEntriesCount(group.getGroupId()));

			if (isIndexableBaseModel()) {
				Assert.assertEquals(
					initialBaseModelsSearchCount + 1,
					searchBaseModelsCount(
						getBaseModelClass(), group.getGroupId()));
				Assert.assertEquals(
					initialTrashEntriesSearchCount,
					searchTrashEntriesCount(
						getSearchKeywords(), serviceContext));
			}

			if (isAssetableModel()) {
				Assert.assertTrue(isAssetEntryVisible(baseModel));
			}
		}
	}

	protected void trashVersionParentBaseModel() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialBaseModelsSearchCount = 0;
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());
		int initialTrashEntriesSearchCount = 0;

		if (isIndexableBaseModel()) {
			initialBaseModelsSearchCount = searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId());
			initialTrashEntriesSearchCount = searchTrashEntriesCount(
				getSearchKeywords(), serviceContext);
		}

		baseModel = addBaseModel(parentBaseModel, true, serviceContext);

		baseModel = updateBaseModel(
			(Long)baseModel.getPrimaryKeyObj(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		if (isIndexableBaseModel()) {
			Assert.assertEquals(
				initialBaseModelsSearchCount + 1,
				searchBaseModelsCount(getBaseModelClass(), group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesSearchCount,
				searchTrashEntriesCount(getSearchKeywords(), serviceContext));
		}

		if (isAssetableModel()) {
			Assert.assertTrue(isAssetEntryVisible(baseModel));
		}

		moveParentBaseModelToTrash((Long)parentBaseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		Assert.assertTrue(isInTrashContainer(baseModel));

		if (isAssetableModel()) {
			Assert.assertFalse(isAssetEntryVisible(baseModel));
		}

		if (isBaseModelMoveableFromTrash()) {
			BaseModel<?> newParentBaseModel = moveBaseModelFromTrash(
				baseModel, group, serviceContext);

			Assert.assertEquals(
				initialBaseModelsCount + 1,
				getNotInTrashBaseModelsCount(newParentBaseModel));
			Assert.assertEquals(
				initialTrashEntriesCount + 1,
				getTrashEntriesCount(group.getGroupId()));
			Assert.assertFalse(isInTrashContainer(baseModel));

			if (isAssetableModel()) {
				Assert.assertTrue(isAssetEntryVisible(baseModel));
			}
		}
	}

	protected BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		return getBaseModel(primaryKey);
	}

	protected BaseModel<?> baseModel;
	protected Group group;

}