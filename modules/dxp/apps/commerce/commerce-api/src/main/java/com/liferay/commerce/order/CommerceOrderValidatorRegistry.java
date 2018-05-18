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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CommerceOrderValidatorRegistry {

	public CommerceOrderValidator getCommerceOrderValidator(String key);

	public Map<Long, List<CommerceOrderValidatorResult>>
			getCommerceOrderValidatorResults(CommerceOrder commerceOrder)
		throws PortalException;

	public List<CommerceOrderValidator> getCommerceOrderValidators();

	public boolean isValid(CommerceOrder commerceOrder) throws PortalException;

	public List<CommerceOrderValidatorResult> validate(
			CommerceOrderItem commerceOrderItem)
		throws PortalException;

	public List<CommerceOrderValidatorResult> validate(
			CPInstance cpInstance, int quantity)
		throws PortalException;

}