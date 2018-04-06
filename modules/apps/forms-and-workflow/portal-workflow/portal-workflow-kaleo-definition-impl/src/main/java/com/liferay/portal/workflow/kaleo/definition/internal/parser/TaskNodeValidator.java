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
import com.liferay.portal.workflow.kaleo.definition.Assignment;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.TaskForm;
import com.liferay.portal.workflow.kaleo.definition.TaskFormReference;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.definition.parser.NodeValidator;

import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "node.type=TASK", service = NodeValidator.class
)
public class TaskNodeValidator extends BaseNodeValidator<Task> {

	@Override
	protected void doValidate(Definition definition, Task task)
		throws KaleoDefinitionValidationException {

		if (task.getIncomingTransitionsCount() == 0) {
			throw new KaleoDefinitionValidationException.
				MustSetIncomingTransition(task.getName());
		}

		if (task.getOutgoingTransitionsCount() == 0) {
			throw new KaleoDefinitionValidationException.
				MustSetOutgoingTransition(task.getName());
		}

		Set<Assignment> assignments = task.getAssignments();

		if ((assignments == null) || assignments.isEmpty()) {
			throw new KaleoDefinitionValidationException.MustSetAssignments(
				task.getName());
		}

		Set<TaskForm> taskForms = task.getTaskForms();

		for (TaskForm taskForm : taskForms) {
			String formDefinition = taskForm.getFormDefinition();

			TaskFormReference taskFormReference =
				taskForm.getTaskFormReference();

			if (Validator.isNull(formDefinition) ||
				(taskFormReference == null)) {

				throw new KaleoDefinitionValidationException.
					MustSetTaskFormDefinitionOrReference(
						task.getName(), taskForm.getName());
			}
		}
	}

}