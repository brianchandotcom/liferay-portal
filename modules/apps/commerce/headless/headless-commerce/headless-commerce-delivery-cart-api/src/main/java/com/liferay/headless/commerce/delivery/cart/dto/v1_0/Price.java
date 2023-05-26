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
@GraphQLName("Price")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Price")
public class Price implements Serializable {

	public static Price toDTO(String json) {
		return ObjectMapperUtil.readValue(Price.class, json);
	}

	public static Price unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Price.class, json);
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

	@Schema
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@JsonIgnore
	public void setDiscount(
		UnsafeSupplier<Double, Exception> discountUnsafeSupplier) {

		try {
			discount = discountUnsafeSupplier.get();
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
	protected Double discount;

	@Schema
	public String getDiscountFormatted() {
		return discountFormatted;
	}

	public void setDiscountFormatted(String discountFormatted) {
		this.discountFormatted = discountFormatted;
	}

	@JsonIgnore
	public void setDiscountFormatted(
		UnsafeSupplier<String, Exception> discountFormattedUnsafeSupplier) {

		try {
			discountFormatted = discountFormattedUnsafeSupplier.get();
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
	protected String discountFormatted;

	@Schema
	public String getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(String discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	@JsonIgnore
	public void setDiscountPercentage(
		UnsafeSupplier<String, Exception> discountPercentageUnsafeSupplier) {

		try {
			discountPercentage = discountPercentageUnsafeSupplier.get();
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
	protected String discountPercentage;

	@Schema
	public Double getDiscountPercentageLevel1() {
		return discountPercentageLevel1;
	}

	public void setDiscountPercentageLevel1(Double discountPercentageLevel1) {
		this.discountPercentageLevel1 = discountPercentageLevel1;
	}

	@JsonIgnore
	public void setDiscountPercentageLevel1(
		UnsafeSupplier<Double, Exception>
			discountPercentageLevel1UnsafeSupplier) {

		try {
			discountPercentageLevel1 =
				discountPercentageLevel1UnsafeSupplier.get();
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
	protected Double discountPercentageLevel1;

	@Schema
	public Double getDiscountPercentageLevel2() {
		return discountPercentageLevel2;
	}

	public void setDiscountPercentageLevel2(Double discountPercentageLevel2) {
		this.discountPercentageLevel2 = discountPercentageLevel2;
	}

	@JsonIgnore
	public void setDiscountPercentageLevel2(
		UnsafeSupplier<Double, Exception>
			discountPercentageLevel2UnsafeSupplier) {

		try {
			discountPercentageLevel2 =
				discountPercentageLevel2UnsafeSupplier.get();
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
	protected Double discountPercentageLevel2;

	@Schema
	public Double getDiscountPercentageLevel3() {
		return discountPercentageLevel3;
	}

	public void setDiscountPercentageLevel3(Double discountPercentageLevel3) {
		this.discountPercentageLevel3 = discountPercentageLevel3;
	}

	@JsonIgnore
	public void setDiscountPercentageLevel3(
		UnsafeSupplier<Double, Exception>
			discountPercentageLevel3UnsafeSupplier) {

		try {
			discountPercentageLevel3 =
				discountPercentageLevel3UnsafeSupplier.get();
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
	protected Double discountPercentageLevel3;

	@Schema
	public Double getDiscountPercentageLevel4() {
		return discountPercentageLevel4;
	}

	public void setDiscountPercentageLevel4(Double discountPercentageLevel4) {
		this.discountPercentageLevel4 = discountPercentageLevel4;
	}

	@JsonIgnore
	public void setDiscountPercentageLevel4(
		UnsafeSupplier<Double, Exception>
			discountPercentageLevel4UnsafeSupplier) {

		try {
			discountPercentageLevel4 =
				discountPercentageLevel4UnsafeSupplier.get();
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
	protected Double discountPercentageLevel4;

	@Schema
	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	@JsonIgnore
	public void setFinalPrice(
		UnsafeSupplier<Double, Exception> finalPriceUnsafeSupplier) {

		try {
			finalPrice = finalPriceUnsafeSupplier.get();
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
	protected Double finalPrice;

	@Schema
	public String getFinalPriceFormatted() {
		return finalPriceFormatted;
	}

	public void setFinalPriceFormatted(String finalPriceFormatted) {
		this.finalPriceFormatted = finalPriceFormatted;
	}

	@JsonIgnore
	public void setFinalPriceFormatted(
		UnsafeSupplier<String, Exception> finalPriceFormattedUnsafeSupplier) {

		try {
			finalPriceFormatted = finalPriceFormattedUnsafeSupplier.get();
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
	protected String finalPriceFormatted;

	@Schema
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@JsonIgnore
	public void setPrice(
		UnsafeSupplier<Double, Exception> priceUnsafeSupplier) {

		try {
			price = priceUnsafeSupplier.get();
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
	protected Double price;

	@Schema
	public String getPriceFormatted() {
		return priceFormatted;
	}

	public void setPriceFormatted(String priceFormatted) {
		this.priceFormatted = priceFormatted;
	}

	@JsonIgnore
	public void setPriceFormatted(
		UnsafeSupplier<String, Exception> priceFormattedUnsafeSupplier) {

		try {
			priceFormatted = priceFormattedUnsafeSupplier.get();
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
	protected String priceFormatted;

	@Schema
	public Boolean getPriceOnApplication() {
		return priceOnApplication;
	}

	public void setPriceOnApplication(Boolean priceOnApplication) {
		this.priceOnApplication = priceOnApplication;
	}

	@JsonIgnore
	public void setPriceOnApplication(
		UnsafeSupplier<Boolean, Exception> priceOnApplicationUnsafeSupplier) {

		try {
			priceOnApplication = priceOnApplicationUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Boolean priceOnApplication;

	@Schema
	public Double getPromoPrice() {
		return promoPrice;
	}

	public void setPromoPrice(Double promoPrice) {
		this.promoPrice = promoPrice;
	}

	@JsonIgnore
	public void setPromoPrice(
		UnsafeSupplier<Double, Exception> promoPriceUnsafeSupplier) {

		try {
			promoPrice = promoPriceUnsafeSupplier.get();
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
	protected Double promoPrice;

	@Schema
	public String getPromoPriceFormatted() {
		return promoPriceFormatted;
	}

	public void setPromoPriceFormatted(String promoPriceFormatted) {
		this.promoPriceFormatted = promoPriceFormatted;
	}

	@JsonIgnore
	public void setPromoPriceFormatted(
		UnsafeSupplier<String, Exception> promoPriceFormattedUnsafeSupplier) {

		try {
			promoPriceFormatted = promoPriceFormattedUnsafeSupplier.get();
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
	protected String promoPriceFormatted;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Price)) {
			return false;
		}

		Price price = (Price)object;

		return Objects.equals(toString(), price.toString());
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

		if (discount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discount\": ");

			sb.append(discount);
		}

		if (discountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(discountFormatted));

			sb.append("\"");
		}

		if (discountPercentage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountPercentage\": ");

			sb.append("\"");

			sb.append(_escape(discountPercentage));

			sb.append("\"");
		}

		if (discountPercentageLevel1 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountPercentageLevel1\": ");

			sb.append(discountPercentageLevel1);
		}

		if (discountPercentageLevel2 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountPercentageLevel2\": ");

			sb.append(discountPercentageLevel2);
		}

		if (discountPercentageLevel3 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountPercentageLevel3\": ");

			sb.append(discountPercentageLevel3);
		}

		if (discountPercentageLevel4 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountPercentageLevel4\": ");

			sb.append(discountPercentageLevel4);
		}

		if (finalPrice != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"finalPrice\": ");

			sb.append(finalPrice);
		}

		if (finalPriceFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"finalPriceFormatted\": ");

			sb.append("\"");

			sb.append(_escape(finalPriceFormatted));

			sb.append("\"");
		}

		if (price != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(price);
		}

		if (priceFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceFormatted\": ");

			sb.append("\"");

			sb.append(_escape(priceFormatted));

			sb.append("\"");
		}

		if (priceOnApplication != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceOnApplication\": ");

			sb.append(priceOnApplication);
		}

		if (promoPrice != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"promoPrice\": ");

			sb.append(promoPrice);
		}

		if (promoPriceFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"promoPriceFormatted\": ");

			sb.append("\"");

			sb.append(_escape(promoPriceFormatted));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.delivery.cart.dto.v1_0.Price",
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