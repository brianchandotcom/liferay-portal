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

import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowValidator;

import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = WorkflowValidator.class)
public class DefaultWorkflowValidator implements WorkflowValidator {

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

		if (definition.getForksCount() != definition.getJoinsCount()) {
			throw new KaleoDefinitionValidationException.
				UnbalancedForkAndJoinNodes();
		}

		Collection<Node> nodes = definition.getNodes();

		for (Node node : nodes) {
			NodeValidator<Node> nodeValidator =
				_nodeValidatorRegistry.getNodeValidator(node.getNodeType());

			nodeValidator.validate(definition, node);
		}
	}

	@Reference
	private NodeValidatorRegistry _nodeValidatorRegistry;

}