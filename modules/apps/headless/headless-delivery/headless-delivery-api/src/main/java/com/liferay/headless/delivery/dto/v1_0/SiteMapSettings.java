/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Represents settings related with the site map.",
	value = "SiteMapSettings"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SiteMapSettings")
public class SiteMapSettings implements Serializable {

	public static SiteMapSettings toDTO(String json) {
		return ObjectMapperUtil.readValue(SiteMapSettings.class, json);
	}

	public static SiteMapSettings unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(SiteMapSettings.class, json);
	}

	@Schema(description = "Indicates how often a page is updated.")
	@Valid
	public ChangeFrequency getChangeFrequency() {
		return changeFrequency;
	}

	@JsonIgnore
	public String getChangeFrequencyAsString() {
		if (changeFrequency == null) {
			return null;
		}

		return changeFrequency.toString();
	}

	public void setChangeFrequency(ChangeFrequency changeFrequency) {
		this.changeFrequency = changeFrequency;
	}

	@JsonIgnore
	public void setChangeFrequency(
		UnsafeSupplier<ChangeFrequency, Exception>
			changeFrequencyUnsafeSupplier) {

		try {
			changeFrequency = changeFrequencyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "Indicates how often a page is updated.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ChangeFrequency changeFrequency;

	@Schema(
		description = "Whether search engines should crawl and index the page."
	)
	public Boolean getInclude() {
		return include;
	}

	public void setInclude(Boolean include) {
		this.include = include;
	}

	@JsonIgnore
	public void setInclude(
		UnsafeSupplier<Boolean, Exception> includeUnsafeSupplier) {

		try {
			include = includeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Whether search engines should crawl and index the page."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean include;

	@DecimalMax("1")
	@DecimalMin("0")
	@Schema(
		description = "How the page should be prioritized relative to other pages."
	)
	public Double getPagePriority() {
		return pagePriority;
	}

	public void setPagePriority(Double pagePriority) {
		this.pagePriority = pagePriority;
	}

	@JsonIgnore
	public void setPagePriority(
		UnsafeSupplier<Double, Exception> pagePriorityUnsafeSupplier) {

		try {
			pagePriority = pagePriorityUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "How the page should be prioritized relative to other pages."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Double pagePriority;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SiteMapSettings)) {
			return false;
		}

		SiteMapSettings siteMapSettings = (SiteMapSettings)object;

		return Objects.equals(toString(), siteMapSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (changeFrequency != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"changeFrequency\": ");

			sb.append("\"");

			sb.append(changeFrequency);

			sb.append("\"");
		}

		if (include != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"include\": ");

			sb.append(include);
		}

		if (pagePriority != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pagePriority\": ");

			sb.append(pagePriority);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.SiteMapSettings",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("ChangeFrequency")
	public static enum ChangeFrequency {

		ALWAYS("Always"), HOURLY("Hourly"), DAILY("Daily"), WEEKLY("Weekly"),
		MONTHLY("Monthly"), YEARLY("Yearly"), NEVER("Never");

		@JsonCreator
		public static ChangeFrequency create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (ChangeFrequency changeFrequency : values()) {
				if (Objects.equals(changeFrequency.getValue(), value)) {
					return changeFrequency;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private ChangeFrequency(String value) {
			_value = value;
		}

		private final String _value;

	}

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