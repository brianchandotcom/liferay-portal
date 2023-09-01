/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.InvalidFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenCopyingWithDocumentTypesTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		super.setUp();

		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			group.getCreatorUserId(), group.getGroupId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			PortalUtil.getClassNameId(DLFileEntryMetadata.class),
			StringPool.BLANK,
			HashMapBuilder.put(
				LocaleUtil.getDefault(),
				DLFileEntryMetadata.class.getSimpleName()
			).build(),
			new HashMap<>(), StringPool.BLANK, StorageType.DEFAULT.toString(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		_dlFileEntryType = _dlFileEntryTypeLocalService.addFileEntryType(
			group.getCreatorUserId(), group.getGroupId(),
			ddmStructure.getStructureId(), null,
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			new HashMap<>(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		_childGroup = GroupTestUtil.addGroup(group.getGroupId());

		_newParentFolder = _dlAppService.addFolder(
			null, group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "New Test Folder",
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
		_targetParentFolder = _dlAppService.addFolder(
			null, targetGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Target Test Folder",
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				targetGroup.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testCopyFileShouldCopyBasicDocumentTypeWhenCopyDocumentTypesInDifferentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		DLFileEntry dlFileEntry1 = (DLFileEntry)fileEntry1.getModel();

		Assert.assertEquals(
			_dlFileEntryType.getFileEntryTypeId(),
			dlFileEntry1.getFileEntryTypeId());

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _newParentFolder.getFolderId(),
			_newParentFolder.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(
				_newParentFolder.getGroupId()));

		DLFileEntry dlFileEntry2 = (DLFileEntry)fileEntry2.getModel();

		Assert.assertEquals(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			dlFileEntry2.getFileEntryTypeId());
	}

	@Test
	public void testCopyFileShouldCopyDocumentTypesFromParentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		DLFileEntry dlFileEntry1 = (DLFileEntry)fileEntry1.getModel();

		Assert.assertEquals(
			_dlFileEntryType.getFileEntryTypeId(),
			dlFileEntry1.getFileEntryTypeId());

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			_childGroup.getGroupId(), _dlFileEntryType.getFileEntryTypeId(),
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(_childGroup.getGroupId()));

		DLFileEntry dlFileEntry2 = (DLFileEntry)fileEntry2.getModel();

		Assert.assertEquals(
			dlFileEntry1.getFileEntryTypeId(),
			dlFileEntry2.getFileEntryTypeId());
	}

	@Test
	public void testCopyFileShouldCopyDocumentTypesFromSameGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		DLFileEntry dlFileEntry1 = (DLFileEntry)fileEntry1.getModel();

		Assert.assertEquals(
			_dlFileEntryType.getFileEntryTypeId(),
			dlFileEntry1.getFileEntryTypeId());

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _newParentFolder.getFolderId(),
			_newParentFolder.getGroupId(),
			_dlFileEntryType.getFileEntryTypeId(),
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(
				_newParentFolder.getGroupId()));

		DLFileEntry dlFileEntry2 = (DLFileEntry)fileEntry2.getModel();

		Assert.assertEquals(
			dlFileEntry1.getFileEntryTypeId(),
			dlFileEntry2.getFileEntryTypeId());
	}

	@Test(expected = InvalidFileEntryTypeException.class)
	public void testCopyFileThrowsExceptionWhenCopyDocumentTypesFromDifferentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		DLFileEntry dlFileEntry1 = (DLFileEntry)fileEntry1.getModel();

		Assert.assertEquals(
			_dlFileEntryType.getFileEntryTypeId(),
			dlFileEntry1.getFileEntryTypeId());

		_dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _targetParentFolder.getFolderId(),
			_targetParentFolder.getGroupId(),
			_dlFileEntryType.getFileEntryTypeId(),
			new long[] {_targetParentFolder.getGroupId()},
			ServiceContextTestUtil.getServiceContext(
				_targetParentFolder.getGroupId()));
	}

	@Test
	public void testCopyFolderShouldCopyBasicDocumentTypeWhenCopyDocumentTypesFromDifferentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), DLAppServiceTestUtil.FILE_NAME,
			ContentTypes.TEXT_PLAIN, DLAppServiceTestUtil.FILE_NAME,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), null, null, serviceContext);

		DLFileEntry dlFileEntry1 = (DLFileEntry)fileEntry1.getModel();

		Assert.assertEquals(
			_dlFileEntryType.getFileEntryTypeId(),
			dlFileEntry1.getFileEntryTypeId());

		Folder folder = _dlAppService.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			targetGroup.getGroupId(), _targetParentFolder.getFolderId(),
			new HashMap<>(), new long[] {targetGroup.getGroupId()},
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			_childGroup.getGroupId(), folder.getFolderId());

		Assert.assertEquals(fileEntries.toString(), 1, fileEntries.size());

		FileEntry fileEntry2 = fileEntries.get(0);

		DLFileEntry dlFileEntry2 = (DLFileEntry)fileEntry2.getModel();

		Assert.assertEquals(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			dlFileEntry2.getFileEntryTypeId());
	}

	@Test
	public void testCopyFolderShouldCopyDocumentTypesFromParentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), DLAppServiceTestUtil.FILE_NAME,
			ContentTypes.TEXT_PLAIN, DLAppServiceTestUtil.FILE_NAME,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), null, null, serviceContext);

		DLFileEntry dlFileEntry1 = (DLFileEntry)fileEntry1.getModel();

		Folder folder = _dlAppService.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			_childGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			HashMapBuilder.put(
				dlFileEntry1.getFileEntryId(), dlFileEntry1.getFileEntryTypeId()
			).build(),
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(_childGroup.getGroupId()));

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			_childGroup.getGroupId(), folder.getFolderId());

		Assert.assertEquals(fileEntries.toString(), 1, fileEntries.size());

		FileEntry fileEntry2 = fileEntries.get(0);

		DLFileEntry dlFileEntry2 = (DLFileEntry)fileEntry2.getModel();

		Assert.assertEquals(
			dlFileEntry1.getFileEntryTypeId(),
			dlFileEntry2.getFileEntryTypeId());
	}

	@Test(expected = InvalidFileEntryTypeException.class)
	public void testCopyFolderThrowsExceptionWhenCopyDocumentTypesFromDifferentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), DLAppServiceTestUtil.FILE_NAME,
			ContentTypes.TEXT_PLAIN, DLAppServiceTestUtil.FILE_NAME,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), null, null, serviceContext);

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		_dlAppService.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			targetGroup.getGroupId(), _targetParentFolder.getFolderId(),
			HashMapBuilder.put(
				dlFileEntry.getFileEntryId(), dlFileEntry.getFileEntryTypeId()
			).build(),
			new long[] {targetGroup.getGroupId()},
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Inject
	private static DLAppService _dlAppService;

	@Inject
	private static DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@DeleteAfterTestRun
	private Group _childGroup;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	private DLFileEntryType _dlFileEntryType;
	private Folder _newParentFolder;
	private Folder _targetParentFolder;

}