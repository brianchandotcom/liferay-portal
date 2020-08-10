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

package com.liferay.headless.commerce.admin.pricing.client.dto.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.SkuSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class Sku implements Cloneable {

	public static Sku toDTO(String json) {
		return SkuSerDes.toDTO(json);
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public void setBasePrice(
		UnsafeSupplier<Double, Exception> basePriceUnsafeSupplier) {

		try {
			basePrice = basePriceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double basePrice;

	public String getBasePriceFormatted() {
		return basePriceFormatted;
	}

	public void setBasePriceFormatted(String basePriceFormatted) {
		this.basePriceFormatted = basePriceFormatted;
	}

	public void setBasePriceFormatted(
		UnsafeSupplier<String, Exception> basePriceFormattedUnsafeSupplier) {

		try {
			basePriceFormatted = basePriceFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String basePriceFormatted;

	public Double getBasePromoPrice() {
		return basePromoPrice;
	}

	public void setBasePromoPrice(Double basePromoPrice) {
		this.basePromoPrice = basePromoPrice;
	}

	public void setBasePromoPrice(
		UnsafeSupplier<Double, Exception> basePromoPriceUnsafeSupplier) {

		try {
			basePromoPrice = basePromoPriceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double basePromoPrice;

	public String getBasePromoPriceFormatted() {
		return basePromoPriceFormatted;
	}

	public void setBasePromoPriceFormatted(String basePromoPriceFormatted) {
		this.basePromoPriceFormatted = basePromoPriceFormatted;
	}

	public void setBasePromoPriceFormatted(
		UnsafeSupplier<String, Exception>
			basePromoPriceFormattedUnsafeSupplier) {

		try {
			basePromoPriceFormatted =
				basePromoPriceFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String basePromoPriceFormatted;

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

	@Override
	public Sku clone() throws CloneNotSupportedException {
		return (Sku)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Sku)) {
			return false;
		}

		Sku sku = (Sku)object;

		return Objects.equals(toString(), sku.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SkuSerDes.toJSON(this);
	}

}