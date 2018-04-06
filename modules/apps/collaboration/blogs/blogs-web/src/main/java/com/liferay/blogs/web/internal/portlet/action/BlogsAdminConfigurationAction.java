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

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.portlet.BaseJSPSettingsConfigurationAction;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
	service = ConfigurationAction.class
)
public class BlogsAdminConfigurationAction
	extends BaseJSPSettingsConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/blogs_admin/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNotNull(cmd)) {
			validateEmail(actionRequest, "emailEntryAdded");
			validateEmail(actionRequest, "emailEntryUpdated");
			validateEmailFrom(actionRequest);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.blogs.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}