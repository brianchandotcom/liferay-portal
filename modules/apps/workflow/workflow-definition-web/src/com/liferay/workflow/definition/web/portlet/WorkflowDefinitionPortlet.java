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

package com.liferay.workflow.definition.web.portlet;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.workflow.definition.web.portlet.constants.WorkflowDefinitionConstants;

import java.io.IOException;

import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.control-panel-entry-category=configuration",
		"com.liferay.portlet.control-panel-entry-weight=4.0",
		"com.liferay.portlet.css-class-wrapper=portlet-workflow-definitions",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/workflow_definition.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Workflow Definition",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PortletKeys.WORKFLOW_DEFINITIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = { WorkflowDefinitionPortlet.class, Portlet.class }
)
public class WorkflowDefinitionPortlet extends MVCPortlet {

	@Override
	public void render(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		try {
			setWorkflowDefinitionRenderRequestAttribute(request);
		}
		catch (Exception e) {
			if (isSessionErrorException(e)) {
				hideDefaultErrorMessage(request);

				SessionErrors.add(request, e.getClass());
			}
			else {
				throw new PortletException(e);
			}
		}

		super.render(request, response);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, WorkflowException.class.getName())) {

			hideDefaultErrorMessage(renderRequest);

			include("/error.jsp", renderRequest, renderResponse);
		}
		else if (SessionErrors.contains(
					renderRequest,
					RequiredWorkflowDefinitionException.class.getName())) {

			hideDefaultErrorMessage(renderRequest);

			include("/view.jsp", renderRequest, renderResponse);
		}
		else {
			if (SessionErrors.contains(
					renderRequest,
					WorkflowDefinitionFileException.class.getName())) {

				hideDefaultErrorMessage(renderRequest);
			}

			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof WorkflowException ||
			cause instanceof WorkflowDefinitionFileException ||
			cause instanceof RequiredWorkflowDefinitionException) {

			return true;
		}

		return false;
	}

	protected void setWorkflowDefinitionRenderRequestAttribute(
			RenderRequest renderRequest)
		throws PortalException {

		Object workflowDefinitionAttr = renderRequest.getAttribute(
			WebKeys.WORKFLOW_DEFINITION);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Validator.isNull(workflowDefinitionAttr)) {
			String name = ParamUtil.getString(
				renderRequest, WorkflowDefinitionConstants.NAME);
			int version = ParamUtil.getInteger(
				renderRequest, WorkflowDefinitionConstants.VERSION);

			List<WorkflowDefinition> workflowDefinitions =
				WorkflowDefinitionManagerUtil.getWorkflowDefinitions(
					themeDisplay.getCompanyId(), name, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
				if (version == workflowDefinition.getVersion()) {
					renderRequest.setAttribute(
						WebKeys.WORKFLOW_DEFINITION, workflowDefinition);

					break;
				}
			}
		}

		List<WorkflowDefinition> workflowDefinitions =
			WorkflowDefinitionManagerUtil.getWorkflowDefinitions(
				themeDisplay.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		renderRequest.setAttribute(
			WebKeys.WORKFLOW_DEFINITIONS, workflowDefinitions);
	}

}