/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.dto.v1_0;

import com.liferay.headless.object.client.function.UnsafeSupplier;
import com.liferay.headless.object.client.serdes.v1_0.ObjectEntryCMSBulkActionResponseSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class ObjectEntryCMSBulkActionResponse
	implements Cloneable, Serializable {

	public static ObjectEntryCMSBulkActionResponse toDTO(String json) {
		return ObjectEntryCMSBulkActionResponseSerDes.toDTO(json);
	}

	public BatchEngineJobResponse[] getBatchEngineJobResponses() {
		return batchEngineJobResponses;
	}

	public void setBatchEngineJobResponses(
		BatchEngineJobResponse[] batchEngineJobResponses) {

		this.batchEngineJobResponses = batchEngineJobResponses;
	}

	public void setBatchEngineJobResponses(
		UnsafeSupplier<BatchEngineJobResponse[], Exception>
			batchEngineJobResponsesUnsafeSupplier) {

		try {
			batchEngineJobResponses =
				batchEngineJobResponsesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BatchEngineJobResponse[] batchEngineJobResponses;

	@Override
	public ObjectEntryCMSBulkActionResponse clone()
		throws CloneNotSupportedException {

		return (ObjectEntryCMSBulkActionResponse)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectEntryCMSBulkActionResponse)) {
			return false;
		}

		ObjectEntryCMSBulkActionResponse objectEntryCMSBulkActionResponse =
			(ObjectEntryCMSBulkActionResponse)object;

		return Objects.equals(
			toString(), objectEntryCMSBulkActionResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ObjectEntryCMSBulkActionResponseSerDes.toJSON(this);
	}

}