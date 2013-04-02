/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBAttachmentsTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_group = null;
		_message = null;
		_category = null;
		_thread = null;
	}

	@Test
	@Transactional
	public void testDeleteAttachmentsWhenDeletingMessage() throws Exception {
		int initialFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addMessageAttachment();

		Assert.assertEquals(
			initialFileEntryCount + 1,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());

		MBMessageLocalServiceUtil.deleteMessage(_message.getMessageId());

		Assert.assertEquals(
			initialFileEntryCount,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingAttachmentsToSameMessage()
		throws Exception {

		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		int firstFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(initialFolderCount + 3, firstFolderCount);

		addMessageAttachment();

		int finalFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(firstFolderCount, finalFolderCount);
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingCategory() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addCategory();

		Assert.assertEquals(
			initialFolderCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingMessage() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessage();

		Assert.assertEquals(
			initialFolderCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingMessageAttachment() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		Assert.assertEquals(
			initialFolderCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingWikiPageAttachments()
		throws Exception {

		int foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		// One folder per group, thread and message

		Assert.assertEquals(
			foldersCount + 3, DLFolderLocalServiceUtil.getDLFoldersCount());

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		Assert.assertEquals(
			foldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		_thread = null;
		_message = null;

		addMessageAttachment();

		Assert.assertEquals(
			foldersCount + 2, DLFolderLocalServiceUtil.getDLFoldersCount());

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		_group = null;
		_message = null;
		_thread = null;

		addMessageAttachment();

		Assert.assertEquals(
			foldersCount + 3, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenDeletingMessageWithAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());

		MBMessageLocalServiceUtil.deleteMessage(_message.getMessageId());

		Assert.assertEquals(
			initialFoldersCount + 1,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenDeletingMessageWithoutAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessage();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());

		MBMessageLocalServiceUtil.deleteMessage(_message.getMessageId());

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenDeletingThreadWithAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());

		MBThreadLocalServiceUtil.deleteThread(_message.getThreadId());

		Assert.assertEquals(
			initialFoldersCount + 1,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenMovingToTrashMessageAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageAttachment();

		int folderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(initialFoldersCount + 3, folderCount);

		MBMessageLocalServiceUtil.moveMessageAttachmentToTrash(
			TestPropsValues.getUserId(), _message.getMessageId(),
			"company_logo.png");

		Assert.assertNotEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testMoveToTrashAndDeleteMessage() throws Exception {
		addMessageAttachment();

		_trashMBAttachments(false);

		RepositoryLocalServiceUtil.deleteRepositories(_group.getGroupId());
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testMoveToTrashAndRestoreMessage() throws Exception {
		addMessageAttachment();

		_trashMBAttachments(true);

		RepositoryLocalServiceUtil.deleteRepositories(_group.getGroupId());
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	protected void addCategory() throws Exception {
		if (_group == null) {
			_group = GroupTestUtil.addGroup();
		}

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		_category = MBTestUtil.addCategory(serviceContext);
	}

	protected void addMessage() throws Exception {
		if (_category == null) {
			addCategory();
		}

		_message = MBTestUtil.addMessage(_category);
	}

	protected void addMessageAttachment() throws Exception {
		if (_message == null) {
			if (_category == null) {
				addCategory();
			}

			if (_group == null) {
				_group = GroupTestUtil.addGroup();
			}

			ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
				_group.getGroupId());

			User user = TestPropsValues.getUser();

			List<ObjectValuePair<String, InputStream>> objectValuePairs =
				MBTestUtil.getInputStreamOVPs(
					"company_logo.png", getClass(), StringPool.BLANK);

			_message = MBMessageLocalServiceUtil.addMessage(
				user.getUserId(), user.getFullName(), _group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject",
				"Body", MBMessageConstants.DEFAULT_FORMAT, objectValuePairs,
				false, 0, false, serviceContext);
		}
		else {
			ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
				_group.getGroupId());

			User user = TestPropsValues.getUser();

			List<ObjectValuePair<String, InputStream>> objectValuePairs =
				MBTestUtil.getInputStreamOVPs(
					"OSX_Test.docx", getClass(), StringPool.BLANK);

			List<FileEntry> fileEntries = _message.getAttachmentsFileEntries();

			List<String> existingFiles = new ArrayList<String>();

			for (FileEntry fileEntry : fileEntries) {
				existingFiles.add(String.valueOf(fileEntry.getFileEntryId()));
			}

			_message = MBMessageLocalServiceUtil.updateMessage(
				user.getUserId(), _message.getMessageId(), "Subject", "Body",
				objectValuePairs, existingFiles, 0, false, serviceContext);
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

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

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
				TestPropsValues.getUserId(), _message.getMessageId(),
				fileEntry.getTitle());

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
	private Group _group;
	private MBMessage _message;
	private MBThread _thread;

}