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

package com.liferay.headless.form.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Grid")
@XmlRootElement(name = "Grid")
public class Grid {

	public Columns[] getColumns() {
		return columns;
	}

	public Long getId() {
		return id;
	}

	public Rows[] getRows() {
		return rows;
	}

	public void setColumns(Columns[] columns) {
		this.columns = columns;
	}

	public void setColumns(
		UnsafeSupplier<Columns[], Throwable> columnsUnsafeSupplier) {

		try {
			columns = columnsUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setRows(Rows[] rows) {
		this.rows = rows;
	}

	public void setRows(UnsafeSupplier<Rows[], Throwable> rowsUnsafeSupplier) {
		try {
			rows = rowsUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected Columns[] columns;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected Rows[] rows;

}