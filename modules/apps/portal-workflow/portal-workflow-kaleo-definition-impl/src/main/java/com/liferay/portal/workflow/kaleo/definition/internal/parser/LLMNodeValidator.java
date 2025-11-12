/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.parser;

import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.LLM;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(service = NodeValidator.class)
public class LLMNodeValidator extends BaseNodeValidator<LLM> {

	@Override
	public NodeType getNodeType() {
		return NodeType.LLM;
	}

	@Override
	protected void doValidate(Definition definition, LLM llm)
		throws KaleoDefinitionValidationException {

		if (llm.getOutgoingTransitionsCount() > 1) {
			throw new KaleoDefinitionValidationException.
				MustNotSetMoreThanOneOutgoingTransition(llm.getDefaultLabel());
		}
	}

}