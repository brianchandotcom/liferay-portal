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

package com.liferay.headless.workflow.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

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
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "WorkflowLog")
public class WorkflowLog {

	public Creator getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(Creator auditPerson) {
		this.auditPerson = auditPerson;
	}

	@JsonIgnore
	public void setAuditPerson(
		UnsafeSupplier<Creator, Exception> auditPersonUnsafeSupplier) {

		try {
			auditPerson = auditPersonUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Creator auditPerson;

	public String getCommentLog() {
		return commentLog;
	}

	public void setCommentLog(String commentLog) {
		this.commentLog = commentLog;
	}

	@JsonIgnore
	public void setCommentLog(
		UnsafeSupplier<String, Exception> commentLogUnsafeSupplier) {

		try {
			commentLog = commentLogUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String commentLog;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date dateCreated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	public Creator getPerson() {
		return person;
	}

	public void setPerson(Creator person) {
		this.person = person;
	}

	@JsonIgnore
	public void setPerson(
		UnsafeSupplier<Creator, Exception> personUnsafeSupplier) {

		try {
			person = personUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Creator person;

	public Creator getPreviousPerson() {
		return previousPerson;
	}

	public void setPreviousPerson(Creator previousPerson) {
		this.previousPerson = previousPerson;
	}

	@JsonIgnore
	public void setPreviousPerson(
		UnsafeSupplier<Creator, Exception> previousPersonUnsafeSupplier) {

		try {
			previousPerson = previousPersonUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Creator previousPerson;

	public String getPreviousState() {
		return previousState;
	}

	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	@JsonIgnore
	public void setPreviousState(
		UnsafeSupplier<String, Exception> previousStateUnsafeSupplier) {

		try {
			previousState = previousStateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String previousState;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonIgnore
	public void setState(
		UnsafeSupplier<String, Exception> stateUnsafeSupplier) {

		try {
			state = stateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String state;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@JsonIgnore
	public void setTaskId(
		UnsafeSupplier<Long, Exception> taskIdUnsafeSupplier) {

		try {
			taskId = taskIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long taskId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String type;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"auditPerson\": ");

		sb.append(auditPerson);
		sb.append(", ");

		sb.append("\"commentLog\": ");

		sb.append("\"");
		sb.append(commentLog);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(dateCreated);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"person\": ");

		sb.append(person);
		sb.append(", ");

		sb.append("\"previousPerson\": ");

		sb.append(previousPerson);
		sb.append(", ");

		sb.append("\"previousState\": ");

		sb.append("\"");
		sb.append(previousState);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"state\": ");

		sb.append("\"");
		sb.append(state);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"taskId\": ");

		sb.append(taskId);
		sb.append(", ");

		sb.append("\"type\": ");

		sb.append("\"");
		sb.append(type);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

}