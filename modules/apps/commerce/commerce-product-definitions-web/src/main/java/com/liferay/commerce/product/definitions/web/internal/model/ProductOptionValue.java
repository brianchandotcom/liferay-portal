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
		long cpDefinitionOptionValueRelId, String name, double order,
		String sku) {

		_cpDefinitionOptionValueRelId = cpDefinitionOptionValueRelId;
		_name = name;
		_order = order;
		_sku = sku;
	}

	public long getCPDefinitionOptionValueRelId() {
		return _cpDefinitionOptionValueRelId;
	}

	public String getName() {
		return _name;
	}

	public double getOrder() {
		return _order;
	}

	public String getSku() {
		return _sku;
	}

	private final long _cpDefinitionOptionValueRelId;
	private final String _name;
	private final double _order;
	private final String _sku;

}