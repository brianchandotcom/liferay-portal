/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

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
 */
@ExecutionTestListeners(
	listeners = {
			EnvironmentExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MessageBoardsAttachmentsTrashTest {

	@Before
	public void setUp() throws Exception {
		User user = TestPropsValues.getUser();

		_group = ServiceTestUtil.addGroup();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(_group.getGroupId());

		_message = MBMessageLocalServiceUtil.addMessage(
			user.getUserId(), user.getFullName(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject", "Body",
			MBMessageConstants.DEFAULT_FORMAT,
			getInputStreamOVPs("company_logo.png"), false, 0, false,
			serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		if (_group != null) {
			GroupLocalServiceUtil.deleteGroup(_group);

			_group = null;
		}

		if (_message != null) {
			_message = null;
		}
	}

	@Test
	public void testTrashAndDelete() throws Exception {
		trashMessageBoardsAttachments(false);
	}

	@Test
	public void testTrashAndRestore() throws Exception {
		trashMessageBoardsAttachments(true);
	}

	private List<ObjectValuePair<String, InputStream>> getInputStreamOVPs(
		String fileName) {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(1);

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		ObjectValuePair<String, InputStream> inputStreamOVP =
			new ObjectValuePair<String, InputStream>(fileName, inputStream);

		inputStreamOVPs.add(inputStreamOVP);

		return inputStreamOVPs;
	}

	private void trashMessageBoardsAttachments(boolean restore)
		throws Exception {

		User user = TestPropsValues.getUser();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(_group.getGroupId());

		int initialNotInTrashCount = _message.getAttachmentsFileEntriesCount();

		int initialTrashEntriesCount =
			_message.getDeletedAttachmentsFileEntriesCount();

		List<FileEntry> fileEntries = _message.getAttachmentsFileEntries();

		List<String> existingFiles = new ArrayList<String>();

		for (FileEntry fileEntry : fileEntries) {
			existingFiles.add(fileEntry.getTitle());
		}

		_message = MBMessageLocalServiceUtil.updateMessage(
			user.getUserId(), _message.getMessageId(), "Subject", "Body",
			getInputStreamOVPs("OSX_Test.docx"), existingFiles, 0, false,
			serviceContext);

		Assert.assertEquals(
			initialNotInTrashCount + 1,
			_message.getAttachmentsFileEntriesCount());

		Assert.assertEquals(
			initialTrashEntriesCount,
			_message.getDeletedAttachmentsFileEntriesCount());

		FileEntry attachmentFileEntry = fileEntries.get(0);

		MBMessageLocalServiceUtil.moveMessageAttachmentToTrash(
			user.getUserId(), _message.getMessageId(),
			attachmentFileEntry.getTitle());

		Assert.assertEquals(
			initialNotInTrashCount, _message.getAttachmentsFileEntriesCount());

		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			_message.getDeletedAttachmentsFileEntriesCount());

		List<FileEntry> deletedAttachmentsFileEntries =
			_message.getDeletedAttachmentsFileEntries(0, 1);

		FileEntry deleteAttachmentFileEntry = deletedAttachmentsFileEntries.get(
			0);

		DLFileEntry dlFileEntry =
			(DLFileEntry)deleteAttachmentFileEntry.getModel();

		if (restore) {
			MBMessageLocalServiceUtil.restoreMessageAttachmentFromTrash(
				user.getUserId(), _message.getMessageId(),
				dlFileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount + 1,
				_message.getAttachmentsFileEntriesCount());

			Assert.assertEquals(
				initialTrashEntriesCount,
				_message.getDeletedAttachmentsFileEntriesCount());
		}
		else {
			MBMessageLocalServiceUtil.deleteMessageAttachment(
				_message.getMessageId(), dlFileEntry.getTitle());
		}
	}

	private Group _group;
	private MBMessage _message;

}