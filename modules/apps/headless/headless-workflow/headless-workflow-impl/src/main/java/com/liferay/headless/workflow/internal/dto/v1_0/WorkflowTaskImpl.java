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

	public void setCompleted(
			Boolean completed) {

			this.completed = completed;
	}

	@JsonIgnore
	public void setCompleted(
			UnsafeSupplier<Boolean, Throwable>
				completedUnsafeSupplier) {

			try {
				completed =
					completedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean completed;
	public Date getDateCompleted() {
			return dateCompleted;
	}

	public void setDateCompleted(
			Date dateCompleted) {

			this.dateCompleted = dateCompleted;
	}

	@JsonIgnore
	public void setDateCompleted(
			UnsafeSupplier<Date, Throwable>
				dateCompletedUnsafeSupplier) {

			try {
				dateCompleted =
					dateCompletedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date dateCompleted;
	public Date getDateCreated() {
			return dateCreated;
	}

	public void setDateCreated(
			Date dateCreated) {

			this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
			UnsafeSupplier<Date, Throwable>
				dateCreatedUnsafeSupplier) {

			try {
				dateCreated =
					dateCreatedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;
	public String getDefinitionName() {
			return definitionName;
	}

	public void setDefinitionName(
			String definitionName) {

			this.definitionName = definitionName;
	}

	@JsonIgnore
	public void setDefinitionName(
			UnsafeSupplier<String, Throwable>
				definitionNameUnsafeSupplier) {

			try {
				definitionName =
					definitionNameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String definitionName;
	public String getDescription() {
			return description;
	}

	public void setDescription(
			String description) {

			this.description = description;
	}

	@JsonIgnore
	public void setDescription(
			UnsafeSupplier<String, Throwable>
				descriptionUnsafeSupplier) {

			try {
				description =
					descriptionUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String description;
	public Date getDueDate() {
			return dueDate;
	}

	public void setDueDate(
			Date dueDate) {

			this.dueDate = dueDate;
	}

	@JsonIgnore
	public void setDueDate(
			UnsafeSupplier<Date, Throwable>
				dueDateUnsafeSupplier) {

			try {
				dueDate =
					dueDateUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date dueDate;
	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;
	public WorkflowLog[] getLogs() {
			return logs;
	}

	public void setLogs(
			WorkflowLog[] logs) {

			this.logs = logs;
	}

	@JsonIgnore
	public void setLogs(
			UnsafeSupplier<WorkflowLog[], Throwable>
				logsUnsafeSupplier) {

			try {
				logs =
					logsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected WorkflowLog[] logs;
	public Long[] getLogsIds() {
			return logsIds;
	}

	public void setLogsIds(
			Long[] logsIds) {

			this.logsIds = logsIds;
	}

	@JsonIgnore
	public void setLogsIds(
			UnsafeSupplier<Long[], Throwable>
				logsIdsUnsafeSupplier) {

			try {
				logsIds =
					logsIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] logsIds;
	public String getName() {
			return name;
	}

	public void setName(
			String name) {

			this.name = name;
	}

	@JsonIgnore
	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier) {

			try {
				name =
					nameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String name;
	public ObjectReviewed getObjectReviewed() {
			return objectReviewed;
	}

	public void setObjectReviewed(
			ObjectReviewed objectReviewed) {

			this.objectReviewed = objectReviewed;
	}

	@JsonIgnore
	public void setObjectReviewed(
			UnsafeSupplier<ObjectReviewed, Throwable>
				objectReviewedUnsafeSupplier) {

			try {
				objectReviewed =
					objectReviewedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected ObjectReviewed objectReviewed;
	public String[] getTransitions() {
			return transitions;
	}

	public void setTransitions(
			String[] transitions) {

			this.transitions = transitions;
	}

	@JsonIgnore
	public void setTransitions(
			UnsafeSupplier<String[], Throwable>
				transitionsUnsafeSupplier) {

			try {
				transitions =
					transitionsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String[] transitions;

}