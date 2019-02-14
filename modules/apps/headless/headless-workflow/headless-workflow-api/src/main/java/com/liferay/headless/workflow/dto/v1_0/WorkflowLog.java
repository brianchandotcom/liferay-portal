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
		this.auditPerson = auditPerson;
	}

	public void setAuditPerson(
		UnsafeSupplier<String, Throwable> auditPersonUnsafeSupplier) {

		try {
			auditPerson = auditPersonUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setCommentLog(String commentLog) {
		this.commentLog = commentLog;
	}

	public void setCommentLog(
		UnsafeSupplier<String, Throwable> commentLogUnsafeSupplier) {

		try {
			commentLog = commentLogUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public void setPerson(
		UnsafeSupplier<String, Throwable> personUnsafeSupplier) {

		try {
			person = personUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setPreviousPerson(String previousPerson) {
		this.previousPerson = previousPerson;
	}

	public void setPreviousPerson(
		UnsafeSupplier<String, Throwable> previousPersonUnsafeSupplier) {

		try {
			previousPerson = previousPersonUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setPreviousState(String previousState) {
		this.previousState = previousState;
	}

	public void setPreviousState(
		UnsafeSupplier<String, Throwable> previousStateUnsafeSupplier) {

		try {
			previousState = previousStateUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setState(
		UnsafeSupplier<String, Throwable> stateUnsafeSupplier) {

		try {
			state = stateUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setTask(
		UnsafeSupplier<WorkflowTask, Throwable> taskUnsafeSupplier) {

		try {
			task = taskUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setTask(WorkflowTask task) {
		this.task = task;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setTaskId(
		UnsafeSupplier<Long, Throwable> taskIdUnsafeSupplier) {

		try {
			taskId = taskIdUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<String, Throwable> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected String auditPerson;

	@GraphQLField
	protected String commentLog;

	@GraphQLField
	protected Date dateCreated;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected String person;

	@GraphQLField
	protected String previousPerson;

	@GraphQLField
	protected String previousState;

	@GraphQLField
	protected String state;

	@GraphQLField
	protected WorkflowTask task;

	@GraphQLField
	protected Long taskId;

	@GraphQLField
	protected String type;

}