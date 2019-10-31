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
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
@GraphQLName("TaxonomyCategoryBulkSelection")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TaxonomyCategoryBulkSelection")
public class TaxonomyCategoryBulkSelection {

	@Schema
	@Valid
	public DocumentBulkSelection getDocumentBulkSelection() {
		return documentBulkSelection;
	}

	public void setDocumentBulkSelection(
		DocumentBulkSelection documentBulkSelection) {

		this.documentBulkSelection = documentBulkSelection;
	}

	@JsonIgnore
	public void setDocumentBulkSelection(
		UnsafeSupplier<DocumentBulkSelection, Exception>
			documentBulkSelectionUnsafeSupplier) {

		try {
			documentBulkSelection = documentBulkSelectionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DocumentBulkSelection documentBulkSelection;

	@Schema
	public Long[] getTaxonomyCategoryIdsToAdd() {
		return taxonomyCategoryIdsToAdd;
	}

	public void setTaxonomyCategoryIdsToAdd(Long[] taxonomyCategoryIdsToAdd) {
		this.taxonomyCategoryIdsToAdd = taxonomyCategoryIdsToAdd;
	}

	@JsonIgnore
	public void setTaxonomyCategoryIdsToAdd(
		UnsafeSupplier<Long[], Exception>
			taxonomyCategoryIdsToAddUnsafeSupplier) {

		try {
			taxonomyCategoryIdsToAdd =
				taxonomyCategoryIdsToAddUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long[] taxonomyCategoryIdsToAdd;

	@Schema
	public Long[] getTaxonomyCategoryIdsToRemove() {
		return taxonomyCategoryIdsToRemove;
	}

	public void setTaxonomyCategoryIdsToRemove(
		Long[] taxonomyCategoryIdsToRemove) {

		this.taxonomyCategoryIdsToRemove = taxonomyCategoryIdsToRemove;
	}

	@JsonIgnore
	public void setTaxonomyCategoryIdsToRemove(
		UnsafeSupplier<Long[], Exception>
			taxonomyCategoryIdsToRemoveUnsafeSupplier) {

		try {
			taxonomyCategoryIdsToRemove =
				taxonomyCategoryIdsToRemoveUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long[] taxonomyCategoryIdsToRemove;

	@Schema(
		defaultValue = "com.liferay.bulk.rest.dto.v1_0.TaxonomyCategoryBulkSelection",
		name = "x-classname"
	)
	public String xClassName;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TaxonomyCategoryBulkSelection)) {
			return false;
		}

		TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection =
			(TaxonomyCategoryBulkSelection)object;

		return Objects.equals(
			toString(), taxonomyCategoryBulkSelection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (documentBulkSelection != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentBulkSelection\": ");

			sb.append(String.valueOf(documentBulkSelection));
		}

		if (taxonomyCategoryIdsToAdd != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryIdsToAdd\": ");

			sb.append("[");

			for (int i = 0; i < taxonomyCategoryIdsToAdd.length; i++) {
				sb.append(taxonomyCategoryIdsToAdd[i]);

				if ((i + 1) < taxonomyCategoryIdsToAdd.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (taxonomyCategoryIdsToRemove != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryIdsToRemove\": ");

			sb.append("[");

			for (int i = 0; i < taxonomyCategoryIdsToRemove.length; i++) {
				sb.append(taxonomyCategoryIdsToRemove[i]);

				if ((i + 1) < taxonomyCategoryIdsToRemove.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}