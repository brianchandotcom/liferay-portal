/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;
import com.liferay.bulk.rest.client.serdes.v1_0.UpdateValuesBulkActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class UpdateValuesBulkAction
	extends BulkAction implements Cloneable, Serializable {

	public static UpdateValuesBulkAction toDTO(String json) {
		return UpdateValuesBulkActionSerDes.toDTO(json);
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	public void setValues(
		UnsafeSupplier<Map<String, Object>, Exception> valuesUnsafeSupplier) {

		try {
			values = valuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> values;

	@Override
	public UpdateValuesBulkAction clone() throws CloneNotSupportedException {
		return (UpdateValuesBulkAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UpdateValuesBulkAction)) {
			return false;
		}

		UpdateValuesBulkAction updateValuesBulkAction =
			(UpdateValuesBulkAction)object;

		return Objects.equals(toString(), updateValuesBulkAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return UpdateValuesBulkActionSerDes.toJSON(this);
	}

}