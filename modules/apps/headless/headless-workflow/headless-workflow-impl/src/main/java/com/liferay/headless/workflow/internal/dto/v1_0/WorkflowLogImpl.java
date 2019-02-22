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

package com.liferay.headless.workflow.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.workflow.dto.v1_0.WorkflowLog;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTask;
import com.liferay.petra.function.UnsafeSupplier;

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
public class WorkflowLogImpl implements WorkflowLog {

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

	public String getState() {
		return state;
	}

	public WorkflowTask getTask() {
		return task;
	}

	public Long getTaskId() {
		return taskId;
	}

	public String getType() {
		return type;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = (String)auditPerson;
	}

	@JsonIgnore
	public void setAuditPerson(
		UnsafeSupplier<String, Throwable> auditPersonUnsafeSupplier) {

		try {
			auditPerson = (String)auditPersonUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setCommentLog(String commentLog) {
		this.commentLog = (String)commentLog;
	}

	@JsonIgnore
	public void setCommentLog(
		UnsafeSupplier<String, Throwable> commentLogUnsafeSupplier) {

		try {
			commentLog = (String)commentLogUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = (Date)dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = (Date)dateCreatedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setId(Long id) {
		this.id = (Long)id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = (Long)idUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setPerson(String person) {
		this.person = (String)person;
	}

	@JsonIgnore
	public void setPerson(
		UnsafeSupplier<String, Throwable> personUnsafeSupplier) {

		try {
			person = (String)personUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setPreviousPerson(String previousPerson) {
		this.previousPerson = (String)previousPerson;
	}

	@JsonIgnore
	public void setPreviousPerson(
		UnsafeSupplier<String, Throwable> previousPersonUnsafeSupplier) {

		try {
			previousPerson = (String)previousPersonUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setPreviousState(String previousState) {
		this.previousState = (String)previousState;
	}

	@JsonIgnore
	public void setPreviousState(
		UnsafeSupplier<String, Throwable> previousStateUnsafeSupplier) {

		try {
			previousState = (String)previousStateUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setState(String state) {
		this.state = (String)state;
	}

	@JsonIgnore
	public void setState(
		UnsafeSupplier<String, Throwable> stateUnsafeSupplier) {

		try {
			state = (String)stateUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@JsonIgnore
	public void setTask(
		UnsafeSupplier<WorkflowTask, Throwable> taskUnsafeSupplier) {

		try {
			task = (WorkflowTaskImpl)taskUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setTask(WorkflowTask task) {
		this.task = (WorkflowTaskImpl)task;
	}

	public void setTaskId(Long taskId) {
		this.taskId = (Long)taskId;
	}

	@JsonIgnore
	public void setTaskId(
		UnsafeSupplier<Long, Throwable> taskIdUnsafeSupplier) {

		try {
			taskId = (Long)taskIdUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setType(String type) {
		this.type = (String)type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<String, Throwable> typeUnsafeSupplier) {
		try {
			type = (String)typeUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected String auditPerson;

	@GraphQLField
	@JsonProperty
	protected String commentLog;

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String person;

	@GraphQLField
	@JsonProperty
	protected String previousPerson;

	@GraphQLField
	@JsonProperty
	protected String previousState;

	@GraphQLField
	@JsonProperty
	protected String state;

	@GraphQLField
	@JsonProperty
	protected WorkflowTaskImpl task;

	@GraphQLField
	@JsonProperty
	protected Long taskId;

	@GraphQLField
	@JsonProperty
	protected String type;

}