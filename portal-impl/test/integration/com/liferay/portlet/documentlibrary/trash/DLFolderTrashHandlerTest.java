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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.util.TrashUtil;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alexander Chow
 * @author Eudaldo Alonso
 */
@Sync
public class DLFolderTrashHandlerTest extends BaseTrashHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Ignore()
	@Override
	@Test
	public void testTrashAndDeleteDraft() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashAndRestoreDraft() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashMyBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashRecentBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionBaseModelAndDelete() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionBaseModelAndRestore() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionParentBaseModel() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestore() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		DLFolder parentDLFolder = (DLFolder)parentBaseModel;

		return addBaseModelWithWorkflow(
			parentDLFolder.getGroupId(), parentDLFolder.getFolderId());
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		return addBaseModelWithWorkflow(
			serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	protected BaseModel<?> addBaseModelWithWorkflow(long groupId, long folderId)
		throws Exception {

		Folder folder = DLAppTestUtil.addFolder(
			groupId, folderId, getSearchKeywords());

		return (DLFolder)folder.getModel();
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
		return DLFolderLocalServiceUtil.getFolder(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return DLFolder.class;
	}

	@Override
	protected String getBaseModelName(ClassedModel classedModel) {
		DLFolder dlFolder = (DLFolder)classedModel;

		return dlFolder.getName();
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		DLFolder parentDLFolder = (DLFolder)parentBaseModel;

		return DLFolderLocalServiceUtil.getFoldersCount(
			parentDLFolder.getGroupId(), parentDLFolder.getFolderId(),
			WorkflowConstants.STATUS_APPROVED, false);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, long parentBaseModelId, ServiceContext serviceContext)
		throws Exception {

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), parentBaseModelId,
			RandomTestUtil.randomString(_FOLDER_NAME_MAX_LENGTH));

		return (DLFolder)folder.getModel();
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return getParentBaseModel(
			group, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return _FOLDER_NAME;
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		DLFolder dlFolder = (DLFolder)baseModel;

		String name = dlFolder.getName();

		return TrashUtil.getOriginalTitle(name);
	}

	@Override
	protected BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		DLAppServiceUtil.moveFolderFromTrash(
			(Long)classedModel.getPrimaryKeyObj(),
			(Long)parentBaseModel.getPrimaryKeyObj(), serviceContext);

		return parentBaseModel;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		DLAppServiceUtil.moveFolderToTrash(primaryKey);
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		DLAppServiceUtil.moveFolderToTrash(primaryKey);
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(primaryKey);

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			dlFolder = DLFolderLocalServiceUtil.updateStatus(
				TestPropsValues.getUserId(), primaryKey,
				WorkflowConstants.STATUS_DRAFT, null, serviceContext);
		}

		return dlFolder;
	}

	private static final String _FOLDER_NAME = RandomTestUtil.randomString(100);

	private static final int _FOLDER_NAME_MAX_LENGTH = 100;

}