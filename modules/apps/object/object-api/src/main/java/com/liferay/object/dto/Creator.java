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

package com.liferay.object.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier de Arcos
 */
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Creator")
public class Creator implements Serializable {

	public static Creator toDTO(String json) {
		return ObjectMapperUtil.readValue(Creator.class, json);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Creator)) {
			return false;
		}

		Creator creator = (Creator)object;

		return Objects.equals(toString(), creator.toString());
	}

	@Schema(description = "The author's additional name (e.g., middle name).")
	public String getAdditionalName() {
		return additionalName;
	}

	@Schema(description = "The type of the content.")
	public String getContentType() {
		return contentType;
	}

	@Schema(description = "The author's surname.")
	public String getFamilyName() {
		return familyName;
	}

	@Schema(description = "The author's first name.")
	public String getGivenName() {
		return givenName;
	}

	@Schema(description = "The author's ID.")
	public Long getId() {
		return id;
	}

	@Schema(description = "A relative URL to the author's profile image.")
	public String getImage() {
		return image;
	}

	@Schema(description = "The author's full name.")
	public String getName() {
		return name;
	}

	@Schema(
		description = "A relative URL to the author's user profile. Optional field, can be embedded with nestedFields."
	)
	public String getProfileURL() {
		return profileURL;
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public void setAdditionalName(String additionalName) {
		this.additionalName = additionalName;
	}

	@JsonIgnore
	public void setAdditionalName(
		UnsafeSupplier<String, Exception> additionalNameUnsafeSupplier) {

		try {
			additionalName = additionalNameUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@JsonIgnore
	public void setContentType(
		UnsafeSupplier<String, Exception> contentTypeUnsafeSupplier) {

		try {
			contentType = contentTypeUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	@JsonIgnore
	public void setFamilyName(
		UnsafeSupplier<String, Exception> familyNameUnsafeSupplier) {

		try {
			familyName = familyNameUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	@JsonIgnore
	public void setGivenName(
		UnsafeSupplier<String, Exception> givenNameUnsafeSupplier) {

		try {
			givenName = givenNameUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setImage(String image) {
		this.image = image;
	}

	@JsonIgnore
	public void setImage(
		UnsafeSupplier<String, Exception> imageUnsafeSupplier) {

		try {
			image = imageUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setProfileURL(String profileURL) {
		this.profileURL = profileURL;
	}

	@JsonIgnore
	public void setProfileURL(
		UnsafeSupplier<String, Exception> profileURLUnsafeSupplier) {

		try {
			profileURL = profileURLUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(40);

		sb.append("{");

		if (additionalName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"additionalName\": ");

			sb.append("\"");

			sb.append(_escape(additionalName));

			sb.append("\"");
		}

		if (contentType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append("\"");

			sb.append(_escape(contentType));

			sb.append("\"");
		}

		if (familyName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"familyName\": ");

			sb.append("\"");

			sb.append(_escape(familyName));

			sb.append("\"");
		}

		if (givenName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"givenName\": ");

			sb.append("\"");

			sb.append(_escape(givenName));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (image != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append("\"");

			sb.append(_escape(image));

			sb.append("\"");
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

		if (profileURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"profileURL\": ");

			sb.append("\"");

			sb.append(_escape(profileURL));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.Creator",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLField(
		description = "The author's additional name (e.g., middle name)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String additionalName;

	@GraphQLField(description = "The type of the content.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String contentType;

	@GraphQLField(description = "The author's surname.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String familyName;

	@GraphQLField(description = "The author's first name.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String givenName;

	@GraphQLField(description = "The author's ID.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@GraphQLField(description = "A relative URL to the author's profile image.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String image;

	@GraphQLField(description = "The author's full name.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String name;

	@GraphQLField(
		description = "A relative URL to the author's user profile. Optional field, can be embedded with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String profileURL;

	private String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set<?> set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator =
			(Iterator<Map.Entry<String, ?>>)set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

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
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}