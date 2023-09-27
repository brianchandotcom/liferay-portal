/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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