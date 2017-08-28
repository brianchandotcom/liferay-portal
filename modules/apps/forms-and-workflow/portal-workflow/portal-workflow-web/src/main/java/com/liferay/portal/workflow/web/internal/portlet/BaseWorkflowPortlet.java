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
package com.liferay.portal.workflow.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.web.internal.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.web.internal.request.prepocessor.WorkflowDefinitionLinkRenderPreprocessor;
import com.liferay.portal.workflow.web.internal.request.prepocessor.WorkflowDefinitionRenderPreprocessor;
import com.liferay.portal.workflow.web.internal.request.prepocessor.WorkflowInstanceDispatchPreprocessor;
import com.liferay.portal.workflow.web.internal.request.prepocessor.WorkflowInstanceProcessActionPreprocessor;
import com.liferay.portal.workflow.web.internal.request.prepocessor.WorkflowInstanceRenderPreprocessor;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
public abstract class BaseWorkflowPortlet extends MVCPortlet {

	public String getDefaultTab() {
		if (isWorkflowDefinitionTabVisible()) {
			return WorkflowWebKeys.WORKFLOW_TAB_DEFINITION;
		}

		if (isWorkflowDefinitionLinkTabVisible()) {
			return WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK;
		}

		return WorkflowWebKeys.WORKFLOW_TAB_INSTANCE;
	}

	public abstract boolean isWorkflowDefinitionLinkTabVisible();

	public abstract boolean isWorkflowDefinitionTabVisible();

	public abstract boolean isWorkflowInstanceTabVisible();

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		setWorkflowTabsVisibilityPortletRequestAttribute(actionRequest);

		if (isWorkflowDefinitionTabVisible()) {
			workflowInstanceProcessActionPreprocessor.prepareProcessAction(
				actionRequest, actionResponse);
		}

		super.processAction(actionRequest, actionResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		setWorkflowTabsVisibilityPortletRequestAttribute(renderRequest);

		if (isWorkflowDefinitionLinkTabVisible()) {
			workflowDefinitionLinkRenderPreprocessor.prepareRender(
				renderRequest, renderResponse);
		}

		if (isWorkflowDefinitionTabVisible()) {
			workflowDefinitionRenderPreprocessor.prepareRender(
				renderRequest, renderResponse);
		}

		if (isWorkflowInstanceTabVisible()) {
			workflowInstanceRenderPreprocessor.prepareRender(
				renderRequest, renderResponse);
		}

		super.render(renderRequest, renderResponse);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, WorkflowException.class.getName())) {

			include("/instance/error.jsp", renderRequest, renderResponse);
		}
		else {
			workflowInstanceDispatchPreprocessor.prepareDispatch(
				renderRequest, renderResponse);

			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected void setWorkflowTabsVisibilityPortletRequestAttribute(
		PortletRequest portletRequest) {

		portletRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_DEFAULT_TAB, getDefaultTab());
		portletRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_VISIBILITY_DEFINITION,
			isWorkflowDefinitionTabVisible());
		portletRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_VISIBILITY_DEFINITION_LINK,
			isWorkflowDefinitionLinkTabVisible());
		portletRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_VISIBILITY_INSTANCE,
			isWorkflowInstanceTabVisible());
	}

	@Reference(unbind = "-")
	protected WorkflowDefinitionLinkRenderPreprocessor
		workflowDefinitionLinkRenderPreprocessor;

	@Reference(unbind = "-")
	protected WorkflowDefinitionRenderPreprocessor
		workflowDefinitionRenderPreprocessor;

	@Reference(unbind = "-")
	protected WorkflowInstanceDispatchPreprocessor
		workflowInstanceDispatchPreprocessor;

	@Reference(unbind = "-")
	protected WorkflowInstanceProcessActionPreprocessor
		workflowInstanceProcessActionPreprocessor;

	@Reference(unbind = "-")
	protected WorkflowInstanceRenderPreprocessor
		workflowInstanceRenderPreprocessor;

}