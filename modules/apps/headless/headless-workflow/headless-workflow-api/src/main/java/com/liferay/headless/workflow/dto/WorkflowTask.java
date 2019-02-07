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

package com.liferay.headless.workflow.dto;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "WorkflowTask")
public class WorkflowTask {

	public Boolean getCompleted() {
		return _completed;
	}

	public Date getDateCompleted() {
		return _dateCompleted;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public String getDefinitionName() {
		return _definitionName;
	}

	public String getDescription() {
		return _description;
	}

	public Date getDueDate() {
		return _dueDate;
	}

	public Long getId() {
		return _id;
	}

	public WorkflowLog[] getLogs() {
		return _logs;
	}

	public Long[] getLogsIds() {
		return _logsIds;
	}

	public String getName() {
		return _name;
	}

	public Object getObject() {
		return _object;
	}

	public String getSelf() {
		return _self;
	}

	public String[] getTransitions() {
		return _transitions;
	}

	public void setCompleted(Boolean completed) {
		_completed = completed;
	}

	public void setDateCompleted(Date dateCompleted) {
		_dateCompleted = dateCompleted;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDefinitionName(String definitionName) {
		_definitionName = definitionName;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDueDate(Date dueDate) {
		_dueDate = dueDate;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setLogs(WorkflowLog[] logs) {
		_logs = logs;
	}

	public void setLogsIds(Long[] logsIds) {
		_logsIds = logsIds;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setObject(Object object) {
		_object = object;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setTransitions(String[] transitions) {
		_transitions = transitions;
	}

	private Boolean _completed;
	private Date _dateCompleted;
	private Date _dateCreated;
	private String _definitionName;
	private String _description;
	private Date _dueDate;
	private Long _id;
	private WorkflowLog[] _logs;
	private Long[] _logsIds;
	private String _name;
	private Object _object;
	private String _self;
	private String[] _transitions;

}