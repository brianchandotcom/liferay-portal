/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.model;

/**
 * @author Riccardo Alberti
 */
public class ProductPricingClass {

	public ProductPricingClass(
		long cpDefinitionId, long pricingClassId, String title,
		int numberOfProducts) {

		_cpDefinitionId = cpDefinitionId;
		_pricingClassId = pricingClassId;
		_title = title;
		_numberOfProducts = numberOfProducts;
	}

	public long getCpDefinitionId() {
		return _cpDefinitionId;
	}

	public int getNumberOfProducts() {
		return _numberOfProducts;
	}

	public long getPricingClassId() {
		return _pricingClassId;
	}

	public String getTitle() {
		return _title;
	}

	private final long _cpDefinitionId;
	private final int _numberOfProducts;
	private final long _pricingClassId;
	private final String _title;

}