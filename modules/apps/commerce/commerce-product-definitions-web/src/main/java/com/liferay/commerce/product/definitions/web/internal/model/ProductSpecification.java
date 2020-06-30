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
public class ProductSpecification {

	public ProductSpecification(
		long cpDefinitionSpecificationOptionValueId, String label, String value,
		String group, double order) {

		_cpDefinitionSpecificationOptionValueId =
			cpDefinitionSpecificationOptionValueId;
		_label = label;
		_value = value;
		_group = group;
		_order = order;
	}

	public long getCPDefinitionSpecificationOptionValueId() {
		return _cpDefinitionSpecificationOptionValueId;
	}

	public String getGroup() {
		return _group;
	}

	public String getLabel() {
		return _label;
	}

	public double getOrder() {
		return _order;
	}

	public String getValue() {
		return _value;
	}

	private final long _cpDefinitionSpecificationOptionValueId;
	private final String _group;
	private final String _label;
	private final double _order;
	private final String _value;

}