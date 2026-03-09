/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.serdes.v1_0.ResetPermissionObjectBulkSelectionActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ResetPermissionObjectBulkSelectionAction
	extends BulkAction implements Cloneable, Serializable {

	public static ResetPermissionObjectBulkSelectionAction toDTO(String json) {
		return ResetPermissionObjectBulkSelectionActionSerDes.toDTO(json);
	}

	@Override
	public ResetPermissionObjectBulkSelectionAction clone()
		throws CloneNotSupportedException {

		return (ResetPermissionObjectBulkSelectionAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ResetPermissionObjectBulkSelectionAction)) {
			return false;
		}

		ResetPermissionObjectBulkSelectionAction
			resetPermissionObjectBulkSelectionAction =
				(ResetPermissionObjectBulkSelectionAction)object;

		return Objects.equals(
			toString(), resetPermissionObjectBulkSelectionAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ResetPermissionObjectBulkSelectionActionSerDes.toJSON(this);
	}

}