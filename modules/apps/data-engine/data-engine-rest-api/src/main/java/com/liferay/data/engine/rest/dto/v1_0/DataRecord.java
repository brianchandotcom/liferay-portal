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
@GraphQLName("DataRecord")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DataRecord")
public class DataRecord {

	public Long getDataRecordCollectionId() {
		return dataRecordCollectionId;
	}

	public void setDataRecordCollectionId(Long dataRecordCollectionId) {
		this.dataRecordCollectionId = dataRecordCollectionId;
	}

	@JsonIgnore
	public void setDataRecordCollectionId(
		UnsafeSupplier<Long, Exception> dataRecordCollectionIdUnsafeSupplier) {

		try {
			dataRecordCollectionId = dataRecordCollectionIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long dataRecordCollectionId;

	public DataRecordValue[] getDataRecordValues() {
		return dataRecordValues;
	}

	public void setDataRecordValues(DataRecordValue[] dataRecordValues) {
		this.dataRecordValues = dataRecordValues;
	}

	@JsonIgnore
	public void setDataRecordValues(
		UnsafeSupplier<DataRecordValue[], Exception>
			dataRecordValuesUnsafeSupplier) {

		try {
			dataRecordValues = dataRecordValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DataRecordValue[] dataRecordValues;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"dataRecordCollectionId\": ");

		sb.append(dataRecordCollectionId);
		sb.append(", ");

		sb.append("\"dataRecordValues\": ");

		if (dataRecordValues == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < dataRecordValues.length; i++) {
				sb.append(dataRecordValues[i]);

				if ((i + 1) < dataRecordValues.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);

		sb.append("}");

		return sb.toString();
	}

}