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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.ByteArrayInputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 * @author Eudaldo Alonso
 * @author Sergio González
 */
@Sync
public class VerifyDocumentLibraryTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteMismatchCompanyIdDLFileEntryMetadatas()
		throws Exception {

		DLFileEntry dlFileEntry = addDLFileEntry();

		DLFileEntryType dlFileEntryType = dlFileEntry.getDLFileEntryType();

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		DDMStructure ddmStructure = ddmStructures.get(0);

		ddmStructure.setCompanyId(12345);

		DDMStructureLocalServiceUtil.updateDDMStructure(ddmStructure);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		DLFileEntryMetadata dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
				ddmStructure.getStructureId(),
				dlFileVersion.getFileVersionId());

		Assert.assertNotNull(dlFileEntryMetadata);

		doVerify();

		dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
				ddmStructure.getStructureId(),
				dlFileVersion.getFileVersionId());

		Assert.assertNull(dlFileEntryMetadata);
	}

	@Test
	public void testDeleteNoStructuresDLFileEntryMetadatas() throws Exception {
		DLFileEntry dlFileEntry = addDLFileEntry();

		DLFileEntryType dlFileEntryType = dlFileEntry.getDLFileEntryType();

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		DDMStructure ddmStructure = ddmStructures.get(0);

		DDMStructureLocalServiceUtil.deleteDDMStructure(ddmStructure);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		DLFileEntryMetadata dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
				ddmStructure.getStructureId(),
				dlFileVersion.getFileVersionId());

		Assert.assertNotNull(dlFileEntryMetadata);

		doVerify();

		dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
				ddmStructure.getStructureId(),
				dlFileVersion.getFileVersionId());

		Assert.assertNull(dlFileEntryMetadata);
	}

	@Test
	public void testDLFileEntryTreePathWithDLFileEntryInTrash()
		throws Exception {

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

		DLFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFileEntryTreePathWithParentDLFolderInTrash()
		throws Exception {

		Folder grandparentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId());

		DLAppTestUtil.addFileEntry(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		DLAppServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFileShortcutTreePathWithDLFileShortcutInTrash()
		throws Exception {

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		DLFileShortcut dlFileShortcut = DLAppTestUtil.addDLFileShortcut(
			fileEntry, _group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.moveFileShortcutToTrash(
			dlFileShortcut.getFileShortcutId());

		DLFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFileShortcutTreePathWithParentDLFolderInTrash()
		throws Exception {

		Folder grandparentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId());

		DLAppTestUtil.addFileEntry(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		DLAppServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFolderTreePathWithDLFolderInTrash() throws Exception {
		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Folder folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFolderTreePathWithParentDLFolderInTrash()
		throws Exception {

		Folder grandparentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId());

		DLAppTestUtil.addFolder(
			_group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	protected DLFileEntry addDLFileEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		byte[] bytes = FileUtil.getBytes(
			getClass(),
			"/com/liferay/portlet/documentlibrary/service/dependencies" +
				"/ddmstructure.xml");

		serviceContext.setAttribute("definition", new String(bytes));

		User user = TestPropsValues.getUser();

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(user.getLocale()));

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK, new long[0],
				serviceContext);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		DDMStructure ddmStructure = ddmStructures.get(0);

		Map<String, Fields> fieldsMap = new HashMap<>();

		Fields fields = new Fields();

		Field nameField = new Field(
			ddmStructure.getStructureId(), "date_an", new Date());

		fields.put(nameField);

		fieldsMap.put(ddmStructure.getStructureKey(), fields);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			RandomTestUtil.randomBytes());

		return DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			null, null, dlFileEntryType.getFileEntryTypeId(), fieldsMap, null,
			byteArrayInputStream, byteArrayInputStream.available(),
			serviceContext);
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyDocumentLibrary();
	}

	@DeleteAfterTestRun
	private Group _group;

}