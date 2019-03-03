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

package com.liferay.bulk.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("BulkAssetEntryCommonTagsModel")
@XmlRootElement(name = "BulkAssetEntryCommonTagsModel")
public class BulkAssetEntryCommonTagsModel {

	public String getDescription() {
		return description;
	}

	public Long[] getGroupIds() {
		return groupIds;
	}

	public String getStatus() {
		return status;
	}

	public String[] getTagNames() {
		return tagNames;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setGroupIds(Long[] groupIds) {
		this.groupIds = groupIds;
	}

	@JsonIgnore
	public void setGroupIds(
		UnsafeSupplier<Long[], Exception> groupIdsUnsafeSupplier) {

		try {
			groupIds = groupIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<String, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setTagNames(String[] tagNames) {
		this.tagNames = tagNames;
	}

	@JsonIgnore
	public void setTagNames(
		UnsafeSupplier<String[], Exception> tagNamesUnsafeSupplier) {

		try {
			tagNames = tagNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(description);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"groupIds\": ");

		sb.append("\"");
		sb.append(groupIds);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"status\": ");

		sb.append("\"");
		sb.append(status);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"tagNames\": ");

		sb.append("\"");
		sb.append(tagNames);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String description;

	@GraphQLField
	@JsonProperty
	protected Long[] groupIds;

	@GraphQLField
	@JsonProperty
	protected String status;

	@GraphQLField
	@JsonProperty
	protected String[] tagNames;

}