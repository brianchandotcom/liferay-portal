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

package com.liferay.workflow.definition.web.portlet.action;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bruno Farache
 * @author Leonardo Barros
 */
public class ActionUtil {

	public static void getWorkflowDefinition(HttpServletRequest request)
		throws Exception {

		if (request.getAttribute(WebKeys.WORKFLOW_DEFINITION) != null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(request, _NAME);
		int version = ParamUtil.getInteger(request, _VERSION);

		List<WorkflowDefinition> workflowDefinitions =
			WorkflowDefinitionManagerUtil.getWorkflowDefinitions(
				themeDisplay.getCompanyId(), name, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			if (version == workflowDefinition.getVersion()) {
				request.setAttribute(
					WebKeys.WORKFLOW_DEFINITION, workflowDefinition);

				break;
			}
		}
	}

	public static void getWorkflowDefinition(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getWorkflowDefinition(request);
	}

	public static void getWorkflowDefinitions(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		List<WorkflowDefinition> workflowDefinitions =
			WorkflowDefinitionManagerUtil.getWorkflowDefinitions(
				company.getCompanyId(), 0, 100, null);

		request.setAttribute(WebKeys.WORKFLOW_DEFINITIONS, workflowDefinitions);
	}

	public static void getWorkflowDefinitions(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getWorkflowDefinitions(request);
	}

	private static final String _NAME = "name";

	private static final String _VERSION = "version";

}