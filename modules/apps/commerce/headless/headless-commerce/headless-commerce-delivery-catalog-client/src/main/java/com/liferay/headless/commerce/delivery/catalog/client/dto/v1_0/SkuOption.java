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
import com.liferay.headless.commerce.delivery.catalog.client.serdes.v1_0.SkuOptionSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class SkuOption implements Cloneable, Serializable {

	public static SkuOption toDTO(String json) {
		return SkuOptionSerDes.toDTO(json);
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public void setKey(UnsafeSupplier<Long, Exception> keyUnsafeSupplier) {
		try {
			key = keyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long key;

	public Long getSkuOptionId() {
		return skuOptionId;
	}

	public void setSkuOptionId(Long skuOptionId) {
		this.skuOptionId = skuOptionId;
	}

	public void setSkuOptionId(
		UnsafeSupplier<Long, Exception> skuOptionIdUnsafeSupplier) {

		try {
			skuOptionId = skuOptionIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long skuOptionId;

	public String getSkuOptionKey() {
		return skuOptionKey;
	}

	public void setSkuOptionKey(String skuOptionKey) {
		this.skuOptionKey = skuOptionKey;
	}

	public void setSkuOptionKey(
		UnsafeSupplier<String, Exception> skuOptionKeyUnsafeSupplier) {

		try {
			skuOptionKey = skuOptionKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String skuOptionKey;

	public Long getSkuOptionValueId() {
		return skuOptionValueId;
	}

	public void setSkuOptionValueId(Long skuOptionValueId) {
		this.skuOptionValueId = skuOptionValueId;
	}

	public void setSkuOptionValueId(
		UnsafeSupplier<Long, Exception> skuOptionValueIdUnsafeSupplier) {

		try {
			skuOptionValueId = skuOptionValueIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long skuOptionValueId;

	public String getSkuOptionValueKey() {
		return skuOptionValueKey;
	}

	public void setSkuOptionValueKey(String skuOptionValueKey) {
		this.skuOptionValueKey = skuOptionValueKey;
	}

	public void setSkuOptionValueKey(
		UnsafeSupplier<String, Exception> skuOptionValueKeyUnsafeSupplier) {

		try {
			skuOptionValueKey = skuOptionValueKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String skuOptionValueKey;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public void setValue(UnsafeSupplier<Long, Exception> valueUnsafeSupplier) {
		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long value;

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