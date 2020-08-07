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

package com.liferay.commerce.order.engine;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Alec Sloan
 */
public interface CommerceOrderEngine {

	public CommerceOrder checkCommerceOrderShipmentStatus(
			CommerceOrder commerceOrder)
		throws PortalException;

	public CommerceOrder checkoutCommerceOrder(
			CommerceOrder commerceOrder, long userId)
		throws PortalException;

	public CommerceOrderStatus getCurrentCommerceOrderStatus(
		CommerceOrder commerceOrder);

	public List<CommerceOrderStatus> getNextCommerceOrderStatuses(
			CommerceOrder commerceOrder)
		throws PortalException;

	public CommerceOrder transitionCommerceOrder(
			CommerceOrder commerceOrder, int orderStatus, long userId)
		throws PortalException;

}