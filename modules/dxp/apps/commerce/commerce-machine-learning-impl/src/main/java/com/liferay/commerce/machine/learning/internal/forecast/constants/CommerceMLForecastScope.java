/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.forecast.constants;

/**
 * @author Riccardo Ferrari
 */
public enum CommerceMLForecastScope {

	ASSET_CATEGORY("asset-category"), COMMERCE_ACCOUNT("commerce-account"),
	SKU("sku");

	public String getLabel() {
		return _label;
	}

	private CommerceMLForecastScope(String label) {
		_label = label;
	}

	private final String _label;

}