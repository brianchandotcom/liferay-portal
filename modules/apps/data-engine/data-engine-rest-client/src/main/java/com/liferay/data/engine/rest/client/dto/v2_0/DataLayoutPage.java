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
import com.liferay.data.engine.rest.client.serdes.v2_0.DataLayoutPageSerDes;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataLayoutPage implements Cloneable {

	public DataLayoutRow[] getDataLayoutRows() {
		return dataLayoutRows;
	}

	public void setDataLayoutRows(DataLayoutRow[] dataLayoutRows) {
		this.dataLayoutRows = dataLayoutRows;
	}

	public void setDataLayoutRows(
		UnsafeSupplier<DataLayoutRow[], Exception>
			dataLayoutRowsUnsafeSupplier) {

		try {
			dataLayoutRows = dataLayoutRowsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DataLayoutRow[] dataLayoutRows;

	public Map<String, Object> getDescription() {
		return description;
	}

	public void setDescription(Map<String, Object> description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<Map<String, Object>, Exception>
			descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> description;

	public Map<String, Object> getTitle() {
		return title;
	}

	public void setTitle(Map<String, Object> title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<Map<String, Object>, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> title;

	@Override
	public DataLayoutPage clone() throws CloneNotSupportedException {
		return (DataLayoutPage)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataLayoutPage)) {
			return false;
		}

		DataLayoutPage dataLayoutPage = (DataLayoutPage)object;

		return Objects.equals(toString(), dataLayoutPage.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DataLayoutPageSerDes.toJSON(this);
	}

}