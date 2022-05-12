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

package com.liferay.headless.commerce.delivery.catalog.dto.v1_0;

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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
@GraphQLName("ProductConfiguration")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ProductConfiguration")
public class ProductConfiguration implements Serializable {

	public static ProductConfiguration toDTO(String json) {
		return ObjectMapperUtil.readValue(ProductConfiguration.class, json);
	}

	public static ProductConfiguration unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			ProductConfiguration.class, json);
	}

	@Schema(example = "true")
	public Boolean getAllowBackOrder() {
		return allowBackOrder;
	}

	public void setAllowBackOrder(Boolean allowBackOrder) {
		this.allowBackOrder = allowBackOrder;
	}

	@JsonIgnore
	public void setAllowBackOrder(
		UnsafeSupplier<Boolean, Exception> allowBackOrderUnsafeSupplier) {

		try {
			allowBackOrder = allowBackOrderUnsafeSupplier.get();
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
	protected Boolean allowBackOrder;

	@Schema(example = "[10, 20, 30, 40]")
	public Integer[] getAllowedOrderQuantities() {
		return allowedOrderQuantities;
	}

	public void setAllowedOrderQuantities(Integer[] allowedOrderQuantities) {
		this.allowedOrderQuantities = allowedOrderQuantities;
	}

	@JsonIgnore
	public void setAllowedOrderQuantities(
		UnsafeSupplier<Integer[], Exception>
			allowedOrderQuantitiesUnsafeSupplier) {

		try {
			allowedOrderQuantities = allowedOrderQuantitiesUnsafeSupplier.get();
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
	protected Integer[] allowedOrderQuantities;

	@Schema(example = "true")
	public Boolean getDisplayAvailability() {
		return displayAvailability;
	}

	public void setDisplayAvailability(Boolean displayAvailability) {
		this.displayAvailability = displayAvailability;
	}

	@JsonIgnore
	public void setDisplayAvailability(
		UnsafeSupplier<Boolean, Exception> displayAvailabilityUnsafeSupplier) {

		try {
			displayAvailability = displayAvailabilityUnsafeSupplier.get();
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
	protected Boolean displayAvailability;

	@Schema(example = "true")
	public Boolean getDisplayStockQuantity() {
		return displayStockQuantity;
	}

	public void setDisplayStockQuantity(Boolean displayStockQuantity) {
		this.displayStockQuantity = displayStockQuantity;
	}

	@JsonIgnore
	public void setDisplayStockQuantity(
		UnsafeSupplier<Boolean, Exception> displayStockQuantityUnsafeSupplier) {

		try {
			displayStockQuantity = displayStockQuantityUnsafeSupplier.get();
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
	protected Boolean displayStockQuantity;

	@Schema(
		description = "The inventory engine that will be used to manage the product inventory"
	)
	public String getInventoryEngine() {
		return inventoryEngine;
	}

	public void setInventoryEngine(String inventoryEngine) {
		this.inventoryEngine = inventoryEngine;
	}

	@JsonIgnore
	public void setInventoryEngine(
		UnsafeSupplier<String, Exception> inventoryEngineUnsafeSupplier) {

		try {
			inventoryEngine = inventoryEngineUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The inventory engine that will be used to manage the product inventory"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String inventoryEngine;

	@Schema(
		description = "The low stock action that will be performed when a product is out of stock"
	)
	public String getLowStockAction() {
		return lowStockAction;
	}

	public void setLowStockAction(String lowStockAction) {
		this.lowStockAction = lowStockAction;
	}

	@JsonIgnore
	public void setLowStockAction(
		UnsafeSupplier<String, Exception> lowStockActionUnsafeSupplier) {

		try {
			lowStockAction = lowStockActionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The low stock action that will be performed when a product is out of stock"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String lowStockAction;

	@Schema
	public Integer getMaxOrderQuantity() {
		return maxOrderQuantity;
	}

	public void setMaxOrderQuantity(Integer maxOrderQuantity) {
		this.maxOrderQuantity = maxOrderQuantity;
	}

	@JsonIgnore
	public void setMaxOrderQuantity(
		UnsafeSupplier<Integer, Exception> maxOrderQuantityUnsafeSupplier) {

		try {
			maxOrderQuantity = maxOrderQuantityUnsafeSupplier.get();
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
	protected Integer maxOrderQuantity;

	@Schema
	public Integer getMinOrderQuantity() {
		return minOrderQuantity;
	}

	public void setMinOrderQuantity(Integer minOrderQuantity) {
		this.minOrderQuantity = minOrderQuantity;
	}

	@JsonIgnore
	public void setMinOrderQuantity(
		UnsafeSupplier<Integer, Exception> minOrderQuantityUnsafeSupplier) {

		try {
			minOrderQuantity = minOrderQuantityUnsafeSupplier.get();
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
	protected Integer minOrderQuantity;

	@Schema
	public Integer getMinStockQuantity() {
		return minStockQuantity;
	}

	public void setMinStockQuantity(Integer minStockQuantity) {
		this.minStockQuantity = minStockQuantity;
	}

	@JsonIgnore
	public void setMinStockQuantity(
		UnsafeSupplier<Integer, Exception> minStockQuantityUnsafeSupplier) {

		try {
			minStockQuantity = minStockQuantityUnsafeSupplier.get();
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
	protected Integer minStockQuantity;

	@Schema
	public Integer getMultipleOrderQuantity() {
		return multipleOrderQuantity;
	}

	public void setMultipleOrderQuantity(Integer multipleOrderQuantity) {
		this.multipleOrderQuantity = multipleOrderQuantity;
	}

	@JsonIgnore
	public void setMultipleOrderQuantity(
		UnsafeSupplier<Integer, Exception>
			multipleOrderQuantityUnsafeSupplier) {

		try {
			multipleOrderQuantity = multipleOrderQuantityUnsafeSupplier.get();
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
	protected Integer multipleOrderQuantity;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProductConfiguration)) {
			return false;
		}

		ProductConfiguration productConfiguration =
			(ProductConfiguration)object;

		return Objects.equals(toString(), productConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (allowBackOrder != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"allowBackOrder\": ");

			sb.append(allowBackOrder);
		}

		if (allowedOrderQuantities != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"allowedOrderQuantities\": ");

			sb.append("[");

			for (int i = 0; i < allowedOrderQuantities.length; i++) {
				sb.append(allowedOrderQuantities[i]);

				if ((i + 1) < allowedOrderQuantities.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (displayAvailability != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayAvailability\": ");

			sb.append(displayAvailability);
		}

		if (displayStockQuantity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayStockQuantity\": ");

			sb.append(displayStockQuantity);
		}

		if (inventoryEngine != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inventoryEngine\": ");

			sb.append("\"");

			sb.append(_escape(inventoryEngine));

			sb.append("\"");
		}

		if (lowStockAction != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lowStockAction\": ");

			sb.append("\"");

			sb.append(_escape(lowStockAction));

			sb.append("\"");
		}

		if (maxOrderQuantity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxOrderQuantity\": ");

			sb.append(maxOrderQuantity);
		}

		if (minOrderQuantity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minOrderQuantity\": ");

			sb.append(minOrderQuantity);
		}

		if (minStockQuantity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minStockQuantity\": ");

			sb.append(minStockQuantity);
		}

		if (multipleOrderQuantity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multipleOrderQuantity\": ");

			sb.append(multipleOrderQuantity);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductConfiguration",
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