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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@ExecutionTestListeners(
	listeners = {
			EnvironmentExecutionTestListener.class,
			TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MessageBoardsAttachmentsTrashTest {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();
	}

	@Test
	@Transactional
	public void testTrashAndDelete() throws Exception {
		trashMessageBoardsAttachments(false);
	}

	@Test
	@Transactional
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

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			user.getUserId(), user.getFullName(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject", "Body",
			MBMessageConstants.DEFAULT_FORMAT,
			getInputStreamOVPs("company_logo.png"), false, 0, false,
			serviceContext);

		String[] attachmentsFiles = message.getAttachmentsFiles();

		int initialNotInTrashCount = attachmentsFiles.length;

		String[] deletedAttachmentsFiles = message.getDeletedAttachmentsFiles();

		int initialTrashEntriesCount = deletedAttachmentsFiles.length;

		String[] existingAttachments = DLStoreUtil.getFileNames(
			message.getCompanyId(), CompanyConstants.SYSTEM,
			message.getAttachmentsDir());

		List<String> existingFiles = new ArrayList<String>();

		for (int i = 0; i < existingAttachments.length; i++) {
			existingFiles.add(existingAttachments[i]);
		}

		message = MBMessageLocalServiceUtil.updateMessage(
			user.getUserId(), message.getMessageId(), "Subject", "Body",
			getInputStreamOVPs("OSX_Test.docx"), existingFiles, 0, false,
			serviceContext);

		attachmentsFiles = message.getAttachmentsFiles();

		Assert.assertEquals(
			initialNotInTrashCount + 1, attachmentsFiles.length);

		deletedAttachmentsFiles = message.getDeletedAttachmentsFiles();

		Assert.assertEquals(
			initialTrashEntriesCount, deletedAttachmentsFiles.length);

		String fileName = attachmentsFiles[0];

		MBMessageLocalServiceUtil.moveMessageAttachmentToTrash(
			message.getMessageId(), fileName);

		attachmentsFiles = message.getAttachmentsFiles();

		Assert.assertEquals(initialNotInTrashCount, attachmentsFiles.length);

		deletedAttachmentsFiles = message.getDeletedAttachmentsFiles();

		Assert.assertEquals(
			initialTrashEntriesCount + 1, deletedAttachmentsFiles.length);

		fileName = deletedAttachmentsFiles[0];

		if (restore) {
			MBMessageLocalServiceUtil.moveMessageAttachmentFromTrash(
				message.getMessageId(), fileName);

			attachmentsFiles = message.getAttachmentsFiles();

			Assert.assertEquals(
				initialNotInTrashCount + 1, attachmentsFiles.length);

			deletedAttachmentsFiles = message.getDeletedAttachmentsFiles();

			Assert.assertEquals(
				initialTrashEntriesCount, deletedAttachmentsFiles.length);
		}
		else {
			MBMessageLocalServiceUtil.deleteMessageAttachment(
				message.getMessageId(), fileName);
		}
	}

	private Group _group;

}