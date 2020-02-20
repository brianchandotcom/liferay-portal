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
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskTransitionSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WorkflowTaskTransition implements Cloneable {

	public Transition[] getTransitions() {
		return transitions;
	}

	public void setTransitions(Transition[] transitions) {
		this.transitions = transitions;
	}

	public void setTransitions(
		UnsafeSupplier<Transition[], Exception> transitionsUnsafeSupplier) {

		try {
			transitions = transitionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Transition[] transitions;

	public String getWorkflowDefinitionVersion() {
		return workflowDefinitionVersion;
	}

	public void setWorkflowDefinitionVersion(String workflowDefinitionVersion) {
		this.workflowDefinitionVersion = workflowDefinitionVersion;
	}

	public void setWorkflowDefinitionVersion(
		UnsafeSupplier<String, Exception>
			workflowDefinitionVersionUnsafeSupplier) {

		try {
			workflowDefinitionVersion =
				workflowDefinitionVersionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String workflowDefinitionVersion;

	public String getWorkflowTaskLabel() {
		return workflowTaskLabel;
	}

	public void setWorkflowTaskLabel(String workflowTaskLabel) {
		this.workflowTaskLabel = workflowTaskLabel;
	}

	public void setWorkflowTaskLabel(
		UnsafeSupplier<String, Exception> workflowTaskLabelUnsafeSupplier) {

		try {
			workflowTaskLabel = workflowTaskLabelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String workflowTaskLabel;

	public String getWorkflowTaskName() {
		return workflowTaskName;
	}

	public void setWorkflowTaskName(String workflowTaskName) {
		this.workflowTaskName = workflowTaskName;
	}

	public void setWorkflowTaskName(
		UnsafeSupplier<String, Exception> workflowTaskNameUnsafeSupplier) {

		try {
			workflowTaskName = workflowTaskNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String workflowTaskName;

	@Override
	public WorkflowTaskTransition clone() throws CloneNotSupportedException {
		return (WorkflowTaskTransition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowTaskTransition)) {
			return false;
		}

		WorkflowTaskTransition workflowTaskTransition =
			(WorkflowTaskTransition)object;

		return Objects.equals(toString(), workflowTaskTransition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return WorkflowTaskTransitionSerDes.toJSON(this);
	}

}