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
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

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
@GraphQLName("KeywordBulkSelection")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "KeywordBulkSelection")
public class KeywordBulkSelection {

	public static KeywordBulkSelection toDTO(String json) {
		return ObjectMapperUtil.readValue(KeywordBulkSelection.class, json);
	}

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
	public String[] getKeywordsToAdd() {
		return keywordsToAdd;
	}

	public void setKeywordsToAdd(String[] keywordsToAdd) {
		this.keywordsToAdd = keywordsToAdd;
	}

	@JsonIgnore
	public void setKeywordsToAdd(
		UnsafeSupplier<String[], Exception> keywordsToAddUnsafeSupplier) {

		try {
			keywordsToAdd = keywordsToAddUnsafeSupplier.get();
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
	protected String[] keywordsToAdd;

	@Schema
	public String[] getKeywordsToRemove() {
		return keywordsToRemove;
	}

	public void setKeywordsToRemove(String[] keywordsToRemove) {
		this.keywordsToRemove = keywordsToRemove;
	}

	@JsonIgnore
	public void setKeywordsToRemove(
		UnsafeSupplier<String[], Exception> keywordsToRemoveUnsafeSupplier) {

		try {
			keywordsToRemove = keywordsToRemoveUnsafeSupplier.get();
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
	protected String[] keywordsToRemove;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KeywordBulkSelection)) {
			return false;
		}

		KeywordBulkSelection keywordBulkSelection =
			(KeywordBulkSelection)object;

		return Objects.equals(toString(), keywordBulkSelection.toString());
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

		if (keywordsToAdd != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywordsToAdd\": ");

			sb.append("[");

			for (int i = 0; i < keywordsToAdd.length; i++) {
				sb.append("\"");

				sb.append(_escape(keywordsToAdd[i]));

				sb.append("\"");

				if ((i + 1) < keywordsToAdd.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (keywordsToRemove != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywordsToRemove\": ");

			sb.append("[");

			for (int i = 0; i < keywordsToRemove.length; i++) {
				sb.append("\"");

				sb.append(_escape(keywordsToRemove[i]));

				sb.append("\"");

				if ((i + 1) < keywordsToRemove.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.bulk.rest.dto.v1_0.KeywordBulkSelection",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(value);
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}