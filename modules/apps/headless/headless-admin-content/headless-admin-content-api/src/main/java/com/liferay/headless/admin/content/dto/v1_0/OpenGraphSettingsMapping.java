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

package com.liferay.headless.admin.content.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("OpenGraphSettingsMapping")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "OpenGraphSettingsMapping")
public class OpenGraphSettingsMapping implements Serializable {

	public static OpenGraphSettingsMapping toDTO(String json) {
		return ObjectMapperUtil.readValue(OpenGraphSettingsMapping.class, json);
	}

	@Schema(
		description = "Field of the content type that will be used as the description"
	)
	public String getDescriptionMappingField() {
		return descriptionMappingField;
	}

	public void setDescriptionMappingField(String descriptionMappingField) {
		this.descriptionMappingField = descriptionMappingField;
	}

	@JsonIgnore
	public void setDescriptionMappingField(
		UnsafeSupplier<String, Exception>
			descriptionMappingFieldUnsafeSupplier) {

		try {
			descriptionMappingField =
				descriptionMappingFieldUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Field of the content type that will be used as the description"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String descriptionMappingField;

	@Schema(
		description = "Field of the content type that will be used as the alt property of the image"
	)
	public String getImageAltMappingField() {
		return imageAltMappingField;
	}

	public void setImageAltMappingField(String imageAltMappingField) {
		this.imageAltMappingField = imageAltMappingField;
	}

	@JsonIgnore
	public void setImageAltMappingField(
		UnsafeSupplier<String, Exception> imageAltMappingFieldUnsafeSupplier) {

		try {
			imageAltMappingField = imageAltMappingFieldUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Field of the content type that will be used as the alt property of the image"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String imageAltMappingField;

	@Schema(
		description = "Field of the content type that will be used as the image"
	)
	public String getImageMappingField() {
		return imageMappingField;
	}

	public void setImageMappingField(String imageMappingField) {
		this.imageMappingField = imageMappingField;
	}

	@JsonIgnore
	public void setImageMappingField(
		UnsafeSupplier<String, Exception> imageMappingFieldUnsafeSupplier) {

		try {
			imageMappingField = imageMappingFieldUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Field of the content type that will be used as the image"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String imageMappingField;

	@Schema(
		description = "Field of the content type that will be used as the title"
	)
	public String getTitleMappingField() {
		return titleMappingField;
	}

	public void setTitleMappingField(String titleMappingField) {
		this.titleMappingField = titleMappingField;
	}

	@JsonIgnore
	public void setTitleMappingField(
		UnsafeSupplier<String, Exception> titleMappingFieldUnsafeSupplier) {

		try {
			titleMappingField = titleMappingFieldUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Field of the content type that will be used as the title"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String titleMappingField;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OpenGraphSettingsMapping)) {
			return false;
		}

		OpenGraphSettingsMapping openGraphSettingsMapping =
			(OpenGraphSettingsMapping)object;

		return Objects.equals(toString(), openGraphSettingsMapping.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (descriptionMappingField != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"descriptionMappingField\": ");

			sb.append("\"");

			sb.append(_escape(descriptionMappingField));

			sb.append("\"");
		}

		if (imageAltMappingField != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"imageAltMappingField\": ");

			sb.append("\"");

			sb.append(_escape(imageAltMappingField));

			sb.append("\"");
		}

		if (imageMappingField != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"imageMappingField\": ");

			sb.append("\"");

			sb.append(_escape(imageMappingField));

			sb.append("\"");
		}

		if (titleMappingField != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"titleMappingField\": ");

			sb.append("\"");

			sb.append(_escape(titleMappingField));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.admin.content.dto.v1_0.OpenGraphSettingsMapping",
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