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

package com.liferay.document.library.webdav.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.LocalizedValue;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <p>
 * Based on Microsoft Office 2008 for OS X.
 * </p>
 *
 * @author Alexander Chow
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class WebDAVOSXTest extends BaseWebDAVTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			WebDAVEnvironmentConfigClassTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		_testFileBytes = FileUtil.getBytes(clazz, _OFFICE_TEST_DOCX);
		_testMetaBytes = FileUtil.getBytes(clazz, _OFFICE_TEST_META_DOCX);
		_testDeltaBytes = FileUtil.getBytes(clazz, _OFFICE_TEST_DELTA_DOCX);

		servicePut(_TEST_FILE_NAME, _testFileBytes, getLock(_TEST_FILE_NAME));
	}

	@Test
	public void testGetFileWithEscapedCharactersInFileName() throws Exception {
		FileEntry fileEntry = null;

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				PortalUtil.getDefaultCompanyId(), getGroupFriendlyURL());

			Folder folder = DLAppLocalServiceUtil.getFolder(
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				getFolderName());

			fileEntry = DLAppLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), group.getGroupId(),
				folder.getFolderId(), _TEST_FILE_NAME_ILLEGAL_CHARACTERS,
				ContentTypes.APPLICATION_MSWORD,
				_TEST_FILE_NAME_ILLEGAL_CHARACTERS, StringPool.BLANK,
				StringPool.BLANK, _testFileBytes,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

			assertCode(
				HttpServletResponse.SC_OK,
				serviceGet(_TEST_FILE_NAME_ILLEGAL_CHARACTERS_ESCAPED));
		}
		finally {
			if (fileEntry != null) {
				DLAppLocalServiceUtil.deleteFileEntry(
					fileEntry.getFileEntryId());
			}
		}
	}

	@Test
	public void testMSOffice1Create() throws Exception {
		Tuple tuple = null;

		for (int i = 0; i < 3; i++) {
			lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);
			unlock(_TEST_FILE_NAME);
		}

		lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_TEST_FILE_NAME, _testFileBytes, getLock(_TEST_FILE_NAME)));

		unlock(_TEST_FILE_NAME);

		for (int i = 0; i < 3; i++) {
			lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

			tuple = serviceGet(_TEST_FILE_NAME);

			assertCode(HttpServletResponse.SC_OK, tuple);
			Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));

			unlock(_TEST_FILE_NAME);
		}

		for (int i = 0; i < 2; i++) {
			assertCode(
				HttpServletResponse.SC_NOT_FOUND,
				servicePropFind(_TEST_META_NAME));
			assertCode(
				HttpServletResponse.SC_CREATED,
				servicePut(_TEST_META_NAME, _testMetaBytes));

			lock(HttpServletResponse.SC_OK, _TEST_META_NAME);

			assertCode(
				HttpServletResponse.SC_CREATED,
				servicePut(
					_TEST_META_NAME, _testMetaBytes, getLock(_TEST_META_NAME)));
			assertCode(
				WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEST_META_NAME));

			tuple = serviceGet(_TEST_META_NAME);

			assertCode(HttpServletResponse.SC_OK, tuple);
			Assert.assertArrayEquals(_testMetaBytes, getResponseBody(tuple));

			unlock(_TEST_META_NAME);

			assertCode(
				HttpServletResponse.SC_NO_CONTENT,
				serviceDelete(_TEST_META_NAME));
		}

		for (int i = 0; i < 3; i++) {
			if (i == 1) {
				lock(HttpServletResponse.SC_OK, _TEST_META_NAME);

				tuple = serviceGet(_TEST_META_NAME);

				assertCode(HttpServletResponse.SC_OK, tuple);
				Assert.assertArrayEquals(
					_testMetaBytes, getResponseBody(tuple));
			}
			else {
				lock(HttpServletResponse.SC_CREATED, _TEST_META_NAME);

				tuple = serviceGet(_TEST_META_NAME);

				assertCode(HttpServletResponse.SC_OK, tuple);

				byte[] responseBody = getResponseBody(tuple);

				Assert.assertTrue(responseBody.length == 0);
			}

			unlock(_TEST_META_NAME);

			if (i == 0) {
				assertCode(
					HttpServletResponse.SC_CREATED,
					servicePut(
						_TEST_META_NAME, _testMetaBytes,
						getLock(_TEST_META_NAME)));

				tuple = serviceGet(_TEST_META_NAME);

				assertCode(HttpServletResponse.SC_OK, tuple);
				Assert.assertArrayEquals(
					_testMetaBytes, getResponseBody(tuple));

				assertCode(
					WebDAVUtil.SC_MULTI_STATUS,
					servicePropFind(_TEST_META_NAME));
			}
		}
	}

	@Test
	public void testMSOffice2Open() throws Exception {
		Tuple tuple = null;

		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEST_FILE_NAME));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind("MCF-Test.docx"));

		lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

		tuple = serviceGet(_TEST_FILE_NAME);

		assertCode(HttpServletResponse.SC_OK, tuple);
		Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));

		unlock(_TEST_FILE_NAME);
		lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

		tuple = serviceGet(_TEST_FILE_NAME);

		assertCode(HttpServletResponse.SC_OK, tuple);
		Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));
	}

	@Test
	public void testMSOffice3Modify() throws Exception {
		Tuple tuple = null;

		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_1));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind("MCF-Word Work File D_1.tmp"));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_1));
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(_TEMP_FILE_NAME_1, _testDeltaBytes));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_META_NAME_1));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_META_NAME_1));
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(_TEMP_META_NAME_1, _testMetaBytes));

		lock(HttpServletResponse.SC_OK, _TEMP_META_NAME_1);

		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_TEMP_META_NAME_1, _testMetaBytes, getLock(_TEMP_META_NAME_1)));
		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEMP_FILE_NAME_1));

		unlock(_TEMP_META_NAME_1);
		lock(HttpServletResponse.SC_OK, _TEMP_FILE_NAME_1);
		unlock(_TEMP_FILE_NAME_1);
		lock(HttpServletResponse.SC_OK, _TEMP_FILE_NAME_1);

		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_TEMP_FILE_NAME_1, _testDeltaBytes,
				getLock(_TEMP_FILE_NAME_1)));
		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEMP_FILE_NAME_1));

		unlock(_TEST_FILE_NAME);
		lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

		tuple = serviceGet(_TEST_FILE_NAME);

		assertCode(HttpServletResponse.SC_OK, tuple);
		Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));

		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind("Backup of Test.docx"));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_2));

		unlock(_TEST_FILE_NAME);

		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_2));
		assertCode(
			HttpServletResponse.SC_CREATED,
			serviceCopyOrMove(Method.MOVE, _TEST_FILE_NAME, _TEMP_FILE_NAME_2));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_TEST_META_NAME));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_META_NAME_2));

		for (int i = 0; i < 2; i++) {
			lock(HttpServletResponse.SC_OK, _TEMP_FILE_NAME_2);

			tuple = serviceGet(_TEMP_FILE_NAME_2);

			assertCode(HttpServletResponse.SC_OK, tuple);
			Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));

			unlock(_TEMP_FILE_NAME_2);
		}

		for (int i = 0; i < 2; i++) {
			String orig = _TEMP_FILE_NAME_1;
			String dest = _TEST_FILE_NAME;

			if (i == 1) {
				orig = _TEMP_META_NAME_1;
				dest = _TEST_META_NAME;
			}

			assertCode(HttpServletResponse.SC_NOT_FOUND, servicePropFind(dest));
			assertCode(HttpServletResponse.SC_NOT_FOUND, servicePropFind(dest));

			if (i == 0) {
				assertCode(
					HttpServletResponse.SC_CREATED,
					serviceCopyOrMove(Method.MOVE, orig, dest, getLock(orig)));

				moveLock(orig, dest);
			}
		}

		for (int i = 0; i < 2; i++) {
			lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

			tuple = serviceGet(_TEST_FILE_NAME);

			assertCode(HttpServletResponse.SC_OK, tuple);
			Assert.assertArrayEquals(_testDeltaBytes, getResponseBody(tuple));

			unlock(_TEST_FILE_NAME);
		}

		lock(HttpServletResponse.SC_CREATED, _TEST_META_NAME);

		tuple = serviceGet(_TEST_META_NAME);

		assertCode(HttpServletResponse.SC_OK, tuple);

		byte[] responseBody = getResponseBody(tuple);

		Assert.assertTrue(responseBody.length == 0);

		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_TEST_META_NAME, _testMetaBytes, getLock(_TEST_META_NAME)));
		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEST_META_NAME));

		unlock(_TEST_META_NAME);
		unlock(_TEMP_FILE_NAME_2);

		assertCode(
			HttpServletResponse.SC_NO_CONTENT,
			serviceDelete(_TEMP_FILE_NAME_2));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_META_NAME_2));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_2));
	}

	@Test
	public void testPutFileWithDDMFormValuesKeepsDDMFormValues()
		throws Exception {

		FileEntry fileEntry = null;

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				PortalUtil.getDefaultCompanyId(), getGroupFriendlyURL());

			Folder folder = DLAppLocalServiceUtil.getFolder(
				TestPropsValues.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, getFolderName());

			DDMStructure ddmStructure = addDDMStructure(group);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId());

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.addFileEntryType(
					TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					new long[] {ddmStructure.getStructureId()}, serviceContext);

			serviceContext.setAttribute(
				"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());

			DDMFormValues expectedDDDMFormValues = getDDMFormValues();

			serviceContext.setAttribute(
				DDMFormValues.class.getName() + StringPool.POUND +
					ddmStructure.getStructureId(),
				expectedDDDMFormValues);

			fileEntry = DLAppLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
				folder.getFolderId(), _TEST_FILE_NAME_2,
				ContentTypes.APPLICATION_TEXT, _TEST_FILE_NAME_2,
				StringPool.BLANK, StringPool.BLANK, _testFileBytes,
				serviceContext);

			servicePut(_TEST_FILE_NAME_2, _testDeltaBytes);

			FileEntry finalFileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileEntry.getFileEntryId());

			DLFileEntry finalDLFileEntryModel =
				(DLFileEntry)finalFileEntry.getModel();

			DLFileEntryType finalDLFileEntryModelDLFileEntryType =
				finalDLFileEntryModel.getDLFileEntryType();

			List<DDMStructure> ddmStructures =
				finalDLFileEntryModelDLFileEntryType.getDDMStructures();

			for (DDMStructure structure : ddmStructures) {
				String structureKey = structure.getStructureKey();

				if (structureKey.equals("TIKARAWMETADATA")) {
					continue;
				}

				FileVersion fileVersion = finalFileEntry.getLatestFileVersion();

				DLFileEntryMetadata fileEntryMetadata =
					DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
						structure.getStructureId(),
						fileVersion.getFileVersionId());

				DDMFormValues finalDDMFormValues =
					StorageEngineManagerUtil.getDDMFormValues(
						fileEntryMetadata.getDDMStorageId());

				Assert.assertTrue(
					expectedDDDMFormValues.equals(finalDDMFormValues));
			}
		}
		finally {
			if (fileEntry != null) {
				DLAppLocalServiceUtil.deleteFileEntry(
					fileEntry.getFileEntryId());
			}
		}
	}

	@Test
	public void testPutFileWithFileEntryTypeKeepsFileEntryType()
		throws Exception {

		FileEntry fileEntry = null;

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				PortalUtil.getDefaultCompanyId(), getGroupFriendlyURL());

			Folder folder = DLAppLocalServiceUtil.getFolder(
				TestPropsValues.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, getFolderName());

			DDMStructure ddmStructure = addDDMStructure(group);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId());

			DLFileEntryType initialDLFileEntryType =
				DLFileEntryTypeLocalServiceUtil.addFileEntryType(
					TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					new long[] {ddmStructure.getStructureId()}, serviceContext);

			serviceContext.setAttribute(
				"fileEntryTypeId", initialDLFileEntryType.getFileEntryTypeId());

			DDMFormValues expectedDDDMFormValues = getDDMFormValues();

			serviceContext.setAttribute(
				DDMFormValues.class.getName() + StringPool.POUND +
					ddmStructure.getStructureId(),
				expectedDDDMFormValues);

			fileEntry = DLAppLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
				folder.getFolderId(), _TEST_FILE_NAME_2,
				ContentTypes.APPLICATION_TEXT, _TEST_FILE_NAME_2,
				StringPool.BLANK, StringPool.BLANK, _testFileBytes,
				serviceContext);

			servicePut(_TEST_FILE_NAME_2, _testDeltaBytes);

			FileEntry finalFileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileEntry.getFileEntryId());

			DLFileEntry finalDLFileEntryModel =
				(DLFileEntry)finalFileEntry.getModel();

			DLFileEntryType finalFileEntryType =
				finalDLFileEntryModel.getDLFileEntryType();

			List<DDMStructure> ddmStructures =
				finalFileEntryType.getDDMStructures();

			Assert.assertTrue(ListUtil.isNotEmpty(ddmStructures));

			Assert.assertEquals(
				initialDLFileEntryType.getFileEntryTypeId(),
				finalFileEntryType.getFileEntryTypeId());
		}
		finally {
			if (fileEntry != null) {
				DLAppLocalServiceUtil.deleteFileEntry(
					fileEntry.getFileEntryId());
			}
		}
	}

	protected static DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		Set<Locale> availableLocales = new LinkedHashSet<>();

		availableLocales.add(LocaleUtil.US);

		ddmForm.setAvailableLocales(availableLocales);

		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	protected DDMStructure addDDMStructure(Group group) throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField ddmFormField = createLocalizableTextDDMFormField("Text");

		ddmFormField.setIndexType("text");

		ddmForm.addDDMFormField(ddmFormField);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(ddmForm.getDefaultLocale(), "Test Structure");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureManagerUtil.addStructure(
			TestPropsValues.getUserId(), group.getGroupId(), null,
			PortalUtil.getClassNameId(DLFileEntryMetadata.class.getName()),
			null, nameMap, null, ddmForm,
			StorageEngineManager.STORAGE_TYPE_DEFAULT,
			DDMStructureManager.STRUCTURE_TYPE_DEFAULT, serviceContext);
	}

	protected DDMFormField createLocalizableTextDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "separator");

		ddmFormField.setDataType("string");
		ddmFormField.setLocalizable(true);
		ddmFormField.setRepeatable(false);
		ddmFormField.setRequired(false);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, name);

		return ddmFormField;
	}

	protected DDMFormValues getDDMFormValues() {
		DDMForm ddmForm = createDDMForm();

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		DDMFormField ddmFormField = createLocalizableTextDDMFormField("Text");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(ddmForm.getAvailableLocales());
		ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());

		return ddmFormValues;
	}

	protected String getLock(String fileName) {
		return _lockMap.get(fileName);
	}

	@Override
	protected String getUserAgent() {
		return _USER_AGENT;
	}

	protected void lock(int statusCode, String fileName) {
		Tuple tuple = serviceLock(fileName, null, 0);

		assertCode(statusCode, tuple);

		_lockMap.put(fileName, getLock(tuple));
	}

	protected void moveLock(String src, String dest) {
		String lock = _lockMap.remove(src);

		if (lock != null) {
			_lockMap.put(dest, lock);
		}
	}

	protected void unlock(String fileName) {
		String lock = _lockMap.remove(fileName);

		assertCode(
			HttpServletResponse.SC_NO_CONTENT, serviceUnlock(fileName, lock));
	}

	private static final String _OFFICE_TEST_DELTA_DOCX =
		"/com/liferay/document/library/dependencies/OSX_Test_Delta.docx";

	private static final String _OFFICE_TEST_DOCX =
		"/com/liferay/document/library/dependencies/OSX_Test.docx";

	private static final String _OFFICE_TEST_META_DOCX =
		"/com/liferay/document/library/dependencies/OSX_Test_Meta.docx";

	private static final String _TEMP_FILE_NAME_1 = "Word Work File D_1.tmp";

	private static final String _TEMP_FILE_NAME_2 = "Word Work File L_2.tmp";

	private static final String _TEMP_META_NAME_1 = "._Word Work File D_1.tmp";

	private static final String _TEMP_META_NAME_2 = "._Word Work File L_2.tmp";

	private static final String _TEST_FILE_NAME = "Test.docx";

	private static final String _TEST_FILE_NAME_2 = "Test2.docx";

	private static final String _TEST_FILE_NAME_ILLEGAL_CHARACTERS =
		"Test/0.docx";

	private static final String _TEST_FILE_NAME_ILLEGAL_CHARACTERS_ESCAPED =
		"Test" + PropsValues.DL_WEBDAV_SUBSTITUTION_CHAR + "0.docx";

	private static final String _TEST_META_NAME = "._Test.docx";

	private static final String _USER_AGENT =
		"WebDAVFS/1.8 (01808000) Darwin/10.3.0 (i386)";

	private static byte[] _testDeltaBytes;
	private static byte[] _testFileBytes;
	private static byte[] _testMetaBytes;

	private final Map<String, String> _lockMap = new HashMap<>();

}