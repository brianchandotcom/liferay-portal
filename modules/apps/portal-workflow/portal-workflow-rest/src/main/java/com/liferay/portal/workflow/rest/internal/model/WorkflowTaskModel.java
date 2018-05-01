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

package com.liferay.portal.workflow.rest.internal.model;

import com.liferay.portal.kernel.workflow.WorkflowTask;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam Brandizzi
 */
@XmlRootElement
public class WorkflowTaskModel {

	public WorkflowTaskModel() {
		_completed = false;
		_createTime = 0;
		_description = null;
		_dueDate = null;
		_lastActivityTime = 0;
		_name = null;
		_state = null;
		_transitions = null;
		_workflowAssetModel = null;
		_workflowAssigneeModel = null;
		_workflowTaskId = 0;
	}

	public WorkflowTaskModel(
		WorkflowTask workflowTask, WorkflowAssigneeModel workflowAssigneeModel,
		WorkflowAssetModel workflowAssetModel, long lastActivityTime,
		String state, List<String> transitions) {

		_workflowAssigneeModel = workflowAssigneeModel;
		_workflowAssetModel = workflowAssetModel;
		_lastActivityTime = lastActivityTime;
		_state = state;
		_transitions = transitions;

		_completed = workflowTask.isCompleted();

		Date createDate = workflowTask.getCreateDate();

		_createTime = createDate.getTime();

		_description = workflowTask.getDescription();
		_dueDate = workflowTask.getDueDate();
		_name = workflowTask.getName();
		_workflowTaskId = workflowTask.getWorkflowTaskId();
	}

	@XmlElement
	public long getCreateTime() {
		return _createTime;
	}

	@XmlElement
	public String getDescription() {
		return _description;
	}

	@XmlElement
	public Date getDueDate() {
		return _dueDate;
	}

	@XmlElement
	public long getLastActivityTime() {
		return _lastActivityTime;
	}

	@XmlElement
	public String getName() {
		return _name;
	}

	@XmlElement
	public String getState() {
		return _state;
	}

	@XmlElement
	public List<String> getTransitions() {
		return _transitions;
	}

	@XmlElement(name = "asset")
	public WorkflowAssetModel getWorkflowAssetModel() {
		return _workflowAssetModel;
	}

	@XmlElement(name = "assignee")
	public WorkflowAssigneeModel getWorkflowAssigneeModel() {
		return _workflowAssigneeModel;
	}

	@XmlElement(name = "id")
	public long getWorkflowTaskId() {
		return _workflowTaskId;
	}

	@XmlElement
	public boolean isCompleted() {
		return _completed;
	}

	private final boolean _completed;
	private final long _createTime;
	private final String _description;
	private final Date _dueDate;
	private final long _lastActivityTime;
	private final String _name;
	private final String _state;
	private final List<String> _transitions;
	private final WorkflowAssetModel _workflowAssetModel;
	private final WorkflowAssigneeModel _workflowAssigneeModel;
	private final long _workflowTaskId;

}