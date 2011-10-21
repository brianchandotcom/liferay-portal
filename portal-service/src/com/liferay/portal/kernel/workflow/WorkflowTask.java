/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.workflow;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public interface WorkflowTask {

	public long getAssigneeUserId();

	public Date getCompletionDate();

	public Date getCreateDate();

	public String getDescription();

	public Date getDueDate();

	public String getName();

	public Map<String, Serializable> getOptionalAttributes();

	public long getWorkflowDefinitionId();

	public String getWorkflowDefinitionName();

	public int getWorkflowDefinitionVersion();

	public long getWorkflowInstanceId();

	public List<WorkflowTaskAssignee> getWorkflowTaskAssignees();

	public long getWorkflowTaskId();

	public boolean isAssignedToSingleUser();

	public boolean isAsynchronous();

	public boolean isCompleted();

}