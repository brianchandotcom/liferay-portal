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
import javax.validation.constraints.DecimalMin;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
@GraphQLName("MappedProduct")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "MappedProduct")
public class MappedProduct implements Serializable {

	public static MappedProduct toDTO(String json) {
		return ObjectMapperUtil.readValue(MappedProduct.class, json);
	}

	public static MappedProduct unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(MappedProduct.class, json);
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

	@Schema
	@Valid
	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	@JsonIgnore
	public void setAvailability(
		UnsafeSupplier<Availability, Exception> availabilityUnsafeSupplier) {

		try {
			availability = availabilityUnsafeSupplier.get();
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
	protected Availability availability;

	@Schema
	@Valid
	public MappedProduct getFirstAvailableReplacementMappedProduct() {
		return firstAvailableReplacementMappedProduct;
	}

	public void setFirstAvailableReplacementMappedProduct(
		MappedProduct firstAvailableReplacementMappedProduct) {

		this.firstAvailableReplacementMappedProduct =
			firstAvailableReplacementMappedProduct;
	}

	@JsonIgnore
	public void setFirstAvailableReplacementMappedProduct(
		UnsafeSupplier<MappedProduct, Exception>
			firstAvailableReplacementMappedProductUnsafeSupplier) {

		try {
			firstAvailableReplacementMappedProduct =
				firstAvailableReplacementMappedProductUnsafeSupplier.get();
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
	protected MappedProduct firstAvailableReplacementMappedProduct;

	@DecimalMin("0")
	@Schema
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
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
	protected Long id;

	@Schema
	@Valid
	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	@JsonIgnore
	public void setOptions(
		UnsafeSupplier<Map<String, String>, Exception> optionsUnsafeSupplier) {

		try {
			options = optionsUnsafeSupplier.get();
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
	protected Map<String, String> options;

	@Schema
	@Valid
	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	@JsonIgnore
	public void setPrice(UnsafeSupplier<Price, Exception> priceUnsafeSupplier) {
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
	protected Price price;

	@Schema
	@Valid
	public ProductConfiguration getProductConfiguration() {
		return productConfiguration;
	}

	public void setProductConfiguration(
		ProductConfiguration productConfiguration) {

		this.productConfiguration = productConfiguration;
	}

	@JsonIgnore
	public void setProductConfiguration(
		UnsafeSupplier<ProductConfiguration, Exception>
			productConfigurationUnsafeSupplier) {

		try {
			productConfiguration = productConfigurationUnsafeSupplier.get();
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
	protected ProductConfiguration productConfiguration;

	@Schema
	public String getProductExternalReferenceCode() {
		return productExternalReferenceCode;
	}

	public void setProductExternalReferenceCode(
		String productExternalReferenceCode) {

		this.productExternalReferenceCode = productExternalReferenceCode;
	}

	@JsonIgnore
	public void setProductExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			productExternalReferenceCodeUnsafeSupplier) {

		try {
			productExternalReferenceCode =
				productExternalReferenceCodeUnsafeSupplier.get();
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
	protected String productExternalReferenceCode;

	@DecimalMin("0")
	@Schema
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@JsonIgnore
	public void setProductId(
		UnsafeSupplier<Long, Exception> productIdUnsafeSupplier) {

		try {
			productId = productIdUnsafeSupplier.get();
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
	protected Long productId;

	@Schema
	@Valid
	public Map<String, String> getProductName() {
		return productName;
	}

	public void setProductName(Map<String, String> productName) {
		this.productName = productName;
	}

	@JsonIgnore
	public void setProductName(
		UnsafeSupplier<Map<String, String>, Exception>
			productNameUnsafeSupplier) {

		try {
			productName = productNameUnsafeSupplier.get();
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
	protected Map<String, String> productName;

	@Schema
	@Valid
	public ProductOption[] getProductOptions() {
		return productOptions;
	}

	public void setProductOptions(ProductOption[] productOptions) {
		this.productOptions = productOptions;
	}

	@JsonIgnore
	public void setProductOptions(
		UnsafeSupplier<ProductOption[], Exception>
			productOptionsUnsafeSupplier) {

		try {
			productOptions = productOptionsUnsafeSupplier.get();
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
	protected ProductOption[] productOptions;

	@Schema
	public Boolean getPurchasable() {
		return purchasable;
	}

	public void setPurchasable(Boolean purchasable) {
		this.purchasable = purchasable;
	}

	@JsonIgnore
	public void setPurchasable(
		UnsafeSupplier<Boolean, Exception> purchasableUnsafeSupplier) {

		try {
			purchasable = purchasableUnsafeSupplier.get();
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
	protected Boolean purchasable;

	@DecimalMin("0")
	@Schema
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@JsonIgnore
	public void setQuantity(
		UnsafeSupplier<Integer, Exception> quantityUnsafeSupplier) {

		try {
			quantity = quantityUnsafeSupplier.get();
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
	protected Integer quantity;

	@Schema
	@Valid
	public MappedProduct getReplacementMappedProduct() {
		return replacementMappedProduct;
	}

	public void setReplacementMappedProduct(
		MappedProduct replacementMappedProduct) {

		this.replacementMappedProduct = replacementMappedProduct;
	}

	@JsonIgnore
	public void setReplacementMappedProduct(
		UnsafeSupplier<MappedProduct, Exception>
			replacementMappedProductUnsafeSupplier) {

		try {
			replacementMappedProduct =
				replacementMappedProductUnsafeSupplier.get();
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
	protected MappedProduct replacementMappedProduct;

	@Schema
	public String getReplacementMessage() {
		return replacementMessage;
	}

	public void setReplacementMessage(String replacementMessage) {
		this.replacementMessage = replacementMessage;
	}

	@JsonIgnore
	public void setReplacementMessage(
		UnsafeSupplier<String, Exception> replacementMessageUnsafeSupplier) {

		try {
			replacementMessage = replacementMessageUnsafeSupplier.get();
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
	protected String replacementMessage;

	@Schema
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@JsonIgnore
	public void setSequence(
		UnsafeSupplier<String, Exception> sequenceUnsafeSupplier) {

		try {
			sequence = sequenceUnsafeSupplier.get();
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
	protected String sequence;

	@Schema
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@JsonIgnore
	public void setSku(UnsafeSupplier<String, Exception> skuUnsafeSupplier) {
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
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String sku;

	@Schema
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
	@Schema
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
	protected Long skuId;

	@Schema
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@JsonIgnore
	public void setThumbnail(
		UnsafeSupplier<String, Exception> thumbnailUnsafeSupplier) {

		try {
			thumbnail = thumbnailUnsafeSupplier.get();
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
	protected String thumbnail;

	@Schema
	@Valid
	public Type getType() {
		return type;
	}

	@JsonIgnore
	public String getTypeAsString() {
		if (type == null) {
			return null;
		}

		return type.toString();
	}

	public void setType(Type type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
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
	protected Type type;

	@Schema
	@Valid
	public Map<String, String> getUrls() {
		return urls;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}

	@JsonIgnore
	public void setUrls(
		UnsafeSupplier<Map<String, String>, Exception> urlsUnsafeSupplier) {

		try {
			urls = urlsUnsafeSupplier.get();
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
	protected Map<String, String> urls;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MappedProduct)) {
			return false;
		}

		MappedProduct mappedProduct = (MappedProduct)object;

		return Objects.equals(toString(), mappedProduct.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (actions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(actions));
		}

		if (availability != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availability\": ");

			sb.append(String.valueOf(availability));
		}

		if (firstAvailableReplacementMappedProduct != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"firstAvailableReplacementMappedProduct\": ");

			sb.append(String.valueOf(firstAvailableReplacementMappedProduct));
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (options != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"options\": ");

			sb.append(_toJSON(options));
		}

		if (price != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(String.valueOf(price));
		}

		if (productConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productConfiguration\": ");

			sb.append(String.valueOf(productConfiguration));
		}

		if (productExternalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(productExternalReferenceCode));

			sb.append("\"");
		}

		if (productId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(productId);
		}

		if (productName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productName\": ");

			sb.append(_toJSON(productName));
		}

		if (productOptions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productOptions\": ");

			sb.append("[");

			for (int i = 0; i < productOptions.length; i++) {
				sb.append(String.valueOf(productOptions[i]));

				if ((i + 1) < productOptions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (purchasable != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"purchasable\": ");

			sb.append(purchasable);
		}

		if (quantity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append(quantity);
		}

		if (replacementMappedProduct != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"replacementMappedProduct\": ");

			sb.append(String.valueOf(replacementMappedProduct));
		}

		if (replacementMessage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"replacementMessage\": ");

			sb.append("\"");

			sb.append(_escape(replacementMessage));

			sb.append("\"");
		}

		if (sequence != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sequence\": ");

			sb.append("\"");

			sb.append(_escape(sequence));

			sb.append("\"");
		}

		if (sku != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append("\"");

			sb.append(_escape(sku));

			sb.append("\"");
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

		if (thumbnail != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"thumbnail\": ");

			sb.append("\"");

			sb.append(_escape(thumbnail));

			sb.append("\"");
		}

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(type);

			sb.append("\"");
		}

		if (urls != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"urls\": ");

			sb.append(_toJSON(urls));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.delivery.catalog.dto.v1_0.MappedProduct",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Type")
	public static enum Type {

		DIAGRAM("diagram"), EXTERNAL("external"), SKU("sku");

		@JsonCreator
		public static Type create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value)) {
					return type;
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

		private Type(String value) {
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