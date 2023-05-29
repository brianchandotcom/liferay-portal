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

package com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0;

import com.liferay.headless.commerce.delivery.catalog.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.delivery.catalog.client.serdes.v1_0.ReplacementSkuSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class ReplacementSku implements Cloneable, Serializable {

	public static ReplacementSku toDTO(String json) {
		return ReplacementSkuSerDes.toDTO(json);
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public void setPrice(UnsafeSupplier<Price, Exception> priceUnsafeSupplier) {
		try {
			price = priceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Price price;

	public ProductConfiguration getProductConfiguration() {
		return productConfiguration;
	}

	public void setProductConfiguration(
		ProductConfiguration productConfiguration) {

		this.productConfiguration = productConfiguration;
	}

	public void setProductConfiguration(
		UnsafeSupplier<ProductConfiguration, Exception>
			productConfigurationUnsafeSupplier) {

		try {
			productConfiguration = productConfigurationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ProductConfiguration productConfiguration;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setSku(UnsafeSupplier<String, Exception> skuUnsafeSupplier) {
		try {
			sku = skuUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String sku;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public void setSkuId(UnsafeSupplier<Long, Exception> skuIdUnsafeSupplier) {
		try {
			skuId = skuIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long skuId;

	public SkuOption[] getSkuOptions() {
		return skuOptions;
	}

	public void setSkuOptions(SkuOption[] skuOptions) {
		this.skuOptions = skuOptions;
	}

	public void setSkuOptions(
		UnsafeSupplier<SkuOption[], Exception> skuOptionsUnsafeSupplier) {

		try {
			skuOptions = skuOptionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SkuOption[] skuOptions;

	public Map<String, String> getUrls() {
		return urls;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}

	public void setUrls(
		UnsafeSupplier<Map<String, String>, Exception> urlsUnsafeSupplier) {

		try {
			urls = urlsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> urls;

	@Override
	public ReplacementSku clone() throws CloneNotSupportedException {
		return (ReplacementSku)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ReplacementSku)) {
			return false;
		}

		ReplacementSku replacementSku = (ReplacementSku)object;

		return Objects.equals(toString(), replacementSku.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ReplacementSkuSerDes.toJSON(this);
	}

}