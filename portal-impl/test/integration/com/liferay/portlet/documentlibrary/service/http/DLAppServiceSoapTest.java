/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.client.soap.portal.kernel.repository.model.FileEntrySoap;
import com.liferay.client.soap.portal.kernel.repository.model.FolderSoap;
import com.liferay.client.soap.portal.service.ServiceContext;
import com.liferay.client.soap.portlet.documentlibrary.service.http.DLAppServiceSoap;
import com.liferay.client.soap.portlet.documentlibrary.service.http.DLAppServiceSoapServiceLocator;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLAppServiceSoapTest {

	@BeforeClass
	public static void setUp() throws Exception {
		String name = "Test Folder";
		String description = "This is a test folder.";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		try {
			getDLAppServiceSoap().deleteFolder(
				TestPropsValues.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);
		}
		catch (Exception e) {
		}

		_folder = getDLAppServiceSoap().addFolder(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, description,
			serviceContext);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		try {
			if (_folder != null) {
				getDLAppServiceSoap().deleteFolder(_folder.getFolderId());
			}
		}
		catch (Exception e) {
		}
	}

	protected static DLAppServiceSoap getDLAppServiceSoap() throws Exception {
		DLAppServiceSoapServiceLocator locator =
			new DLAppServiceSoapServiceLocator();

		DLAppServiceSoap service = locator.getPortlet_DL_DLAppService(
			TestPropsValues.getSoapURL(
				locator.getPortlet_DL_DLAppServiceWSDDServiceName()));

		return service;
	}

	@Test
	public void testAddFileEntry() throws Exception {
		addFileEntry("Test Add.txt");
	}

	@Test
	public void testDeleteFileEntry() throws Exception {
		FileEntrySoap fileEntry = addFileEntry("Test Delete.txt");

		getDLAppServiceSoap().deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Test
	public void testGetFileEntry() throws Exception {
		FileEntrySoap fileEntry = addFileEntry("Test Get.txt");

		getDLAppServiceSoap().getFileEntryByUuidAndGroupId(
			fileEntry.getUuid(), fileEntry.getGroupId());
	}

	protected FileEntrySoap addFileEntry(String title) throws Exception {
		long folderId = _folder.getFolderId();
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;
		byte[] bytes = _CONTENT.getBytes();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return getDLAppServiceSoap().addFileEntry(
			TestPropsValues.getGroupId(), folderId, title,
			ContentTypes.TEXT_PLAIN, title, description, changeLog, bytes,
			serviceContext);
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	private static FolderSoap _folder;

}