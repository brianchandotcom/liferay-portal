/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.rest.internal.resource.v1_0.helper;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowState;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTransition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.RoleAssignment;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.definition.export.DefinitionExporter;
import com.liferay.portal.workflow.kaleo.definition.export.builder.DefinitionBuilder;

import java.util.Collections;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = AppWorkflowResourceHelper.class)
public class AppWorkflowResourceHelper {

	public WorkflowDefinition deployWorkflowDefinition(
			AppBuilderApp appBuilderApp, long companyId, Definition definition,
			long userId)
		throws PortalException {

		String content = _definitionExporter.export(definition);

		return _workflowDefinitionManager.deployWorkflowDefinition(
			companyId, userId, appBuilderApp.getName(), appBuilderApp.getUuid(),
			AppBuilderApp.class.getSimpleName(), content.getBytes());
	}

	public Definition getDefinition(Long appWorkflowId, long companyId)
		throws PortalException {

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
				companyId, 0, AppBuilderApp.class.getName(), appWorkflowId, 0);

		return _definitionBuilder.buildDefinition(
			companyId, workflowDefinitionLink.getWorkflowDefinitionName(),
			workflowDefinitionLink.getWorkflowDefinitionVersion());
	}

	public Definition toDefinition(
		AppBuilderApp appBuilderApp, AppWorkflow appWorkflow) {

		Definition definition = new Definition(
			appBuilderApp.getUuid(), StringPool.BLANK, StringPool.BLANK, 0);

		for (AppWorkflowState appWorkflowState :
				appWorkflow.getAppWorkflowStates()) {

			definition.addNode(
				new State(
					appWorkflowState.getName(), StringPool.BLANK,
					appWorkflowState.getInitial()));
		}

		if (Objects.nonNull(appWorkflow.getAppWorkflowTasks())) {
			for (AppWorkflowTask appWorkflowTask :
					appWorkflow.getAppWorkflowTasks()) {

				Task task = new Task(
					appWorkflowTask.getName(), StringPool.BLANK);

				task.setAssignments(
					Collections.singleton(
						new RoleAssignment(
							RoleConstants.PORTAL_CONTENT_REVIEWER,
							RoleConstants.TYPE_REGULAR_LABEL)));

				definition.addNode(task);
			}
		}

		for (AppWorkflowState appWorkflowState :
				appWorkflow.getAppWorkflowStates()) {

			_addTransitions(
				appWorkflowState.getAppWorkflowTransitions(), definition,
				appWorkflowState.getName());
		}

		if (Objects.nonNull(appWorkflow.getAppWorkflowTasks())) {
			for (AppWorkflowTask appWorkflowTask :
					appWorkflow.getAppWorkflowTasks()) {

				_addTransitions(
					appWorkflowTask.getAppWorkflowTransitions(), definition,
					appWorkflowTask.getName());
			}
		}

		return definition;
	}

	private void _addTransition(
		boolean defaultTransition, String name, Node sourceNode,
		Node targetNode) {

		Transition transition = new Transition(
			name, sourceNode, targetNode, defaultTransition);

		sourceNode.addOutgoingTransition(transition);

		targetNode.addIncomingTransition(transition);
	}

	private void _addTransitions(
		AppWorkflowTransition[] appWorkflowTransitions, Definition definition,
		String sourceNodeName) {

		for (AppWorkflowTransition appWorkflowTransition :
				appWorkflowTransitions) {

			_addTransition(
				appWorkflowTransition.getPrimary(),
				appWorkflowTransition.getName(),
				definition.getNode(sourceNodeName),
				definition.getNode(appWorkflowTransition.getTransitionTo()));
		}
	}

	@Reference
	private DefinitionBuilder _definitionBuilder;

	@Reference
	private DefinitionExporter _definitionExporter;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}