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

package com.liferay.headless.admin.workflow.client.dto.v1_0;

import com.liferay.headless.admin.workflow.client.function.UnsafeSupplier;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskAssignableUsersSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WorkflowTaskAssignableUsers implements Cloneable {

	public WorkflowTaskAssignableUser[] getWorkflowTaskAssignableUsers() {
		return workflowTaskAssignableUsers;
	}

	public void setWorkflowTaskAssignableUsers(
		WorkflowTaskAssignableUser[] workflowTaskAssignableUsers) {

		this.workflowTaskAssignableUsers = workflowTaskAssignableUsers;
	}

	public void setWorkflowTaskAssignableUsers(
		UnsafeSupplier<WorkflowTaskAssignableUser[], Exception>
			workflowTaskAssignableUsersUnsafeSupplier) {

		try {
			workflowTaskAssignableUsers =
				workflowTaskAssignableUsersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WorkflowTaskAssignableUser[] workflowTaskAssignableUsers;

	@Override
	public WorkflowTaskAssignableUsers clone()
		throws CloneNotSupportedException {

		return (WorkflowTaskAssignableUsers)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowTaskAssignableUsers)) {
			return false;
		}

		WorkflowTaskAssignableUsers workflowTaskAssignableUsers =
			(WorkflowTaskAssignableUsers)object;

		return Objects.equals(
			toString(), workflowTaskAssignableUsers.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WorkflowTaskAssignableUsersSerDes.toJSON(this);
	}

}