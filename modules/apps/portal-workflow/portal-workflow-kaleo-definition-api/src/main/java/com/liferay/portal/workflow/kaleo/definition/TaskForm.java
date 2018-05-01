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

package com.liferay.portal.workflow.kaleo.definition;

/**
 * @author Michael C. Han
 */
public class TaskForm implements Comparable<TaskForm> {

	public TaskForm(String name, int priority) {
		_name = name;
		_priority = priority;
	}

	@Override
	public int compareTo(TaskForm form) {
		if (getPriority() > form.getPriority()) {
			return 1;
		}
		else if (getPriority() < form.getPriority()) {
			return -1;
		}

		return 0;
	}

	public String getDescription() {
		return _description;
	}

	public String getFormDefinition() {
		return _formDefinition;
	}

	public String getMetadata() {
		return _metadata;
	}

	public String getName() {
		return _name;
	}

	public int getPriority() {
		return _priority;
	}

	public TaskFormReference getTaskFormReference() {
		return _taskFormReference;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setFormDefinition(String formDefinition) {
		_formDefinition = formDefinition;
	}

	public void setMetadata(String metadata) {
		_metadata = metadata;
	}

	public void setTaskFormReference(TaskFormReference taskFormReference) {
		_taskFormReference = taskFormReference;
	}

	private String _description;
	private String _formDefinition;
	private String _metadata;
	private final String _name;
	private final int _priority;
	private TaskFormReference _taskFormReference;

}