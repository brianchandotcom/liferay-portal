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

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "WorkflowLog")
public class WorkflowLog {

	public String getAuditPerson() {
		return _auditPerson;
	}

	public String getCommentLog() {
		return _commentLog;
	}

	public String getDateCreated() {
		return _dateCreated;
	}

	public Integer getId() {
		return _id;
	}

	public String getPerson() {
		return _person;
	}

	public String getPreviousPerson() {
		return _previousPerson;
	}

	public String getPreviousState() {
		return _previousState;
	}

	public String getSelf() {
		return _self;
	}

	public String getState() {
		return _state;
	}

	public WorkflowTask getTask() {
		return _task;
	}

	public String getType() {
		return _type;
	}

	public void setAuditPerson(String auditPerson) {
		_auditPerson = auditPerson;
	}

	public void setCommentLog(String commentLog) {
		_commentLog = commentLog;
	}

	public void setDateCreated(String dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setPerson(String person) {
		_person = person;
	}

	public void setPreviousPerson(String previousPerson) {
		_previousPerson = previousPerson;
	}

	public void setPreviousState(String previousState) {
		_previousState = previousState;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setTask(WorkflowTask task) {
		_task = task;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _auditPerson;
	private String _commentLog;
	private String _dateCreated;
	private Integer _id;
	private String _person;
	private String _previousPerson;
	private String _previousState;
	private String _self;
	private String _state;
	private WorkflowTask _task;
	private String _type;

}