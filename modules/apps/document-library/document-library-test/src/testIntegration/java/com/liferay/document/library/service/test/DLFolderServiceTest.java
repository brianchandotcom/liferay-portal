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
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderServiceUtil;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelReadCountComparator;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.view.count.ViewCountManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import jodd.util.MimeTypes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Samuel Trong Tran
 */
@RunWith(Arquillian.class)
public class DLFolderServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	@Rule
	public static final PermissionCheckerMethodTestRule
		permissionCheckerTestRule = PermissionCheckerMethodTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_name = PrincipalThreadLocal.getName();

		_group = GroupTestUtil.addGroup();

		try {
			_dlAppService.deleteFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"Test Folder");
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}
		}

		_parentFolder = _dlAppService.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder", RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		_ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName());

		_dlFileEntryType = DLFileEntryTypeServiceUtil.addFileEntryType(
			_group.getGroupId(), StringUtil.randomString(),
			StringUtil.randomString(),
			new long[] {_ddmStructure.getStructureId()},
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@After
	public void tearDown() throws Exception {
		PrincipalThreadLocal.setName(_name);
	}

	@Test
	public void testShouldReturnOrderedByReadCount() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _dlFileEntryType.getFileEntryTypeId());

		List<Object> expectedResults = new ArrayList<>();

		FileEntry fileEntry1 = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			"title1", StringUtil.randomString(), StringPool.BLANK, (byte[])null,
			serviceContext);

		ViewCountManagerUtil.incrementViewCount(
			fileEntry1.getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(DLFileEntry.class),
			fileEntry1.getFileEntryId(), 2);

		FileEntry fileEntry2 = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			"title2", StringUtil.randomString(), StringPool.BLANK, (byte[])null,
			serviceContext);

		ViewCountManagerUtil.incrementViewCount(
			fileEntry2.getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(DLFileEntry.class),
			fileEntry2.getFileEntryId(), 1);

		FileEntry fileEntry3 = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			"title3", StringUtil.randomString(), StringPool.BLANK, (byte[])null,
			serviceContext);

		ViewCountManagerUtil.incrementViewCount(
			fileEntry3.getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(DLFileEntry.class),
			fileEntry3.getFileEntryId(), 4);

		Folder hiddenFolder = _dlAppService.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Hidden Folder", RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		FileEntry fileEntry4 = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), hiddenFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			"title4", StringUtil.randomString(), StringPool.BLANK, (byte[])null,
			serviceContext);

		ViewCountManagerUtil.incrementViewCount(
			fileEntry4.getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(DLFileEntry.class),
			fileEntry4.getFileEntryId(), 5);

		FileShortcut fileShortcut1 = DLAppServiceUtil.addFileShortcut(
			_group.getGroupId(), _parentFolder.getFolderId(),
			fileEntry4.getFileEntryId(), serviceContext);

		FileEntry fileEntry5 = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), hiddenFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			"title5", StringUtil.randomString(), StringPool.BLANK, (byte[])null,
			serviceContext);

		ViewCountManagerUtil.incrementViewCount(
			fileEntry5.getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(DLFileEntry.class),
			fileEntry5.getFileEntryId(), 3);

		FileShortcut fileShortcut2 = DLAppServiceUtil.addFileShortcut(
			_group.getGroupId(), _parentFolder.getFolderId(),
			fileEntry5.getFileEntryId(), serviceContext);

		expectedResults.add(fileEntry2);
		expectedResults.add(fileEntry1);
		expectedResults.add(fileShortcut2);
		expectedResults.add(fileEntry3);
		expectedResults.add(fileShortcut1);

		List<Object> actualResults =
			DLFolderServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				_group.getGroupId(), _parentFolder.getFolderId(), null,
				_dlFileEntryType.getFileEntryTypeId(), false,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new RepositoryModelReadCountComparator<>(true));

		Assert.assertEquals(actualResults.toString(), 5, actualResults.size());

		for (int i = 0; i < 5; i++) {
			if (expectedResults.get(i) instanceof FileEntry) {
				DLFileEntry actualFileEntry = (DLFileEntry)actualResults.get(i);
				FileEntry expectedFileEntry = (FileEntry)expectedResults.get(i);

				Assert.assertEquals(
					expectedFileEntry.getFileEntryId(),
					actualFileEntry.getFileEntryId());
			}
			else {
				DLFileShortcut actualFileShortcut =
					(DLFileShortcut)actualResults.get(i);
				FileShortcut expectedFileShortcut =
					(FileShortcut)expectedResults.get(i);

				Assert.assertEquals(
					expectedFileShortcut.getFileShortcutId(),
					actualFileShortcut.getFileShortcutId());
			}
		}
	}

	@Test
	public void testShouldReturnOrderedByTitle() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _dlFileEntryType.getFileEntryTypeId());

		List<FileEntry> expectedResults = new ArrayList<>();

		FileEntry fileEntry1 = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			"title2", StringUtil.randomString(), StringPool.BLANK, (byte[])null,
			serviceContext);

		FileEntry fileEntry2 = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			"title1", StringUtil.randomString(), StringPool.BLANK, (byte[])null,
			serviceContext);

		FileEntry fileEntry3 = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), _parentFolder.getFolderId(),
			StringUtil.randomString(), MimeTypes.MIME_APPLICATION_OCTET_STREAM,
			"title3", StringUtil.randomString(), StringPool.BLANK, (byte[])null,
			serviceContext);

		expectedResults.add(fileEntry2);
		expectedResults.add(fileEntry1);
		expectedResults.add(fileEntry3);

		List<Object> actualResults =
			DLFolderServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				_group.getGroupId(), _parentFolder.getFolderId(), null,
				_dlFileEntryType.getFileEntryTypeId(), false,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new RepositoryModelTitleComparator<FileEntry>(true));

		Assert.assertEquals(actualResults.toString(), 3, actualResults.size());

		for (int i = 0; i < 3; i++) {
			DLFileEntry actualFileEntry = (DLFileEntry)actualResults.get(i);
			FileEntry expectedFileEntry = expectedResults.get(i);

			Assert.assertEquals(
				expectedFileEntry.getFileEntryId(),
				actualFileEntry.getFileEntryId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFolderServiceTest.class);

	@DeleteAfterTestRun
	private DDMStructure _ddmStructure;

	@Inject
	private DLAppService _dlAppService;

	@DeleteAfterTestRun
	private DLFileEntryType _dlFileEntryType;

	@DeleteAfterTestRun
	private Group _group;

	private String _name;
	private Folder _parentFolder;

}