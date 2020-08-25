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
@GraphQLName("RenderedContent")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "RenderedContent")
public class RenderedContent {

	public static RenderedContent toDTO(String json) {
		return ObjectMapperUtil.readValue(RenderedContent.class, json);
	}

	@Schema(description = "An absolute URL to the rendered content.")
	public String getRenderedContentURL() {
		return renderedContentURL;
	}

	public void setRenderedContentURL(String renderedContentURL) {
		this.renderedContentURL = renderedContentURL;
	}

	@JsonIgnore
	public void setRenderedContentURL(
		UnsafeSupplier<String, Exception> renderedContentURLUnsafeSupplier) {

		try {
			renderedContentURL = renderedContentURLUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "An absolute URL to the rendered content.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String renderedContentURL;

	@Schema(
		description = "optional field with the rendered content, can be embedded with nestedFields"
	)
	public String getRenderedContentValue() {
		return renderedContentValue;
	}

	public void setRenderedContentValue(String renderedContentValue) {
		this.renderedContentValue = renderedContentValue;
	}

	@JsonIgnore
	public void setRenderedContentValue(
		UnsafeSupplier<String, Exception> renderedContentValueUnsafeSupplier) {

		try {
			renderedContentValue = renderedContentValueUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "optional field with the rendered content, can be embedded with nestedFields"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String renderedContentValue;

	@Schema(
		description = "The name of the template used to render the content."
	)
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@JsonIgnore
	public void setTemplateName(
		UnsafeSupplier<String, Exception> templateNameUnsafeSupplier) {

		try {
			templateName = templateNameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The name of the template used to render the content."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String templateName;

	@Schema
	@Valid
	public Map<String, String> getTemplateName_i18n() {
		return templateName_i18n;
	}

	public void setTemplateName_i18n(Map<String, String> templateName_i18n) {
		this.templateName_i18n = templateName_i18n;
	}

	@JsonIgnore
	public void setTemplateName_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			templateName_i18nUnsafeSupplier) {

		try {
			templateName_i18n = templateName_i18nUnsafeSupplier.get();
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
	protected Map<String, String> templateName_i18n;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RenderedContent)) {
			return false;
		}

		RenderedContent renderedContent = (RenderedContent)object;

		return Objects.equals(toString(), renderedContent.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (renderedContentURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"renderedContentURL\": ");

			sb.append("\"");

			sb.append(_escape(renderedContentURL));

			sb.append("\"");
		}

		if (renderedContentValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"renderedContentValue\": ");

			sb.append("\"");

			sb.append(_escape(renderedContentValue));

			sb.append("\"");
		}

		if (templateName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"templateName\": ");

			sb.append("\"");

			sb.append(_escape(templateName));

			sb.append("\"");
		}

		if (templateName_i18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"templateName_i18n\": ");

			sb.append(_toJSON(templateName_i18n));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.RenderedContent",
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