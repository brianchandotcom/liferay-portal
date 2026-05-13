/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.parser;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.security.script.management.configuration.helper.ScriptManagementConfigurationHelper;
import com.liferay.portal.workflow.kaleo.definition.Action;
import com.liferay.portal.workflow.kaleo.definition.Assignment;
import com.liferay.portal.workflow.kaleo.definition.Condition;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.Recipient;
import com.liferay.portal.workflow.kaleo.definition.ScriptAction;
import com.liferay.portal.workflow.kaleo.definition.ScriptAssignment;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;
import com.liferay.portal.workflow.kaleo.definition.ScriptRecipient;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.Timer;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowValidator;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
@Component(service = WorkflowValidator.class)
public class XMLWorkflowValidator implements WorkflowValidator {

	@Override
	public void validate(Definition definition) throws WorkflowException {
		State initialState = definition.getInitialState();

		if (initialState == null) {
			throw new KaleoDefinitionValidationException.
				MustSetInitialStateNode();
		}

		List<State> terminalStates = definition.getTerminalStates();

		if (terminalStates.isEmpty()) {
			throw new KaleoDefinitionValidationException.
				MustSetTerminalStateNode();
		}

		if (!_scriptManagementConfigurationHelper.
				isAllowScriptContentToBeExecutedOrIncluded()) {

			_validateScriptLanguages(definition);
		}

		if (definition.getForksCount() != definition.getJoinsCount()) {
			throw new KaleoDefinitionValidationException.
				UnbalancedForkAndJoinNodes();
		}

		Collection<Node> nodes = definition.getNodes();

		for (Node node : nodes) {
			NodeValidator<Node> nodeValidator = _serviceTrackerMap.getService(
				node.getNodeType());

			nodeValidator.validate(definition, node);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<NodeValidator<Node>>)(Class<?>)NodeValidator.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(nodeValidator, emitter) -> emitter.emit(
					nodeValidator.getNodeType())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _validateScriptLanguage(
			Definition definition, ScriptLanguage scriptLanguage)
		throws KaleoDefinitionValidationException {

		if (scriptLanguage == ScriptLanguage.GROOVY) {
			throw new KaleoDefinitionValidationException.
				NotAllowedScriptLanguage("Groovy is not allowed");
		}

		if ((scriptLanguage == ScriptLanguage.JAVA) &&
			!Objects.equals(
				definition.getName(),
				"Message Board Threads and Comments Reputation Approver")) {

			throw new KaleoDefinitionValidationException.
				NotAllowedScriptLanguage("Java is not allowed");
		}
	}

	private void _validateScriptLanguages(Definition definition)
		throws KaleoDefinitionValidationException {

		for (Node node : definition.getNodes()) {
			_validateScriptLanguagesInActions(node.getActions(), definition);
			_validateScriptLanguagesInNotifications(
				definition, node.getNotifications());

			for (Timer timer : node.getTimers()) {
				_validateScriptLanguagesInActions(
					timer.getActions(), definition);
				_validateScriptLanguagesInAssignments(
					timer.getReassignments(), definition);
				_validateScriptLanguagesInNotifications(
					definition, timer.getNotifications());
			}

			for (Transition transition :
					node.getOutgoingTransitions(
					).values()) {

				Timer timer = transition.getTimer();

				if (timer == null) {
					continue;
				}

				_validateScriptLanguagesInActions(
					timer.getActions(), definition);
				_validateScriptLanguagesInAssignments(
					timer.getReassignments(), definition);
				_validateScriptLanguagesInNotifications(
					definition, timer.getNotifications());
			}

			if (node instanceof Condition) {
				Condition condition = (Condition)node;

				_validateScriptLanguage(
					definition, condition.getScriptLanguage());
			}

			if (node instanceof Task) {
				Task task = (Task)node;

				_validateScriptLanguagesInAssignments(
					task.getAssignments(), definition);
			}
		}
	}

	private void _validateScriptLanguagesInActions(
			Set<Action> actions, Definition definition)
		throws KaleoDefinitionValidationException {

		for (Action action : actions) {
			if (action instanceof ScriptAction) {
				ScriptAction scriptAction = (ScriptAction)action;

				_validateScriptLanguage(
					definition, scriptAction.getScriptLanguage());
			}
		}
	}

	private void _validateScriptLanguagesInAssignments(
			Set<Assignment> assignments, Definition definition)
		throws KaleoDefinitionValidationException {

		if (assignments == null) {
			return;
		}

		for (Assignment assignment : assignments) {
			if (assignment instanceof ScriptAssignment) {
				ScriptAssignment scriptAssignment =
					(ScriptAssignment)assignment;

				_validateScriptLanguage(
					definition, scriptAssignment.getScriptLanguage());
			}
		}
	}

	private void _validateScriptLanguagesInNotifications(
			Definition definition, Set<Notification> notifications)
		throws KaleoDefinitionValidationException {

		for (Notification notification : notifications) {
			for (Set<Recipient> recipients :
					notification.getRecipientsMap(
					).values()) {

				for (Recipient recipient : recipients) {
					if (recipient instanceof ScriptRecipient) {
						ScriptRecipient scriptRecipient =
							(ScriptRecipient)recipient;

						_validateScriptLanguage(
							definition, scriptRecipient.getScriptLanguage());
					}
				}
			}
		}
	}

	@Reference
	private ScriptManagementConfigurationHelper
		_scriptManagementConfigurationHelper;

	private ServiceTrackerMap<NodeType, NodeValidator<Node>> _serviceTrackerMap;

}