/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelReadCountComparator;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.view.count.ViewCountManager;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenViewingFolderContentsTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldCountDraftsIfOwner() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), null, StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, null, null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), null, StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, null, null, serviceContext);

		Assert.assertEquals(
			2,
			_dlAppService.getFoldersAndFileEntriesAndFileShortcutsCount(
				group.getGroupId(), parentFolder.getFolderId(),
				WorkflowConstants.STATUS_APPROVED, false));
	}

	@Test
	public void testShouldNotCountDraftsIfNotOwner() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), null, StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, null, null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), null, StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, null, null, serviceContext);

		User user = UserTestUtil.addGroupUser(group, "User");

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user)) {

			Assert.assertEquals(
				1,
				_dlAppService.getFoldersAndFileEntriesAndFileShortcutsCount(
					group.getGroupId(), parentFolder.getFolderId(),
					WorkflowConstants.STATUS_APPROVED, false));
		}
		finally {
			_userLocalService.deleteUser(user.getUserId());
		}
	}

	@Test
	public void testShouldNotReturnContentOfFolderNotInGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Folder targetGroupFolder = _dlAppService.addFolder(
			null, targetGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(targetGroup.getGroupId()));

		_dlAppService.addFileEntry(
			null, targetGroup.getGroupId(), targetGroupFolder.getFolderId(),
			StringUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			"title1", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);
		_dlAppService.addFileEntry(
			null, targetGroup.getGroupId(), targetGroupFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			"title2", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);

		Assert.assertEquals(
			0,
			_dlAppService.getFoldersAndFileEntriesAndFileShortcutsCount(
				group.getGroupId(), targetGroupFolder.getFolderId(),
				WorkflowConstants.STATUS_APPROVED,
				ArrayUtil.toStringArray(DLUtil.getAllMediaGalleryMimeTypes()),
				false));
	}

	@Test
	public void testShouldNotReturnDraftsIfNotOwner() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), null, StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, null, null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), null, StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, null, null, serviceContext);

		User user = UserTestUtil.addGroupUser(group, "User");

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user)) {

			List<Object> foldersAndFileEntriesAndFileShortcuts =
				_dlAppService.getFoldersAndFileEntriesAndFileShortcuts(
					group.getGroupId(), parentFolder.getFolderId(),
					WorkflowConstants.STATUS_APPROVED, false, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			Assert.assertEquals(
				foldersAndFileEntriesAndFileShortcuts.toString(), 1,
				foldersAndFileEntriesAndFileShortcuts.size());
		}
		finally {
			_userLocalService.deleteUser(user.getUserId());
		}
	}

	@Test
	public void testShouldNotReturnNotAcceptedMimeTypes() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			"title1", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);
		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			"title2", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);

		_dlAppService.addFolder(
			null, group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertEquals(
			2,
			_dlAppService.getFoldersAndFileEntriesAndFileShortcutsCount(
				group.getGroupId(), parentFolder.getFolderId(),
				WorkflowConstants.STATUS_APPROVED,
				ArrayUtil.toStringArray(DLUtil.getAllMediaGalleryMimeTypes()),
				false));
	}

	@Test
	public void testShouldReturnDraftsIfOwner() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), null, StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, null, null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		_dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), null, StringUtil.randomString(),
			StringPool.BLANK, (byte[])null, null, null, serviceContext);

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			_dlAppService.getFoldersAndFileEntriesAndFileShortcuts(
				group.getGroupId(), parentFolder.getFolderId(),
				WorkflowConstants.STATUS_APPROVED, false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			foldersAndFileEntriesAndFileShortcuts.toString(), 2,
			foldersAndFileEntriesAndFileShortcuts.size());
	}

	@Test
	public void testShouldReturnOrderedByReadCount() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		List<FileEntry> expectedFileEntries = new ArrayList<>();

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			"title1", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);

		_viewCountManager.incrementViewCount(
			fileEntry1.getCompanyId(),
			_classNameLocalService.getClassNameId(DLFileEntry.class),
			fileEntry1.getFileEntryId(), 2);

		FileEntry fileEntry2 = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			"title2", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);

		_viewCountManager.incrementViewCount(
			fileEntry2.getCompanyId(),
			_classNameLocalService.getClassNameId(DLFileEntry.class),
			fileEntry2.getFileEntryId(), 1);

		FileEntry fileEntry3 = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			"title3", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);

		_viewCountManager.incrementViewCount(
			fileEntry3.getCompanyId(),
			_classNameLocalService.getClassNameId(DLFileEntry.class),
			fileEntry3.getFileEntryId(), 3);

		expectedFileEntries.add(fileEntry2);
		expectedFileEntries.add(fileEntry1);
		expectedFileEntries.add(fileEntry3);

		List<Object> actualFoldersAndFileEntriesAndFileShortcuts =
			_dlAppService.getFoldersAndFileEntriesAndFileShortcuts(
				group.getGroupId(), parentFolder.getFolderId(),
				WorkflowConstants.STATUS_APPROVED, false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new RepositoryModelReadCountComparator<>(true));

		Assert.assertEquals(
			actualFoldersAndFileEntriesAndFileShortcuts.toString(), 3,
			actualFoldersAndFileEntriesAndFileShortcuts.size());

		for (int i = 0; i < 3; i++) {
			FileEntry expectedFileEntry = expectedFileEntries.get(i);
			FileEntry actualFileEntry =
				(FileEntry)actualFoldersAndFileEntriesAndFileShortcuts.get(i);

			Assert.assertEquals(
				expectedFileEntry.getFileEntryId(),
				actualFileEntry.getFileEntryId());
		}
	}

	@Test
	public void testShouldReturnOrderedByTitle() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		List<FileEntry> expectedFileEntries = new ArrayList<>();

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			"title2", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);

		FileEntry fileEntry2 = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			"title1", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);

		FileEntry fileEntry3 = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			"title3", null, StringUtil.randomString(), StringPool.BLANK,
			(byte[])null, null, null, serviceContext);

		expectedFileEntries.add(fileEntry2);
		expectedFileEntries.add(fileEntry1);
		expectedFileEntries.add(fileEntry3);

		List<Object> actualFoldersAndFileEntriesAndFileShortcuts =
			_dlAppService.getFoldersAndFileEntriesAndFileShortcuts(
				group.getGroupId(), parentFolder.getFolderId(),
				WorkflowConstants.STATUS_APPROVED, false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new RepositoryModelTitleComparator<FileEntry>(true));

		Assert.assertEquals(
			actualFoldersAndFileEntriesAndFileShortcuts.toString(), 3,
			actualFoldersAndFileEntriesAndFileShortcuts.size());

		for (int i = 0; i < 3; i++) {
			FileEntry expectedFileEntry = expectedFileEntries.get(i);
			FileEntry actualFileEntry =
				(FileEntry)actualFoldersAndFileEntriesAndFileShortcuts.get(i);

			Assert.assertEquals(
				expectedFileEntry.getFileEntryId(),
				actualFileEntry.getFileEntryId());
		}
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static DLAppService _dlAppService;

	@Inject
	private static UserLocalService _userLocalService;

	@Inject
	private static ViewCountManager _viewCountManager;

}