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
@GraphQLName("SelectionToAddCategoryIds")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SelectionToAddCategoryIds")
public class SelectionToAddCategoryIds extends DocumentSelection {

	public Long[] getSelectionToAddCategoryIds() {
		return selectionToAddCategoryIds;
	}

	public void setSelectionToAddCategoryIds(Long[] selectionToAddCategoryIds) {
		this.selectionToAddCategoryIds = selectionToAddCategoryIds;
	}

	@JsonIgnore
	public void setSelectionToAddCategoryIds(
		UnsafeSupplier<Long[], Exception>
			selectionToAddCategoryIdsUnsafeSupplier) {

		try {
			selectionToAddCategoryIds =
				selectionToAddCategoryIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long[] selectionToAddCategoryIds;

	public Long[] getSelectionToRemoveCategoryIds() {
		return selectionToRemoveCategoryIds;
	}

	public void setSelectionToRemoveCategoryIds(
		Long[] selectionToRemoveCategoryIds) {

		this.selectionToRemoveCategoryIds = selectionToRemoveCategoryIds;
	}

	@JsonIgnore
	public void setSelectionToRemoveCategoryIds(
		UnsafeSupplier<Long[], Exception>
			selectionToRemoveCategoryIdsUnsafeSupplier) {

		try {
			selectionToRemoveCategoryIds =
				selectionToRemoveCategoryIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long[] selectionToRemoveCategoryIds;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"selectionToAddCategoryIds\": ");

		if (selectionToAddCategoryIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < selectionToAddCategoryIds.length; i++) {
				sb.append(selectionToAddCategoryIds[i]);

				if ((i + 1) < selectionToAddCategoryIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"selectionToRemoveCategoryIds\": ");

		if (selectionToRemoveCategoryIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < selectionToRemoveCategoryIds.length; i++) {
				sb.append(selectionToRemoveCategoryIds[i]);

				if ((i + 1) < selectionToRemoveCategoryIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}