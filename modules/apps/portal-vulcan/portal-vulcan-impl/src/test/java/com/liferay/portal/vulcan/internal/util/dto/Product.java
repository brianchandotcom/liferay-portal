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

package com.liferay.portal.vulcan.internal.util.dto;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Ivica Cardic
 */
public class Product {

	public Long getId() {
		return _id;
	}

	public Collection<ProductOption> getProductOptions() {
		return _productOptions;
	}

	public Collection<Sku> getSkus() {
		return _skus;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setProductOptions(Collection<ProductOption> productOptions) {
		_productOptions = productOptions;
	}

	public void setSkus(Collection<Sku> skus) {
		_skus = skus;
	}

	private Long _id;
	private Collection<ProductOption> _productOptions = Collections.emptyList();
	private Collection<Sku> _skus = Collections.emptyList();

}