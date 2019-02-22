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

import com.liferay.headless.workflow.dto.v1_0.ObjectReviewed;
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
@GraphQLName("WorkflowTask")
@XmlRootElement(name = "WorkflowTask")
public class WorkflowTaskImpl implements WorkflowTask {

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

	public WorkflowLog[] getLogs() {
		return logs;
	}

	public Long[] getLogsIds() {
		return logsIds;
	}

	public String getName() {
		return name;
	}

	public ObjectReviewed getObjectReviewed() {
		return objectReviewed;
	}

	public String[] getTransitions() {
		return transitions;
	}

	public void setCompleted(Boolean completed) {
		this.completed = (Boolean)completed;
	}

	@JsonIgnore
	public void setCompleted(
		UnsafeSupplier<Boolean, Throwable> completedUnsafeSupplier) {

		try {
			completed = (Boolean)completedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = (Date)dateCompleted;
	}

	@JsonIgnore
	public void setDateCompleted(
		UnsafeSupplier<Date, Throwable> dateCompletedUnsafeSupplier) {

		try {
			dateCompleted = (Date)dateCompletedUnsafeSupplier.get();
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

	public void setDefinitionName(String definitionName) {
		this.definitionName = (String)definitionName;
	}

	@JsonIgnore
	public void setDefinitionName(
		UnsafeSupplier<String, Throwable> definitionNameUnsafeSupplier) {

		try {
			definitionName = (String)definitionNameUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDescription(String description) {
		this.description = (String)description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

		try {
			description = (String)descriptionUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = (Date)dueDate;
	}

	@JsonIgnore
	public void setDueDate(
		UnsafeSupplier<Date, Throwable> dueDateUnsafeSupplier) {

		try {
			dueDate = (Date)dueDateUnsafeSupplier.get();
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

	@JsonIgnore
	public void setLogs(
		UnsafeSupplier<WorkflowLog[], Throwable> logsUnsafeSupplier) {

		try {
			logs = (WorkflowLogImpl[])logsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setLogs(WorkflowLog[] logs) {
		this.logs = (WorkflowLogImpl[])logs;
	}

	public void setLogsIds(Long[] logsIds) {
		this.logsIds = (Long[])logsIds;
	}

	@JsonIgnore
	public void setLogsIds(
		UnsafeSupplier<Long[], Throwable> logsIdsUnsafeSupplier) {

		try {
			logsIds = (Long[])logsIdsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setName(String name) {
		this.name = (String)name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
		try {
			name = (String)nameUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setObjectReviewed(ObjectReviewed objectReviewed) {
		this.objectReviewed = (ObjectReviewedImpl)objectReviewed;
	}

	@JsonIgnore
	public void setObjectReviewed(
		UnsafeSupplier<ObjectReviewed, Throwable>
			objectReviewedUnsafeSupplier) {

		try {
			objectReviewed =
				(ObjectReviewedImpl)objectReviewedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setTransitions(String[] transitions) {
		this.transitions = (String[])transitions;
	}

	@JsonIgnore
	public void setTransitions(
		UnsafeSupplier<String[], Throwable> transitionsUnsafeSupplier) {

		try {
			transitions = (String[])transitionsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean completed;

	@GraphQLField
	@JsonProperty
	protected Date dateCompleted;

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;

	@GraphQLField
	@JsonProperty
	protected String definitionName;

	@GraphQLField
	@JsonProperty
	protected String description;

	@GraphQLField
	@JsonProperty
	protected Date dueDate;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected WorkflowLogImpl[] logs;

	@GraphQLField
	@JsonProperty
	protected Long[] logsIds;

	@GraphQLField
	@JsonProperty
	protected String name;

	@GraphQLField
	@JsonProperty
	protected ObjectReviewedImpl objectReviewed;

	@GraphQLField
	@JsonProperty
	protected String[] transitions;

}