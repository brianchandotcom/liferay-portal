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

package com.liferay.commerce.price;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CommercePriceCalculation {

	public CommerceMoney getFinalPrice(
			long cpInstanceId, int quantity, boolean includeDiscounts,
			boolean includeTaxes, CommerceContext commerceContext)
		throws PortalException;

	public CommerceMoney getOrderSubtotal(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException;

	public CommerceMoney getUnitPrice(
			long cpInstanceId, int quantity, CommerceContext commerceContext)
		throws PortalException;

}