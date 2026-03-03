/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.serdes.v1_0.DeleteObjectEntryBulkActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class DeleteObjectEntryBulkAction
	extends BulkAction implements Cloneable, Serializable {

	public static DeleteObjectEntryBulkAction toDTO(String json) {
		return DeleteObjectEntryBulkActionSerDes.toDTO(json);
	}

	@Override
	public DeleteObjectEntryBulkAction clone()
		throws CloneNotSupportedException {

		return (DeleteObjectEntryBulkAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DeleteObjectEntryBulkAction)) {
			return false;
		}

		DeleteObjectEntryBulkAction deleteObjectEntryBulkAction =
			(DeleteObjectEntryBulkAction)object;

		return Objects.equals(
			toString(), deleteObjectEntryBulkAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DeleteObjectEntryBulkActionSerDes.toJSON(this);
	}

}