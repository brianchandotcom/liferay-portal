/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.serdes.v1_0.DeleteObjectEntryBulkSelectionActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class DeleteObjectEntryBulkSelectionAction
	extends BulkAction implements Cloneable, Serializable {

	public static DeleteObjectEntryBulkSelectionAction toDTO(String json) {
		return DeleteObjectEntryBulkSelectionActionSerDes.toDTO(json);
	}

	@Override
	public DeleteObjectEntryBulkSelectionAction clone()
		throws CloneNotSupportedException {

		return (DeleteObjectEntryBulkSelectionAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DeleteObjectEntryBulkSelectionAction)) {
			return false;
		}

		DeleteObjectEntryBulkSelectionAction
			deleteObjectEntryBulkSelectionAction =
				(DeleteObjectEntryBulkSelectionAction)object;

		return Objects.equals(
			toString(), deleteObjectEntryBulkSelectionAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DeleteObjectEntryBulkSelectionActionSerDes.toJSON(this);
	}

}