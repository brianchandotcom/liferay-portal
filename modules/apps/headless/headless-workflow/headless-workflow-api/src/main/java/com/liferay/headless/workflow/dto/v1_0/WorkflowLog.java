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

	public String getCommentLog();

	public Date getDateCreated();

	public Long getId();

	public String getPerson();

	public String getPreviousPerson();

	public String getPreviousState();

	public String getState();

	public WorkflowTask getTask();

	public Long getTaskId();

	public String getType();

	public void setAuditPerson(String auditPerson);

	public void setAuditPerson(
		UnsafeSupplier<String, Throwable> auditPersonUnsafeSupplier);

	public void setCommentLog(String commentLog);

	public void setCommentLog(
		UnsafeSupplier<String, Throwable> commentLogUnsafeSupplier);

	public void setDateCreated(Date dateCreated);

	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setPerson(String person);

	public void setPerson(
		UnsafeSupplier<String, Throwable> personUnsafeSupplier);

	public void setPreviousPerson(String previousPerson);

	public void setPreviousPerson(
		UnsafeSupplier<String, Throwable> previousPersonUnsafeSupplier);

	public void setPreviousState(String previousState);

	public void setPreviousState(
		UnsafeSupplier<String, Throwable> previousStateUnsafeSupplier);

	public void setState(String state);

	public void setState(UnsafeSupplier<String, Throwable> stateUnsafeSupplier);

	public void setTask(
		UnsafeSupplier<WorkflowTask, Throwable> taskUnsafeSupplier);

	public void setTask(WorkflowTask task);

	public void setTaskId(Long taskId);

	public void setTaskId(UnsafeSupplier<Long, Throwable> taskIdUnsafeSupplier);

	public void setType(String type);

	public void setType(UnsafeSupplier<String, Throwable> typeUnsafeSupplier);

}