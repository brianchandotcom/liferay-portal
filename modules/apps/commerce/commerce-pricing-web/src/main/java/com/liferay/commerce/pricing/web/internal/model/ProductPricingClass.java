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

package com.liferay.commerce.pricing.web.internal.model;

/**
 * @author Riccardo Alberti
 */
public class ProductPricingClass {

	public ProductPricingClass(
		long cpDefinitionId, long pricingClassId, String title,
		int numberOfProducts) {

		_cpDefinitionId = cpDefinitionId;
		_pricingClassId = pricingClassId;
		_title = title;
		_numberOfProducts = numberOfProducts;
	}

	public long getCpDefinitionId() {
		return _cpDefinitionId;
	}

	public int getNumberOfProducts() {
		return _numberOfProducts;
	}

	public long getPricingClassId() {
		return _pricingClassId;
	}

	public String getTitle() {
		return _title;
	}

	private final long _cpDefinitionId;
	private final int _numberOfProducts;
	private final long _pricingClassId;
	private final String _title;

}