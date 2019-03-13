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
@GraphQLName("SelectionToAddTagNames")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SelectionToAddTagNames")
public class SelectionToAddTagNames extends DocumentSelection {

	public String[] getSelectionToAddTagNames() {
		return selectionToAddTagNames;
	}

	public void setSelectionToAddTagNames(String[] selectionToAddTagNames) {
		this.selectionToAddTagNames = selectionToAddTagNames;
	}

	@JsonIgnore
	public void setSelectionToAddTagNames(
		UnsafeSupplier<String[], Exception>
			selectionToAddTagNamesUnsafeSupplier) {

		try {
			selectionToAddTagNames = selectionToAddTagNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] selectionToAddTagNames;

	public String[] getSelectionToRemoveTagNames() {
		return selectionToRemoveTagNames;
	}

	public void setSelectionToRemoveTagNames(
		String[] selectionToRemoveTagNames) {

		this.selectionToRemoveTagNames = selectionToRemoveTagNames;
	}

	@JsonIgnore
	public void setSelectionToRemoveTagNames(
		UnsafeSupplier<String[], Exception>
			selectionToRemoveTagNamesUnsafeSupplier) {

		try {
			selectionToRemoveTagNames =
				selectionToRemoveTagNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] selectionToRemoveTagNames;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"selectionToAddTagNames\": ");

		if (selectionToAddTagNames == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < selectionToAddTagNames.length; i++) {
				sb.append("\"");
				sb.append(selectionToAddTagNames[i]);
				sb.append("\"");

				if ((i + 1) < selectionToAddTagNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"selectionToRemoveTagNames\": ");

		if (selectionToRemoveTagNames == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < selectionToRemoveTagNames.length; i++) {
				sb.append("\"");
				sb.append(selectionToRemoveTagNames[i]);
				sb.append("\"");

				if ((i + 1) < selectionToRemoveTagNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}