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

package com.liferay.document.library.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashServiceUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.trash.test.util.BaseTrashHandlerTestCase;
import com.liferay.trash.test.util.DefaultWhenIsAssetable;
import com.liferay.trash.test.util.WhenHasGrandParent;
import com.liferay.trash.test.util.WhenIsAssetable;
import com.liferay.trash.test.util.WhenIsAssetableParentModel;
import com.liferay.trash.test.util.WhenIsMoveableFromTrashBaseModel;
import com.liferay.trash.test.util.WhenIsRestorableBaseModel;
import com.liferay.trash.test.util.WhenIsUpdatableBaseModel;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class DLFileShortcutTrashHandlerTest
	extends BaseTrashHandlerTestCase
	implements WhenHasGrandParent, WhenIsAssetableParentModel,
			   WhenIsMoveableFromTrashBaseModel, WhenIsRestorableBaseModel,
			   WhenIsUpdatableBaseModel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public AssetEntry fetchAssetEntry(ClassedModel classedModel)
		throws Exception {

		return _whenIsAssetable.fetchAssetEntry(classedModel);
	}

	@Override
	public String getParentBaseModelClassName() {
		Class<DLFolder> dlFolderClass = DLFolder.class;

		return dlFolderClass.getName();
	}

	@Override
	public boolean isAssetEntryVisible(ClassedModel classedModel, long classPK)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			classedModel.getModelClassName(), classPK);

		return assetEntry.isVisible();
	}

	@Override
	public BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		DLTrashServiceUtil.moveFileShortcutFromTrash(
			(Long)classedModel.getPrimaryKeyObj(),
			(Long)parentBaseModel.getPrimaryKeyObj(), serviceContext);

		return parentBaseModel;
	}

	@Override
	public void moveParentBaseModelToTrash(long primaryKey) throws Exception {
		DLTrashServiceUtil.moveFolderToTrash(primaryKey);
	}

	@Test
	public void testTrashFileEntry() throws Exception {
		trashFileEntry();
	}

	@Override
	public BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		DLFileShortcut dlFileShortcut =
			DLFileShortcutLocalServiceUtil.getFileShortcut(primaryKey);

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			DLFileShortcutLocalServiceUtil.updateStatus(
				TestPropsValues.getUserId(), primaryKey,
				WorkflowConstants.STATUS_DRAFT, serviceContext);
		}

		return dlFileShortcut;
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		DLFolder dlFolder = (DLFolder)parentBaseModel;

		return addBaseModelWithWorkflow(
			dlFolder.getGroupId(), dlFolder.getFolderId(), serviceContext);
	}

	protected BaseModel<?> addBaseModelWithWorkflow(
			long groupId, long folderId, ServiceContext serviceContext)
		throws Exception {

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), groupId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Text.txt", "Text.txt",
			true, serviceContext);

		FileShortcut fileShortcut = DLAppServiceUtil.addFileShortcut(
			groupId, folderId, fileEntry.getFileEntryId(), serviceContext);

		return (BaseModel<?>)fileShortcut.getModel();
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			ServiceContext serviceContext)
		throws Exception {

		return addBaseModelWithWorkflow(
			serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		DLFolder dlFolder = (DLFolder)parentBaseModel;

		DLFolderLocalServiceUtil.deleteFolder(dlFolder.getFolderId(), false);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return DLFileShortcutLocalServiceUtil.getDLFileShortcut(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return DLFileShortcut.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		DLFolder dlFolder = (DLFolder)parentBaseModel;

		return DLFileShortcutLocalServiceUtil.getFileShortcutsCount(
			dlFolder.getGroupId(), dlFolder.getFolderId(), true,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		Folder folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), group.getGroupId(), parentBaseModelId,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		return (BaseModel<?>)folder.getModel();
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return getParentBaseModel(
			group, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		DLTrashServiceUtil.moveFileShortcutToTrash(primaryKey);
	}

	protected void trashFileEntry() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);

		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		BaseModel<?> baseModel = addBaseModel(parentBaseModel, serviceContext);

		DLFileShortcut dlFileShortcut = (DLFileShortcut)baseModel;

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
			dlFileShortcut.getToFileEntryId());

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		DLTrashServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		DLTrashServiceUtil.restoreFileEntryFromTrash(
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));
	}

	private final WhenIsAssetable _whenIsAssetable =
		new DefaultWhenIsAssetable();

}