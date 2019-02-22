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

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface WorkflowTask {

	public Boolean getCompleted();

	public Date getDateCompleted();

	public Date getDateCreated();

	public String getDefinitionName();

	public String getDescription();

	public Date getDueDate();

	public Long getId();

	public WorkflowLog[] getLogs();

	public Long[] getLogsIds();

	public String getName();

	public ObjectReviewed getObjectReviewed();

	public String[] getTransitions();

	public void setCompleted(Boolean completed);

	public void setCompleted(
		UnsafeSupplier<Boolean, Throwable> completedUnsafeSupplier);

	public void setDateCompleted(Date dateCompleted);

	public void setDateCompleted(
		UnsafeSupplier<Date, Throwable> dateCompletedUnsafeSupplier);

	public void setDateCreated(Date dateCreated);

	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier);

	public void setDefinitionName(String definitionName);

	public void setDefinitionName(
		UnsafeSupplier<String, Throwable> definitionNameUnsafeSupplier);

	public void setDescription(String description);

	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier);

	public void setDueDate(Date dueDate);

	public void setDueDate(
		UnsafeSupplier<Date, Throwable> dueDateUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setLogs(
		UnsafeSupplier<WorkflowLog[], Throwable> logsUnsafeSupplier);

	public void setLogs(WorkflowLog[] logs);

	public void setLogsIds(Long[] logsIds);

	public void setLogsIds(
		UnsafeSupplier<Long[], Throwable> logsIdsUnsafeSupplier);

	public void setName(String name);

	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier);

	public void setObjectReviewed(ObjectReviewed objectReviewed);

	public void setObjectReviewed(
		UnsafeSupplier<ObjectReviewed, Throwable> objectReviewedUnsafeSupplier);

	public void setTransitions(String[] transitions);

	public void setTransitions(
		UnsafeSupplier<String[], Throwable> transitionsUnsafeSupplier);

}