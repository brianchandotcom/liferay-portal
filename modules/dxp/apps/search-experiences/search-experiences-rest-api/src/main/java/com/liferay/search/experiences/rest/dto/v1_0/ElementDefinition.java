/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.dto.v1_0;

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

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
@GraphQLName("ElementDefinition")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ElementDefinition")
public class ElementDefinition implements Serializable {

	public static ElementDefinition toDTO(String json) {
		return ObjectMapperUtil.readValue(ElementDefinition.class, json);
	}

	public static ElementDefinition unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ElementDefinition.class, json);
	}

	@Schema
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@JsonIgnore
	public void setCategory(
		UnsafeSupplier<String, Exception> categoryUnsafeSupplier) {

		try {
			category = categoryUnsafeSupplier.get();
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
	protected String category;

	@Schema
	@Valid
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@JsonIgnore
	public void setConfiguration(
		UnsafeSupplier<Configuration, Exception> configurationUnsafeSupplier) {

		try {
			configuration = configurationUnsafeSupplier.get();
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
	protected Configuration configuration;

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
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> description_i18n;

	@Schema
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@JsonIgnore
	public void setIcon(UnsafeSupplier<String, Exception> iconUnsafeSupplier) {
		try {
			icon = iconUnsafeSupplier.get();
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
	protected String icon;

	@Schema
	@Valid
	public Map<String, String> getTitle_i18n() {
		return title_i18n;
	}

	public void setTitle_i18n(Map<String, String> title_i18n) {
		this.title_i18n = title_i18n;
	}

	@JsonIgnore
	public void setTitle_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			title_i18nUnsafeSupplier) {

		try {
			title_i18n = title_i18nUnsafeSupplier.get();
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
	protected Map<String, String> title_i18n;

	@Schema
	@Valid
	public UiConfiguration getUiConfiguration() {
		return uiConfiguration;
	}

	public void setUiConfiguration(UiConfiguration uiConfiguration) {
		this.uiConfiguration = uiConfiguration;
	}

	@JsonIgnore
	public void setUiConfiguration(
		UnsafeSupplier<UiConfiguration, Exception>
			uiConfigurationUnsafeSupplier) {

		try {
			uiConfiguration = uiConfigurationUnsafeSupplier.get();
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
	protected UiConfiguration uiConfiguration;

	@Schema
	@Valid
	public Map<String, ValueDefinition> getUiConfigurationValues() {
		return uiConfigurationValues;
	}

	public void setUiConfigurationValues(
		Map<String, ValueDefinition> uiConfigurationValues) {

		this.uiConfigurationValues = uiConfigurationValues;
	}

	@JsonIgnore
	public void setUiConfigurationValues(
		UnsafeSupplier<Map<String, ValueDefinition>, Exception>
			uiConfigurationValuesUnsafeSupplier) {

		try {
			uiConfigurationValues = uiConfigurationValuesUnsafeSupplier.get();
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
	protected Map<String, ValueDefinition> uiConfigurationValues;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ElementDefinition)) {
			return false;
		}

		ElementDefinition elementDefinition = (ElementDefinition)object;

		return Objects.equals(toString(), elementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (category != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"category\": ");

			sb.append("\"");

			sb.append(_escape(category));

			sb.append("\"");
		}

		if (configuration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"configuration\": ");

			sb.append(String.valueOf(configuration));
		}

		if (description_i18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(description_i18n));
		}

		if (icon != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"icon\": ");

			sb.append("\"");

			sb.append(_escape(icon));

			sb.append("\"");
		}

		if (title_i18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(title_i18n));
		}

		if (uiConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uiConfiguration\": ");

			sb.append(String.valueOf(uiConfiguration));
		}

		if (uiConfigurationValues != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uiConfigurationValues\": ");

			sb.append(_toJSON(uiConfigurationValues));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition",
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