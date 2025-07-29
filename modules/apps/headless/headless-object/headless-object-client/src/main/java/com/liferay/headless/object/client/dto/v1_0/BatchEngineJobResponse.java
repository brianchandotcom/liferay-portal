/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.dto.v1_0;

import com.liferay.headless.object.client.function.UnsafeSupplier;
import com.liferay.headless.object.client.serdes.v1_0.BatchEngineJobResponseSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class BatchEngineJobResponse implements Cloneable, Serializable {

	public static BatchEngineJobResponse toDTO(String json) {
		return BatchEngineJobResponseSerDes.toDTO(json);
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public void setBatchId(
		UnsafeSupplier<Long, Exception> batchIdUnsafeSupplier) {

		try {
			batchId = batchIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long batchId;

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public void setObjectType(
		UnsafeSupplier<String, Exception> objectTypeUnsafeSupplier) {

		try {
			objectType = objectTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String objectType;

	public Long[] getProcessedIds() {
		return processedIds;
	}

	public void setProcessedIds(Long[] processedIds) {
		this.processedIds = processedIds;
	}

	public void setProcessedIds(
		UnsafeSupplier<Long[], Exception> processedIdsUnsafeSupplier) {

		try {
			processedIds = processedIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] processedIds;

	@Override
	public BatchEngineJobResponse clone() throws CloneNotSupportedException {
		return (BatchEngineJobResponse)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchEngineJobResponse)) {
			return false;
		}

		BatchEngineJobResponse batchEngineJobResponse =
			(BatchEngineJobResponse)object;

		return Objects.equals(toString(), batchEngineJobResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return BatchEngineJobResponseSerDes.toJSON(this);
	}

}