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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Michael C. Han
 */
public abstract class BaseNodeValidator<T extends Node>
	implements NodeValidator<T> {

	@Override
	public void validate(Definition definition, T node)
		throws KaleoDefinitionValidationException {

		doValidate(definition, node);

		_validateName(node);
		_validateNotifications(node);
		_validateTransitions(node.getOutgoingTransitions());
	}

	protected abstract void doValidate(Definition definition, T node)
		throws KaleoDefinitionValidationException;

	private void _validateName(T node)
		throws KaleoDefinitionValidationException {

		String name = node.getName();

		if (name.length() > 200) {
			throw new KaleoDefinitionValidationException.
				MustSetValidNodeNameLength(200, name);
		}
	}

	private void _validateNotifications(T node)
		throws KaleoDefinitionValidationException {

		Set<Notification> notifications = node.getNotifications();

		Stream<Notification> notificationsStream = notifications.stream();

		if (notificationsStream.anyMatch(
				notification -> Validator.isNull(notification.getTemplate()))) {

			throw new KaleoDefinitionValidationException.
				EmptyNotificationTemplate(node.getName());
		}
	}

	private void _validateTransition(Transition transition)
		throws KaleoDefinitionValidationException {

		if (transition.getTargetNode() == null) {
			throw new KaleoDefinitionValidationException.MustSetTargetNode(
				transition.getName());
		}
	}

	private void _validateTransitions(Map<String, Transition> transitions)
		throws KaleoDefinitionValidationException {

		for (Transition transition : transitions.values()) {
			_validateTransition(transition);
		}
	}

}