/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.dto.v1_0;

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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
@GraphQLName("Summary")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Summary")
public class Summary implements Serializable {

	public static Summary toDTO(String json) {
		return ObjectMapperUtil.readValue(Summary.class, json);
	}

	public static Summary unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Summary.class, json);
	}

	@Schema
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@JsonIgnore
	public void setCurrency(
		UnsafeSupplier<String, Exception> currencyUnsafeSupplier) {

		try {
			currency = currencyUnsafeSupplier.get();
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
	protected String currency;

	@Schema(example = "1.1")
	@Valid
	public BigDecimal getItemsQuantity() {
		return itemsQuantity;
	}

	public void setItemsQuantity(BigDecimal itemsQuantity) {
		this.itemsQuantity = itemsQuantity;
	}

	@JsonIgnore
	public void setItemsQuantity(
		UnsafeSupplier<BigDecimal, Exception> itemsQuantityUnsafeSupplier) {

		try {
			itemsQuantity = itemsQuantityUnsafeSupplier.get();
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
	protected BigDecimal itemsQuantity;

	@Schema
	public String[] getShippingDiscountPercentages() {
		return shippingDiscountPercentages;
	}

	public void setShippingDiscountPercentages(
		String[] shippingDiscountPercentages) {

		this.shippingDiscountPercentages = shippingDiscountPercentages;
	}

	@JsonIgnore
	public void setShippingDiscountPercentages(
		UnsafeSupplier<String[], Exception>
			shippingDiscountPercentagesUnsafeSupplier) {

		try {
			shippingDiscountPercentages =
				shippingDiscountPercentagesUnsafeSupplier.get();
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
	protected String[] shippingDiscountPercentages;

	@Schema
	public Double getShippingDiscountValue() {
		return shippingDiscountValue;
	}

	public void setShippingDiscountValue(Double shippingDiscountValue) {
		this.shippingDiscountValue = shippingDiscountValue;
	}

	@JsonIgnore
	public void setShippingDiscountValue(
		UnsafeSupplier<Double, Exception> shippingDiscountValueUnsafeSupplier) {

		try {
			shippingDiscountValue = shippingDiscountValueUnsafeSupplier.get();
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
	protected Double shippingDiscountValue;

	@Schema
	public String getShippingDiscountValueFormatted() {
		return shippingDiscountValueFormatted;
	}

	public void setShippingDiscountValueFormatted(
		String shippingDiscountValueFormatted) {

		this.shippingDiscountValueFormatted = shippingDiscountValueFormatted;
	}

	@JsonIgnore
	public void setShippingDiscountValueFormatted(
		UnsafeSupplier<String, Exception>
			shippingDiscountValueFormattedUnsafeSupplier) {

		try {
			shippingDiscountValueFormatted =
				shippingDiscountValueFormattedUnsafeSupplier.get();
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
	protected String shippingDiscountValueFormatted;

	@Schema
	public Double getShippingValue() {
		return shippingValue;
	}

	public void setShippingValue(Double shippingValue) {
		this.shippingValue = shippingValue;
	}

	@JsonIgnore
	public void setShippingValue(
		UnsafeSupplier<Double, Exception> shippingValueUnsafeSupplier) {

		try {
			shippingValue = shippingValueUnsafeSupplier.get();
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
	protected Double shippingValue;

	@Schema
	public String getShippingValueFormatted() {
		return shippingValueFormatted;
	}

	public void setShippingValueFormatted(String shippingValueFormatted) {
		this.shippingValueFormatted = shippingValueFormatted;
	}

	@JsonIgnore
	public void setShippingValueFormatted(
		UnsafeSupplier<String, Exception>
			shippingValueFormattedUnsafeSupplier) {

		try {
			shippingValueFormatted = shippingValueFormattedUnsafeSupplier.get();
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
	protected String shippingValueFormatted;

	@Schema
	public Double getShippingValueWithTaxAmount() {
		return shippingValueWithTaxAmount;
	}

	public void setShippingValueWithTaxAmount(
		Double shippingValueWithTaxAmount) {

		this.shippingValueWithTaxAmount = shippingValueWithTaxAmount;
	}

	@JsonIgnore
	public void setShippingValueWithTaxAmount(
		UnsafeSupplier<Double, Exception>
			shippingValueWithTaxAmountUnsafeSupplier) {

		try {
			shippingValueWithTaxAmount =
				shippingValueWithTaxAmountUnsafeSupplier.get();
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
	protected Double shippingValueWithTaxAmount;

	@Schema
	public String getShippingValueWithTaxAmountFormatted() {
		return shippingValueWithTaxAmountFormatted;
	}

	public void setShippingValueWithTaxAmountFormatted(
		String shippingValueWithTaxAmountFormatted) {

		this.shippingValueWithTaxAmountFormatted =
			shippingValueWithTaxAmountFormatted;
	}

	@JsonIgnore
	public void setShippingValueWithTaxAmountFormatted(
		UnsafeSupplier<String, Exception>
			shippingValueWithTaxAmountFormattedUnsafeSupplier) {

		try {
			shippingValueWithTaxAmountFormatted =
				shippingValueWithTaxAmountFormattedUnsafeSupplier.get();
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
	protected String shippingValueWithTaxAmountFormatted;

	@Schema
	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	@JsonIgnore
	public void setSubtotal(
		UnsafeSupplier<Double, Exception> subtotalUnsafeSupplier) {

		try {
			subtotal = subtotalUnsafeSupplier.get();
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
	protected Double subtotal;

	@Schema
	public String[] getSubtotalDiscountPercentages() {
		return subtotalDiscountPercentages;
	}

	public void setSubtotalDiscountPercentages(
		String[] subtotalDiscountPercentages) {

		this.subtotalDiscountPercentages = subtotalDiscountPercentages;
	}

	@JsonIgnore
	public void setSubtotalDiscountPercentages(
		UnsafeSupplier<String[], Exception>
			subtotalDiscountPercentagesUnsafeSupplier) {

		try {
			subtotalDiscountPercentages =
				subtotalDiscountPercentagesUnsafeSupplier.get();
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
	protected String[] subtotalDiscountPercentages;

	@Schema
	public Double getSubtotalDiscountValue() {
		return subtotalDiscountValue;
	}

	public void setSubtotalDiscountValue(Double subtotalDiscountValue) {
		this.subtotalDiscountValue = subtotalDiscountValue;
	}

	@JsonIgnore
	public void setSubtotalDiscountValue(
		UnsafeSupplier<Double, Exception> subtotalDiscountValueUnsafeSupplier) {

		try {
			subtotalDiscountValue = subtotalDiscountValueUnsafeSupplier.get();
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
	protected Double subtotalDiscountValue;

	@Schema
	public String getSubtotalDiscountValueFormatted() {
		return subtotalDiscountValueFormatted;
	}

	public void setSubtotalDiscountValueFormatted(
		String subtotalDiscountValueFormatted) {

		this.subtotalDiscountValueFormatted = subtotalDiscountValueFormatted;
	}

	@JsonIgnore
	public void setSubtotalDiscountValueFormatted(
		UnsafeSupplier<String, Exception>
			subtotalDiscountValueFormattedUnsafeSupplier) {

		try {
			subtotalDiscountValueFormatted =
				subtotalDiscountValueFormattedUnsafeSupplier.get();
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
	protected String subtotalDiscountValueFormatted;

	@Schema
	public String getSubtotalFormatted() {
		return subtotalFormatted;
	}

	public void setSubtotalFormatted(String subtotalFormatted) {
		this.subtotalFormatted = subtotalFormatted;
	}

	@JsonIgnore
	public void setSubtotalFormatted(
		UnsafeSupplier<String, Exception> subtotalFormattedUnsafeSupplier) {

		try {
			subtotalFormatted = subtotalFormattedUnsafeSupplier.get();
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
	protected String subtotalFormatted;

	@Schema
	public Double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}

	@JsonIgnore
	public void setTaxValue(
		UnsafeSupplier<Double, Exception> taxValueUnsafeSupplier) {

		try {
			taxValue = taxValueUnsafeSupplier.get();
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
	protected Double taxValue;

	@Schema
	public String getTaxValueFormatted() {
		return taxValueFormatted;
	}

	public void setTaxValueFormatted(String taxValueFormatted) {
		this.taxValueFormatted = taxValueFormatted;
	}

	@JsonIgnore
	public void setTaxValueFormatted(
		UnsafeSupplier<String, Exception> taxValueFormattedUnsafeSupplier) {

		try {
			taxValueFormatted = taxValueFormattedUnsafeSupplier.get();
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
	protected String taxValueFormatted;

	@Schema
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@JsonIgnore
	public void setTotal(
		UnsafeSupplier<Double, Exception> totalUnsafeSupplier) {

		try {
			total = totalUnsafeSupplier.get();
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
	protected Double total;

	@Schema
	public String[] getTotalDiscountPercentages() {
		return totalDiscountPercentages;
	}

	public void setTotalDiscountPercentages(String[] totalDiscountPercentages) {
		this.totalDiscountPercentages = totalDiscountPercentages;
	}

	@JsonIgnore
	public void setTotalDiscountPercentages(
		UnsafeSupplier<String[], Exception>
			totalDiscountPercentagesUnsafeSupplier) {

		try {
			totalDiscountPercentages =
				totalDiscountPercentagesUnsafeSupplier.get();
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
	protected String[] totalDiscountPercentages;

	@Schema
	public Double getTotalDiscountValue() {
		return totalDiscountValue;
	}

	public void setTotalDiscountValue(Double totalDiscountValue) {
		this.totalDiscountValue = totalDiscountValue;
	}

	@JsonIgnore
	public void setTotalDiscountValue(
		UnsafeSupplier<Double, Exception> totalDiscountValueUnsafeSupplier) {

		try {
			totalDiscountValue = totalDiscountValueUnsafeSupplier.get();
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
	protected Double totalDiscountValue;

	@Schema
	public String getTotalDiscountValueFormatted() {
		return totalDiscountValueFormatted;
	}

	public void setTotalDiscountValueFormatted(
		String totalDiscountValueFormatted) {

		this.totalDiscountValueFormatted = totalDiscountValueFormatted;
	}

	@JsonIgnore
	public void setTotalDiscountValueFormatted(
		UnsafeSupplier<String, Exception>
			totalDiscountValueFormattedUnsafeSupplier) {

		try {
			totalDiscountValueFormatted =
				totalDiscountValueFormattedUnsafeSupplier.get();
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
	protected String totalDiscountValueFormatted;

	@Schema
	public String getTotalFormatted() {
		return totalFormatted;
	}

	public void setTotalFormatted(String totalFormatted) {
		this.totalFormatted = totalFormatted;
	}

	@JsonIgnore
	public void setTotalFormatted(
		UnsafeSupplier<String, Exception> totalFormattedUnsafeSupplier) {

		try {
			totalFormatted = totalFormattedUnsafeSupplier.get();
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
	protected String totalFormatted;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Summary)) {
			return false;
		}

		Summary summary = (Summary)object;

		return Objects.equals(toString(), summary.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (currency != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"currency\": ");

			sb.append("\"");

			sb.append(_escape(currency));

			sb.append("\"");
		}

		if (itemsQuantity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"itemsQuantity\": ");

			sb.append(itemsQuantity);
		}

		if (shippingDiscountPercentages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentages\": ");

			sb.append("[");

			for (int i = 0; i < shippingDiscountPercentages.length; i++) {
				sb.append("\"");

				sb.append(_escape(shippingDiscountPercentages[i]));

				sb.append("\"");

				if ((i + 1) < shippingDiscountPercentages.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (shippingDiscountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountValue\": ");

			sb.append(shippingDiscountValue);
		}

		if (shippingDiscountValueFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountValueFormatted\": ");

			sb.append("\"");

			sb.append(_escape(shippingDiscountValueFormatted));

			sb.append("\"");
		}

		if (shippingValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingValue\": ");

			sb.append(shippingValue);
		}

		if (shippingValueFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingValueFormatted\": ");

			sb.append("\"");

			sb.append(_escape(shippingValueFormatted));

			sb.append("\"");
		}

		if (shippingValueWithTaxAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingValueWithTaxAmount\": ");

			sb.append(shippingValueWithTaxAmount);
		}

		if (shippingValueWithTaxAmountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingValueWithTaxAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(shippingValueWithTaxAmountFormatted));

			sb.append("\"");
		}

		if (subtotal != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotal\": ");

			sb.append(subtotal);
		}

		if (subtotalDiscountPercentages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentages\": ");

			sb.append("[");

			for (int i = 0; i < subtotalDiscountPercentages.length; i++) {
				sb.append("\"");

				sb.append(_escape(subtotalDiscountPercentages[i]));

				sb.append("\"");

				if ((i + 1) < subtotalDiscountPercentages.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (subtotalDiscountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountValue\": ");

			sb.append(subtotalDiscountValue);
		}

		if (subtotalDiscountValueFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountValueFormatted\": ");

			sb.append("\"");

			sb.append(_escape(subtotalDiscountValueFormatted));

			sb.append("\"");
		}

		if (subtotalFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalFormatted\": ");

			sb.append("\"");

			sb.append(_escape(subtotalFormatted));

			sb.append("\"");
		}

		if (taxValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxValue\": ");

			sb.append(taxValue);
		}

		if (taxValueFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxValueFormatted\": ");

			sb.append("\"");

			sb.append(_escape(taxValueFormatted));

			sb.append("\"");
		}

		if (total != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"total\": ");

			sb.append(total);
		}

		if (totalDiscountPercentages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentages\": ");

			sb.append("[");

			for (int i = 0; i < totalDiscountPercentages.length; i++) {
				sb.append("\"");

				sb.append(_escape(totalDiscountPercentages[i]));

				sb.append("\"");

				if ((i + 1) < totalDiscountPercentages.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (totalDiscountValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountValue\": ");

			sb.append(totalDiscountValue);
		}

		if (totalDiscountValueFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountValueFormatted\": ");

			sb.append("\"");

			sb.append(_escape(totalDiscountValueFormatted));

			sb.append("\"");
		}

		if (totalFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalFormatted\": ");

			sb.append("\"");

			sb.append(_escape(totalFormatted));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.delivery.cart.dto.v1_0.Summary",
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