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

package com.liferay.data.engine.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@GraphQLName("DataLayoutColumn")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DataLayoutColumn")
public class DataLayoutColumn {

	public Integer getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(Integer columnSize) {
		this.columnSize = columnSize;
	}

	@JsonIgnore
	public void setColumnSize(
		UnsafeSupplier<Integer, Exception> columnSizeUnsafeSupplier) {

		try {
			columnSize = columnSizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer columnSize;

	public String[] getFieldsName() {
		return fieldsName;
	}

	public void setFieldsName(String[] fieldsName) {
		this.fieldsName = fieldsName;
	}

	@JsonIgnore
	public void setFieldsName(
		UnsafeSupplier<String[], Exception> fieldsNameUnsafeSupplier) {

		try {
			fieldsName = fieldsNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] fieldsName;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"columnSize\": ");

		sb.append(columnSize);
		sb.append(", ");

		sb.append("\"fieldsName\": ");

		if (fieldsName == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < fieldsName.length; i++) {
				sb.append("\"");
				sb.append(fieldsName[i]);
				sb.append("\"");

				if ((i + 1) < fieldsName.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}