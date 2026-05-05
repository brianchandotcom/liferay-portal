/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.serdes.v1_0.DuplicateObjectBulkSelectionActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class DuplicateObjectBulkSelectionAction
	extends BulkAction implements Cloneable, Serializable {

	public static DuplicateObjectBulkSelectionAction toDTO(String json) {
		return DuplicateObjectBulkSelectionActionSerDes.toDTO(json);
	}

	@Override
	public DuplicateObjectBulkSelectionAction clone()
		throws CloneNotSupportedException {

		return (DuplicateObjectBulkSelectionAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DuplicateObjectBulkSelectionAction)) {
			return false;
		}

		DuplicateObjectBulkSelectionAction duplicateObjectBulkSelectionAction =
			(DuplicateObjectBulkSelectionAction)object;

		return Objects.equals(
			toString(), duplicateObjectBulkSelectionAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DuplicateObjectBulkSelectionActionSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:705753700