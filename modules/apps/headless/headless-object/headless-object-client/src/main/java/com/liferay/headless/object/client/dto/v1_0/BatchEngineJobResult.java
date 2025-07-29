/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.dto.v1_0;

import com.liferay.headless.object.client.function.UnsafeSupplier;
import com.liferay.headless.object.client.serdes.v1_0.BatchEngineJobResultSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class BatchEngineJobResult implements Cloneable, Serializable {

	public static BatchEngineJobResult toDTO(String json) {
		return BatchEngineJobResultSerDes.toDTO(json);
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

	public String getObjectDefinitionName() {
		return objectDefinitionName;
	}

	public void setObjectDefinitionName(String objectDefinitionName) {
		this.objectDefinitionName = objectDefinitionName;
	}

	public void setObjectDefinitionName(
		UnsafeSupplier<String, Exception> objectDefinitionNameUnsafeSupplier) {

		try {
			objectDefinitionName = objectDefinitionNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String objectDefinitionName;

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
	public BatchEngineJobResult clone() throws CloneNotSupportedException {
		return (BatchEngineJobResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchEngineJobResult)) {
			return false;
		}

		BatchEngineJobResult batchEngineJobResult =
			(BatchEngineJobResult)object;

		return Objects.equals(toString(), batchEngineJobResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return BatchEngineJobResultSerDes.toJSON(this);
	}

}