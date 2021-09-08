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
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectLayoutBoxSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectLayoutBox implements Cloneable, Serializable {

	public static ObjectLayoutBox toDTO(String json) {
		return ObjectLayoutBoxSerDes.toDTO(json);
	}

	public Boolean getCollapsable() {
		return collapsable;
	}

	public void setCollapsable(Boolean collapsable) {
		this.collapsable = collapsable;
	}

	public void setCollapsable(
		UnsafeSupplier<Boolean, Exception> collapsableUnsafeSupplier) {

		try {
			collapsable = collapsableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean collapsable;

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

	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	public void setName(
		UnsafeSupplier<Map<String, String>, Exception> nameUnsafeSupplier) {

		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> name;

	public ObjectLayoutRow[] getObjectLayoutRows() {
		return objectLayoutRows;
	}

	public void setObjectLayoutRows(ObjectLayoutRow[] objectLayoutRows) {
		this.objectLayoutRows = objectLayoutRows;
	}

	public void setObjectLayoutRows(
		UnsafeSupplier<ObjectLayoutRow[], Exception>
			objectLayoutRowsUnsafeSupplier) {

		try {
			objectLayoutRows = objectLayoutRowsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ObjectLayoutRow[] objectLayoutRows;

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
	public ObjectLayoutBox clone() throws CloneNotSupportedException {
		return (ObjectLayoutBox)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutBox)) {
			return false;
		}

		ObjectLayoutBox objectLayoutBox = (ObjectLayoutBox)object;

		return Objects.equals(toString(), objectLayoutBox.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ObjectLayoutBoxSerDes.toJSON(this);
	}

}