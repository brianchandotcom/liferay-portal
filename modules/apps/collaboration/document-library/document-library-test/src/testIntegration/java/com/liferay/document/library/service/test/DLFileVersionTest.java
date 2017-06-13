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
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.LocalizedValue;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
@Sync
public class DLFileVersionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		setUpParentFolder();
		setUpResourcePermission();

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					_group.getGroupId()));

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			String fileEntryTypeKey = dlFileEntryType.getFileEntryTypeKey();

			if (fileEntryTypeKey.equals(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT)) {

				_contractDLFileEntryTypeId =
					dlFileEntryType.getFileEntryTypeId();
			}
		}

		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.addDefaultTable(
				PortalUtil.getDefaultCompanyId(), DLFileEntry.class.getName());

		ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), _EXPANDO_ATTRIBUTE_NAME,
			ExpandoColumnConstants.STRING, StringPool.BLANK);

		_serviceContext = getServiceContext();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _parentFolder.getFolderId(), _SOURCE_FILE_NAME,
			ContentTypes.APPLICATION_OCTET_STREAM, _TITLE, StringPool.BLANK,
			StringPool.BLANK, _DATA_VERSION_1, _serviceContext);

		_fileVersion = DLFileVersionLocalServiceUtil.getFileVersion(
			fileEntry.getFileEntryId(), DLFileEntryConstants.VERSION_DEFAULT);
	}

	@After
	public void tearDown() throws Exception {
		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.getDefaultTable(
				PortalUtil.getDefaultCompanyId(), DLFileEntry.class.getName());

		ExpandoTableLocalServiceUtil.deleteTable(expandoTable);

		tearDownResourcePermission();
	}

	@Test
	public void testRevertVersion() throws Exception {
		DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _UPDATE_VALUE,
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		DLAppServiceUtil.revertFileEntry(
			fileEntry.getFileEntryId(), DLFileEntryConstants.VERSION_DEFAULT,
			_serviceContext);

		fileEntry = DLAppServiceUtil.getFileEntry(fileEntry.getFileEntryId());

		Assert.assertEquals("2.0", fileEntry.getVersion());
	}

	@Test
	public void testUpdateChecksum() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_2, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateDescription() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(), _UPDATE_VALUE,
			_fileVersion.getChangeLog(), false, _DATA_VERSION_1,
			_serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateExpando() throws Exception {
		updateServiceContext(
			_UPDATE_VALUE, _contractDLFileEntryTypeId, StringPool.BLANK);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateFileEntryType() throws Exception {
		updateServiceContext(
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			StringPool.BLANK);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateMetadata() throws Exception {
		updateServiceContext(
			StringPool.BLANK, _contractDLFileEntryTypeId, _UPDATE_VALUE);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateNothing() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateSize() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _fileVersion.getTitle(),
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_3, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateTitle() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileVersion.getFileEntryId(), _SOURCE_FILE_NAME,
			_fileVersion.getMimeType(), _UPDATE_VALUE,
			_fileVersion.getDescription(), _fileVersion.getChangeLog(), false,
			_DATA_VERSION_1, _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	protected DDMFormFieldValue createDDMFormFieldValue(String name) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(name);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, StringPool.BLANK);

		ddmFormFieldValue.setValue(localizedValue);

		return ddmFormFieldValue;
	}

	protected DDMFormValues createDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(LocaleUtil.US);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	protected ServiceContext getServiceContext() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _contractDLFileEntryTypeId);

		Map<String, Serializable> expandoBridgeAttributes =
			serviceContext.getExpandoBridgeAttributes();

		expandoBridgeAttributes.put(_EXPANDO_ATTRIBUTE_NAME, StringPool.BLANK);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				_contractDLFileEntryTypeId);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			DDMFormValues ddmFormValues = createDDMFormValues(
				ddmStructure.getDDMForm());

			for (String fieldName : ddmStructure.getFieldNames()) {
				DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
					fieldName);

				ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
			}

			serviceContext.setAttribute(
				DDMFormValues.class.getName() + StringPool.POUND +
					ddmStructure.getStructureId(),
				ddmFormValues);
		}

		return serviceContext;
	}

	protected void setUpParentFolder() throws Exception {
		try {
			DLAppServiceUtil.deleteFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"Test Folder");
		}
		catch (NoSuchFolderException nsfe) {
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_parentFolder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder", RandomTestUtil.randomString(), serviceContext);
	}

	protected void setUpResourcePermission() throws Exception {
		RoleTestUtil.addResourcePermission(
			RoleConstants.GUEST, "com.liferay.document.library",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	protected void tearDownResourcePermission() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, "com.liferay.document.library",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	protected void updateServiceContext(
			String expando, long fileEntryTypeId, String metadata)
		throws PortalException {

		Map<String, Serializable> expandoBridgeAttributes =
			_serviceContext.getExpandoBridgeAttributes();

		expandoBridgeAttributes.put(_EXPANDO_ATTRIBUTE_NAME, expando);

		_serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		_serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);

		if (fileEntryTypeId <= 0) {
			return;
		}

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			DDMFormValues ddmFormValues =
				(DDMFormValues)_serviceContext.getAttribute(
					DDMFormValues.class.getName() + StringPool.POUND +
						ddmStructure.getStructureId());

			for (DDMFormFieldValue ddmFormFieldValue :
					ddmFormValues.getDDMFormFieldValues()) {

				String fieldType = ddmStructure.getFieldType(
					ddmFormFieldValue.getName());

				if (fieldType.equals("text")) {
					LocalizedValue localizedValue = new LocalizedValue(
						LocaleUtil.US);

					localizedValue.addString(LocaleUtil.US, metadata);

					ddmFormFieldValue.setValue(localizedValue);
				}
			}

			_serviceContext.setAttribute(
				DDMFormValues.class.getName() + StringPool.POUND +
					ddmStructure.getStructureId(),
				ddmFormValues);
		}
	}

	private static final int _DATA_SIZE_1 = 512;

	private static final int _DATA_SIZE_2 = 1024;

	private static final byte[] _DATA_VERSION_1 = new byte[_DATA_SIZE_1];

	private static final byte[] _DATA_VERSION_2 = new byte[_DATA_SIZE_1];

	private static final byte[] _DATA_VERSION_3 = new byte[_DATA_SIZE_2];

	private static final String _EXPANDO_ATTRIBUTE_NAME = "Expando";

	private static final String _SOURCE_FILE_NAME = "SourceFileName.txt";

	private static final String _TITLE = "Title";

	private static final String _UPDATE_VALUE = "Update Value";

	static {
		for (int i = 0; i < _DATA_SIZE_1; i++) {
			_DATA_VERSION_1[i] = (byte)i;
			_DATA_VERSION_2[i] = (byte)(i + 1);
		}

		for (int i = 0; i < _DATA_SIZE_2; i++) {
			_DATA_VERSION_3[i] = (byte)i;
		}
	}

	private long _contractDLFileEntryTypeId;
	private DLFileVersion _fileVersion;

	@DeleteAfterTestRun
	private Group _group;

	private Folder _parentFolder;
	private ServiceContext _serviceContext;

}