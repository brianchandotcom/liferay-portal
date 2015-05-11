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

package com.liferay.workflow.task.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.workflow.task.web.portlet.constants.WorkflowTaskConstants;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Leonardo Barros
 */
public abstract class WorkflowTaskBaseActionCommand extends BaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		String redirect = ParamUtil.getString(
			portletRequest, WorkflowTaskConstants.REDIRECT);

		String closeRedirect = ParamUtil.getString(
			portletRequest, WorkflowTaskConstants.CLOSE_REDIRECT);

		if (Validator.isNotNull(closeRedirect)) {
			redirect = HttpUtil.setParameter(
				redirect, WorkflowTaskConstants.CLOSE_REDIRECT, closeRedirect);

			SessionMessages.add(
				portletRequest, PortalUtil.getPortletId(portletRequest) +
					SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT, closeRedirect);
		}

		portletRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	protected long getCompanyId(PortletRequest portletRequest) {
		return getThemeDisplay(portletRequest).getCompanyId();
	}

	protected ThemeDisplay getThemeDisplay(PortletRequest portletRequest) {
		return (ThemeDisplay)portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	protected long getUserId(PortletRequest portletRequest) {
		return getThemeDisplay(portletRequest).getUserId();
	}

}