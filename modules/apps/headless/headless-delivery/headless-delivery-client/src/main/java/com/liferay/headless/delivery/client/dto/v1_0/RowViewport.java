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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.RowViewportSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RowViewport implements Cloneable, Serializable {

	public static RowViewport toDTO(String json) {
		return RowViewportSerDes.toDTO(json);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String id;

	public RowViewportDefinition getRowViewportDefinition() {
		return rowViewportDefinition;
	}

	public void setRowViewportDefinition(
		RowViewportDefinition rowViewportDefinition) {

		this.rowViewportDefinition = rowViewportDefinition;
	}

	public void setRowViewportDefinition(
		UnsafeSupplier<RowViewportDefinition, Exception>
			rowViewportDefinitionUnsafeSupplier) {

		try {
			rowViewportDefinition = rowViewportDefinitionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RowViewportDefinition rowViewportDefinition;

	@Override
	public RowViewport clone() throws CloneNotSupportedException {
		return (RowViewport)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RowViewport)) {
			return false;
		}

		RowViewport rowViewport = (RowViewport)object;

		return Objects.equals(toString(), rowViewport.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RowViewportSerDes.toJSON(this);
	}

}