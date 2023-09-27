/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.content.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
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

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Specific settings related to SEO",
	value = "SEOSettingsMapping"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SEOSettingsMapping")
public class SEOSettingsMapping implements Serializable {

	public static SEOSettingsMapping toDTO(String json) {
		return ObjectMapperUtil.readValue(SEOSettingsMapping.class, json);
	}

	public static SEOSettingsMapping unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(SEOSettingsMapping.class, json);
	}

	@Schema(
		description = "Field of the content type that will be used as the description"
	)
	public String getDescriptionMappingFieldKey() {
		return descriptionMappingFieldKey;
	}

	public void setDescriptionMappingFieldKey(
		String descriptionMappingFieldKey) {

		this.descriptionMappingFieldKey = descriptionMappingFieldKey;
	}

	@JsonIgnore
	public void setDescriptionMappingFieldKey(
		UnsafeSupplier<String, Exception>
			descriptionMappingFieldKeyUnsafeSupplier) {

		try {
			descriptionMappingFieldKey =
				descriptionMappingFieldKeyUnsafeSupplier.get();
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
	protected String descriptionMappingFieldKey;

	@Schema(
		description = "Field of the content type that will be used as the HTML title"
	)
	public String getHtmlTitleMappingFieldKey() {
		return htmlTitleMappingFieldKey;
	}

	public void setHtmlTitleMappingFieldKey(String htmlTitleMappingFieldKey) {
		this.htmlTitleMappingFieldKey = htmlTitleMappingFieldKey;
	}

	@JsonIgnore
	public void setHtmlTitleMappingFieldKey(
		UnsafeSupplier<String, Exception>
			htmlTitleMappingFieldKeyUnsafeSupplier) {

		try {
			htmlTitleMappingFieldKey =
				htmlTitleMappingFieldKeyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Field of the content type that will be used as the HTML title"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String htmlTitleMappingFieldKey;

	@Schema(
		description = "Robots of the page that renders the Display Page Template"
	)
	public String getRobots() {
		return robots;
	}

	public void setRobots(String robots) {
		this.robots = robots;
	}

	@JsonIgnore
	public void setRobots(
		UnsafeSupplier<String, Exception> robotsUnsafeSupplier) {

		try {
			robots = robotsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Robots of the page that renders the Display Page Template"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String robots;

	@Schema(
		description = "Internationalized field of the robots of the page that renders the Display Page Template"
	)
	@Valid
	public Map<String, String> getRobots_i18n() {
		return robots_i18n;
	}

	public void setRobots_i18n(Map<String, String> robots_i18n) {
		this.robots_i18n = robots_i18n;
	}

	@JsonIgnore
	public void setRobots_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			robots_i18nUnsafeSupplier) {

		try {
			robots_i18n = robots_i18nUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Internationalized field of the robots of the page that renders the Display Page Template"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> robots_i18n;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SEOSettingsMapping)) {
			return false;
		}

		SEOSettingsMapping seoSettingsMapping = (SEOSettingsMapping)object;

		return Objects.equals(toString(), seoSettingsMapping.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (descriptionMappingFieldKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"descriptionMappingFieldKey\": ");

			sb.append("\"");

			sb.append(_escape(descriptionMappingFieldKey));

			sb.append("\"");
		}

		if (htmlTitleMappingFieldKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"htmlTitleMappingFieldKey\": ");

			sb.append("\"");

			sb.append(_escape(htmlTitleMappingFieldKey));

			sb.append("\"");
		}

		if (robots != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"robots\": ");

			sb.append("\"");

			sb.append(_escape(robots));

			sb.append("\"");
		}

		if (robots_i18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"robots_i18n\": ");

			sb.append(_toJSON(robots_i18n));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.admin.content.dto.v1_0.SEOSettingsMapping",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
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
			sb.append(_escape(entry.getKey()));
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
				sb.append(_escape(value));
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

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}