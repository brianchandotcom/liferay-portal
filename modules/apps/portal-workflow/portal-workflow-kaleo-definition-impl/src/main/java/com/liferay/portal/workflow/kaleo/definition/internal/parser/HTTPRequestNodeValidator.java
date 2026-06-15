/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.parser;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.HTTPRequestNode;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.Setting;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Fabian Bouché
 * @author Iliyan Peychev
 */
@Component(service = NodeValidator.class)
public class HTTPRequestNodeValidator
	extends BaseNodeValidator<HTTPRequestNode> {

	@Override
	public NodeType getNodeType() {
		return NodeType.HTTP_REQUEST;
	}

	@Override
	protected void doValidate(
			Definition definition, HTTPRequestNode httpRequestNode)
		throws KaleoDefinitionValidationException {

		String url = null;

		for (Setting setting : httpRequestNode.getSettings()) {
			if (Objects.equals(setting.getName(), "url")) {
				url = setting.getValue();
			}
		}

		if (Validator.isNull(url)) {
			throw new KaleoDefinitionValidationException(
				String.format(
					"HTTP call node %s must set a \"url\"",
					httpRequestNode.getDefaultLabel()));
		}

		if (httpRequestNode.getOutgoingTransitionsCount() == 0) {
			throw new KaleoDefinitionValidationException.
				MustSetOutgoingTransition(httpRequestNode.getDefaultLabel());
		}

		if (httpRequestNode.getOutgoingTransitionsCount() > 1) {
			throw new KaleoDefinitionValidationException.
				MustNotSetMultipleOutgoingTransitions(
					httpRequestNode.getDefaultLabel());
		}
	}

}