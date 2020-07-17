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

package com.liferay.headless.delivery.dto.v1_0;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("DocumentType")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DocumentType")
public class DocumentType {

	public static DocumentType toDTO(String json) {
		return ObjectMapperUtil.readValue(DocumentType.class, json);
	}

	@Schema
	public String[] getAvailableLanguages() {
		return availableLanguages;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		this.availableLanguages = availableLanguages;
	}

	@JsonIgnore
	public void setAvailableLanguages(
		UnsafeSupplier<String[], Exception> availableLanguagesUnsafeSupplier) {

		try {
			availableLanguages = availableLanguagesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String[] availableLanguages;

	@Schema
	@Valid
	public ContentField[] getContentFields() {
		return contentFields;
	}

	public void setContentFields(ContentField[] contentFields) {
		this.contentFields = contentFields;
	}

	@JsonIgnore
	public void setContentFields(
		UnsafeSupplier<ContentField[], Exception> contentFieldsUnsafeSupplier) {

		try {
			contentFields = contentFieldsUnsafeSupplier.get();
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
	protected ContentField[] contentFields;

	@Schema
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String description;

	@Schema
	@Valid
	public Map<String, String> getDescription_i18n() {
		return description_i18n;
	}

	public void setDescription_i18n(Map<String, String> description_i18n) {
		this.description_i18n = description_i18n;
	}

	@JsonIgnore
	public void setDescription_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			description_i18nUnsafeSupplier) {

		try {
			description_i18n = description_i18nUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, String> description_i18n;

	@Schema
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
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
	protected String name;

	@Schema
	@Valid
	public Map<String, String> getName_i18n() {
		return name_i18n;
	}

	public void setName_i18n(Map<String, String> name_i18n) {
		this.name_i18n = name_i18n;
	}

	@JsonIgnore
	public void setName_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			name_i18nUnsafeSupplier) {

		try {
			name_i18n = name_i18nUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, String> name_i18n;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DocumentType)) {
			return false;
		}

		DocumentType documentType = (DocumentType)object;

		return Objects.equals(toString(), documentType.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (availableLanguages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availableLanguages\": ");

			sb.append("[");

			for (int i = 0; i < availableLanguages.length; i++) {
				sb.append("\"");

				sb.append(_escape(availableLanguages[i]));

				sb.append("\"");

				if ((i + 1) < availableLanguages.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contentFields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentFields\": ");

			sb.append("[");

			for (int i = 0; i < contentFields.length; i++) {
				sb.append(String.valueOf(contentFields[i]));

				if ((i + 1) < contentFields.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (description != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(description));

			sb.append("\"");
		}

		if (description_i18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(description_i18n));
		}

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		if (name_i18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(name_i18n));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.DocumentType",
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