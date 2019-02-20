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
public interface WorkflowLog {

	public String getAuditPerson();

	public void setAuditPerson(
			String auditPerson);

	public void setAuditPerson(
			UnsafeSupplier<String, Throwable>
				auditPersonUnsafeSupplier);
	public String getCommentLog();

	public void setCommentLog(
			String commentLog);

	public void setCommentLog(
			UnsafeSupplier<String, Throwable>
				commentLogUnsafeSupplier);
	public Date getDateCreated();

	public void setDateCreated(
			Date dateCreated);

	public void setDateCreated(
			UnsafeSupplier<Date, Throwable>
				dateCreatedUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public String getPerson();

	public void setPerson(
			String person);

	public void setPerson(
			UnsafeSupplier<String, Throwable>
				personUnsafeSupplier);
	public String getPreviousPerson();

	public void setPreviousPerson(
			String previousPerson);

	public void setPreviousPerson(
			UnsafeSupplier<String, Throwable>
				previousPersonUnsafeSupplier);
	public String getPreviousState();

	public void setPreviousState(
			String previousState);

	public void setPreviousState(
			UnsafeSupplier<String, Throwable>
				previousStateUnsafeSupplier);
	public String getState();

	public void setState(
			String state);

	public void setState(
			UnsafeSupplier<String, Throwable>
				stateUnsafeSupplier);
	public WorkflowTask getTask();

	public void setTask(
			WorkflowTask task);

	public void setTask(
			UnsafeSupplier<WorkflowTask, Throwable>
				taskUnsafeSupplier);
	public Long getTaskId();

	public void setTaskId(
			Long taskId);

	public void setTaskId(
			UnsafeSupplier<Long, Throwable>
				taskIdUnsafeSupplier);
	public String getType();

	public void setType(
			String type);

	public void setType(
			UnsafeSupplier<String, Throwable>
				typeUnsafeSupplier);

}