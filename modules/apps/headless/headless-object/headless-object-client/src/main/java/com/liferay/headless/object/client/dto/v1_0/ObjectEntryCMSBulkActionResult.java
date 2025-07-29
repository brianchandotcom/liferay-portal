/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.dto.v1_0;

import com.liferay.headless.object.client.function.UnsafeSupplier;
import com.liferay.headless.object.client.serdes.v1_0.ObjectEntryCMSBulkActionResultSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class ObjectEntryCMSBulkActionResult implements Cloneable, Serializable {

	public static ObjectEntryCMSBulkActionResult toDTO(String json) {
		return ObjectEntryCMSBulkActionResultSerDes.toDTO(json);
	}

	public BatchEngineJobResult[] getBatchEngineJobResults() {
		return batchEngineJobResults;
	}

	public void setBatchEngineJobResults(
		BatchEngineJobResult[] batchEngineJobResults) {

		this.batchEngineJobResults = batchEngineJobResults;
	}

	public void setBatchEngineJobResults(
		UnsafeSupplier<BatchEngineJobResult[], Exception>
			batchEngineJobResultsUnsafeSupplier) {

		try {
			batchEngineJobResults = batchEngineJobResultsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BatchEngineJobResult[] batchEngineJobResults;

	@Override
	public ObjectEntryCMSBulkActionResult clone()
		throws CloneNotSupportedException {

		return (ObjectEntryCMSBulkActionResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectEntryCMSBulkActionResult)) {
			return false;
		}

		ObjectEntryCMSBulkActionResult objectEntryCMSBulkActionResult =
			(ObjectEntryCMSBulkActionResult)object;

		return Objects.equals(
			toString(), objectEntryCMSBulkActionResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ObjectEntryCMSBulkActionResultSerDes.toJSON(this);
	}

}