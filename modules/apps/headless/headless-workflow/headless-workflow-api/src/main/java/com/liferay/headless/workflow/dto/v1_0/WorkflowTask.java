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

	public void setCompleted(
			Boolean completed);

	public void setCompleted(
			UnsafeSupplier<Boolean, Throwable>
				completedUnsafeSupplier);
	public Date getDateCompleted();

	public void setDateCompleted(
			Date dateCompleted);

	public void setDateCompleted(
			UnsafeSupplier<Date, Throwable>
				dateCompletedUnsafeSupplier);
	public Date getDateCreated();

	public void setDateCreated(
			Date dateCreated);

	public void setDateCreated(
			UnsafeSupplier<Date, Throwable>
				dateCreatedUnsafeSupplier);
	public String getDefinitionName();

	public void setDefinitionName(
			String definitionName);

	public void setDefinitionName(
			UnsafeSupplier<String, Throwable>
				definitionNameUnsafeSupplier);
	public String getDescription();

	public void setDescription(
			String description);

	public void setDescription(
			UnsafeSupplier<String, Throwable>
				descriptionUnsafeSupplier);
	public Date getDueDate();

	public void setDueDate(
			Date dueDate);

	public void setDueDate(
			UnsafeSupplier<Date, Throwable>
				dueDateUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public WorkflowLog[] getLogs();

	public void setLogs(
			WorkflowLog[] logs);

	public void setLogs(
			UnsafeSupplier<WorkflowLog[], Throwable>
				logsUnsafeSupplier);
	public Long[] getLogsIds();

	public void setLogsIds(
			Long[] logsIds);

	public void setLogsIds(
			UnsafeSupplier<Long[], Throwable>
				logsIdsUnsafeSupplier);
	public String getName();

	public void setName(
			String name);

	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier);
	public ObjectReviewed getObjectReviewed();

	public void setObjectReviewed(
			ObjectReviewed objectReviewed);

	public void setObjectReviewed(
			UnsafeSupplier<ObjectReviewed, Throwable>
				objectReviewedUnsafeSupplier);
	public String[] getTransitions();

	public void setTransitions(
			String[] transitions);

	public void setTransitions(
			UnsafeSupplier<String[], Throwable>
				transitionsUnsafeSupplier);

}