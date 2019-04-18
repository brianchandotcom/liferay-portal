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

package com.liferay.data.engine.rest.client.dto.v1_0;

import com.liferay.data.engine.rest.client.function.UnsafeSupplier;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataLayoutPage {

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

	public LocalizedValue[] getDescription() {
		return description;
	}

	public void setDescription(LocalizedValue[] description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<LocalizedValue[], Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LocalizedValue[] description;

	public LocalizedValue[] getTitle() {
		return title;
	}

	public void setTitle(LocalizedValue[] title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<LocalizedValue[], Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LocalizedValue[] title;

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
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"dataLayoutRows\": ");

		if (dataLayoutRows == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < dataLayoutRows.length; i++) {
				sb.append(dataLayoutRows[i]);

				if ((i + 1) < dataLayoutRows.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (description == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < description.length; i++) {
				sb.append(description[i]);

				if ((i + 1) < description.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (title == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < title.length; i++) {
				sb.append(title[i]);

				if ((i + 1) < title.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}