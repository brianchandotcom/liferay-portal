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

import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
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

	@Test
	@Transactional
	public void testTrashMessageBoardsAttachments() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(TestPropsValues.getGroupId());

		User user = TestPropsValues.getUser();

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			user.getUserId(), user.getFullName(), TestPropsValues.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Message Subject",
			"Message Body", MBMessageConstants.DEFAULT_FORMAT,
			getInputStreamOVPs("company_logo.png"), false, 0, false,
			serviceContext);

		int initialNotInTrashCount = message.getAttachmentsFiles().length;
		int initialTrashEntriesCount =
			message.getDeletedAttachmentsFiles().length;

		String[] existingAttachments = DLStoreUtil.getFileNames(
			message.getCompanyId(), CompanyConstants.SYSTEM,
			message.getAttachmentsDir());

		List<String> existingFiles = new ArrayList<String>();

		for (int i = 0; i < existingAttachments.length; i++) {
			existingFiles.add(existingAttachments[i]);
		}

		message = MBMessageLocalServiceUtil.updateMessage(
			user.getUserId(), message.getMessageId(), "Message Subject",
			"Message Body", getInputStreamOVPs("OSX_Test.docx"),
			existingFiles, 0, false, serviceContext);

		Assert.assertEquals(
			initialTrashEntriesCount,
			message.getDeletedAttachmentsFiles().length);

		Assert.assertEquals(
			initialNotInTrashCount + 1, message.getAttachmentsFiles().length);

		String fileName = message.getAttachmentsFiles()[0];

		MBMessageLocalServiceUtil.moveMessageAttachmentToTrash(
			message.getMessageId(), fileName);

		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			message.getDeletedAttachmentsFiles().length);

		Assert.assertEquals(
			initialNotInTrashCount, message.getAttachmentsFiles().length);

		fileName = message.getDeletedAttachmentsFiles()[0];

		MBMessageLocalServiceUtil.moveMessageAttachmentFromTrash(
			message.getMessageId(), fileName);

		Assert.assertEquals(
			initialTrashEntriesCount,
			message.getDeletedAttachmentsFiles().length);

		Assert.assertEquals(
			initialNotInTrashCount + 1, message.getAttachmentsFiles().length);
	}

	private List<ObjectValuePair<String, InputStream>> getInputStreamOVPs(
		String fileName) {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(1);

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		ObjectValuePair<String, InputStream> inputStreamOVP =
			new ObjectValuePair<String, InputStream>(
				fileName, inputStream);

		inputStreamOVPs.add(inputStreamOVP);

		return inputStreamOVPs;
	}

}