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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLFileShortcutTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Override
	public void testTrashAndDeleteDraft() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashAndRestoreDraft() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashDuplicate() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Test
	@Transactional
	public void testTrashFileEntry() throws Exception {
		trashFileEntry();
	}

	@Override
	public void testTrashMyBaseModel() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashRecentBaseModel() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionBaseModelAndDelete() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionBaseModelAndRestore() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionParentBaseModel() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		DLFolder dlFolder = (DLFolder)parentBaseModel;

		String content = "Content: Enterprise. Open Source. For Life.";

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			dlFolder.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Text.txt", ContentTypes.TEXT_PLAIN, getSearchKeywords(),
			StringPool.BLANK, StringPool.BLANK, content.getBytes(),
			serviceContext);

		return DLAppServiceUtil.addFileShortcut(
			dlFolder.getGroupId(), dlFolder.getFolderId(),
			fileEntry.getFileEntryId(), serviceContext);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return DLFileShortcutLocalServiceUtil.fetchDLFileShortcut(primaryKey);
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
			Group group, ServiceContext serviceContext)
		throws Exception {

		return DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), group.getGroupId(), group.getGroupId(),
			false, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString(), StringPool.BLANK, false,
			serviceContext);
	}

	@Override
	protected Class<?> getParentBaseModelClass() {
		return DLFolder.class;
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected boolean isAssetableModel() {
		return false;
	}

	@Override
	protected boolean isIndexableBaseModel() {
		return false;
	}

	@Override
	protected BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		DLAppServiceUtil.moveFileShortcutFromTrash(
			(Long)classedModel.getPrimaryKeyObj(),
			(Long)parentBaseModel.getPrimaryKeyObj(), serviceContext);

		return parentBaseModel;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		DLAppServiceUtil.moveFileShortcutToTrash(primaryKey);
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		DLAppServiceUtil.moveFolderToTrash(primaryKey);
	}

	protected void trashFileEntry() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsCount = getNotInTrashBaseModelsCount(
			parentBaseModel);
		int initialTrashEntriesCount = getTrashEntriesCount(group.getGroupId());

		BaseModel<?> baseModel = addBaseModel(
			parentBaseModel, true, serviceContext);

		DLFileShortcut dlFileShortcut = (DLFileShortcut)baseModel;

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
			dlFileShortcut.getToFileEntryId());

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount, getTrashEntriesCount(group.getGroupId()));

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

		Assert.assertEquals(
			initialBaseModelsCount,
			getNotInTrashBaseModelsCount(parentBaseModel));
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			getTrashEntriesCount(group.getGroupId()));

		DLAppServiceUtil.restoreFileEntryFromTrash(fileEntry.getFileEntryId());

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			getNotInTrashBaseModelsCount(parentBaseModel));
	}

}