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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
@JsonSubTypes(
	{
		@JsonSubTypes.Type(
			name = "selectionToAddCategoryIds",
			value = SelectionToAddCategoryIds.class
		),
		@JsonSubTypes.Type(
			name = "selectionToAddTagNames",
			value = SelectionToAddTagNames.class
		)
	}
)
@JsonTypeInfo(
	include = JsonTypeInfo.As.PROPERTY, property = "type",
	use = JsonTypeInfo.Id.NAME
)
@Generated("")
@GraphQLName("DocumentSelection")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DocumentSelection")
public class DocumentSelection {

	public String[] getDocumentIds() {
		return documentIds;
	}

	public void setDocumentIds(String[] documentIds) {
		this.documentIds = documentIds;
	}

	@JsonIgnore
	public void setDocumentIds(
		UnsafeSupplier<String[], Exception> documentIdsUnsafeSupplier) {

		try {
			documentIds = documentIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] documentIds;

	public SelectionScope getSelectionScope() {
		return selectionScope;
	}

	public void setSelectionScope(SelectionScope selectionScope) {
		this.selectionScope = selectionScope;
	}

	@JsonIgnore
	public void setSelectionScope(
		UnsafeSupplier<SelectionScope, Exception>
			selectionScopeUnsafeSupplier) {

		try {
			selectionScope = selectionScopeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected SelectionScope selectionScope;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"documentIds\": ");

		if (documentIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < documentIds.length; i++) {
				sb.append("\"");
				sb.append(documentIds[i]);
				sb.append("\"");

				if ((i + 1) < documentIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"selectionScope\": ");

		sb.append(selectionScope);

		sb.append("}");

		return sb.toString();
	}

}