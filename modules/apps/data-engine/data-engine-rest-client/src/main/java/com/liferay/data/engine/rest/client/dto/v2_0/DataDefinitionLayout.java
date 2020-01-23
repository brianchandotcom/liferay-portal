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
import com.liferay.data.engine.rest.client.serdes.v2_0.DataDefinitionLayoutSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionLayout {

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

	public DataLayout getDataLayout() {
		return dataLayout;
	}

	public void setDataLayout(DataLayout dataLayout) {
		this.dataLayout = dataLayout;
	}

	public void setDataLayout(
		UnsafeSupplier<DataLayout, Exception> dataLayoutUnsafeSupplier) {

		try {
			dataLayout = dataLayoutUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DataLayout dataLayout;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataDefinitionLayout)) {
			return false;
		}

		DataDefinitionLayout dataDefinitionLayout =
			(DataDefinitionLayout)object;

		return Objects.equals(toString(), dataDefinitionLayout.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DataDefinitionLayoutSerDes.toJSON(this);
	}

}