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

package com.liferay.message.boards.test.util;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 * @author Daniel Kocsis
 */
public class MBTestUtil {

	public static MBMessage addMessage(
			long groupId, long userId, long categoryId, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException {

		return MBMessageLocalServiceUtil.addMessage(
			userId, RandomTestUtil.randomString(), groupId, categoryId, subject,
			body, serviceContext);
	}

	public static MBMessage addMessage(
			long groupId, long userId, String subject, String body)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		return addMessage(
			groupId, userId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			subject, body, serviceContext);
	}

	public static MBMessage addMessage(String subject, String body)
		throws PortalException {

		return addMessage(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(), subject,
			body);
	}

	public static MBMessage addMessageWithWorkflow(
			long groupId, long categoryId, String subject, String body,
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			MBMessage message = addMessage(
				groupId, serviceContext.getUserId(), categoryId, subject, body,
				serviceContext);

			if (approved) {
				return updateStatus(message, serviceContext);
			}

			return message;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static List<ObjectValuePair<String, InputStream>> getInputStreamOVPs(
		String fileName, Class<?> clazz, String keywords) {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>(1);

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		ObjectValuePair<String, InputStream> inputStreamOVP = null;

		if (Validator.isBlank(keywords)) {
			inputStreamOVP = new ObjectValuePair<>(fileName, inputStream);
		}
		else {
			inputStreamOVP = new ObjectValuePair<>(keywords, inputStream);
		}

		inputStreamOVPs.add(inputStreamOVP);

		return inputStreamOVPs;
	}

	public static void populateNotificationsServiceContext(
			ServiceContext serviceContext, String command)
		throws Exception {

		serviceContext.setAttribute("entryURL", "http://localhost");

		if (Validator.isNotNull(command)) {
			serviceContext.setCommand(command);
		}

		serviceContext.setLayoutFullURL("http://localhost");
	}

	protected static MBMessage updateStatus(
			MBMessage message, ServiceContext serviceContext)
		throws Exception {

		Map<String, Serializable> workflowContext = new HashMap<>();

		workflowContext.put(WorkflowConstants.CONTEXT_URL, "http://localhost");

		message = MBMessageLocalServiceUtil.updateStatus(
			message.getUserId(), message.getMessageId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);

		return message;
	}

}