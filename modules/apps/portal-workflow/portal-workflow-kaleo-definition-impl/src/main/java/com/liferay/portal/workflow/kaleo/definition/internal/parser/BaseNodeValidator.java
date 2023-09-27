/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.parser;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import java.util.Locale;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class BaseNodeValidator<T extends Node>
	implements NodeValidator<T> {

	@Override
	public void validate(Definition definition, T node)
		throws KaleoDefinitionValidationException {

		doValidate(definition, node);

		_validateLabel(node);
		_validateName(node);
		_validateNotifications(node);
		_validateTransitions(node.getOutgoingTransitions());
	}

	protected abstract void doValidate(Definition definition, T node)
		throws KaleoDefinitionValidationException;

	private void _validateLabel(T node)
		throws KaleoDefinitionValidationException {

		Map<Locale, String> labelMap = node.getLabelMap();

		if (labelMap == null) {
			return;
		}

		for (Map.Entry<Locale, String> entry : labelMap.entrySet()) {
			String value = entry.getValue();

			if (value.length() > _NODE_VALUE_MAX_LENGTH) {
				throw new KaleoDefinitionValidationException.
					MustSetValidNodeNameLength(_NODE_VALUE_MAX_LENGTH, value);
			}
		}
	}

	private void _validateName(T node)
		throws KaleoDefinitionValidationException {

		String name = node.getName();

		if (name.length() > _NODE_VALUE_MAX_LENGTH) {
			throw new KaleoDefinitionValidationException.
				MustSetValidNodeNameLength(_NODE_VALUE_MAX_LENGTH, name);
		}
	}

	private void _validateNotifications(T node)
		throws KaleoDefinitionValidationException {

		for (Notification notification : node.getNotifications()) {
			if (Validator.isNull(notification.getTemplate())) {
				throw new KaleoDefinitionValidationException.
					EmptyNotificationTemplate(node.getDefaultLabel());
			}
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

	private static final int _NODE_VALUE_MAX_LENGTH = 200;

}