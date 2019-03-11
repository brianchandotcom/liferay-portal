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
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
@GraphQLName("BulkAssetEntryCommonTags")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "BulkAssetEntryCommonTags")
public class BulkAssetEntryCommonTags {

	public String getDescription() {
		return description;
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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String description;

	public Long[] getGroupIds() {
		return groupIds;
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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long[] groupIds;

	public String getStatus() {
		return status;
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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String status;

	public String[] getTagNames() {
		return tagNames;
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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] tagNames;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(description);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"groupIds\": ");

		if (groupIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < groupIds.length; i++) {
				sb.append(groupIds[i]);

				if ((i + 1) < groupIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"status\": ");

		sb.append("\"");
		sb.append(status);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"tagNames\": ");

		if (tagNames == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < tagNames.length; i++) {
				sb.append("\"");
				sb.append(tagNames[i]);
				sb.append("\"");

				if ((i + 1) < tagNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}