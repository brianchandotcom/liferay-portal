/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.discount.CommerceDiscountValue;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public interface CommerceOrderPrice {

	public CommerceDiscountValue getShippingDiscountValue();

	public CommerceDiscountValue getShippingDiscountValueWithTaxAmount();

	public CommerceMoney getShippingValue();

	public CommerceMoney getShippingValueWithTaxAmount();

	public CommerceMoney getSubtotal();

	public CommerceDiscountValue getSubtotalDiscountValue();

	public CommerceDiscountValue getSubtotalDiscountValueWithTaxAmount();

	public CommerceMoney getSubtotalWithTaxAmount();

	public CommerceMoney getTaxValue();

	public CommerceMoney getTotal();

	public CommerceDiscountValue getTotalDiscountValue();

	public CommerceDiscountValue getTotalDiscountValueWithTaxAmount();

	public CommerceMoney getTotalWithTaxAmount();

}