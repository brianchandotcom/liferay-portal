/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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