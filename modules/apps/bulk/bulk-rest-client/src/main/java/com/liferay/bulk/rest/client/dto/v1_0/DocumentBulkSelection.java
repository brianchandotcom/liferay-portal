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

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class DocumentBulkSelection {

	public String[] getDocumentIds() {
		return documentIds;
	}

	public void setDocumentIds(String[] documentIds) {
		this.documentIds = documentIds;
	}

	public void setDocumentIds(
		UnsafeSupplier<String[], Exception> documentIdsUnsafeSupplier) {

		try {
			documentIds = documentIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] documentIds;

	public SelectionScope getSelectionScope() {
		return selectionScope;
	}

	public void setSelectionScope(SelectionScope selectionScope) {
		this.selectionScope = selectionScope;
	}

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

	protected SelectionScope selectionScope;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DocumentBulkSelection)) {
			return false;
		}

		DocumentBulkSelection documentBulkSelection =
			(DocumentBulkSelection)object;

		return Objects.equals(toString(), documentBulkSelection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

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

		if (selectionScope == null) {
			sb.append("null");
		}
		else {
			sb.append(selectionScope);
		}

		sb.append("}");

		return sb.toString();
	}

}