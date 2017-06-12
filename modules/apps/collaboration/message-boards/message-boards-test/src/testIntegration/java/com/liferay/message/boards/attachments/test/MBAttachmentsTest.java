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

package com.liferay.message.boards.attachments.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageConstants;
import com.liferay.message.boards.kernel.service.MBCategoryServiceUtil;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Roberto Díaz
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class MBAttachmentsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE,
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteAttachmentsWhenDeletingMessage() throws Exception {
		int initialFileEntriesCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addMessageAttachment();

		_message = replyMessage();

		addMessageAttachment();

		Assert.assertEquals(
			initialFileEntriesCount + 2,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());

		MBMessageLocalServiceUtil.deleteMessage(_message);

		Assert.assertEquals(
			initialFileEntriesCount + 1,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());
	}

	@Test
	public void testDeleteAttachmentsWhenDeletingRootMessage()
		throws Exception {

		int initialFileEntriesCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addMessageAttachment();

		Assert.assertEquals(
			initialFileEntriesCount + 1,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());

		MBMessageLocalServiceUtil.deleteMessage(_message);

		Assert.assertEquals(
			initialFileEntriesCount,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());
	}

	@Test
	public void testFoldersCountWhenAddingAttachmentsToSameMessage()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		int foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(initialFoldersCount + 3, foldersCount);

		addMessageAttachment();

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(initialFoldersCount + 3, foldersCount);
	}

	@Test
	public void testFoldersCountWhenAddingMessage() throws Exception {
		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessage();

		_message = replyMessage();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenAddingMessageAttachment() throws Exception {
		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenAddingMessageAttachments()
		throws Exception {

		int foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		Assert.assertEquals(
			foldersCount + 3, DLFolderLocalServiceUtil.getDLFoldersCount());

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		_message = replyMessage();

		addMessageAttachment();

		Assert.assertEquals(
			foldersCount + 1, DLFolderLocalServiceUtil.getDLFoldersCount());

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		_message = null;

		addMessageAttachment();

		Assert.assertEquals(
			foldersCount + 2, DLFolderLocalServiceUtil.getDLFoldersCount());

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		_group = null;
		_message = null;

		addMessageAttachment();

		Assert.assertEquals(
			foldersCount + 3, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenAddingRootMessage() throws Exception {
		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessage();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenDeletingMessageWithAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		_message = replyMessage();

		addMessageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 4,
			DLFolderLocalServiceUtil.getDLFoldersCount());

		MBMessageLocalServiceUtil.deleteMessage(_message);

		Assert.assertEquals(
			initialFoldersCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenDeletingMessageWithoutAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessage();

		_message = replyMessage();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());

		MBMessageLocalServiceUtil.deleteMessage(_message);

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenDeletingRootMessageWithAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());

		MBMessageLocalServiceUtil.deleteMessage(_message);

		Assert.assertEquals(
			initialFoldersCount + 1,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenDeletingRootMessageWithoutAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessage();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());

		MBMessageLocalServiceUtil.deleteMessage(_message);

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testMoveToTrashAndDeleteMessageAttachment() throws Exception {
		addMessageAttachment();

		_trashMBAttachments(false);
	}

	@Test
	public void testMoveToTrashAndRestoreMessageAttachment() throws Exception {
		addMessageAttachment();

		_trashMBAttachments(true);
	}

	protected void addCategory() throws Exception {
		if (_group == null) {
			_group = GroupTestUtil.addGroup();
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_category = MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	protected void addMessage() throws Exception {
		if (_category == null) {
			addCategory();
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_category.getGroupId(), _category.getCategoryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	protected void addMessageAttachment() throws Exception {
		if (_message == null) {
			if (_category == null) {
				addCategory();
			}

			User user = TestPropsValues.getUser();

			if (_group == null) {
				_group = GroupTestUtil.addGroup();
			}

			List<ObjectValuePair<String, InputStream>> objectValuePairs =
				MBTestUtil.getInputStreamOVPs(
					"company_logo.png", getClass(), StringPool.BLANK);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), user.getUserId());

			_message = MBMessageLocalServiceUtil.addMessage(
				user.getUserId(), user.getFullName(), _group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject",
				"Body", MBMessageConstants.DEFAULT_FORMAT, objectValuePairs,
				false, 0, false, serviceContext);
		}
		else {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId());

			List<ObjectValuePair<String, InputStream>> objectValuePairs =
				MBTestUtil.getInputStreamOVPs(
					"OSX_Test.docx", getClass(), StringPool.BLANK);

			List<String> existingFiles = new ArrayList<>();

			List<FileEntry> fileEntries = _message.getAttachmentsFileEntries();

			for (FileEntry fileEntry : fileEntries) {
				existingFiles.add(String.valueOf(fileEntry.getFileEntryId()));
			}

			_message = MBMessageLocalServiceUtil.updateMessage(
				TestPropsValues.getUserId(), _message.getMessageId(), "Subject",
				"Body", objectValuePairs, existingFiles, 0, false,
				serviceContext);
		}
	}

	protected MBMessage replyMessage() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_message.getGroupId(), _message.getCategoryId(),
			_message.getThreadId(), _message.getMessageId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
			false, serviceContext);
	}

	private void _trashMBAttachments(boolean restore) throws Exception {
		int initialNotInTrashCount = _message.getAttachmentsFileEntriesCount();
		int initialTrashEntriesCount =
			_message.getDeletedAttachmentsFileEntriesCount();

		List<FileEntry> fileEntries = _message.getAttachmentsFileEntries();

		List<String> existingFiles = new ArrayList<>();

		for (FileEntry fileEntry : fileEntries) {
			existingFiles.add(String.valueOf(fileEntry.getFileEntryId()));
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String fileName = "OSX_Test.docx";

		List<ObjectValuePair<String, InputStream>> objectValuePairs =
			MBTestUtil.getInputStreamOVPs(
				fileName, getClass(), StringPool.BLANK);

		_message = MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), _message.getMessageId(), "Subject",
			"Body", objectValuePairs, existingFiles, 0, false, serviceContext);

		Assert.assertEquals(
			initialNotInTrashCount + 1,
			_message.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount,
			_message.getDeletedAttachmentsFileEntriesCount());

		long fileEntryId =
			MBMessageLocalServiceUtil.moveMessageAttachmentToTrash(
				TestPropsValues.getUserId(), _message.getMessageId(), fileName);

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			fileEntryId);

		Assert.assertEquals(
			initialNotInTrashCount, _message.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			_message.getDeletedAttachmentsFileEntriesCount());

		if (restore) {
			MBMessageLocalServiceUtil.restoreMessageAttachmentFromTrash(
				TestPropsValues.getUserId(), _message.getMessageId(), fileName);

			Assert.assertEquals(
				initialNotInTrashCount + 1,
				_message.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_message.getDeletedAttachmentsFileEntriesCount());

			MBMessageLocalServiceUtil.deleteMessageAttachment(
				_message.getMessageId(), fileName);
		}
		else {
			MBMessageLocalServiceUtil.deleteMessageAttachment(
				_message.getMessageId(), fileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount,
				_message.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_message.getDeletedAttachmentsFileEntriesCount());
		}
	}

	private MBCategory _category;

	@DeleteAfterTestRun
	private Group _group;

	private MBMessage _message;

}