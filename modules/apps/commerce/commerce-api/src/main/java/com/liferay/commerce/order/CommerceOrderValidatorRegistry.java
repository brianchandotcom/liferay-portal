/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceOrderValidatorRegistry {

	public CommerceOrderValidator getCommerceOrderValidator(String key);

	public Map<Long, List<CommerceOrderValidatorResult>>
			getCommerceOrderValidatorResults(
				Locale locale, CommerceOrder commerceOrder)
		throws PortalException;

	public List<CommerceOrderValidator> getCommerceOrderValidators();

	public boolean isValid(Locale locale, CommerceOrder commerceOrder)
		throws PortalException;

	public default List<CommerceOrderValidatorResult> validate(
			Locale locale, CommerceOrder commerceOrder)
		throws PortalException {

		return Collections.emptyList();
	}

	public List<CommerceOrderValidatorResult> validate(
			Locale locale, CommerceOrder commerceOrder, CPInstance cpInstance,
			BigDecimal quantity)
		throws PortalException;

	public List<CommerceOrderValidatorResult> validate(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException;

}