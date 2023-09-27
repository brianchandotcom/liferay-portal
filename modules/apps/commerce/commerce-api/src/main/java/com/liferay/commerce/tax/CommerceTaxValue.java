/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public class CommerceTaxValue {

	public CommerceTaxValue(String name, String label, BigDecimal amount) {
		_name = name;
		_label = label;
		_amount = amount;
	}

	public BigDecimal getAmount() {
		return _amount;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	private final BigDecimal _amount;
	private final String _label;
	private final String _name;

}