/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.internal.model;

/**
 * @author Ivica Cardic
 */
public class TaxCategoryMapping {

	public TaxCategoryMapping(
		String externalReferenceCode, String name, long taxCategoryMappingId) {

		_externalReferenceCode = externalReferenceCode;
		_name = name;
		_taxCategoryMappingId = taxCategoryMappingId;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getName() {
		return _name;
	}

	public long getTaxCategoryMappingId() {
		return _taxCategoryMappingId;
	}

	private final String _externalReferenceCode;
	private final String _name;
	private final long _taxCategoryMappingId;

}