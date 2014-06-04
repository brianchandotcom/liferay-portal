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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.events.AddDefaultDocumentLibraryStructuresAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;

import java.util.List;
import java.util.Locale;

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

		_group = GroupTestUtil.addGroup();

		_folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A", "",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_subfolder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "SubFolder AA", "",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_basicDocumentDLFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		_dlFileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(_group.getGroupId()));

		for (DLFileEntryType dlFileEntryType : _dlFileEntryTypes) {
			String name = dlFileEntryType.getName(LocaleUtil.getSiteDefault());

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
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testAddFileEntryType() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		byte[] testFileBytes = FileUtil.getBytes(
			getClass(), _TEST_DDM_STRUCTURE);

		serviceContext.setAttribute("xsd", new String(testFileBytes));

		User user = TestPropsValues.getUser();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(user.getLocale()));

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Test Structure", StringPool.BLANK, new long[0],
				serviceContext);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		Assert.assertEquals(1, ddmStructures.size());

		DDMStructure ddmStructure = ddmStructures.get(0);

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(
			ddmStructure.getAvailableLanguageIds());

		boolean hasDefaultLocale = ArrayUtil.contains(
			availableLocales, LocaleUtil.getSiteDefault());

		Assert.assertTrue(hasDefaultLocale);

		boolean hasHungarianLocale = ArrayUtil.contains(
			availableLocales, LocaleUtil.HUNGARY);

		Assert.assertTrue(hasHungarianLocale);

		boolean hasUserLocale = ArrayUtil.contains(
			availableLocales, user.getLocale());

		Assert.assertTrue(hasUserLocale);

		DLFileEntryTypeLocalServiceUtil.deleteDLFileEntryType(dlFileEntryType);
	}

	@Test
	public void testCheckDefaultFileEntryTypes() throws Exception {
		Assert.assertNotNull(
			DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT + " is null",
			_basicDocumentDLFileEntryType);
		Assert.assertNotNull(
			DLFileEntryTypeConstants.NAME_CONTRACT + " is null",
			_contractDLFileEntryType);
		Assert.assertNotNull(
			DLFileEntryTypeConstants.NAME_MARKETING_BANNER + " is null",
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
			true,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		// Add file to folder

		String name = "Test.txt";
		byte[] bytes = _CONTENT.getBytes();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), name,
			ContentTypes.TEXT_PLAIN, name, "", "", bytes,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		assertFileEntryType(fileEntry, _contractDLFileEntryType);

		// Add file to subfolder

		fileEntry = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _subfolder.getFolderId(), name,
			ContentTypes.TEXT_PLAIN, name, "", "", bytes,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		assertFileEntryType(fileEntry, _contractDLFileEntryType);

		// Configure subfolder

		DLFolderLocalServiceUtil.updateFolder(
			_subfolder.getFolderId(), _subfolder.getParentFolderId(),
			_subfolder.getName(), _subfolder.getDescription(),
			_basicDocumentDLFileEntryType.getPrimaryKey(),
			ListUtil.toList(
				new long[] {_basicDocumentDLFileEntryType.getPrimaryKey()}),
			true,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		fileEntry = DLAppServiceUtil.getFileEntry(fileEntry.getFileEntryId());

		assertFileEntryType(fileEntry, _basicDocumentDLFileEntryType);
	}

	@Test
	public void testLocalizedSiteAddFileEntryType() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

			String name = RandomTestUtil.randomString();
			String description = RandomTestUtil.randomString();
			DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
				DLFileEntry.class.getName(), new Locale[] {LocaleUtil.SPAIN},
				LocaleUtil.SPAIN);

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.addFileEntryType(
					TestPropsValues.getUserId(), group.getGroupId(), name,
					description, new long[] {ddmStructure.getStructureId()},
					serviceContext);

			Assert.assertEquals(
				name, dlFileEntryType.getName(LocaleUtil.US, true));
			Assert.assertEquals(
				description,
				dlFileEntryType.getDescription(LocaleUtil.US, true));
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(locale);
		}
	}

	@Test
	public void testLocalizedSiteUpdateFileEntryType() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

			String name = RandomTestUtil.randomString();
			String description = RandomTestUtil.randomString();
			DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
				DLFileEntry.class.getName(), new Locale[] {LocaleUtil.SPAIN},
				LocaleUtil.SPAIN);

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.addFileEntryType(
					TestPropsValues.getUserId(), group.getGroupId(), name,
					description, new long[] {ddmStructure.getStructureId()},
					serviceContext);

			name = RandomTestUtil.randomString();
			description = RandomTestUtil.randomString();

			DLFileEntryTypeLocalServiceUtil.updateFileEntryType(
				TestPropsValues.getUserId(),
				dlFileEntryType.getFileEntryTypeId(), name, description,
				new long[] {ddmStructure.getStructureId()}, serviceContext);

			dlFileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				dlFileEntryType.getFileEntryTypeId());

			Assert.assertEquals(
				name, dlFileEntryType.getName(LocaleUtil.US, true));
			Assert.assertEquals(
				description,
				dlFileEntryType.getDescription(LocaleUtil.US, true));
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(locale);
		}
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

	private static final String _TEST_DDM_STRUCTURE =
		"dependencies/ddmstructure.xml";

	private DLFileEntryType _basicDocumentDLFileEntryType;
	private DLFileEntryType _contractDLFileEntryType;
	private List<DLFileEntryType> _dlFileEntryTypes;
	private Folder _folder;
	private Group _group;
	private DLFileEntryType _marketingBannerDLFileEntryType;
	private Folder _subfolder;

}