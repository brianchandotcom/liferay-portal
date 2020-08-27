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
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.ProductGroupSerDes;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class ProductGroup implements Cloneable {

	public static ProductGroup toDTO(String json) {
		return ProductGroupSerDes.toDTO(json);
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

	public Integer getProductsCount() {
		return productsCount;
	}

	public void setProductsCount(Integer productsCount) {
		this.productsCount = productsCount;
	}

	public void setProductsCount(
		UnsafeSupplier<Integer, Exception> productsCountUnsafeSupplier) {

		try {
			productsCount = productsCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer productsCount;

	public Map<String, String> getTitle() {
		return title;
	}

	public void setTitle(Map<String, String> title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<Map<String, String>, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> title;

	@Override
	public ProductGroup clone() throws CloneNotSupportedException {
		return (ProductGroup)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProductGroup)) {
			return false;
		}

		ProductGroup productGroup = (ProductGroup)object;

		return Objects.equals(toString(), productGroup.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ProductGroupSerDes.toJSON(this);
	}

}