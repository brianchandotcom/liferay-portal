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