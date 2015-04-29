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

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.util.PortletKeys;
import com.liferay.workflow.definition.web.portlet.action.ActionUtil;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.icon=/icons/workflow_definition.png",
		"com.liferay.portlet.control-panel-entry-category=configuration",
		"com.liferay.portlet.control-panel-entry-weight=4.0",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.css-class-wrapper=portlet-workflow-definitions",
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
			
			ActionUtil.getWorkflowDefinition(request);
		} catch (Exception e) {
			if (e instanceof WorkflowException) {
				hideDefaultErrorMessage(request);
				
				SessionErrors.add(request, e.getClass());

				include("/error.jsp", request, response);

			} else {
				throw new PortletException(e);
			}
		}

		super.render(request, response);
	}
	
	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof WorkflowDefinitionFileException ||
			cause instanceof WorkflowException ||
			cause instanceof RequiredWorkflowDefinitionException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}
	
}
