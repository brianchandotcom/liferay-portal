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

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderThreadLocal;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(service = ModelListener.class)
public class CommerceOrderItemModelListener
	extends BaseModelListener<CommerceOrderItem> {

	@Override
	public void onAfterRemove(CommerceOrderItem commerceOrderItem) {
		try {
			if (CommerceOrderThreadLocal.isDeleteInProcess()) {
				return;
			}

			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			if (commerceOrder.getOrderStatus() ==
					CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED) {

				_commerceOrderEngine.checkCommerceOrderShipmentStatus(
					commerceOrderItem.getCommerceOrder());
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	@Override
	public void onAfterUpdate(
		CommerceOrderItem originalCommerceOrderItem,
		CommerceOrderItem commerceOrderItem) {

		try {
			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			if ((commerceOrder.getOrderStatus() ==
					CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED) ||
				(commerceOrder.getOrderStatus() ==
					CommerceOrderConstants.ORDER_STATUS_SHIPPED)) {

				_commerceOrderEngine.checkCommerceOrderShipmentStatus(
					commerceOrderItem.getCommerceOrder());
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemModelListener.class);

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

}