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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
@Sync
public class DLFileEntryTypeServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpDDMFormXSDDeserializer();
		setUpPermissionThreadLocal();
		setUpPrincipalThreadLocal();

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
			String fileEntryTypeKey = dlFileEntryType.getFileEntryTypeKey();

			if (fileEntryTypeKey.equals(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT)) {

				_contractDLFileEntryType = dlFileEntryType;
			}
			else if (fileEntryTypeKey.equals(
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_KEY_MARKETING_BANNER)) {

				_marketingBannerDLFileEntryType = dlFileEntryType;
			}
		}
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddFileEntryType() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		byte[] testFileBytes = FileUtil.getBytes(
			getClass(), _TEST_DDM_STRUCTURE);

		DDMForm ddmForm = _ddmFormXSDDeserializer.deserialize(
			new String(testFileBytes));

		serviceContext.setAttribute(
			"ddmForm", DDMBeanTranslatorUtil.translate(ddmForm));

		User user = TestPropsValues.getUser();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(user.getLocale()));

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Test Structure", StringPool.BLANK, new long[0],
				serviceContext);

		List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
			ddmStructures = dlFileEntryType.getDDMStructures();

		Assert.assertEquals(ddmStructures.toString(), 1, ddmStructures.size());

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructures.get(0).getStructureId());

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

		DLAppLocalServiceUtil.updateFolder(
			_folder.getFolderId(), _folder.getParentFolderId(),
			_folder.getName(), _folder.getDescription(),
			_getFolderServiceContext(
				_contractDLFileEntryType, _marketingBannerDLFileEntryType));

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

		DLAppLocalServiceUtil.updateFolder(
			_subfolder.getFolderId(), _subfolder.getParentFolderId(),
			_subfolder.getName(), _subfolder.getDescription(),
			_getFolderServiceContext(_basicDocumentDLFileEntryType));

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
				DLFileEntryMetadata.class.getName(),
				new Locale[] {LocaleUtil.SPAIN}, LocaleUtil.SPAIN);

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
				DLFileEntryMetadata.class.getName(),
				new Locale[] {LocaleUtil.SPAIN}, LocaleUtil.SPAIN);

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

	protected void setUpDDMFormXSDDeserializer() {
		Registry registry = RegistryUtil.getRegistry();

		_ddmFormXSDDeserializer = registry.getService(
			DDMFormXSDDeserializer.class);
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new SimplePermissionChecker() {

				{
					init(TestPropsValues.getUser());
				}

				@Override
				public boolean hasOwnerPermission(
					long companyId, String name, String primKey, long ownerId,
					String actionId) {

					return true;
				}

			});
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	private ServiceContext _getFolderServiceContext(
			DLFileEntryType... dlFileEntryTypes)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			"defaultFileEntryTypeId", dlFileEntryTypes[0].getPrimaryKey());
		serviceContext.setAttribute(
			"dlFileEntryTypesSearchContainerPrimaryKeys",
			ArrayUtil.toString(dlFileEntryTypes, "primaryKey"));
		serviceContext.setAttribute(
			"restrictionType",
			DLFolderConstants.RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW);

		return serviceContext;
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	private static final String _TEST_DDM_STRUCTURE =
		"dependencies/ddmstructure.xml";

	private DLFileEntryType _basicDocumentDLFileEntryType;
	private DLFileEntryType _contractDLFileEntryType;
	private DDMFormXSDDeserializer _ddmFormXSDDeserializer;
	private List<DLFileEntryType> _dlFileEntryTypes;
	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

	private DLFileEntryType _marketingBannerDLFileEntryType;
	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private Folder _subfolder;

}