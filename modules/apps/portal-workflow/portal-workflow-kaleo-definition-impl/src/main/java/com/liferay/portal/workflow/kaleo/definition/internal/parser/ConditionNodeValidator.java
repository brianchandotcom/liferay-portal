/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.parser;

import com.liferay.portal.workflow.kaleo.definition.Condition;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
@Component(service = NodeValidator.class)
public class ConditionNodeValidator extends BaseNodeValidator<Condition> {

	@Override
	public NodeType getNodeType() {
		return NodeType.CONDITION;
	}

	@Override
	protected void doValidate(Definition definition, Condition condition)
		throws KaleoDefinitionValidationException {

		if (condition.getIncomingTransitionsCount() == 0) {
			throw new KaleoDefinitionValidationException.
				MustSetIncomingTransition(condition.getDefaultLabel());
		}

		if (condition.getOutgoingTransitionsCount() < 2) {
			throw new KaleoDefinitionValidationException.
				MustSetMultipleOutgoingTransition(condition.getDefaultLabel());
		}
	}

}