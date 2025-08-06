/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.BulkActionTaskSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class BulkActionTask implements Cloneable, Serializable {

	public static BulkActionTask toDTO(String json) {
		return BulkActionTaskSerDes.toDTO(json);
	}

	public Long getBulkActionTaskId() {
		return bulkActionTaskId;
	}

	public void setBulkActionTaskId(Long bulkActionTaskId) {
		this.bulkActionTaskId = bulkActionTaskId;
	}

	public void setBulkActionTaskId(
		UnsafeSupplier<Long, Exception> bulkActionTaskIdUnsafeSupplier) {

		try {
			bulkActionTaskId = bulkActionTaskIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long bulkActionTaskId;

	@Override
	public BulkActionTask clone() throws CloneNotSupportedException {
		return (BulkActionTask)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BulkActionTask)) {
			return false;
		}

		BulkActionTask bulkActionTask = (BulkActionTask)object;

		return Objects.equals(toString(), bulkActionTask.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return BulkActionTaskSerDes.toJSON(this);
	}

}