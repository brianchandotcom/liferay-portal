/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.model.impl;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactoryUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
public class CommerceCurrencyImpl extends CommerceCurrencyBaseImpl {

	@Override
	public CommerceMoney getZero() {
		return CommerceMoneyFactoryUtil.create(this, BigDecimal.ZERO);
	}

	@Override
	public BigDecimal round(BigDecimal value) {
		return value.setScale(
			getMaxFractionDigits(), RoundingMode.valueOf(getRoundingMode()));
	}

}