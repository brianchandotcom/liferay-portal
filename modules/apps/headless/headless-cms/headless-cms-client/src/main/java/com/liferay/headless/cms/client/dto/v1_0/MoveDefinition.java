/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.MoveDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class MoveDefinition
	extends BulkActionDefinition implements Cloneable, Serializable {

	public static MoveDefinition toDTO(String json) {
		return MoveDefinitionSerDes.toDTO(json);
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
	public MoveDefinition clone() throws CloneNotSupportedException {
		return (MoveDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MoveDefinition)) {
			return false;
		}

		MoveDefinition moveDefinition = (MoveDefinition)object;

		return Objects.equals(toString(), moveDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return MoveDefinitionSerDes.toJSON(this);
	}

}