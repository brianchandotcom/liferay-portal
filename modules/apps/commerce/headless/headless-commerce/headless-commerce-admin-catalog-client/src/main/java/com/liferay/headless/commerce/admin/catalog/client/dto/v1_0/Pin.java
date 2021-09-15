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

package com.liferay.headless.commerce.admin.catalog.client.dto.v1_0;

import com.liferay.headless.commerce.admin.catalog.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0.PinSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class Pin implements Cloneable, Serializable {

	public static Pin toDTO(String json) {
		return PinSerDes.toDTO(json);
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

	public MappedProduct getMappedProduct() {
		return mappedProduct;
	}

	public void setMappedProduct(MappedProduct mappedProduct) {
		this.mappedProduct = mappedProduct;
	}

	public void setMappedProduct(
		UnsafeSupplier<MappedProduct, Exception> mappedProductUnsafeSupplier) {

		try {
			mappedProduct = mappedProductUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected MappedProduct mappedProduct;

	public Double getPositionX() {
		return positionX;
	}

	public void setPositionX(Double positionX) {
		this.positionX = positionX;
	}

	public void setPositionX(
		UnsafeSupplier<Double, Exception> positionXUnsafeSupplier) {

		try {
			positionX = positionXUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double positionX;

	public Double getPositionY() {
		return positionY;
	}

	public void setPositionY(Double positionY) {
		this.positionY = positionY;
	}

	public void setPositionY(
		UnsafeSupplier<Double, Exception> positionYUnsafeSupplier) {

		try {
			positionY = positionYUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double positionY;

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void setSequence(
		UnsafeSupplier<String, Exception> sequenceUnsafeSupplier) {

		try {
			sequence = sequenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String sequence;

	@Override
	public Pin clone() throws CloneNotSupportedException {
		return (Pin)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Pin)) {
			return false;
		}

		Pin pin = (Pin)object;

		return Objects.equals(toString(), pin.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PinSerDes.toJSON(this);
	}

}