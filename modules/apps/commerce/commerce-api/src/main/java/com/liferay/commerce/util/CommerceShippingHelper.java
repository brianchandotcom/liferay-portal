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

package com.liferay.commerce.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.Dimensions;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public interface CommerceShippingHelper {

	public Dimensions getDimensions(CommerceOrderItem commerceOrderItem);

	public Dimensions getDimensions(CPInstance cpInstance);

	public Dimensions getDimensions(List<CommerceOrderItem> commerceOrderItems)
		throws PortalException;

	public double getWeight(CommerceOrderItem commerceOrderItem);

	public double getWeight(CPInstance cpInstance);

	public double getWeight(List<CommerceOrderItem> commerceOrderItems)
		throws PortalException;

	public boolean isFreeShipping(CommerceOrder commerceOrder)
		throws PortalException;

	public boolean isShippable(CommerceOrder commerceOrder)
		throws PortalException;

}