/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.instance.tracker.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(
	property = {
		"jakarta.portlet.name=" + WorkflowPortletKeys.WORKFLOW_INSTANCE_TRACKER,
		"mvc.command.name=/workflow_instance_tracker/get_workflow_definition_info"
	},
	service = MVCResourceCommand.class
)
public class GetWorkflowDefinitionInfoMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.liberalGetWorkflowDefinition(
				themeDisplay.getCompanyId(),
				ParamUtil.getString(resourceRequest, "workflowDefinitionName"),
				ParamUtil.getInteger(
					resourceRequest, "workflowDefinitionVersion"));

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"nodes",
				() -> JSONUtil.toJSONArray(
					workflowDefinition.getWorkflowNodes(),
					workflowNode -> JSONUtil.put(
						"label",
						workflowNode.getLabel(resourceRequest.getLocale())
					).put(
						"name", workflowNode.getName()
					).put(
						"type", workflowNode.getType()
					))
			).put(
				"transitions",
				() -> JSONUtil.toJSONArray(
					workflowDefinition.getWorkflowTransitions(),
					workflowTransition -> JSONUtil.put(
						"label",
						workflowTransition.getLabel(resourceRequest.getLocale())
					).put(
						"name", workflowTransition.getName()
					).put(
						"sourceNodeName", workflowTransition.getSourceNodeName()
					).put(
						"targetNodeName", workflowTransition.getTargetNodeName()
					))
			));
	}

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}