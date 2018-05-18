/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.model;

import com.liferay.commerce.currency.model.CommerceCurrency;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public interface CommerceOrderPrice {

	public CommerceCurrency getCommerceCurrency();

	public BigDecimal getDiscountAmount();

	public BigDecimal getShippingAmount();

	public BigDecimal getSubtotal();

	public BigDecimal getTaxAmount();

	public BigDecimal getTotal();

}