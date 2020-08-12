/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.engine.rest.client.dto.v2_0;

import com.liferay.data.engine.rest.client.function.UnsafeSupplier;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataLayoutRenderingContextSerDes;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataLayoutRenderingContext implements Cloneable {

	public static DataLayoutRenderingContext toDTO(String json) {
		return DataLayoutRenderingContextSerDes.toDTO(json);
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setContainerId(
		UnsafeSupplier<String, Exception> containerIdUnsafeSupplier) {

		try {
			containerId = containerIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String containerId;

	public Map<String, Object> getDataRecordValues() {
		return dataRecordValues;
	}

	public void setDataRecordValues(Map<String, Object> dataRecordValues) {
		this.dataRecordValues = dataRecordValues;
	}

	public void setDataRecordValues(
		UnsafeSupplier<Map<String, Object>, Exception>
			dataRecordValuesUnsafeSupplier) {

		try {
			dataRecordValues = dataRecordValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> dataRecordValues;

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setNamespace(
		UnsafeSupplier<String, Exception> namespaceUnsafeSupplier) {

		try {
			namespace = namespaceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String namespace;

	public String getPathThemeImages() {
		return pathThemeImages;
	}

	public void setPathThemeImages(String pathThemeImages) {
		this.pathThemeImages = pathThemeImages;
	}

	public void setPathThemeImages(
		UnsafeSupplier<String, Exception> pathThemeImagesUnsafeSupplier) {

		try {
			pathThemeImages = pathThemeImagesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String pathThemeImages;

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setReadOnly(
		UnsafeSupplier<Boolean, Exception> readOnlyUnsafeSupplier) {

		try {
			readOnly = readOnlyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean readOnly;

	@Override
	public DataLayoutRenderingContext clone()
		throws CloneNotSupportedException {

		return (DataLayoutRenderingContext)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataLayoutRenderingContext)) {
			return false;
		}

		DataLayoutRenderingContext dataLayoutRenderingContext =
			(DataLayoutRenderingContext)object;

		return Objects.equals(
			toString(), dataLayoutRenderingContext.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DataLayoutRenderingContextSerDes.toJSON(this);
	}

}