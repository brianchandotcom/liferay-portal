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

package com.liferay.headless.commerce.delivery.order.client.dto.v1_0;

import com.liferay.headless.commerce.delivery.order.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.delivery.order.client.serdes.v1_0.SettingsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class Settings implements Cloneable, Serializable {

	public static Settings toDTO(String json) {
		return SettingsSerDes.toDTO(json);
	}

	public Integer[] getAllowedQuantities() {
		return allowedQuantities;
	}

	public void setAllowedQuantities(Integer[] allowedQuantities) {
		this.allowedQuantities = allowedQuantities;
	}

	public void setAllowedQuantities(
		UnsafeSupplier<Integer[], Exception> allowedQuantitiesUnsafeSupplier) {

		try {
			allowedQuantities = allowedQuantitiesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer[] allowedQuantities;

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public void setMaxQuantity(
		UnsafeSupplier<Integer, Exception> maxQuantityUnsafeSupplier) {

		try {
			maxQuantity = maxQuantityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer maxQuantity;

	public Integer getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(Integer minQuantity) {
		this.minQuantity = minQuantity;
	}

	public void setMinQuantity(
		UnsafeSupplier<Integer, Exception> minQuantityUnsafeSupplier) {

		try {
			minQuantity = minQuantityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer minQuantity;

	public Integer getMultipleQuantity() {
		return multipleQuantity;
	}

	public void setMultipleQuantity(Integer multipleQuantity) {
		this.multipleQuantity = multipleQuantity;
	}

	public void setMultipleQuantity(
		UnsafeSupplier<Integer, Exception> multipleQuantityUnsafeSupplier) {

		try {
			multipleQuantity = multipleQuantityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer multipleQuantity;

	@Override
	public Settings clone() throws CloneNotSupportedException {
		return (Settings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Settings)) {
			return false;
		}

		Settings settings = (Settings)object;

		return Objects.equals(toString(), settings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SettingsSerDes.toJSON(this);
	}

}