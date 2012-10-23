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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLAppServiceSoapTest {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();

		String name = "Test Folder";
		String description = "This is a test folder.";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(_group.getGroupId());

		try {
			getDLAppServiceSoap().deleteFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				name);
		}
		catch (Exception e) {
		}

		_folder = getDLAppServiceSoap().addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			name, description, serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		try {
			if (_folder != null) {
				getDLAppServiceSoap().deleteFolder(_folder.getFolderId());
			}
		}
		catch (Exception e) {
		}
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
		serviceContext.setScopeGroupId(_group.getGroupId());

		return getDLAppServiceSoap().addFileEntry(
			_group.getGroupId(), folderId, title, ContentTypes.TEXT_PLAIN,
			title, description, changeLog, bytes, serviceContext);
	}

	protected DLAppServiceSoap getDLAppServiceSoap() throws Exception {
		if (_service == null) {
			DLAppServiceSoapServiceLocator locator =
				new DLAppServiceSoapServiceLocator();

			_service = locator.getPortlet_DL_DLAppService(
				TestPropsValues.getSoapURL(
					locator.getPortlet_DL_DLAppServiceWSDDServiceName()));
		}

		return _service;
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	private static FolderSoap _folder;
	private static DLAppServiceSoap _service;

	private Group _group;

}