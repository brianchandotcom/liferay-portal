/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;
import com.liferay.bulk.rest.client.serdes.v1_0.MoveObjectBulkSelectionActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class MoveObjectBulkSelectionAction
	extends BulkAction implements Cloneable, Serializable {

	public static MoveObjectBulkSelectionAction toDTO(String json) {
		return MoveObjectBulkSelectionActionSerDes.toDTO(json);
	}

	public Long getObjectEntryFolderId() {
		return objectEntryFolderId;
	}

	public void setObjectEntryFolderId(Long objectEntryFolderId) {
		this.objectEntryFolderId = objectEntryFolderId;
	}

	public void setObjectEntryFolderId(
		UnsafeSupplier<Long, Exception> objectEntryFolderIdUnsafeSupplier) {

		try {
			objectEntryFolderId = objectEntryFolderIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long objectEntryFolderId;

	@Override
	public MoveObjectBulkSelectionAction clone()
		throws CloneNotSupportedException {

		return (MoveObjectBulkSelectionAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MoveObjectBulkSelectionAction)) {
			return false;
		}

		MoveObjectBulkSelectionAction moveObjectBulkSelectionAction =
			(MoveObjectBulkSelectionAction)object;

		return Objects.equals(
			toString(), moveObjectBulkSelectionAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return MoveObjectBulkSelectionActionSerDes.toJSON(this);
	}

}