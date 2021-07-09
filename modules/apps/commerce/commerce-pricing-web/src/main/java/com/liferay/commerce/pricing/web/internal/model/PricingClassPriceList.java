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

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Riccardo Alberti
 */
public class PricingClassPriceList {

	public PricingClassPriceList(
		long commercePriceListId, String name, String catalogName,
		String createDateString, LabelField status, String active) {

		_commercePriceListId = commercePriceListId;
		_name = name;
		_catalogName = catalogName;
		_createDateString = createDateString;
		_status = status;
		_active = active;
	}

	public String getActive() {
		return _active;
	}

	public String getCatalogName() {
		return _catalogName;
	}

	public long getCommercePriceListId() {
		return _commercePriceListId;
	}

	public String getCreateDateString() {
		return _createDateString;
	}

	public String getName() {
		return _name;
	}

	public LabelField getStatus() {
		return _status;
	}

	private final String _active;
	private final String _catalogName;
	private final long _commercePriceListId;
	private final String _createDateString;
	private final String _name;
	private final LabelField _status;

}