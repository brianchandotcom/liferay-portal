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
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = {"node.type=STATE"},
	service = NodeValidator.class
)
public class StateNodeValidator extends BaseNodeValidator<State> {

	@Override
	protected void doValidate(Definition definition, State state)
		throws WorkflowException {

		if (state.isInitial()) {
			validateInitialState(definition, state);
		}
		else if (state.getIncomingTransitionsCount() == 0) {
			throw new WorkflowException(
				"No incoming transition found for state " + state.getName());
		}
	}

	protected void validateInitialState(Definition definition, State state)
		throws WorkflowException {

		State initialState = definition.getInitialState();

		if (!Objects.equals(initialState, state)) {
			throw new WorkflowException(
				"Multiple initial states " + state.getName() + " and " +
					initialState.getName());
		}

		if (state.getIncomingTransitionsCount() > 0) {
			throw new WorkflowException(
				"An incoming transition was found for initial state " +
					state.getName());
		}

		if (state.getOutgoingTransitionsCount() == 0) {
			throw new WorkflowException(
				"No outgoing transition found for initial state " +
					state.getName());
		}
	}

}