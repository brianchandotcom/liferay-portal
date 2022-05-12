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

package com.liferay.headless.commerce.admin.catalog.dto.v1_0;

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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
@GraphQLName("ProductSubscriptionConfiguration")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ProductSubscriptionConfiguration")
public class ProductSubscriptionConfiguration implements Serializable {

	public static ProductSubscriptionConfiguration toDTO(String json) {
		return ObjectMapperUtil.readValue(
			ProductSubscriptionConfiguration.class, json);
	}

	public static ProductSubscriptionConfiguration unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			ProductSubscriptionConfiguration.class, json);
	}

	@Schema(example = "true")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@JsonIgnore
	public void setEnable(
		UnsafeSupplier<Boolean, Exception> enableUnsafeSupplier) {

		try {
			enable = enableUnsafeSupplier.get();
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
	protected Boolean enable;

	@Schema(example = "2")
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@JsonIgnore
	public void setLength(
		UnsafeSupplier<Integer, Exception> lengthUnsafeSupplier) {

		try {
			length = lengthUnsafeSupplier.get();
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
	protected Integer length;

	@Schema(example = "12")
	public Long getNumberOfLength() {
		return numberOfLength;
	}

	public void setNumberOfLength(Long numberOfLength) {
		this.numberOfLength = numberOfLength;
	}

	@JsonIgnore
	public void setNumberOfLength(
		UnsafeSupplier<Long, Exception> numberOfLengthUnsafeSupplier) {

		try {
			numberOfLength = numberOfLengthUnsafeSupplier.get();
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
	protected Long numberOfLength;

	@Schema(example = "month")
	@Valid
	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	@JsonIgnore
	public String getSubscriptionTypeAsString() {
		if (subscriptionType == null) {
			return null;
		}

		return subscriptionType.toString();
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	@JsonIgnore
	public void setSubscriptionType(
		UnsafeSupplier<SubscriptionType, Exception>
			subscriptionTypeUnsafeSupplier) {

		try {
			subscriptionType = subscriptionTypeUnsafeSupplier.get();
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
	protected SubscriptionType subscriptionType;

	@Schema(example = "{monthDay=1, monthlyMode=0}")
	@Valid
	public Map<String, String> getSubscriptionTypeSettings() {
		return subscriptionTypeSettings;
	}

	public void setSubscriptionTypeSettings(
		Map<String, String> subscriptionTypeSettings) {

		this.subscriptionTypeSettings = subscriptionTypeSettings;
	}

	@JsonIgnore
	public void setSubscriptionTypeSettings(
		UnsafeSupplier<Map<String, String>, Exception>
			subscriptionTypeSettingsUnsafeSupplier) {

		try {
			subscriptionTypeSettings =
				subscriptionTypeSettingsUnsafeSupplier.get();
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
	protected Map<String, String> subscriptionTypeSettings;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProductSubscriptionConfiguration)) {
			return false;
		}

		ProductSubscriptionConfiguration productSubscriptionConfiguration =
			(ProductSubscriptionConfiguration)object;

		return Objects.equals(
			toString(), productSubscriptionConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (enable != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enable\": ");

			sb.append(enable);
		}

		if (length != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"length\": ");

			sb.append(length);
		}

		if (numberOfLength != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfLength\": ");

			sb.append(numberOfLength);
		}

		if (subscriptionType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscriptionType\": ");

			sb.append("\"");

			sb.append(subscriptionType);

			sb.append("\"");
		}

		if (subscriptionTypeSettings != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscriptionTypeSettings\": ");

			sb.append(_toJSON(subscriptionTypeSettings));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSubscriptionConfiguration",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("SubscriptionType")
	public static enum SubscriptionType {

		DAY("day"), MONTH("month"), WEEK("week"), YEAR("year");

		@JsonCreator
		public static SubscriptionType create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (SubscriptionType subscriptionType : values()) {
				if (Objects.equals(subscriptionType.getValue(), value)) {
					return subscriptionType;
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

		private SubscriptionType(String value) {
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

}