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

package com.liferay.portal.workflow.kaleo.definition.internal.parser;

import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.workflow.kaleo.definition.Action;
import com.liferay.portal.workflow.kaleo.definition.ActionAware;
import com.liferay.portal.workflow.kaleo.definition.AddressRecipient;
import com.liferay.portal.workflow.kaleo.definition.AssigneesRecipient;
import com.liferay.portal.workflow.kaleo.definition.Assignment;
import com.liferay.portal.workflow.kaleo.definition.Condition;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.DelayDuration;
import com.liferay.portal.workflow.kaleo.definition.DurationScale;
import com.liferay.portal.workflow.kaleo.definition.Fork;
import com.liferay.portal.workflow.kaleo.definition.Join;
import com.liferay.portal.workflow.kaleo.definition.JoinXor;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.NotificationAware;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.definition.ResourceActionAssignment;
import com.liferay.portal.workflow.kaleo.definition.RoleAssignment;
import com.liferay.portal.workflow.kaleo.definition.RoleRecipient;
import com.liferay.portal.workflow.kaleo.definition.ScriptAssignment;
import com.liferay.portal.workflow.kaleo.definition.ScriptRecipient;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.TaskForm;
import com.liferay.portal.workflow.kaleo.definition.TaskFormReference;
import com.liferay.portal.workflow.kaleo.definition.Timer;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.definition.UserAssignment;
import com.liferay.portal.workflow.kaleo.definition.UserRecipient;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowModelParser;

import java.io.InputStream;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
@Component(immediate = true, service = WorkflowModelParser.class)
public class XMLWorkflowModelParser implements WorkflowModelParser {

	@Override
	public Definition parse(InputStream inputStream) throws WorkflowException {
		try {
			Document document = SAXReaderUtil.read(inputStream, _validate);

			return _parse(document);
		}
		catch (Exception exception) {
			throw new WorkflowDefinitionFileException(
				"Unable to parse definition", exception);
		}
	}

	@Override
	public Definition parse(String content) throws WorkflowException {
		try {
			Document document = SAXReaderUtil.read(content, _validate);

			return _parse(document);
		}
		catch (Exception exception) {
			throw new WorkflowDefinitionFileException(
				"Unable to parse definition", exception);
		}
	}

	@Override
	public void setValidate(boolean validate) {
		_validate = validate;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_validate = GetterUtil.getBoolean(properties.get("validating"), true);
	}

	private Definition _parse(Document document) throws Exception {
		Element rootElement = document.getRootElement();

		String name = rootElement.elementTextTrim("name");
		String description = rootElement.elementTextTrim("description");
		int version = GetterUtil.getInteger(
			rootElement.elementTextTrim("version"));

		Definition definition = new Definition(
			name, description, document.formattedString(), version);

		List<Element> conditionElements = rootElement.elements("condition");

		for (Element conditionElement : conditionElements) {
			Condition condition = _parseCondition(conditionElement);

			definition.addNode(condition);
		}

		List<Element> forkElements = rootElement.elements("fork");

		for (Element forkElement : forkElements) {
			Fork fork = _parseFork(forkElement);

			definition.addNode(fork);
		}

		List<Element> joinElements = rootElement.elements("join");

		for (Element joinElement : joinElements) {
			Join join = _parseJoin(joinElement);

			definition.addNode(join);
		}

		List<Element> joinXorElements = rootElement.elements("join-xor");

		for (Element joinXorElement : joinXorElements) {
			JoinXor joinXor = _parseJoinXor(joinXorElement);

			definition.addNode(joinXor);
		}

		List<Element> stateElements = rootElement.elements("state");

		for (Element stateElement : stateElements) {
			State state = _parseState(stateElement);

			definition.addNode(state);
		}

		List<Element> taskElements = rootElement.elements("task");

		for (Element taskElement : taskElements) {
			Task task = _parseTask(taskElement);

			definition.addNode(task);
		}

		_parseTransitions(
			definition, conditionElements, forkElements, joinElements,
			joinXorElements, stateElements, taskElements);

		return definition;
	}

	private void _parseActionElements(
			List<Element> actionElements, ActionAware actionAware)
		throws Exception {

		if (actionElements.isEmpty()) {
			return;
		}

		Set<Action> actions = new HashSet<>();

		for (Element actionElement : actionElements) {
			String name = actionElement.elementTextTrim("name");
			String description = actionElement.elementTextTrim("description");
			String executionType = actionElement.elementTextTrim(
				"execution-type");
			String script = actionElement.elementText("script");
			String scriptLanguage = actionElement.elementTextTrim(
				"script-language");
			String scriptRequiredContexts = actionElement.elementTextTrim(
				"script-required-contexts");
			int priority = GetterUtil.getInteger(
				actionElement.elementTextTrim("priority"));

			Action action = new Action(
				name, description, executionType, script, scriptLanguage,
				scriptRequiredContexts, priority);

			actions.add(action);
		}

		actionAware.setActions(actions);
	}

	private void _parseActionsElement(Element actionsElement, Node node)
		throws Exception {

		if (actionsElement == null) {
			return;
		}

		List<Element> actionElements = actionsElement.elements("action");

		_parseActionElements(actionElements, node);

		List<Element> notificationElements = actionsElement.elements(
			"notification");

		_parseNotificationElements(notificationElements, node);
	}

	private Set<Assignment> _parseAssignments(Element assignmentsElement)
		throws Exception {

		if (assignmentsElement == null) {
			return Collections.emptySet();
		}

		Set<Assignment> assignments = new HashSet<>();

		Element resourceActionsElement = assignmentsElement.element(
			"resource-actions");

		if (resourceActionsElement != null) {
			List<Element> resourceActionElements =
				resourceActionsElement.elements("resource-action");

			for (Element resourceActionElement : resourceActionElements) {
				String actionId = resourceActionElement.getTextTrim();

				if (Validator.isNotNull(actionId)) {
					ResourceActionAssignment resourceActionAssignment =
						new ResourceActionAssignment(actionId);

					assignments.add(resourceActionAssignment);
				}
			}
		}

		Element rolesElement = assignmentsElement.element("roles");

		if (rolesElement != null) {
			List<Element> roleAssignmentElements = rolesElement.elements(
				"role");

			for (Element roleAssignmentElement : roleAssignmentElements) {
				long roleId = GetterUtil.getLong(
					roleAssignmentElement.elementTextTrim("role-id"));
				String roleType = GetterUtil.getString(
					roleAssignmentElement.elementTextTrim("role-type"),
					RoleConstants.TYPE_REGULAR_LABEL);
				String name = roleAssignmentElement.elementTextTrim("name");

				RoleAssignment roleAssignment = null;

				if (Validator.isNotNull(name)) {
					roleAssignment = new RoleAssignment(name, roleType);

					boolean autoCreate = GetterUtil.getBoolean(
						roleAssignmentElement.elementTextTrim("auto-create"),
						true);

					roleAssignment.setAutoCreate(autoCreate);
				}
				else {
					roleAssignment = new RoleAssignment(roleId);
				}

				assignments.add(roleAssignment);
			}
		}

		List<Element> scriptedAssignmentElements = assignmentsElement.elements(
			"scripted-assignment");

		for (Element scriptedAssignmentElement : scriptedAssignmentElements) {
			String script = scriptedAssignmentElement.elementText("script");
			String scriptLanguage = scriptedAssignmentElement.elementTextTrim(
				"script-language");
			String scriptRequiredContexts =
				scriptedAssignmentElement.elementTextTrim(
					"script-required-contexts");

			ScriptAssignment scriptAssignment = new ScriptAssignment(
				script, scriptLanguage, scriptRequiredContexts);

			assignments.add(scriptAssignment);
		}

		List<Element> userAssignmentElements = assignmentsElement.elements(
			"user");

		for (Element userAssignmentElement : userAssignmentElements) {
			long userId = GetterUtil.getLong(
				userAssignmentElement.elementTextTrim("user-id"));
			String screenName = userAssignmentElement.elementTextTrim(
				"screen-name");
			String emailAddress = userAssignmentElement.elementTextTrim(
				"email-address");

			UserAssignment userAssignment = new UserAssignment(
				userId, screenName, emailAddress);

			assignments.add(userAssignment);
		}

		return assignments;
	}

	private Condition _parseCondition(Element conditionElement)
		throws Exception {

		String name = conditionElement.elementTextTrim("name");
		String description = conditionElement.elementTextTrim("description");
		String script = conditionElement.elementText("script");
		String scriptLanguage = conditionElement.elementTextTrim(
			"script-language");
		String scriptRequiredContexts = conditionElement.elementTextTrim(
			"script-required-contexts");

		Condition condition = new Condition(
			name, description, script, scriptLanguage, scriptRequiredContexts);

		condition.setMetadata(conditionElement.elementTextTrim("metadata"));

		Element actionsElement = conditionElement.element("actions");

		_parseActionsElement(actionsElement, condition);

		condition.setLabelMap(_parseLabels(conditionElement.element("labels")));

		Element timersElement = conditionElement.element("timers");

		_parseTimerElements(timersElement, condition);

		return condition;
	}

	private DelayDuration _parseDelay(Element delayElement) throws Exception {
		if (delayElement == null) {
			return null;
		}

		double duration = GetterUtil.getDouble(
			delayElement.elementTextTrim("duration"));
		DurationScale durationScale = DurationScale.parse(
			delayElement.elementTextTrim("scale"));

		return new DelayDuration(duration, durationScale);
	}

	private Fork _parseFork(Element forkElement) throws Exception {
		String name = forkElement.elementTextTrim("name");
		String description = forkElement.elementTextTrim("description");

		Fork fork = new Fork(name, description);

		fork.setMetadata(forkElement.elementTextTrim("metadata"));

		Element actionsElement = forkElement.element("actions");

		_parseActionsElement(actionsElement, fork);

		fork.setLabelMap(_parseLabels(forkElement.element("labels")));

		Element timersElement = forkElement.element("timers");

		_parseTimerElements(timersElement, fork);

		return fork;
	}

	private Join _parseJoin(Element joinElement) throws Exception {
		String name = joinElement.elementTextTrim("name");
		String description = joinElement.elementTextTrim("description");

		Join join = new Join(name, description);

		join.setMetadata(joinElement.elementTextTrim("metadata"));

		Element actionsElement = joinElement.element("actions");

		_parseActionsElement(actionsElement, join);

		join.setLabelMap(_parseLabels(joinElement.element("labels")));

		Element timersElement = joinElement.element("timers");

		_parseTimerElements(timersElement, join);

		return join;
	}

	private JoinXor _parseJoinXor(Element joinXorElement) throws Exception {
		String name = joinXorElement.elementTextTrim("name");
		String description = joinXorElement.elementTextTrim("description");

		JoinXor joinXor = new JoinXor(name, description);

		joinXor.setMetadata(joinXorElement.elementTextTrim("metadata"));

		Element actionsElement = joinXorElement.element("actions");

		_parseActionsElement(actionsElement, joinXor);

		joinXor.setLabelMap(_parseLabels(joinXorElement.element("labels")));

		Element timersElement = joinXorElement.element("timers");

		_parseTimerElements(timersElement, joinXor);

		return joinXor;
	}

	private Map<Locale, String> _parseLabels(Element labelsElement) {
		if (labelsElement == null) {
			return Collections.emptyMap();
		}

		Map<Locale, String> labelMap = new HashMap<>();

		for (Element labelElement : labelsElement.elements()) {
			labelMap.put(
				LocaleUtil.fromLanguageId(
					labelElement.attributeValue("language-id")),
				labelElement.getText());
		}

		return labelMap;
	}

	private void _parseNotificationElements(
			List<Element> notificationElements,
			NotificationAware notificationAware)
		throws Exception {

		if (notificationElements.isEmpty()) {
			return;
		}

		Set<Notification> notifications = new HashSet<>();

		for (Element notificationElement : notificationElements) {
			String name = notificationElement.elementTextTrim("name");
			String description = notificationElement.elementTextTrim(
				"description");
			String executionType = notificationElement.elementTextTrim(
				"execution-type");
			String template = notificationElement.elementTextTrim("template");
			String templateLanguage = notificationElement.elementTextTrim(
				"template-language");

			Notification notification = new Notification(
				name, description, executionType, template, templateLanguage);

			List<Element> notificationTypeElements =
				notificationElement.elements("notification-type");

			for (Element notificationTypeElement : notificationTypeElements) {
				notification.addNotificationType(
					notificationTypeElement.getTextTrim());
			}

			List<Element> recipientsElements = notificationElement.elements(
				"recipients");

			for (Element recipientsElement : recipientsElements) {
				_parseRecipients(
					recipientsElement, notification,
					NotificationReceptionType.parse(
						recipientsElement.attributeValue("receptionType")));
			}

			notifications.add(notification);
		}

		notificationAware.setNotifications(notifications);
	}

	private void _parseRecipients(
			Element recipientsElement, Notification notification,
			NotificationReceptionType notificationReceptionType)
		throws Exception {

		if (recipientsElement == null) {
			return;
		}

		List<Element> addressRecipientElements = recipientsElement.elements(
			"address");

		for (Element addressRecipientElement : addressRecipientElements) {
			AddressRecipient addressRecipient = new AddressRecipient(
				addressRecipientElement.getTextTrim());

			addressRecipient.setNotificationReceptionType(
				notificationReceptionType);

			notification.addRecipients(addressRecipient);
		}

		Element assigneesRecipientElement = recipientsElement.element(
			"assignees");

		if (assigneesRecipientElement != null) {
			AssigneesRecipient assigneesRecipient = new AssigneesRecipient();

			assigneesRecipient.setNotificationReceptionType(
				notificationReceptionType);

			notification.addRecipients(assigneesRecipient);
		}

		Element rolesElement = recipientsElement.element("roles");

		if (rolesElement != null) {
			List<Element> roleReceipientElements = rolesElement.elements(
				"role");

			for (Element roleReceipientElement : roleReceipientElements) {
				long roleId = GetterUtil.getLong(
					roleReceipientElement.elementTextTrim("role-id"));
				String roleType = GetterUtil.getString(
					roleReceipientElement.elementTextTrim("role-type"),
					RoleConstants.TYPE_REGULAR_LABEL);

				RoleRecipient roleRecipient = null;

				if (roleId > 0) {
					roleRecipient = new RoleRecipient(roleId, roleType);
				}
				else {
					String name = roleReceipientElement.elementTextTrim("name");

					roleRecipient = new RoleRecipient(name, roleType);

					boolean autoCreate = GetterUtil.getBoolean(
						roleReceipientElement.elementTextTrim("auto-create"),
						true);

					roleRecipient.setAutoCreate(autoCreate);
				}

				roleRecipient.setNotificationReceptionType(
					notificationReceptionType);

				notification.addRecipients(roleRecipient);
			}
		}

		List<Element> scriptedRecipientElements = recipientsElement.elements(
			"scripted-recipient");

		for (Element scriptedRecipientElement : scriptedRecipientElements) {
			String script = scriptedRecipientElement.elementText("script");
			String scriptLanguage = scriptedRecipientElement.elementTextTrim(
				"script-language");
			String scriptRequiredContexts =
				scriptedRecipientElement.elementTextTrim(
					"script-required-contexts");

			ScriptRecipient scriptRecipient = new ScriptRecipient(
				script, scriptLanguage, scriptRequiredContexts);

			scriptRecipient.setNotificationReceptionType(
				notificationReceptionType);

			notification.addRecipients(scriptRecipient);
		}

		List<Element> userRecipientElements = recipientsElement.elements(
			"user");

		for (Element userRecipientElement : userRecipientElements) {
			long userId = GetterUtil.getLong(
				userRecipientElement.elementTextTrim("user-id"));
			String screenName = userRecipientElement.elementTextTrim(
				"screen-name");
			String emailAddress = userRecipientElement.elementTextTrim(
				"email-address");

			UserRecipient userRecipient = new UserRecipient(
				userId, screenName, emailAddress);

			userRecipient.setNotificationReceptionType(
				notificationReceptionType);

			notification.addRecipients(userRecipient);
		}
	}

	private State _parseState(Element stateElement) throws Exception {
		String name = stateElement.elementTextTrim("name");
		String description = stateElement.elementTextTrim("description");
		boolean initial = GetterUtil.getBoolean(
			stateElement.elementTextTrim("initial"));

		State state = new State(name, description, initial);

		state.setMetadata(stateElement.elementTextTrim("metadata"));

		Element actionsElement = stateElement.element("actions");

		_parseActionsElement(actionsElement, state);

		state.setLabelMap(_parseLabels(stateElement.element("labels")));

		Element timersElement = stateElement.element("timers");

		_parseTimerElements(timersElement, state);

		return state;
	}

	private Task _parseTask(Element taskElement) throws Exception {
		String name = taskElement.elementTextTrim("name");
		String description = taskElement.elementTextTrim("description");

		Task task = new Task(name, description);

		task.setMetadata(taskElement.elementTextTrim("metadata"));

		Element actionsElement = taskElement.element("actions");

		_parseActionsElement(actionsElement, task);

		Element assignmentsElement = taskElement.element("assignments");

		if (assignmentsElement != null) {
			task.setAssignments(_parseAssignments(assignmentsElement));
		}

		task.setLabelMap(_parseLabels(taskElement.element("labels")));

		Element formsElement = taskElement.element("task-forms");

		_parseTaskFormsElements(formsElement, task);

		Element timersElement = taskElement.element("task-timers");

		_parseTaskTimerElements(timersElement, task);

		return task;
	}

	private void _parseTaskFormsElements(Element taskFormsElement, Task task) {
		if (taskFormsElement == null) {
			return;
		}

		List<Element> taskFormElements = taskFormsElement.elements("task-form");

		if (ListUtil.isEmpty(taskFormElements)) {
			return;
		}

		for (Element taskFormElement : taskFormElements) {
			String name = taskFormElement.elementTextTrim("name");

			int priority = GetterUtil.getInteger(
				taskFormElement.elementTextTrim("priority"));

			TaskForm taskForm = new TaskForm(name, priority);

			String description = taskFormElement.elementTextTrim("description");

			if (Validator.isNotNull(description)) {
				taskForm.setDescription(description);
			}

			String formDefinition = taskFormElement.elementTextTrim(
				"form-definition");

			if (Validator.isNotNull(formDefinition)) {
				taskForm.setFormDefinition(formDefinition);
			}
			else {
				Element formReferenceElement = taskFormElement.element(
					"form-reference");

				TaskFormReference taskFormReference = new TaskFormReference();

				long companyId = GetterUtil.getLong(
					formReferenceElement.elementTextTrim("company-id"));

				taskFormReference.setCompanyId(companyId);

				long groupId = GetterUtil.getLong(
					formReferenceElement.elementTextTrim("group-id"));

				taskFormReference.setGroupId(groupId);

				long formId = GetterUtil.getLong(
					formReferenceElement.elementTextTrim("form-id"));

				taskFormReference.setFormId(formId);

				String formUuid = formReferenceElement.elementTextTrim(
					"form-uuid");

				taskFormReference.setFormUuid(formUuid);

				taskForm.setTaskFormReference(taskFormReference);
			}

			String metadata = taskFormElement.elementTextTrim("metadata");

			if (Validator.isNotNull(metadata)) {
				taskForm.setMetadata(metadata);
			}

			task.addTaskForm(taskForm);
		}
	}

	private void _parseTaskTimerElements(Element taskTimersElement, Node node)
		throws Exception {

		if (taskTimersElement == null) {
			return;
		}

		List<Element> taskTimerElements = taskTimersElement.elements(
			"task-timer");

		if (taskTimerElements.isEmpty()) {
			return;
		}

		Set<Timer> timers = new HashSet<>();

		for (Element timerElement : taskTimerElements) {
			Timer timer = _parseTimerElement(timerElement, true);

			timers.add(timer);
		}

		node.setTimers(timers);
	}

	private void _parseTimerActions(Element timersElement, Timer timer)
		throws Exception {

		if (timersElement == null) {
			return;
		}

		List<Element> timerActionElements = timersElement.elements(
			"timer-action");

		_parseActionElements(timerActionElements, timer);

		List<Element> timerNotificationElements = timersElement.elements(
			"timer-notification");

		_parseNotificationElements(timerNotificationElements, timer);

		Element reassignmentsElement = timersElement.element("reassignments");

		if (reassignmentsElement != null) {
			Set<Assignment> assignments = _parseAssignments(
				reassignmentsElement);

			timer.setReassignments(assignments);
		}
	}

	private Timer _parseTimerElement(Element timerElement, boolean taskTimer)
		throws Exception {

		String name = timerElement.elementTextTrim("name");
		String description = timerElement.elementTextTrim("description");
		boolean blocking = GetterUtil.getBoolean(
			timerElement.elementTextTrim("blocking"), !taskTimer);

		Timer timer = new Timer(name, description, blocking);

		Element delayElement = timerElement.element("delay");

		timer.setDelayDuration(_parseDelay(delayElement));

		if (!blocking) {
			Element recurrenceElement = timerElement.element("recurrence");

			timer.setRecurrence(_parseDelay(recurrenceElement));
		}

		Element timerActions = timerElement.element("timer-actions");

		_parseTimerActions(timerActions, timer);

		return timer;
	}

	private void _parseTimerElements(Element timersElement, Node node)
		throws Exception {

		if (timersElement == null) {
			return;
		}

		List<Element> timerElements = timersElement.elements("timer");

		if (timerElements.isEmpty()) {
			return;
		}

		Set<Timer> timers = new HashSet<>();

		for (Element timerElement : timerElements) {
			Timer timer = _parseTimerElement(timerElement, false);

			timers.add(timer);
		}

		node.setTimers(timers);
	}

	private void _parseTransition(Definition definition, Element nodeElement)
		throws Exception {

		String sourceName = nodeElement.elementTextTrim("name");

		Element transitionsElement = nodeElement.element("transitions");

		if (transitionsElement == null) {
			return;
		}

		Node sourceNode = definition.getNode(sourceName);

		List<Element> transitionElements = transitionsElement.elements(
			"transition");

		for (Element transitionElement : transitionElements) {
			String transitionName = transitionElement.elementTextTrim("name");

			String targetName = transitionElement.elementTextTrim("target");

			Node targetNode = definition.getNode(targetName);

			boolean defaultValue = GetterUtil.getBoolean(
				transitionElement.elementTextTrim("default"), true);

			Transition transition = new Transition(
				defaultValue, _parseLabels(transitionElement.element("labels")),
				transitionName, sourceNode, targetNode);

			Element timerElement = transitionElement.element("timer");

			if (timerElement != null) {
				Timer timer = _parseTimerElement(timerElement, false);

				transition.setTimers(timer);
			}

			sourceNode.addOutgoingTransition(transition);

			if (targetNode != null) {
				targetNode.addIncomingTransition(transition);
			}
		}
	}

	private void _parseTransitions(
			Definition definition, List<Element> conditionElements,
			List<Element> forkElements, List<Element> joinElements,
			List<Element> joinXorElements, List<Element> stateElements,
			List<Element> taskElements)
		throws Exception {

		for (Element conditionElement : conditionElements) {
			_parseTransition(definition, conditionElement);
		}

		for (Element forkElement : forkElements) {
			_parseTransition(definition, forkElement);
		}

		for (Element joinElement : joinElements) {
			_parseTransition(definition, joinElement);
		}

		for (Element joinXorElement : joinXorElements) {
			_parseTransition(definition, joinXorElement);
		}

		for (Element stateElement : stateElements) {
			_parseTransition(definition, stateElement);
		}

		for (Element taskElement : taskElements) {
			_parseTransition(definition, taskElement);
		}
	}

	private boolean _validate;

}