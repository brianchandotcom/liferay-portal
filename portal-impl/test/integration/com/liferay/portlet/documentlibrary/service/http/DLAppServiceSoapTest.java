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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.client.soap.portal.kernel.repository.model.FileEntrySoap;
import com.liferay.client.soap.portal.kernel.repository.model.FolderSoap;
import com.liferay.client.soap.portal.service.ServiceContext;
import com.liferay.client.soap.portlet.documentlibrary.service.http.DLAppServiceSoap;
import com.liferay.client.soap.portlet.documentlibrary.service.http.DLAppServiceSoapServiceLocator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.http.HttpPrincipalTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alexander Chow
 */
public class DLAppServiceSoapTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

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

		_folderSoap = getDLAppServiceSoap().addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			name, description, serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		try {
			if (_folderSoap != null) {
				getDLAppServiceSoap().deleteFolder(_folderSoap.getFolderId());
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
		long folderId = _folderSoap.getFolderId();
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
		DLAppServiceSoapServiceLocator dlAppServiceSoapServiceLocator =
			new DLAppServiceSoapServiceLocator();

		return dlAppServiceSoapServiceLocator.getPortlet_DL_DLAppService(
			HttpPrincipalTestUtil.getSoapURL(
				dlAppServiceSoapServiceLocator.
					getPortlet_DL_DLAppServiceWSDDServiceName()));
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	private FolderSoap _folderSoap;
	private Group _group;

}