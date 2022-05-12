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

import java.math.BigDecimal;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
@GraphQLName("ProductShippingConfiguration")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ProductShippingConfiguration")
public class ProductShippingConfiguration implements Serializable {

	public static ProductShippingConfiguration toDTO(String json) {
		return ObjectMapperUtil.readValue(
			ProductShippingConfiguration.class, json);
	}

	public static ProductShippingConfiguration unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			ProductShippingConfiguration.class, json);
	}

	@DecimalMin("0")
	@Schema(example = "202")
	@Valid
	public BigDecimal getDepth() {
		return depth;
	}

	public void setDepth(BigDecimal depth) {
		this.depth = depth;
	}

	@JsonIgnore
	public void setDepth(
		UnsafeSupplier<BigDecimal, Exception> depthUnsafeSupplier) {

		try {
			depth = depthUnsafeSupplier.get();
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
	protected BigDecimal depth;

	@Schema(example = "true")
	public Boolean getFreeShipping() {
		return freeShipping;
	}

	public void setFreeShipping(Boolean freeShipping) {
		this.freeShipping = freeShipping;
	}

	@JsonIgnore
	public void setFreeShipping(
		UnsafeSupplier<Boolean, Exception> freeShippingUnsafeSupplier) {

		try {
			freeShipping = freeShippingUnsafeSupplier.get();
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
	protected Boolean freeShipping;

	@DecimalMin("0")
	@Schema(example = "202")
	@Valid
	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	@JsonIgnore
	public void setHeight(
		UnsafeSupplier<BigDecimal, Exception> heightUnsafeSupplier) {

		try {
			height = heightUnsafeSupplier.get();
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
	protected BigDecimal height;

	@Schema(example = "true")
	public Boolean getShippable() {
		return shippable;
	}

	public void setShippable(Boolean shippable) {
		this.shippable = shippable;
	}

	@JsonIgnore
	public void setShippable(
		UnsafeSupplier<Boolean, Exception> shippableUnsafeSupplier) {

		try {
			shippable = shippableUnsafeSupplier.get();
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
	protected Boolean shippable;

	@DecimalMin("0")
	@Schema(example = "202")
	@Valid
	public BigDecimal getShippingExtraPrice() {
		return shippingExtraPrice;
	}

	public void setShippingExtraPrice(BigDecimal shippingExtraPrice) {
		this.shippingExtraPrice = shippingExtraPrice;
	}

	@JsonIgnore
	public void setShippingExtraPrice(
		UnsafeSupplier<BigDecimal, Exception>
			shippingExtraPriceUnsafeSupplier) {

		try {
			shippingExtraPrice = shippingExtraPriceUnsafeSupplier.get();
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
	protected BigDecimal shippingExtraPrice;

	@Schema(example = "true")
	public Boolean getShippingSeparately() {
		return shippingSeparately;
	}

	public void setShippingSeparately(Boolean shippingSeparately) {
		this.shippingSeparately = shippingSeparately;
	}

	@JsonIgnore
	public void setShippingSeparately(
		UnsafeSupplier<Boolean, Exception> shippingSeparatelyUnsafeSupplier) {

		try {
			shippingSeparately = shippingSeparatelyUnsafeSupplier.get();
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
	protected Boolean shippingSeparately;

	@DecimalMin("0")
	@Schema(example = "202")
	@Valid
	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@JsonIgnore
	public void setWeight(
		UnsafeSupplier<BigDecimal, Exception> weightUnsafeSupplier) {

		try {
			weight = weightUnsafeSupplier.get();
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
	protected BigDecimal weight;

	@DecimalMin("0")
	@Schema(example = "202")
	@Valid
	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	@JsonIgnore
	public void setWidth(
		UnsafeSupplier<BigDecimal, Exception> widthUnsafeSupplier) {

		try {
			width = widthUnsafeSupplier.get();
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
	protected BigDecimal width;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProductShippingConfiguration)) {
			return false;
		}

		ProductShippingConfiguration productShippingConfiguration =
			(ProductShippingConfiguration)object;

		return Objects.equals(
			toString(), productShippingConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (depth != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"depth\": ");

			sb.append(depth);
		}

		if (freeShipping != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"freeShipping\": ");

			sb.append(freeShipping);
		}

		if (height != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"height\": ");

			sb.append(height);
		}

		if (shippable != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippable\": ");

			sb.append(shippable);
		}

		if (shippingExtraPrice != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingExtraPrice\": ");

			sb.append(shippingExtraPrice);
		}

		if (shippingSeparately != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingSeparately\": ");

			sb.append(shippingSeparately);
		}

		if (weight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"weight\": ");

			sb.append(weight);
		}

		if (width != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"width\": ");

			sb.append(width);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductShippingConfiguration",
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

}