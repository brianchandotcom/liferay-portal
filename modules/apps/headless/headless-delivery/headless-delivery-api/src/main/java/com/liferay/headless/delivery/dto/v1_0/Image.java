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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Image")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Image")
public class Image {

	public static Image toDTO(String json) {
		return ObjectMapperUtil.readValue(Image.class, json);
	}

	@Schema(description = "The text describing the image.")
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@JsonIgnore
	public void setCaption(
		UnsafeSupplier<String, Exception> captionUnsafeSupplier) {

		try {
			caption = captionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The text describing the image.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String caption;

	@Schema(description = "The image's relative URL.")
	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	@JsonIgnore
	public void setContentUrl(
		UnsafeSupplier<String, Exception> contentUrlUnsafeSupplier) {

		try {
			contentUrl = contentUrlUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The image's relative URL.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String contentUrl;

	@Schema(
		description = "optional field with the content of the image in Base64, can be embedded with nestedFields"
	)
	public String getContentValue() {
		return contentValue;
	}

	public void setContentValue(String contentValue) {
		this.contentValue = contentValue;
	}

	@JsonIgnore
	public void setContentValue(
		UnsafeSupplier<String, Exception> contentValueUnsafeSupplier) {

		try {
			contentValue = contentValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "optional field with the content of the image in Base64, can be embedded with nestedFields"
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String contentValue;

	@Schema(
		description = "The image's ID. This can be used to retrieve more information in the `Document` API."
	)
	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	@JsonIgnore
	public void setImageId(
		UnsafeSupplier<Long, Exception> imageIdUnsafeSupplier) {

		try {
			imageId = imageIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The image's ID. This can be used to retrieve more information in the `Document` API."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long imageId;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Image)) {
			return false;
		}

		Image image = (Image)object;

		return Objects.equals(toString(), image.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (caption != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caption\": ");

			sb.append("\"");

			sb.append(_escape(caption));

			sb.append("\"");
		}

		if (contentUrl != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentUrl\": ");

			sb.append("\"");

			sb.append(_escape(contentUrl));

			sb.append("\"");
		}

		if (contentValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentValue\": ");

			sb.append("\"");

			sb.append(_escape(contentValue));

			sb.append("\"");
		}

		if (imageId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"imageId\": ");

			sb.append(imageId);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.Image",
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