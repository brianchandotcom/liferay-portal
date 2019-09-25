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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.InvalidFileVersionException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashLocalServiceUtil;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.LocalizedValue;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.lock.NoSuchLockException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class DLFileEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddsFileEntryWithNoFileEntryType() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, -1,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			serviceContext);

		long defaultFileEntryTypeId =
			DLFileEntryTypeLocalServiceUtil.getDefaultFileEntryTypeId(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			defaultFileEntryTypeId, dlFileEntry.getFileEntryTypeId());
	}

	@Test
	public void testCopyFileEntry() throws Exception {
		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.addDefaultTable(
				PortalUtil.getDefaultCompanyId(), DLFileEntry.class.getName());

		ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), "ExpandoAttributeName",
			ExpandoColumnConstants.STRING, StringPool.BLANK);

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId());

			Folder folder = DLAppServiceUtil.addFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			long fileEntryTypeId = populateServiceContextFileEntryType(
				serviceContext);

			Map<String, Serializable> expandoBridgeAttributes = new HashMap<>();

			expandoBridgeAttributes.put(
				"ExpandoAttributeName", "ExpandoAttributeValue");

			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				folder.getFolderId(), RandomTestUtil.randomString(),
				ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY,
				serviceContext);

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

			Folder destinationFolder = DLAppServiceUtil.addFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLFileEntry copyDLFileEntry =
				DLFileEntryLocalServiceUtil.copyFileEntry(
					TestPropsValues.getUserId(), _group.getGroupId(),
					_group.getGroupId(), fileEntry.getFileEntryId(),
					destinationFolder.getFolderId(), serviceContext);

			ExpandoBridge expandoBridge = copyDLFileEntry.getExpandoBridge();

			String attributeValue = GetterUtil.getString(
				expandoBridge.getAttribute("ExpandoAttributeName"));

			Assert.assertEquals("ExpandoAttributeValue", attributeValue);

			Assert.assertEquals(
				fileEntryTypeId, copyDLFileEntry.getFileEntryTypeId());

			DLFileVersion copyDLFileVersion = copyDLFileEntry.getFileVersion();

			List<DDMStructure> copyDDMStructures =
				copyDLFileVersion.getDDMStructures();

			DDMStructure copyDDMStructure = copyDDMStructures.get(0);

			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
					copyDDMStructure.getStructureId(),
					copyDLFileVersion.getFileVersionId());

			DDMFormValues copyDDMFormValues =
				StorageEngineManagerUtil.getDDMFormValues(
					dlFileEntryMetadata.getDDMStorageId());

			List<DDMFormFieldValue> ddmFormFieldValues =
				copyDDMFormValues.getDDMFormFieldValues();

			DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

			Value value = ddmFormFieldValue.getValue();

			Assert.assertEquals("Text1", ddmFormFieldValue.getName());

			Assert.assertEquals("Text 1 Value", value.getString(LocaleUtil.US));
		}
		finally {
			ExpandoTableLocalServiceUtil.deleteTable(expandoTable);
		}
	}

	@Test
	public void testDeleteFileEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		for (int i = 0; i < 20; i++) {
			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				folder.getFolderId(), RandomTestUtil.randomString(),
				ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY,
				serviceContext);

			LocalRepository localRepository =
				RepositoryProviderUtil.getFileEntryLocalRepository(
					fileEntry.getFileEntryId());

			DLTrashLocalServiceUtil.moveFileEntryToTrash(
				TestPropsValues.getUserId(), localRepository.getRepositoryId(),
				fileEntry.getFileEntryId());
		}

		for (int i = 0; i < IntervalActionProcessor.INTERVAL_DEFAULT; i++) {
			DLAppLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				folder.getFolderId(), RandomTestUtil.randomString(),
				ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY,
				serviceContext);
		}

		DLFileEntryLocalServiceUtil.deleteFileEntries(
			_group.getGroupId(), folder.getFolderId(), false);

		int fileEntriesCount = DLFileEntryLocalServiceUtil.getFileEntriesCount(
			_group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(20, fileEntriesCount);
	}

	@Test(expected = InvalidFileVersionException.class)
	public void testDoesNotDeleteUnapprovedVersion() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		DLFileEntryLocalServiceUtil.deleteFileVersion(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileVersion.getVersion());
	}

	@Test
	public void testDuplicateFileIsIgnored() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());
		Map<String, DDMFormValues> ddmFormValuesMap = Collections.emptyMap();
		InputStream inputStream = new ByteArrayInputStream(new byte[0]);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntry dlFileEntry = addAndApproveFileEntry(
			dlFolder, ddmFormValuesMap, inputStream, serviceContext);

		DLStoreUtil.updateFile(
			dlFileEntry.getCompanyId(), dlFileEntry.getRepositoryId(),
			dlFileEntry.getName(), dlFileEntry.getExtension(), false, "2.0",
			StringUtil.randomString(), inputStream);

		dlFileEntry = updateAndApproveDLFileEntry(
			dlFileEntry, inputStream, ddmFormValuesMap, serviceContext);

		dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			dlFileEntry.getFileEntryId());

		Assert.assertEquals("2.0", dlFileEntry.getVersion());
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testDuplicateTitleFileEntry() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());
		String title = StringUtil.randomString();
		Map<String, DDMFormValues> ddmFormValuesMap = Collections.emptyMap();
		InputStream inputStream = new ByteArrayInputStream(new byte[0]);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getGroupId(),
			dlFolder.getRepositoryId(), dlFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, title,
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0, serviceContext);

		DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getGroupId(),
			dlFolder.getRepositoryId(), dlFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, title,
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0, serviceContext);
	}

	@Test
	public void testFileNameUpdatedWhenUpdatingAFileEntryKeepingFileVersionLabel()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, StringPool.BLANK,
			ContentTypes.TEXT_PLAIN, "FE1.exe", RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), (byte[])null, serviceContext);

		Assert.assertEquals("FE1.exe", fileEntry.getFileName());

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals("FE1.exe", fileVersion.getFileName());

		fileEntry = DLAppLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), fileEntry.getFileEntryId(), "FE2.txt",
			ContentTypes.TEXT_PLAIN, "FE1.exe", fileEntry.getDescription(),
			RandomTestUtil.randomString(), DLVersionNumberIncrease.MINOR,
			TestDataConstants.TEST_BYTE_ARRAY, serviceContext);

		Assert.assertEquals("FE1.exe.txt", fileEntry.getFileName());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals("FE1.exe.txt", fileVersion.getFileName());
	}

	@Test
	public void testGetNoAssetEntries() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream is = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(dlFolder.getGroupId());

		FileEntry assetFileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, is, bytes.length,
			serviceContext);

		is = new ByteArrayInputStream(bytes);

		FileEntry noAssetFileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, is, bytes.length,
			serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntry.class.getName(), noAssetFileEntry.getFileEntryId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getNoAssetFileEntries();

		Assert.assertFalse(dlFileEntries.contains(assetFileEntry.getModel()));
		Assert.assertTrue(dlFileEntries.contains(noAssetFileEntry.getModel()));
	}

	@Test
	public void testKeepsOriginalExtensionAfterChangingTheTitle()
		throws Exception {

		String content = StringUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"file.txt", ContentTypes.TEXT_PLAIN, "file.txt", StringPool.BLANK,
			StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(content.getBytes()), 0, serviceContext);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			dlFileEntry.getFileEntryId(), "file.pdf", null, "file.pdf",
			StringPool.BLANK, StringPool.BLANK, false, null, 0, serviceContext);

		Assert.assertEquals(
			content, StringUtil.read(fileEntry.getContentStream()));

		Assert.assertEquals("txt", fileEntry.getExtension());

		Assert.assertEquals("file.pdf.txt", fileEntry.getFileName());

		Assert.assertEquals(ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());
	}

	@Test
	public void testMoveFileEntryToFolderNotEmpty() throws Exception {
		DLFolder originDLFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), originDLFolder.getGroupId(),
			originDLFolder.getRepositoryId(), originDLFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<String, DDMFormValues>(), null,
			new ByteArrayInputStream(new byte[0]), 0, serviceContext);

		DLFolder destinationDLFolder = DLTestUtil.addDLFolder(
			_group.getGroupId());

		DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), destinationDLFolder.getGroupId(),
			destinationDLFolder.getRepositoryId(),
			destinationDLFolder.getFolderId(), StringUtil.randomString(),
			ContentTypes.TEXT_PLAIN, StringUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<String, DDMFormValues>(), null,
			new ByteArrayInputStream(new byte[0]), 0, serviceContext);

		DLFileEntryLocalServiceUtil.moveFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			destinationDLFolder.getFolderId(), serviceContext);

		dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			dlFileEntry.getFileEntryId());

		DLFolder dlFileEntryFolder = dlFileEntry.getFolder();

		Assert.assertEquals(
			dlFileEntryFolder.getFolderId(), destinationDLFolder.getFolderId());
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testMoveFileEntryToFolderWithSameFileName() throws Exception {
		DLFolder originDLFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String title = StringUtil.randomString();

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), originDLFolder.getGroupId(),
			originDLFolder.getRepositoryId(), originDLFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, title,
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<String, DDMFormValues>(), null,
			new ByteArrayInputStream(new byte[0]), 0, serviceContext);

		DLFolder destinationDLFolder = DLTestUtil.addDLFolder(
			_group.getGroupId());

		DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), destinationDLFolder.getGroupId(),
			destinationDLFolder.getRepositoryId(),
			destinationDLFolder.getFolderId(), StringUtil.randomString(),
			ContentTypes.TEXT_PLAIN, title, StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<String, DDMFormValues>(), null,
			new ByteArrayInputStream(new byte[0]), 0, serviceContext);

		DLFileEntryLocalServiceUtil.moveFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			destinationDLFolder.getFolderId(), serviceContext);
	}

	@Test(expected = NoSuchFolderException.class)
	public void testMoveFileEntryToInvalidDLFolder() throws Exception {
		DLFolder originDLFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), originDLFolder.getGroupId(),
			originDLFolder.getRepositoryId(), originDLFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new HashMap<String, DDMFormValues>(), null,
			new ByteArrayInputStream(new byte[0]), 0, serviceContext);

		Group destinationGroup = GroupTestUtil.addGroup();

		DLFolder destinationDLFolder = DLTestUtil.addDLFolder(
			destinationGroup.getGroupId());

		try {
			DLFileEntryLocalServiceUtil.moveFileEntry(
				TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
				destinationDLFolder.getFolderId(), serviceContext);
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(destinationGroup);
		}
	}

	@Test(expected = DuplicateFolderNameException.class)
	public void testValidateFileFailsWithAnExistingFolder() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		DLFileEntryLocalServiceUtil.validateFile(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, -1,
			RandomTestUtil.randomString(), folder.getName());
	}

	@Test
	public void testVerifyFileEntryCheckout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, -1,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			serviceContext);

		DLFileEntryLocalServiceUtil.checkOutFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getFileEntryTypeId(), serviceContext);

		Lock lock = LockManagerUtil.getLock(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		Assert.assertTrue(
			DLFileEntryLocalServiceUtil.verifyFileEntryCheckOut(
				dlFileEntry.getFileEntryId(), lock.getUuid()));
	}

	@Test(expected = NoSuchLockException.class)
	public void testVerifyFileEntryCheckoutWithUnlockedFileEntry()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, -1,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			serviceContext);

		DLFileEntryLocalServiceUtil.verifyFileEntryCheckOut(
			dlFileEntry.getFileEntryId(), RandomTestUtil.randomString());
	}

	@Test
	public void testVerifyFileEntryCheckoutWithWrongLock() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry1 = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, -1,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			serviceContext);

		DLFileEntryLocalServiceUtil.checkOutFileEntry(
			TestPropsValues.getUserId(), dlFileEntry1.getFileEntryId(),
			dlFileEntry1.getFileEntryTypeId(), serviceContext);

		DLFileEntry dlFileEntry2 = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, -1,
			new HashMap<>(), null, new ByteArrayInputStream(new byte[0]), 0,
			serviceContext);

		DLFileEntryLocalServiceUtil.checkOutFileEntry(
			TestPropsValues.getUserId(), dlFileEntry2.getFileEntryId(),
			dlFileEntry2.getFileEntryTypeId(), serviceContext);

		Lock dlFileEntry2LockUuid = LockManagerUtil.getLock(
			DLFileEntry.class.getName(), dlFileEntry2.getFileEntryId());

		Assert.assertFalse(
			DLFileEntryLocalServiceUtil.verifyFileEntryCheckOut(
				dlFileEntry1.getFileEntryId(), dlFileEntry2LockUuid.getUuid()));
	}

	protected DLFileEntry addAndApproveFileEntry(
			DLFolder dlFolder, Map<String, DDMFormValues> ddmFormValuesMap,
			InputStream inputStream, ServiceContext serviceContext)
		throws Exception {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getGroupId(),
			dlFolder.getRepositoryId(), dlFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0, serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		return DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), dlFileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext,
			new HashMap<String, Serializable>());
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Text1", "text");

		ddmFormField.setDataType("string");

		LocalizedValue label = new LocalizedValue(LocaleUtil.US);

		label.addString(LocaleUtil.US, "Text1");

		ddmFormField.setLabel(label);

		ddmFormField.setLocalizable(false);

		ddmForm.addDDMFormField(ddmFormField);

		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	protected DDMFormValues createDDMFormValues() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(LocaleUtil.US);

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId("baga");
		ddmFormFieldValue.setName("Text1");
		ddmFormFieldValue.setValue(new UnlocalizedValue("Text 1 Value"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	protected long populateServiceContextFileEntryType(
			ServiceContext serviceContext)
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName(), "0",
			createDDMForm(), LocaleUtil.US, serviceContext);

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				new long[] {ddmStructure.getStructureId()}, serviceContext);

		serviceContext.setAttribute(
			"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());

		DDMFormValues ddmFormValues = createDDMFormValues();

		serviceContext.setAttribute(
			DDMFormValues.class.getName() + StringPool.POUND +
				ddmStructure.getStructureId(),
			ddmFormValues);

		return dlFileEntryType.getFileEntryTypeId();
	}

	protected DLFileEntry updateAndApproveDLFileEntry(
			DLFileEntry dlFileEntry, InputStream inputStream,
			Map<String, DDMFormValues> ddmFormValuesMap,
			ServiceContext serviceContext)
		throws Exception {

		dlFileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN,
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			ddmFormValuesMap, null, inputStream, 0, serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		return DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), dlFileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext,
			new HashMap<String, Serializable>());
	}

	@DeleteAfterTestRun
	private Group _group;

}