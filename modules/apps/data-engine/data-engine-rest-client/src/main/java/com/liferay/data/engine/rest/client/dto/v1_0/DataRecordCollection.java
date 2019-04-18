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
public class DataRecordCollection {

	public Long getDataDefinitionId() {
		return dataDefinitionId;
	}

	public void setDataDefinitionId(Long dataDefinitionId) {
		this.dataDefinitionId = dataDefinitionId;
	}

	public void setDataDefinitionId(
		UnsafeSupplier<Long, Exception> dataDefinitionIdUnsafeSupplier) {

		try {
			dataDefinitionId = dataDefinitionIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long dataDefinitionId;

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

	public LocalizedValue[] getName() {
		return name;
	}

	public void setName(LocalizedValue[] name) {
		this.name = name;
	}

	public void setName(
		UnsafeSupplier<LocalizedValue[], Exception> nameUnsafeSupplier) {

		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LocalizedValue[] name;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataRecordCollection)) {
			return false;
		}

		DataRecordCollection dataRecordCollection =
			(DataRecordCollection)object;

		return Objects.equals(toString(), dataRecordCollection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"dataDefinitionId\": ");

		if (dataDefinitionId == null) {
			sb.append("null");
		}
		else {
			sb.append(dataDefinitionId);
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

		sb.append("\"id\": ");

		if (id == null) {
			sb.append("null");
		}
		else {
			sb.append(id);
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (name == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < name.length; i++) {
				sb.append(name[i]);

				if ((i + 1) < name.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}