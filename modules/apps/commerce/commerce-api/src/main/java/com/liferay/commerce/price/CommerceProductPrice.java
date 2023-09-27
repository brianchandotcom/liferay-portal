/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.discount.CommerceDiscountValue;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public interface CommerceProductPrice {

	public long getCommercePriceListId();

	public CommerceDiscountValue getDiscountValue();

	public CommerceDiscountValue getDiscountValueWithTaxAmount();

	public CommerceMoney getFinalPrice();

	public CommerceMoney getFinalPriceWithTaxAmount();

	public BigDecimal getQuantity();

	public BigDecimal getTaxValue();

	public BigDecimal getUnitOfMeasureIncrementalOrderQuantity();

	public String getUnitOfMeasureKey();

	public CommerceMoney getUnitPrice();

	public CommerceMoney getUnitPriceWithTaxAmount();

	public CommerceMoney getUnitPromoPrice();

	public CommerceMoney getUnitPromoPriceWithTaxAmount();

	public boolean isPriceOnApplication();

	public void setPriceOnApplication(boolean priceOnApplication);

}