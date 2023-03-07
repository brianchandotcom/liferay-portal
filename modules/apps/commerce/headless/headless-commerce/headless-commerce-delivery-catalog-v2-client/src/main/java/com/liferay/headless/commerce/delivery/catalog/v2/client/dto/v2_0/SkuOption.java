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

package com.liferay.headless.commerce.delivery.catalog.v2.client.dto.v2_0;

import com.liferay.headless.commerce.delivery.catalog.v2.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.delivery.catalog.v2.client.serdes.v2_0.SkuOptionSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class SkuOption implements Cloneable, Serializable {

	public static SkuOption toDTO(String json) {
		return SkuOptionSerDes.toDTO(json);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setKey(UnsafeSupplier<String, Exception> keyUnsafeSupplier) {
		try {
			key = keyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String key;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public Boolean getPriceContributor() {
		return priceContributor;
	}

	public void setPriceContributor(Boolean priceContributor) {
		this.priceContributor = priceContributor;
	}

	public void setPriceContributor(
		UnsafeSupplier<Boolean, Exception> priceContributorUnsafeSupplier) {

		try {
			priceContributor = priceContributorUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean priceContributor;

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setRequired(
		UnsafeSupplier<Boolean, Exception> requiredUnsafeSupplier) {

		try {
			required = requiredUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean required;

	public Boolean getSkuContributor() {
		return skuContributor;
	}

	public void setSkuContributor(Boolean skuContributor) {
		this.skuContributor = skuContributor;
	}

	public void setSkuContributor(
		UnsafeSupplier<Boolean, Exception> skuContributorUnsafeSupplier) {

		try {
			skuContributor = skuContributorUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean skuContributor;

	public SkuOptionValue[] getSkuOptionValues() {
		return skuOptionValues;
	}

	public void setSkuOptionValues(SkuOptionValue[] skuOptionValues) {
		this.skuOptionValues = skuOptionValues;
	}

	public void setSkuOptionValues(
		UnsafeSupplier<SkuOptionValue[], Exception>
			skuOptionValuesUnsafeSupplier) {

		try {
			skuOptionValues = skuOptionValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SkuOptionValue[] skuOptionValues;

	@Override
	public SkuOption clone() throws CloneNotSupportedException {
		return (SkuOption)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SkuOption)) {
			return false;
		}

		SkuOption skuOption = (SkuOption)object;

		return Objects.equals(toString(), skuOption.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SkuOptionSerDes.toJSON(this);
	}

}