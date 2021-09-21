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
import com.liferay.headless.delivery.client.serdes.v1_0.ColumnViewportSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ColumnViewport implements Cloneable, Serializable {

	public static ColumnViewport toDTO(String json) {
		return ColumnViewportSerDes.toDTO(json);
	}

	public ColumnViewportDefinition getColumnViewportDefinition() {
		return columnViewportDefinition;
	}

	public void setColumnViewportDefinition(
		ColumnViewportDefinition columnViewportDefinition) {

		this.columnViewportDefinition = columnViewportDefinition;
	}

	public void setColumnViewportDefinition(
		UnsafeSupplier<ColumnViewportDefinition, Exception>
			columnViewportDefinitionUnsafeSupplier) {

		try {
			columnViewportDefinition =
				columnViewportDefinitionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ColumnViewportDefinition columnViewportDefinition;

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

	@Override
	public ColumnViewport clone() throws CloneNotSupportedException {
		return (ColumnViewport)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ColumnViewport)) {
			return false;
		}

		ColumnViewport columnViewport = (ColumnViewport)object;

		return Objects.equals(toString(), columnViewport.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ColumnViewportSerDes.toJSON(this);
	}

}