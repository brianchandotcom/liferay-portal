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

package com.liferay.portlet.messageboards.attachments;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Roberto Díaz
 * @author Sergio González
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBAttachmentsTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteAttachmentsWhenDeletingMessage() throws Exception {
		int initialFileEntriesCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addMessageAttachment();

		_message = MBTestUtil.addMessage(
			_message.getGroupId(), _message.getCategoryId(),
			_message.getThreadId(), _message.getMessageId());

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

		_message = MBTestUtil.addMessage(
			_message.getGroupId(), _message.getCategoryId(),
			_message.getThreadId(), _message.getMessageId());

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

		_message = MBTestUtil.addMessage(
			_message.getGroupId(), _message.getCategoryId(),
			_message.getThreadId(), _message.getMessageId());

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

		_message = MBTestUtil.addMessage(
			_message.getGroupId(), _message.getCategoryId(),
			_message.getThreadId(), _message.getMessageId());

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

		_message = MBTestUtil.addMessage(
			_message.getGroupId(), _message.getCategoryId(),
			_message.getThreadId(), _message.getMessageId());

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
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_category = MBTestUtil.addCategory(serviceContext);
	}

	protected void addMessage() throws Exception {
		if (_category == null) {
			addCategory();
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_message = MBTestUtil.addMessage(
			_category.getCategoryId(), serviceContext);
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
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			_message = MBMessageLocalServiceUtil.addMessage(
				user.getUserId(), user.getFullName(), _group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject",
				"Body", MBMessageConstants.DEFAULT_FORMAT, objectValuePairs,
				false, 0, false, serviceContext);
		}
		else {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			List<ObjectValuePair<String, InputStream>> objectValuePairs =
				MBTestUtil.getInputStreamOVPs(
					"OSX_Test.docx", getClass(), StringPool.BLANK);

			List<String> existingFiles = new ArrayList<String>();

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

	private void _trashMBAttachments(boolean restore) throws Exception {
		int initialNotInTrashCount = _message.getAttachmentsFileEntriesCount();
		int initialTrashEntriesCount =
			_message.getDeletedAttachmentsFileEntriesCount();

		List<FileEntry> fileEntries = _message.getAttachmentsFileEntries();

		List<String> existingFiles = new ArrayList<String>();

		for (FileEntry fileEntry : fileEntries) {
			existingFiles.add(String.valueOf(fileEntry.getFileEntryId()));
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

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
			TrashEntryServiceUtil.restoreEntry(
				DLFileEntryConstants.getClassName(), fileEntryId);

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