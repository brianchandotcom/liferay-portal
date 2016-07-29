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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashLocalServiceUtil;
import com.liferay.document.library.kernel.store.DLStoreUtil;
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
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.util.test.DLTestUtil;
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

/**
 * @author Michael C. Han
 * @author Sergio González
 */
@Sync
public class DLFileEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
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
				ContentTypes.TEXT_PLAIN,
				RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
				serviceContext);

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

			Folder destinationFolder = DLAppServiceUtil.addFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLFileEntry copyFileEntry =
				DLFileEntryLocalServiceUtil.copyFileEntry(
					TestPropsValues.getUserId(), _group.getGroupId(),
					_group.getGroupId(), fileEntry.getFileEntryId(),
					destinationFolder.getFolderId(), serviceContext);

			ExpandoBridge expandoBridge = copyFileEntry.getExpandoBridge();

			String attributeValue = GetterUtil.getString(
				expandoBridge.getAttribute("ExpandoAttributeName"));

			Assert.assertEquals("ExpandoAttributeValue", attributeValue);
			Assert.assertEquals(
				fileEntryTypeId, copyFileEntry.getFileEntryTypeId());

			DLFileVersion copyDLFileVersion = copyFileEntry.getFileVersion();

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
				ContentTypes.TEXT_PLAIN,
				RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
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
				ContentTypes.TEXT_PLAIN,
				RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
				serviceContext);
		}

		DLFileEntryLocalServiceUtil.deleteFileEntries(
			_group.getGroupId(), folder.getFolderId(), false);

		int fileEntriesCount = DLFileEntryLocalServiceUtil.getFileEntriesCount(
			_group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(20, fileEntriesCount);
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
	public void testGetMisversionedFileEntries() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		byte[] bytes = RandomTestUtil.randomBytes(
			TikaSafeRandomizerBumper.INSTANCE);

		InputStream is = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(dlFolder.getGroupId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, is, bytes.length,
			serviceContext);

		DLFileEntry dlFileEntry = null;

		DLFileVersion dlFileVersion = null;

		try {
			dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				fileEntry.getFileEntryId());

			dlFileVersion = dlFileEntry.getFileVersion();

			dlFileVersion.setFileEntryId(12345L);

			DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);

			List<DLFileEntry> dlFileEntries =
				DLFileEntryLocalServiceUtil.getMisversionedFileEntries();

			Assert.assertEquals(1, dlFileEntries.size());
			Assert.assertEquals(dlFileEntry, dlFileEntries.get(0));
		}
		finally {
			if (dlFileEntry != null) {
				DLFileEntryLocalServiceUtil.deleteDLFileEntry(
					dlFileEntry.getFileEntryId());
			}

			if (dlFileVersion != null) {
				DLFileVersionLocalServiceUtil.deleteDLFileVersion(
					dlFileVersion.getFileVersionId());
			}
		}
	}

	@Test
	public void testGetNoAssetEntries() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		byte[] bytes = RandomTestUtil.randomBytes(
			TikaSafeRandomizerBumper.INSTANCE);

		InputStream is = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(dlFolder.getGroupId());

		DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, is, bytes.length,
			serviceContext);

		is = new ByteArrayInputStream(bytes);

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, is, bytes.length,
			serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getNoAssetFileEntries();

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals(
			fileEntry.getFileEntryId(), dlFileEntry.getFileEntryId());
	}

	@Test
	public void testGetOrphanedFileEntries() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		byte[] bytes = RandomTestUtil.randomBytes(
			TikaSafeRandomizerBumper.INSTANCE);

		InputStream is = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(dlFolder.getGroupId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, is, bytes.length,
			serviceContext);

		boolean indexReadOnly = IndexWriterHelperUtil.isIndexReadOnly();

		DLFileEntry dlFileEntry = null;

		try {
			IndexWriterHelperUtil.setIndexReadOnly(true);

			dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				fileEntry.getFileEntryId());

			dlFileEntry.setGroupId(10000L);

			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

			List<DLFileEntry> dlFileEntries =
				DLFileEntryLocalServiceUtil.getOrphanedFileEntries();

			Assert.assertEquals(1, dlFileEntries.size());
			Assert.assertEquals(dlFileEntry, dlFileEntries.get(0));
		}
		finally {
			if (dlFileEntry != null) {
				DLFileEntryLocalServiceUtil.deleteDLFileEntry(
					dlFileEntry.getFileEntryId());
			}

			IndexWriterHelperUtil.setIndexReadOnly(indexReadOnly);
		}
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
			DDMFormValues.class.getName() + ddmStructure.getStructureId(),
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
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK, true,
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