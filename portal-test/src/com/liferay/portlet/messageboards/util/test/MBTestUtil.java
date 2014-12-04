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

package com.liferay.portlet.messageboards.util.test;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 * @author Daniel Kocsis
 */
public class MBTestUtil {

	public static MBBan addBan(long groupId) throws Exception {
		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), TestPropsValues.getGroupId());

		return addBan(groupId, user.getUserId());
	}

	public static MBBan addBan(long groupId, long banUserId) throws Exception {
		return addBan(TestPropsValues.getUserId(), groupId, banUserId);
	}

	public static MBBan addBan(long userId, long groupId, long banUserId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return MBBanLocalServiceUtil.addBan(userId, banUserId, serviceContext);
	}

	public static MBCategory addCategory(long groupId) throws Exception {
		return addCategory(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
	}

	public static MBCategory addCategory(long groupId, long parentCategoryId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return addCategory(
			RandomTestUtil.randomString(), parentCategoryId, serviceContext);
	}

	public static MBCategory addCategory(ServiceContext serviceContext)
		throws Exception {

		return MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	public static MBCategory addCategory(
			String name, long parentCategoryId, ServiceContext serviceContext)
		throws Exception {

		return MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategoryId, name,
			RandomTestUtil.randomString(), serviceContext);
	}

	public static MBMessage addDiscussionMessage(
			long groupId, String className, long classPK)
		throws Exception {

		return addDiscussionMessage(
			TestPropsValues.getUser(), groupId, className, classPK);
	}

	public static MBMessage addDiscussionMessage(
			User user, long groupId, String className, long classPK)
		throws Exception {

		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				user.getUserId(), groupId, className, classPK,
				WorkflowConstants.STATUS_APPROVED);

		MBThread thread =  messageDisplay.getThread();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return MBMessageLocalServiceUtil.addDiscussionMessage(
			user.getUserId(), user.getFullName(), groupId, className, classPK,
			thread.getThreadId(), thread.getRootMessageId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(50),
			serviceContext);
	}

	public static MBMessage addMessage(long groupId) throws Exception {
		return addMessage(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
	}

	public static MBMessage addMessage(long groupId, long categoryId)
		throws Exception {

		return addMessage(groupId, categoryId, 0, 0);
	}

	public static MBMessage addMessage(
			long groupId, long categoryId, boolean approved)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addMessage(
			groupId, categoryId, StringPool.BLANK, approved, serviceContext);
	}

	public static MBMessage addMessage(
			long groupId, long categoryId, long threadId, long parentMessageId)
		throws Exception {

		long userId = TestPropsValues.getUserId();
		String userName = RandomTestUtil.randomString();
		String subject = RandomTestUtil.randomString();
		String body = RandomTestUtil.randomString();
		String format = MBMessageConstants.DEFAULT_FORMAT;
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();
		boolean anonymous = false;
		double priority = 0.0;
		boolean allowPingbacks = false;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return MBMessageLocalServiceUtil.addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);
	}

	public static MBMessage addMessage(
			long groupId, long categoryId, ServiceContext serviceContext)
		throws Exception {

		return addMessage(
			groupId, categoryId, StringPool.BLANK, false, serviceContext);
	}

	public static MBMessage addMessage(
			long groupId, long categoryId, String keywords, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		String subject = "subject";
		String body = "body";

		if (!Validator.isBlank(keywords)) {
			subject = keywords;
			body = keywords;
		}

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			serviceContext.getUserId(), RandomTestUtil.randomString(), groupId,
			categoryId, subject, body, serviceContext);

		if (!approved) {
			message = updateStatus(message, serviceContext);
		}

		return message;
	}

	public static MBMessage addMessageWithWorkflow(
			long groupId, boolean approved)
		throws Exception {

		return addMessageWithWorkflow(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, approved);
	}

	public static MBMessage addMessageWithWorkflow(
			long groupId, long categoryId, boolean approved)
		throws Exception {

		return addMessageWithWorkflowAndAttachments(
			groupId, categoryId, approved, null);
	}

	public static MBMessage addMessageWithWorkflowAndAttachments(
			long groupId, long categoryId, boolean approved,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return addMessage(
			groupId, categoryId, true, approved, inputStreamOVPs,
			serviceContext);
	}

	public static MBThreadFlag addThreadFlag(long groupId, MBThread thread)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		MBThreadFlagLocalServiceUtil.addThreadFlag(
			TestPropsValues.getUserId(), thread, serviceContext);

		return MBThreadFlagLocalServiceUtil.getThreadFlag(
			TestPropsValues.getUserId(), thread);
	}

	public static List<ObjectValuePair<String, InputStream>> getInputStreamOVPs(
		String fileName, Class<?> clazz, String keywords) {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(1);

		if (!fileName.startsWith(StringPool.SLASH)) {
			fileName = "dependencies/" + fileName;
		}

		InputStream inputStream = clazz.getResourceAsStream(fileName);

		ObjectValuePair<String, InputStream> inputStreamOVP = null;

		if (Validator.isBlank(keywords)) {
			inputStreamOVP = new ObjectValuePair<String, InputStream>(
				fileName, inputStream);
		}
		else {
			inputStreamOVP = new ObjectValuePair<String, InputStream>(
				keywords, inputStream);
		}

		inputStreamOVPs.add(inputStreamOVP);

		return inputStreamOVPs;
	}

	public static MBMessage updateDiscussionMessage(
			long userId, long groupId, long messageId, String className,
			long classPK)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");

		return MBMessageLocalServiceUtil.updateDiscussionMessage(
			userId, messageId, className, classPK,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(50),
			serviceContext);
	}

	public static MBMessage updateDiscussionMessage(
			long groupId, long messageId, String className, long classPK)
		throws Exception {

		return updateDiscussionMessage(
			TestPropsValues.getUserId(), groupId, messageId, className,
			classPK);
	}

	public static MBMessage updateMessage(MBMessage message, boolean approved)
		throws Exception {

		return updateMessage(
			message, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(50), approved);
	}

	public static MBMessage updateMessage(
			MBMessage message, String subject, String body, boolean approved)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(message.getGroupId());

			serviceContext.setCommand(Constants.UPDATE);
			serviceContext.setLayoutFullURL("http://localhost");
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			message = MBMessageLocalServiceUtil.updateMessage(
				TestPropsValues.getUserId(), message.getMessageId(), subject,
				body,
				Collections.<ObjectValuePair<String, InputStream>>emptyList(),
				Collections.<String>emptyList(), message.getPriority(),
				message.isAllowPingbacks(), serviceContext);

			if (approved) {
				message = updateStatus(message, serviceContext);
			}

			return message;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	protected static MBMessage addMessage(
			long groupId, long categoryId, boolean workflowEnabled,
			boolean approved,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			ServiceContext serviceContext)
		throws Exception {

		long userId = TestPropsValues.getUserId();
		String userName = RandomTestUtil.randomString();
		long threadId = 0;
		long parentMessageId = 0;
		String subject = RandomTestUtil.randomString();
		String body = RandomTestUtil.randomString();
		String format = MBMessageConstants.DEFAULT_FORMAT;
		boolean anonymous = false;
		double priority = 0.0;
		boolean allowPingbacks = false;

		if (inputStreamOVPs == null) {
			inputStreamOVPs = Collections.emptyList();
		}

		if (workflowEnabled) {
			if (approved) {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);
			}
			else {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);
			}
		}

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);

		return MBMessageLocalServiceUtil.getMessage(message.getMessageId());
	}

	protected static MBMessage updateStatus(
			MBMessage message, ServiceContext serviceContext)
		throws Exception {

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		workflowContext.put(WorkflowConstants.CONTEXT_URL, "http://localhost");

		message = MBMessageLocalServiceUtil.updateStatus(
			message.getUserId(), message.getMessageId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);

		return message;
	}

}