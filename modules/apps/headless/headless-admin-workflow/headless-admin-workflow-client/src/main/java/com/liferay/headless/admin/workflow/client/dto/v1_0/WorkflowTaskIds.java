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
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskIdsSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WorkflowTaskIds implements Cloneable {

	public Long[] getWorkflowTaskIds() {
		return workflowTaskIds;
	}

	public void setWorkflowTaskIds(Long[] workflowTaskIds) {
		this.workflowTaskIds = workflowTaskIds;
	}

	public void setWorkflowTaskIds(
		UnsafeSupplier<Long[], Exception> workflowTaskIdsUnsafeSupplier) {

		try {
			workflowTaskIds = workflowTaskIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] workflowTaskIds;

	@Override
	public WorkflowTaskIds clone() throws CloneNotSupportedException {
		return (WorkflowTaskIds)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowTaskIds)) {
			return false;
		}

		WorkflowTaskIds workflowTaskIds = (WorkflowTaskIds)object;

		return Objects.equals(toString(), workflowTaskIds.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WorkflowTaskIdsSerDes.toJSON(this);
	}

}