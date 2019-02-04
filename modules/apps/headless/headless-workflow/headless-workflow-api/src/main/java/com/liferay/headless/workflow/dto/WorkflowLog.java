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

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("WorkflowLog")
@XmlRootElement(name = "WorkflowLog")
public class WorkflowLog {

	public String getAuditPerson() {
		return auditPerson;
	}

	public String getCommentLog() {
		return commentLog;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Long getId() {
		return id;
	}

	public String getPerson() {
		return person;
	}

	public String getPreviousPerson() {
		return previousPerson;
	}

	public String getPreviousState() {
		return previousState;
	}

	public String getSelf() {
		return self;
	}

	public String getState() {
		return state;
	}

	public WorkflowTask getTask() {
		return task;
	}

	public String getType() {
		return type;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public void setCommentLog(String commentLog) {
		this.commentLog = commentLog;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public void setPreviousPerson(String previousPerson) {
		this.previousPerson = previousPerson;
	}

	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTask(WorkflowTask task) {
		this.task = task;
	}

	public void setType(String type) {
		this.type = type;
	}

	@GraphQLField
	private String auditPerson;

	@GraphQLField
	private String commentLog;

	@GraphQLField
	private Date dateCreated;

	@GraphQLField
	private Long id;

	@GraphQLField
	private String person;

	@GraphQLField
	private String previousPerson;

	@GraphQLField
	private String previousState;

	@GraphQLField
	private String self;

	@GraphQLField
	private String state;

	@GraphQLField
	private WorkflowTask task;

	@GraphQLField
	private String type;

}