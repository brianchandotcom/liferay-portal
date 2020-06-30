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

package com.liferay.commerce.catalog.web.internal.model;

/**
 * @author Gianmarco Brunialti Masera
 */
public class Catalog {

	public Catalog(
		long catalogId, String currencyCode, String defaultLanguageId,
		String name, boolean system) {

		_catalogId = catalogId;
		_currencyCode = currencyCode;
		_defaultLanguageId = defaultLanguageId;
		_name = name;
		_system = system;
	}

	public long getCatalogId() {
		return _catalogId;
	}

	public String getCurrencyCode() {
		return _currencyCode;
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public String getName() {
		return _name;
	}

	public boolean isSystem() {
		return _system;
	}

	private final long _catalogId;
	private final String _currencyCode;
	private final String _defaultLanguageId;
	private final String _name;
	private final boolean _system;

}