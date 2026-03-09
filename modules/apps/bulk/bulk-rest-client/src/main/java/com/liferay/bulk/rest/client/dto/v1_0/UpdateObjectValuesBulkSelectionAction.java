/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;
import com.liferay.bulk.rest.client.serdes.v1_0.UpdateObjectValuesBulkSelectionActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class UpdateObjectValuesBulkSelectionAction
	extends BulkAction implements Cloneable, Serializable {

	public static UpdateObjectValuesBulkSelectionAction toDTO(String json) {
		return UpdateObjectValuesBulkSelectionActionSerDes.toDTO(json);
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
	public UpdateObjectValuesBulkSelectionAction clone()
		throws CloneNotSupportedException {

		return (UpdateObjectValuesBulkSelectionAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UpdateObjectValuesBulkSelectionAction)) {
			return false;
		}

		UpdateObjectValuesBulkSelectionAction
			updateObjectValuesBulkSelectionAction =
				(UpdateObjectValuesBulkSelectionAction)object;

		return Objects.equals(
			toString(), updateObjectValuesBulkSelectionAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return UpdateObjectValuesBulkSelectionActionSerDes.toJSON(this);
	}

}