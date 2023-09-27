/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductOptionValue {

	public ProductOptionValue(
		long cpDefinitionOptionValueRelId, String deltaPrice, String key,
		String name, double position, String preselected, String sku) {

		_cpDefinitionOptionValueRelId = cpDefinitionOptionValueRelId;
		_deltaPrice = deltaPrice;
		_key = key;
		_name = name;
		_position = position;
		_preselected = preselected;
		_sku = sku;
	}

	public long getCPDefinitionOptionValueRelId() {
		return _cpDefinitionOptionValueRelId;
	}

	public String getDeltaPrice() {
		return _deltaPrice;
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public double getPosition() {
		return _position;
	}

	public String getPreselected() {
		return _preselected;
	}

	public String getSku() {
		return _sku;
	}

	private final long _cpDefinitionOptionValueRelId;
	private final String _deltaPrice;
	private final String _key;
	private final String _name;
	private final double _position;
	private final String _preselected;
	private final String _sku;

}