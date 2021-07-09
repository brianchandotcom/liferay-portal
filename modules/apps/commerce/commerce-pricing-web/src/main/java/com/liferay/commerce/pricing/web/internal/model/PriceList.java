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
 * @author Alessio Antonio Rendina
 */
public class PriceList {

	public PriceList(
		String active, String author, String catalog, String createDateString,
		String name, long priceListId, double priority, LabelField status) {

		_active = active;
		_author = author;
		_catalog = catalog;
		_createDateString = createDateString;
		_name = name;
		_priceListId = priceListId;
		_priority = priority;
		_status = status;
	}

	public String getActive() {
		return _active;
	}

	public String getAuthor() {
		return _author;
	}

	public String getCatalog() {
		return _catalog;
	}

	public String getCreateDateString() {
		return _createDateString;
	}

	public String getName() {
		return _name;
	}

	public long getPriceListId() {
		return _priceListId;
	}

	public double getPriority() {
		return _priority;
	}

	public LabelField getStatus() {
		return _status;
	}

	private final String _active;
	private final String _author;
	private final String _catalog;
	private final String _createDateString;
	private final String _name;
	private final long _priceListId;
	private final double _priority;
	private final LabelField _status;

}