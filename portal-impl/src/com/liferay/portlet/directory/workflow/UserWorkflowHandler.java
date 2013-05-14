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

package com.liferay.portlet.directory.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class UserWorkflowHandler extends BaseWorkflowHandler {

	@Override
	public String getClassName() {
		return User.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public boolean isScopeable() {
		return false;
	}

	@Override
	public Object updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		User user = UserLocalServiceUtil.getUser(userId);

		if (((user.getStatus() == WorkflowConstants.STATUS_DRAFT) ||
			 (user.getStatus() == WorkflowConstants.STATUS_PENDING)) &&
			(status == WorkflowConstants.STATUS_APPROVED)) {

			ServiceContext serviceContext = (ServiceContext)workflowContext.get(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT);

			UserLocalServiceUtil.completeUserRegistration(user, serviceContext);

			serviceContext.setAttribute(
				"passwordUnencrypted", user.getPasswordUnencrypted());
		}

		return UserLocalServiceUtil.updateStatus(userId, status);
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/user_icon.png";
	}

}