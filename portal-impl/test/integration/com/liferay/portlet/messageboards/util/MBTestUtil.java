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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Eudaldo Alonso
 * @author Daniel Kocsis
 */
public class MBTestUtil {

	public static MBBan addBan(long groupId) throws Exception {
		User user = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), TestPropsValues.getGroupId());

		return addBan(groupId, user.getUserId());
	}

	public static MBBan addBan(long groupId, long banUserId) throws Exception {
		return addBan(TestPropsValues.getUserId(), groupId, banUserId);
	}

	public static MBBan addBan(long userId, long groupId, long banUserId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return MBBanLocalServiceUtil.addBan(userId, banUserId, serviceContext);
	}

	public static MBCategory addCategory(long groupId) throws Exception {
		return addCategory(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
	}

	public static MBCategory addCategory(long groupId, long parentCategoryId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addCategory(
			ServiceTestUtil.randomString(), parentCategoryId, serviceContext);
	}

	public static MBCategory addCategory(ServiceContext serviceContext)
		throws Exception {

		return MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	public static MBCategory addCategory(
			String name, long parentCategoryId, ServiceContext serviceContext)
		throws Exception {

		return MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategoryId, name,
			ServiceTestUtil.randomString(), serviceContext);
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
			long groupId, long categoryId, long threadId, long parentMessageId)
		throws Exception {

		long userId = TestPropsValues.getUserId();
		String userName = ServiceTestUtil.randomString();
		String subject = ServiceTestUtil.randomString();
		String body = ServiceTestUtil.randomString();
		String format = MBMessageConstants.DEFAULT_FORMAT;
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();
		boolean anonymous = false;
		double priority = 0.0;
		boolean allowPingbacks = false;

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return MBMessageLocalServiceUtil.addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);
	}

	public static MBMessage addMessage(
			long categoryId, ServiceContext serviceContext)
		throws Exception {

		return addMessage(categoryId, StringPool.BLANK, false, serviceContext);
	}

	public static MBMessage addMessage(
			long categoryId, String keywords, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		String subject = "subject";
		String body = "body";

		if (!Validator.isBlank(keywords)) {
			subject = keywords;
			body = keywords;
		}

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			categoryId, subject, body, serviceContext);

		if (!approved) {
			return MBMessageLocalServiceUtil.updateStatus(
				message.getStatusByUserId(), message.getMessageId(),
				WorkflowConstants.STATUS_DRAFT, serviceContext);
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

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addMessage(
			groupId, categoryId, true, approved, inputStreamOVPs,
			serviceContext);
	}

	public static MBThreadFlag addThreadFlag(long groupId, MBThread thread)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		MBThreadFlagLocalServiceUtil.addThreadFlag(
			TestPropsValues.getUserId(), thread, serviceContext);

		return MBThreadFlagLocalServiceUtil.getThreadFlag(
			TestPropsValues.getUserId(), thread);
	}

	public static List<ObjectValuePair<String, InputStream>> getInputStreamOVPs(
		String fileName, Class<?> clazz, String keywords) {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(1);

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

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

	protected static MBMessage addMessage(
			long groupId, long categoryId, boolean workflowEnabled,
			boolean approved,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			ServiceContext serviceContext)
		throws Exception {

		long userId = TestPropsValues.getUserId();
		String userName = ServiceTestUtil.randomString();
		long threadId = 0;
		long parentMessageId = 0;
		String subject = ServiceTestUtil.randomString();
		String body = ServiceTestUtil.randomString();
		String format = MBMessageConstants.DEFAULT_FORMAT;
		boolean anonymous = false;
		double priority = 0.0;
		boolean allowPingbacks = false;

		if (inputStreamOVPs == null) {
			inputStreamOVPs = Collections.emptyList();
		}

		if (workflowEnabled) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			if (approved) {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);
			}
		}

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);

		return MBMessageLocalServiceUtil.getMessage(message.getMessageId());
	}

}