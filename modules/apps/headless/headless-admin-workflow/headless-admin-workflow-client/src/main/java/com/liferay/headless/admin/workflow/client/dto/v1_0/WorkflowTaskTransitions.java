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
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskTransitionsSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WorkflowTaskTransitions implements Cloneable {

	public WorkflowTaskTransition[] getWorkflowTaskTransitions() {
		return workflowTaskTransitions;
	}

	public void setWorkflowTaskTransitions(
		WorkflowTaskTransition[] workflowTaskTransitions) {

		this.workflowTaskTransitions = workflowTaskTransitions;
	}

	public void setWorkflowTaskTransitions(
		UnsafeSupplier<WorkflowTaskTransition[], Exception>
			workflowTaskTransitionsUnsafeSupplier) {

		try {
			workflowTaskTransitions =
				workflowTaskTransitionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WorkflowTaskTransition[] workflowTaskTransitions;

	@Override
	public WorkflowTaskTransitions clone() throws CloneNotSupportedException {
		return (WorkflowTaskTransitions)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowTaskTransitions)) {
			return false;
		}

		WorkflowTaskTransitions workflowTaskTransitions =
			(WorkflowTaskTransitions)object;

		return Objects.equals(toString(), workflowTaskTransitions.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WorkflowTaskTransitionsSerDes.toJSON(this);
	}

}