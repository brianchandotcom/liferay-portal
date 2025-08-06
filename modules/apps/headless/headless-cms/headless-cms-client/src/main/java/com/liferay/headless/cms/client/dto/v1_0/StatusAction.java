/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.StatusActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class StatusAction
	extends DocumentBulkAction implements Cloneable, Serializable {

	public static StatusAction toDTO(String json) {
		return StatusActionSerDes.toDTO(json);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Integer, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer status;

	@Override
	public StatusAction clone() throws CloneNotSupportedException {
		return (StatusAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StatusAction)) {
			return false;
		}

		StatusAction statusAction = (StatusAction)object;

		return Objects.equals(toString(), statusAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return StatusActionSerDes.toJSON(this);
	}

}