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

package com.liferay.commerce.order;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

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
			int quantity)
		throws PortalException;

	public List<CommerceOrderValidatorResult> validate(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException;

}