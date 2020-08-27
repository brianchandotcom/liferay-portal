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

package com.liferay.commerce.tax;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.List;

/**
 * @author Marco Leo
 */
public interface CommerceTaxCalculation {

	public List<CommerceTaxValue> getCommerceTaxValues(
			CommerceOrder commerceOrder)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public List<CommerceTaxValue> getCommerceTaxValues(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException;

	public List<CommerceTaxValue> getCommerceTaxValues(
			long groupId, long cpInstanceId, long commerceBillingAddressId,
			long commerceShippingAddressId, BigDecimal amount,
			boolean includeTax)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public List<CommerceTaxValue> getCommerceTaxValues(
			long groupId, long cpInstanceId, long commerceBillingAddressId,
			long commerceShippingAddressId, BigDecimal amount,
			CommerceContext commerceContext)
		throws PortalException;

	public CommerceMoney getShippingTaxValue(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public CommerceMoney getTaxAmount(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException;

	public CommerceMoney getTaxAmount(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency)
		throws PortalException;

}