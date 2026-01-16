/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.serdes.v1_0.ExpireBulkActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ExpireBulkAction
	extends BulkAction implements Cloneable, Serializable {

	public static ExpireBulkAction toDTO(String json) {
		return ExpireBulkActionSerDes.toDTO(json);
	}

	@Override
	public ExpireBulkAction clone() throws CloneNotSupportedException {
		return (ExpireBulkAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ExpireBulkAction)) {
			return false;
		}

		ExpireBulkAction expireBulkAction = (ExpireBulkAction)object;

		return Objects.equals(toString(), expireBulkAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ExpireBulkActionSerDes.toJSON(this);
	}

}