/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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