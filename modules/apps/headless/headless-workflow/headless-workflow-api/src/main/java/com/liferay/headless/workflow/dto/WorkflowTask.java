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
@GraphQLName("WorkflowTask")
@XmlRootElement(name = "WorkflowTask")
public class WorkflowTask {

	public Boolean getCompleted() {
		return completed;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public String getDescription() {
		return description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Long getId() {
		return id;
	}

	public WorkflowLog getLogs() {
		return logs;
	}

	public String getName() {
		return name;
	}

	public Object getObject() {
		return object;
	}

	public String getSelf() {
		return self;
	}

	public String[] getTransitions() {
		return transitions;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogs(WorkflowLog logs) {
		this.logs = logs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public void setTransitions(String[] transitions) {
		this.transitions = transitions;
	}

	@GraphQLField
	private Boolean completed;

	@GraphQLField
	private Date dateCompleted;

	@GraphQLField
	private Date dateCreated;

	@GraphQLField
	private String definitionName;

	@GraphQLField
	private String description;

	@GraphQLField
	private Date dueDate;

	@GraphQLField
	private Long id;

	@GraphQLField
	private WorkflowLog logs;

	@GraphQLField
	private String name;

	@GraphQLField
	private Object object;

	@GraphQLField
	private String self;

	@GraphQLField
	private String[] transitions;

}