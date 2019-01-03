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

package com.liferay.workflow.apio.architect.identifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds information about a reusable {@code
 * com.liferay.portal.kernel.workflow.WorkflowTask} identifier. The workflow
 * task is identified by user, role, or source of the task.
 *
 * @author Víctor Galán
 */
public interface ReusableWorkflowTaskIdentifier {

	public static ReusableWorkflowTaskIdentifier create(
		WorkflowTaskType workflowTaskType) {

		return () -> workflowTaskType;
	}

	public WorkflowTaskType getWorkflowTaskType();

	public enum WorkflowTaskType {

		TO_ME("assigned-to-me"), TO_MY_ROLES("assigned-to-my-roles");

		public static WorkflowTaskType get(String name) {
			return _workflowTaskTypes.get(name);
		}

		public String getName() {
			return _name;
		}

		private WorkflowTaskType(String name) {
			_name = name;
		}

		private static final Map<String, WorkflowTaskType> _workflowTaskTypes =
			Collections.unmodifiableMap(
				new HashMap<String, WorkflowTaskType>() {
					{
						for (WorkflowTaskType workflowTaskType :
								WorkflowTaskType.values()) {

							put(workflowTaskType.getName(), workflowTaskType);
						}
					}
				});

		private final String _name;

	}

}