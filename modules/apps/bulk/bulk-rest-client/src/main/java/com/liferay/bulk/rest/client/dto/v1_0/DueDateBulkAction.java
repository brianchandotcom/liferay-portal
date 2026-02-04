/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;
import com.liferay.bulk.rest.client.serdes.v1_0.DueDateBulkActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class DueDateBulkAction
	extends BulkAction implements Cloneable, Serializable {

	public static DueDateBulkAction toDTO(String json) {
		return DueDateBulkActionSerDes.toDTO(json);
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setDueDate(
		UnsafeSupplier<Date, Exception> dueDateUnsafeSupplier) {

		try {
			dueDate = dueDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dueDate;

	@Override
	public DueDateBulkAction clone() throws CloneNotSupportedException {
		return (DueDateBulkAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DueDateBulkAction)) {
			return false;
		}

		DueDateBulkAction dueDateBulkAction = (DueDateBulkAction)object;

		return Objects.equals(toString(), dueDateBulkAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DueDateBulkActionSerDes.toJSON(this);
	}

}