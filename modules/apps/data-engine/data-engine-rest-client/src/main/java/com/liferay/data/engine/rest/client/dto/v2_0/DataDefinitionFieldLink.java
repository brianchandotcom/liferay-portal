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
import com.liferay.data.engine.rest.client.serdes.v2_0.DataDefinitionFieldLinkSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionFieldLink implements Cloneable {

	public static DataDefinitionFieldLink toDTO(String json) {
		return DataDefinitionFieldLinkSerDes.toDTO(json);
	}

	public DataDefinition getDataDefinition() {
		return dataDefinition;
	}

	public void setDataDefinition(DataDefinition dataDefinition) {
		this.dataDefinition = dataDefinition;
	}

	public void setDataDefinition(
		UnsafeSupplier<DataDefinition, Exception>
			dataDefinitionUnsafeSupplier) {

		try {
			dataDefinition = dataDefinitionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DataDefinition dataDefinition;

	public DataLayout[] getDataLayouts() {
		return dataLayouts;
	}

	public void setDataLayouts(DataLayout[] dataLayouts) {
		this.dataLayouts = dataLayouts;
	}

	public void setDataLayouts(
		UnsafeSupplier<DataLayout[], Exception> dataLayoutsUnsafeSupplier) {

		try {
			dataLayouts = dataLayoutsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DataLayout[] dataLayouts;

	public DataListView[] getDataListViews() {
		return dataListViews;
	}

	public void setDataListViews(DataListView[] dataListViews) {
		this.dataListViews = dataListViews;
	}

	public void setDataListViews(
		UnsafeSupplier<DataListView[], Exception> dataListViewsUnsafeSupplier) {

		try {
			dataListViews = dataListViewsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DataListView[] dataListViews;

	@Override
	public DataDefinitionFieldLink clone() throws CloneNotSupportedException {
		return (DataDefinitionFieldLink)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataDefinitionFieldLink)) {
			return false;
		}

		DataDefinitionFieldLink dataDefinitionFieldLink =
			(DataDefinitionFieldLink)object;

		return Objects.equals(toString(), dataDefinitionFieldLink.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DataDefinitionFieldLinkSerDes.toJSON(this);
	}

}