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

package com.liferay.headless.commerce.admin.pricing.dto.v2_0;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
@GraphQLName("PriceEntry")
@JsonFilter("Liferay.Vulcan")
@Schema(requiredProperties = {"price", "priceListId", "skuId"})
@XmlRootElement(name = "PriceEntry")
public class PriceEntry implements Serializable {

	public static PriceEntry toDTO(String json) {
		return ObjectMapperUtil.readValue(PriceEntry.class, json);
	}

	public static PriceEntry unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(PriceEntry.class, json);
	}

	@Schema
	@Valid
	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	@JsonIgnore
	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
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
	protected Map<String, Map<String, String>> actions;

	@Schema(example = "true")
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonIgnore
	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
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
	protected Boolean active;

	@Schema(example = "true")
	public Boolean getBulkPricing() {
		return bulkPricing;
	}

	public void setBulkPricing(Boolean bulkPricing) {
		this.bulkPricing = bulkPricing;
	}

	@JsonIgnore
	public void setBulkPricing(
		UnsafeSupplier<Boolean, Exception> bulkPricingUnsafeSupplier) {

		try {
			bulkPricing = bulkPricingUnsafeSupplier.get();
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
	protected Boolean bulkPricing;

	@Schema
	@Valid
	public Map<String, ?> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, ?> customFields) {
		this.customFields = customFields;
	}

	@JsonIgnore
	public void setCustomFields(
		UnsafeSupplier<Map<String, ?>, Exception> customFieldsUnsafeSupplier) {

		try {
			customFields = customFieldsUnsafeSupplier.get();
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
	protected Map<String, ?> customFields;

	@Schema(example = "true")
	public Boolean getDiscountDiscovery() {
		return discountDiscovery;
	}

	public void setDiscountDiscovery(Boolean discountDiscovery) {
		this.discountDiscovery = discountDiscovery;
	}

	@JsonIgnore
	public void setDiscountDiscovery(
		UnsafeSupplier<Boolean, Exception> discountDiscoveryUnsafeSupplier) {

		try {
			discountDiscovery = discountDiscoveryUnsafeSupplier.get();
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
	protected Boolean discountDiscovery;

	@DecimalMin("0")
	@Schema(example = "30130")
	@Valid
	public BigDecimal getDiscountLevel1() {
		return discountLevel1;
	}

	public void setDiscountLevel1(BigDecimal discountLevel1) {
		this.discountLevel1 = discountLevel1;
	}

	@JsonIgnore
	public void setDiscountLevel1(
		UnsafeSupplier<BigDecimal, Exception> discountLevel1UnsafeSupplier) {

		try {
			discountLevel1 = discountLevel1UnsafeSupplier.get();
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
	protected BigDecimal discountLevel1;

	@DecimalMin("0")
	@Schema(example = "30130")
	@Valid
	public BigDecimal getDiscountLevel2() {
		return discountLevel2;
	}

	public void setDiscountLevel2(BigDecimal discountLevel2) {
		this.discountLevel2 = discountLevel2;
	}

	@JsonIgnore
	public void setDiscountLevel2(
		UnsafeSupplier<BigDecimal, Exception> discountLevel2UnsafeSupplier) {

		try {
			discountLevel2 = discountLevel2UnsafeSupplier.get();
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
	protected BigDecimal discountLevel2;

	@DecimalMin("0")
	@Schema(example = "30130")
	@Valid
	public BigDecimal getDiscountLevel3() {
		return discountLevel3;
	}

	public void setDiscountLevel3(BigDecimal discountLevel3) {
		this.discountLevel3 = discountLevel3;
	}

	@JsonIgnore
	public void setDiscountLevel3(
		UnsafeSupplier<BigDecimal, Exception> discountLevel3UnsafeSupplier) {

		try {
			discountLevel3 = discountLevel3UnsafeSupplier.get();
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
	protected BigDecimal discountLevel3;

	@DecimalMin("0")
	@Schema(example = "30130")
	@Valid
	public BigDecimal getDiscountLevel4() {
		return discountLevel4;
	}

	public void setDiscountLevel4(BigDecimal discountLevel4) {
		this.discountLevel4 = discountLevel4;
	}

	@JsonIgnore
	public void setDiscountLevel4(
		UnsafeSupplier<BigDecimal, Exception> discountLevel4UnsafeSupplier) {

		try {
			discountLevel4 = discountLevel4UnsafeSupplier.get();
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
	protected BigDecimal discountLevel4;

	@Schema(example = "10 | 10 | 10 | 10")
	public String getDiscountLevelsFormatted() {
		return discountLevelsFormatted;
	}

	public void setDiscountLevelsFormatted(String discountLevelsFormatted) {
		this.discountLevelsFormatted = discountLevelsFormatted;
	}

	@JsonIgnore
	public void setDiscountLevelsFormatted(
		UnsafeSupplier<String, Exception>
			discountLevelsFormattedUnsafeSupplier) {

		try {
			discountLevelsFormatted =
				discountLevelsFormattedUnsafeSupplier.get();
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
	protected String discountLevelsFormatted;

	@Schema(example = "2017-07-21")
	public Date getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}

	@JsonIgnore
	public void setDisplayDate(
		UnsafeSupplier<Date, Exception> displayDateUnsafeSupplier) {

		try {
			displayDate = displayDateUnsafeSupplier.get();
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
	protected Date displayDate;

	@Schema(example = "2017-08-21")
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@JsonIgnore
	public void setExpirationDate(
		UnsafeSupplier<Date, Exception> expirationDateUnsafeSupplier) {

		try {
			expirationDate = expirationDateUnsafeSupplier.get();
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
	protected Date expirationDate;

	@Schema(example = "AB-34098-789-N")
	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	@JsonIgnore
	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
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
	protected String externalReferenceCode;

	@Schema(example = "true")
	public Boolean getHasTierPrice() {
		return hasTierPrice;
	}

	public void setHasTierPrice(Boolean hasTierPrice) {
		this.hasTierPrice = hasTierPrice;
	}

	@JsonIgnore
	public void setHasTierPrice(
		UnsafeSupplier<Boolean, Exception> hasTierPriceUnsafeSupplier) {

		try {
			hasTierPrice = hasTierPriceUnsafeSupplier.get();
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
	protected Boolean hasTierPrice;

	@Schema(example = "true")
	public Boolean getNeverExpire() {
		return neverExpire;
	}

	public void setNeverExpire(Boolean neverExpire) {
		this.neverExpire = neverExpire;
	}

	@JsonIgnore
	public void setNeverExpire(
		UnsafeSupplier<Boolean, Exception> neverExpireUnsafeSupplier) {

		try {
			neverExpire = neverExpireUnsafeSupplier.get();
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
	protected Boolean neverExpire;

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
	@NotNull
	protected Double price;

	@DecimalMin("0")
	@Schema(example = "30130")
	public Long getPriceEntryId() {
		return priceEntryId;
	}

	public void setPriceEntryId(Long priceEntryId) {
		this.priceEntryId = priceEntryId;
	}

	@JsonIgnore
	public void setPriceEntryId(
		UnsafeSupplier<Long, Exception> priceEntryIdUnsafeSupplier) {

		try {
			priceEntryId = priceEntryIdUnsafeSupplier.get();
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
	protected Long priceEntryId;

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

	@Schema(example = "PLAB-34098-789-N")
	public String getPriceListExternalReferenceCode() {
		return priceListExternalReferenceCode;
	}

	public void setPriceListExternalReferenceCode(
		String priceListExternalReferenceCode) {

		this.priceListExternalReferenceCode = priceListExternalReferenceCode;
	}

	@JsonIgnore
	public void setPriceListExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			priceListExternalReferenceCodeUnsafeSupplier) {

		try {
			priceListExternalReferenceCode =
				priceListExternalReferenceCodeUnsafeSupplier.get();
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
	protected String priceListExternalReferenceCode;

	@DecimalMin("0")
	@Schema(example = "20078")
	public Long getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(Long priceListId) {
		this.priceListId = priceListId;
	}

	@JsonIgnore
	public void setPriceListId(
		UnsafeSupplier<Long, Exception> priceListIdUnsafeSupplier) {

		try {
			priceListId = priceListIdUnsafeSupplier.get();
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
	@NotNull
	protected Long priceListId;

	@Schema
	@Valid
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@JsonIgnore
	public void setProduct(
		UnsafeSupplier<Product, Exception> productUnsafeSupplier) {

		try {
			product = productUnsafeSupplier.get();
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
	protected Product product;

	@Schema
	@Valid
	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	@JsonIgnore
	public void setSku(UnsafeSupplier<Sku, Exception> skuUnsafeSupplier) {
		try {
			sku = skuUnsafeSupplier.get();
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
	protected Sku sku;

	@Schema(example = "CAB-34098-789-N")
	public String getSkuExternalReferenceCode() {
		return skuExternalReferenceCode;
	}

	public void setSkuExternalReferenceCode(String skuExternalReferenceCode) {
		this.skuExternalReferenceCode = skuExternalReferenceCode;
	}

	@JsonIgnore
	public void setSkuExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			skuExternalReferenceCodeUnsafeSupplier) {

		try {
			skuExternalReferenceCode =
				skuExternalReferenceCodeUnsafeSupplier.get();
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
	protected String skuExternalReferenceCode;

	@DecimalMin("0")
	@Schema(example = "30130")
	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	@JsonIgnore
	public void setSkuId(UnsafeSupplier<Long, Exception> skuIdUnsafeSupplier) {
		try {
			skuId = skuIdUnsafeSupplier.get();
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
	@NotNull
	protected Long skuId;

	@Schema
	@Valid
	public TierPrice[] getTierPrices() {
		return tierPrices;
	}

	public void setTierPrices(TierPrice[] tierPrices) {
		this.tierPrices = tierPrices;
	}

	@JsonIgnore
	public void setTierPrices(
		UnsafeSupplier<TierPrice[], Exception> tierPricesUnsafeSupplier) {

		try {
			tierPrices = tierPricesUnsafeSupplier.get();
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
	protected TierPrice[] tierPrices;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PriceEntry)) {
			return false;
		}

		PriceEntry priceEntry = (PriceEntry)object;

		return Objects.equals(toString(), priceEntry.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (actions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(actions));
		}

		if (active != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(active);
		}

		if (bulkPricing != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bulkPricing\": ");

			sb.append(bulkPricing);
		}

		if (customFields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(customFields));
		}

		if (discountDiscovery != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountDiscovery\": ");

			sb.append(discountDiscovery);
		}

		if (discountLevel1 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevel1\": ");

			sb.append(discountLevel1);
		}

		if (discountLevel2 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevel2\": ");

			sb.append(discountLevel2);
		}

		if (discountLevel3 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevel3\": ");

			sb.append(discountLevel3);
		}

		if (discountLevel4 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevel4\": ");

			sb.append(discountLevel4);
		}

		if (discountLevelsFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevelsFormatted\": ");

			sb.append("\"");

			sb.append(_escape(discountLevelsFormatted));

			sb.append("\"");
		}

		if (displayDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(displayDate));

			sb.append("\"");
		}

		if (expirationDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(expirationDate));

			sb.append("\"");
		}

		if (externalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceCode));

			sb.append("\"");
		}

		if (hasTierPrice != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hasTierPrice\": ");

			sb.append(hasTierPrice);
		}

		if (neverExpire != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(neverExpire);
		}

		if (price != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(price);
		}

		if (priceEntryId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceEntryId\": ");

			sb.append(priceEntryId);
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

		if (priceListExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(priceListExternalReferenceCode));

			sb.append("\"");
		}

		if (priceListId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListId\": ");

			sb.append(priceListId);
		}

		if (product != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"product\": ");

			sb.append(String.valueOf(product));
		}

		if (sku != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append(String.valueOf(sku));
		}

		if (skuExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(skuExternalReferenceCode));

			sb.append("\"");
		}

		if (skuId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuId\": ");

			sb.append(skuId);
		}

		if (tierPrices != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tierPrices\": ");

			sb.append("[");

			for (int i = 0; i < tierPrices.length; i++) {
				sb.append(String.valueOf(tierPrices[i]));

				if ((i + 1) < tierPrices.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceEntry",
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