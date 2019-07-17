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

package com.liferay.portal.workflow.web.internal.portlet.configuration.icon;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Defines the icon triggering the deletion of a workflow definition.
 *
 * @author Lino Alves
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
		"path=/definition/edit_workflow_definition.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class DeleteDefinitionPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(getLocale(portletRequest), "delete");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String portletNamespace = _portal.getPortletNamespace(
			_portal.getPortletId(portletRequest));

		String deleteURL = getURL(portletRequest, portletResponse);

		StringBundler sb = new StringBundler(4);

		sb.append(portletNamespace);
		sb.append("confirmDeleteDefinition('");
		sb.append(deleteURL);
		sb.append("'); return false;");

		return sb.toString();
	}

	/**
	 * Creates and returns an action URL, setting the workflow definition name
	 * and version as URL parameters.
	 *
	 * @param portletRequest the portlet request from which to get the workflow
	 *        definition name and version
	 * @param portletResponse the portlet response
	 */
	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			portletRequest, WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteWorkflowDefinition");
		portletURL.setParameter("name", portletRequest.getParameter("name"));
		portletURL.setParameter(
			"version", portletRequest.getParameter("version"));

		return portletURL.toString();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		WorkflowDefinition workflowDefinition =
			(WorkflowDefinition)portletRequest.getAttribute(
				WebKeys.WORKFLOW_DEFINITION);

		boolean unpublished = false;

		if ((workflowDefinition != null) && !workflowDefinition.isActive()) {
			unpublished = true;
		}

		return unpublished;
	}

	@Reference
	private Portal _portal;

}