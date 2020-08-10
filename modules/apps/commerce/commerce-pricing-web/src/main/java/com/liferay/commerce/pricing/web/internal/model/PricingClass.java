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
public class PricingClass {

	public PricingClass(
		long pricingClassId, String title, int numberOfProducts,
		String lastPublishDate) {

		_pricingClassId = pricingClassId;
		_title = title;
		_numberOfProducts = numberOfProducts;
		_lastPublishDate = lastPublishDate;
	}

	public String getLastPublishDate() {
		return _lastPublishDate;
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

	private final String _lastPublishDate;
	private final int _numberOfProducts;
	private final long _pricingClassId;
	private final String _title;

}