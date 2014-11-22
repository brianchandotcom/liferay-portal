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

package com.liferay.portlet.documentlibrary.search;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.BaseSearchTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLFolderSearchTest extends BaseSearchTestCase {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Ignore()
	@Override
	@Test
	public void testLocalizedSearch() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchAttachments() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchByDDMStructureField() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchComments() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchExpireAllVersions() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchExpireLatestVersion() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchMyEntries() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchRecentEntries() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchStatus() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchVersions() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchWithinDDMStructure() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		DLFolder parentDLFolder = (DLFolder)parentBaseModel;

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (parentDLFolder != null) {
			folderId = parentDLFolder.getFolderId();
		}

		Folder folder = DLAppTestUtil.addFolder(
			folderId, keywords, serviceContext);

		return (DLFolder)folder.getModel();
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		DLAppLocalServiceUtil.deleteFolder(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return DLFolder.class;
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		Folder folder = DLAppTestUtil.addFolder(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			RandomTestUtil.randomString(_FOLDER_NAME_MAX_LENGTH),
			serviceContext);

		return (DLFolder)folder.getModel();
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		Folder folder = DLAppTestUtil.addFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(_FOLDER_NAME_MAX_LENGTH),
			serviceContext);

		return (DLFolder)folder.getModel();
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		DLFolder dlFolder = (DLFolder)baseModel;

		dlFolder.setName(keywords);

		return DLFolderLocalServiceUtil.updateDLFolder(dlFolder);
	}

	private static final int _FOLDER_NAME_MAX_LENGTH = 100;

}