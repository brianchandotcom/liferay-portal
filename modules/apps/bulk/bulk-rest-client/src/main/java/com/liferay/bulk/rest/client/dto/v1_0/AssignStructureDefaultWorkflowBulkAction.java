/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;
import com.liferay.bulk.rest.client.serdes.v1_0.AssignStructureDefaultWorkflowBulkActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class AssignStructureDefaultWorkflowBulkAction
	extends BulkAction implements Cloneable, Serializable {

	public static AssignStructureDefaultWorkflowBulkAction toDTO(String json) {
		return AssignStructureDefaultWorkflowBulkActionSerDes.toDTO(json);
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public void setWorkflow(
		UnsafeSupplier<String, Exception> workflowUnsafeSupplier) {

		try {
			workflow = workflowUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String workflow;

	@Override
	public AssignStructureDefaultWorkflowBulkAction clone()
		throws CloneNotSupportedException {

		return (AssignStructureDefaultWorkflowBulkAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssignStructureDefaultWorkflowBulkAction)) {
			return false;
		}

		AssignStructureDefaultWorkflowBulkAction
			assignStructureDefaultWorkflowBulkAction =
				(AssignStructureDefaultWorkflowBulkAction)object;

		return Objects.equals(
			toString(), assignStructureDefaultWorkflowBulkAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AssignStructureDefaultWorkflowBulkActionSerDes.toJSON(this);
	}

}