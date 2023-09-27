/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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