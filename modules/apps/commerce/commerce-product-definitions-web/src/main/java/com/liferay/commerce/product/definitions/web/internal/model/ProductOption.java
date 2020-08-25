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
public class ProductOption {

	public ProductOption(
		long cpDefinitionOptionRelId, String fieldType, String name,
		double position, String required, String skuContributor, int values) {

		_cpDefinitionOptionRelId = cpDefinitionOptionRelId;
		_fieldType = fieldType;
		_name = name;
		_position = position;
		_required = required;
		_skuContributor = skuContributor;
		_values = values;
	}

	public long getCPDefinitionOptionRelId() {
		return _cpDefinitionOptionRelId;
	}

	public String getFieldType() {
		return _fieldType;
	}

	public String getName() {
		return _name;
	}

	public double getPosition() {
		return _position;
	}

	public String getRequired() {
		return _required;
	}

	public String getSkuContributor() {
		return _skuContributor;
	}

	public int getValues() {
		return _values;
	}

	private final long _cpDefinitionOptionRelId;
	private final String _fieldType;
	private final String _name;
	private final double _position;
	private final String _required;
	private final String _skuContributor;
	private final int _values;

}