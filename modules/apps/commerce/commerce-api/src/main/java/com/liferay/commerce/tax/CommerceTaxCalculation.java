/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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