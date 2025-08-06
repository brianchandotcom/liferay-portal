/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.serdes.v1_0.DeleteActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class DeleteAction
	extends DocumentBulkAction implements Cloneable, Serializable {

	public static DeleteAction toDTO(String json) {
		return DeleteActionSerDes.toDTO(json);
	}

	@Override
	public DeleteAction clone() throws CloneNotSupportedException {
		return (DeleteAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DeleteAction)) {
			return false;
		}

		DeleteAction deleteAction = (DeleteAction)object;

		return Objects.equals(toString(), deleteAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DeleteActionSerDes.toJSON(this);
	}

}