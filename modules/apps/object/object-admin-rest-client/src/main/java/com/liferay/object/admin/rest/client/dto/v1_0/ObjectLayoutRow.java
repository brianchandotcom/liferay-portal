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

package com.liferay.object.admin.rest.client.dto.v1_0;

import com.liferay.object.admin.rest.client.function.UnsafeSupplier;
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectLayoutRowSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectLayoutRow implements Cloneable, Serializable {

	public static ObjectLayoutRow toDTO(String json) {
		return ObjectLayoutRowSerDes.toDTO(json);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public ObjectLayoutColumn[] getObjectLayoutColumns() {
		return objectLayoutColumns;
	}

	public void setObjectLayoutColumns(
		ObjectLayoutColumn[] objectLayoutColumns) {

		this.objectLayoutColumns = objectLayoutColumns;
	}

	public void setObjectLayoutColumns(
		UnsafeSupplier<ObjectLayoutColumn[], Exception>
			objectLayoutColumnsUnsafeSupplier) {

		try {
			objectLayoutColumns = objectLayoutColumnsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ObjectLayoutColumn[] objectLayoutColumns;

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setPriority(
		UnsafeSupplier<Integer, Exception> priorityUnsafeSupplier) {

		try {
			priority = priorityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer priority;

	@Override
	public ObjectLayoutRow clone() throws CloneNotSupportedException {
		return (ObjectLayoutRow)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutRow)) {
			return false;
		}

		ObjectLayoutRow objectLayoutRow = (ObjectLayoutRow)object;

		return Objects.equals(toString(), objectLayoutRow.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ObjectLayoutRowSerDes.toJSON(this);
	}

}