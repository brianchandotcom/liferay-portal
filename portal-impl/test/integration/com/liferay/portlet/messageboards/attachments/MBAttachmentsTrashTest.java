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
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
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
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBAttachmentsTrashTest {

	@Before
	public void setUp() throws Exception {
		User user = TestPropsValues.getUser();

		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(_group.getGroupId());

		_message = MBMessageLocalServiceUtil.addMessage(
			user.getUserId(), user.getFullName(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject", "Body",
			MBMessageConstants.DEFAULT_FORMAT,
			_getInputStreamOVPs("company_logo.png"), false, 0, false,
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
		_trashMBAttachments(false);
	}

	@Test
	public void testTrashAndRestore() throws Exception {
		_trashMBAttachments(true);
	}

	private List<ObjectValuePair<String, InputStream>> _getInputStreamOVPs(
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

	private void _trashMBAttachments(boolean restore) throws Exception {
		int initialNotInTrashCount = _message.getAttachmentsFileEntriesCount();
		int initialTrashEntriesCount =
			_message.getDeletedAttachmentsFileEntriesCount();

		List<FileEntry> fileEntries = _message.getAttachmentsFileEntries();

		List<String> existingFiles = new ArrayList<String>();

		for (FileEntry fileEntry : fileEntries) {
			existingFiles.add(String.valueOf(fileEntry.getFileEntryId()));
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(_group.getGroupId());

		String fileName = "OSX_Test.docx";

		_message = MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), _message.getMessageId(), "Subject",
			"Body", _getInputStreamOVPs(fileName), existingFiles, 0, false,
			serviceContext);

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

	private Group _group;
	private MBMessage _message;

}