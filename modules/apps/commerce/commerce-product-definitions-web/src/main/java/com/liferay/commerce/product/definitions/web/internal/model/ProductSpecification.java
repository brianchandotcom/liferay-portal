/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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