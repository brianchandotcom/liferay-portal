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

package com.liferay.knowledge.base.web.portlet.action;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.portal.kernel.portlet.BaseJSPSettingsConfigurationAction;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
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
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN},
	service = ConfigurationAction.class
)
public class AdminConfigurationAction
	extends BaseJSPSettingsConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest request) {
		return "/admin/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");

		if (tabs2.equals("article-added-email")) {
			validateEmailKBArticleAdded(actionRequest);
		}
		else if (tabs2.equals("article-updated-email")) {
			validateEmailKBArticleUpdated(actionRequest);
		}
		else if (tabs2.equals("email-from")) {
			validateEmailFrom(actionRequest);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.knowledge.base.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	protected boolean isVariableTerm(String s) {
		if (s.contains("[$") && s.contains("$]")) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void validateEmailFrom(ActionRequest actionRequest) {
		String emailFromName = getParameter(actionRequest, "emailFromName");
		String emailFromAddress = getParameter(
			actionRequest, "emailFromAddress");

		if (Validator.isNull(emailFromName)) {
			SessionErrors.add(actionRequest, "emailFromName");
		}
		else if (!Validator.isEmailAddress(emailFromAddress) &&
				 !isVariableTerm(emailFromAddress)) {

			SessionErrors.add(actionRequest, "emailFromAddress");
		}
	}

	protected void validateEmailKBArticleAdded(ActionRequest actionRequest) {
		String emailKBArticleAddedSubject = getParameter(
			actionRequest, "emailKBArticleAddedSubject");
		String emailKBArticleAddedBody = getParameter(
			actionRequest, "emailKBArticleAddedBody");

		if (Validator.isNull(emailKBArticleAddedSubject)) {
			SessionErrors.add(actionRequest, "emailKBArticleAddedSubject");
		}
		else if (Validator.isNull(emailKBArticleAddedBody)) {
			SessionErrors.add(actionRequest, "emailKBArticleAddedBody");
		}
	}

	protected void validateEmailKBArticleUpdated(ActionRequest actionRequest) {
		String emailKBArticleUpdatedSubject = getParameter(
			actionRequest, "emailKBArticleUpdatedSubject");
		String emailKBArticleUpdatedBody = getParameter(
			actionRequest, "emailKBArticleUpdatedBody");

		if (Validator.isNull(emailKBArticleUpdatedSubject)) {
			SessionErrors.add(actionRequest, "emailKBArticleUpdatedSubject");
		}
		else if (Validator.isNull(emailKBArticleUpdatedBody)) {
			SessionErrors.add(actionRequest, "emailKBArticleUpdatedBody");
		}
	}

}