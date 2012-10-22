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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.events.AddDefaultDocumentLibraryStructuresAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileEntryTypeServiceTest {

	@Before
	public void setUp() throws Exception {
		SimpleAction simpleAction =
			new AddDefaultDocumentLibraryStructuresAction();

		String companyIdString = String.valueOf(TestPropsValues.getCompanyId());

		simpleAction.run(new String[] {companyIdString});

		_group = ServiceTestUtil.addGroup();

		_folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A", "",
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		_subfolder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "SubFolder AA", "",
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		_basicDocumentDLFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		_dlFileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(
			PortalUtil.getSiteAndCompanyGroupIds(_group.getGroupId()));

		for (DLFileEntryType dlFileEntryType : _dlFileEntryTypes) {
			String name = dlFileEntryType.getName();

			if (name.equals(DLFileEntryTypeConstants.NAME_CONTRACT)) {
				_contractDLFileEntryType = dlFileEntryType;
			}
			else if (name.equals(
						DLFileEntryTypeConstants.NAME_MARKETING_BANNER)) {

				_marketingBannerDLFileEntryType = dlFileEntryType;
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		DLAppLocalServiceUtil.deleteFolder(_folder.getFolderId());
	}

	@Test
	public void testCheckDefaultFileEntryTypes() throws Exception {
		Assert.assertNotNull(
			DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT + " cannot be null",
			_basicDocumentDLFileEntryType);
		Assert.assertNotNull(
			DLFileEntryTypeConstants.NAME_CONTRACT + " cannot be null",
			_contractDLFileEntryType);
		Assert.assertNotNull(
			DLFileEntryTypeConstants.NAME_MARKETING_BANNER + " cannot be null",
			_marketingBannerDLFileEntryType);
	}

	@Test
	public void testFileEntryTypeRestrictions() throws Exception {

		// Configure folder

		DLFolderLocalServiceUtil.updateFolder(
			_folder.getFolderId(), _folder.getParentFolderId(),
			_folder.getName(), _folder.getDescription(),
			_contractDLFileEntryType.getPrimaryKey(),
			ListUtil.toList(
				new long[] {
					_contractDLFileEntryType.getPrimaryKey(),
					_marketingBannerDLFileEntryType.getPrimaryKey()
				}),
			true, ServiceTestUtil.getServiceContext(_group.getGroupId()));

		// Add file to folder

		String name = "Test.txt";
		byte[] bytes = _CONTENT.getBytes();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), name,
			ContentTypes.TEXT_PLAIN, name, "", "", bytes,
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		assertFileEntryType(fileEntry, _contractDLFileEntryType);

		// Add file to subfolder

		fileEntry = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _subfolder.getFolderId(), name,
			ContentTypes.TEXT_PLAIN, name, "", "", bytes,
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		assertFileEntryType(fileEntry, _contractDLFileEntryType);

		// Configure subfolder

		DLFolderLocalServiceUtil.updateFolder(
			_subfolder.getFolderId(), _subfolder.getParentFolderId(),
			_subfolder.getName(), _subfolder.getDescription(),
			_basicDocumentDLFileEntryType.getPrimaryKey(),
			ListUtil.toList(
				new long[] {_basicDocumentDLFileEntryType.getPrimaryKey()}),
			true, ServiceTestUtil.getServiceContext(_group.getGroupId()));

		fileEntry = DLAppServiceUtil.getFileEntry(fileEntry.getFileEntryId());

		assertFileEntryType(fileEntry, _basicDocumentDLFileEntryType);
	}

	protected void assertFileEntryType(
		FileEntry fileEntry, DLFileEntryType dlFileEntryType) {

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		Assert.assertEquals(
			"File should be of file entry type " +
				dlFileEntryType.getFileEntryTypeId(),
			dlFileEntryType.getPrimaryKey(), dlFileEntry.getFileEntryTypeId());
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	private DLFileEntryType _basicDocumentDLFileEntryType;
	private DLFileEntryType _contractDLFileEntryType;
	private List<DLFileEntryType> _dlFileEntryTypes;
	private Folder _folder;
	private Group _group;
	private DLFileEntryType _marketingBannerDLFileEntryType;
	private Folder _subfolder;

}