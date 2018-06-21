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

package com.liferay.commerce.data.integration.apio.internal.util;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true, service = CommerceOrderHelper.class)
public class CommerceOrderHelper {

	public CommerceOrder updateCommerceOrder(
			Long commerceOrderId, Long orderStatus, Long paymentStatus)
		throws PortalException {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commerceOrder.getGroupId());

		if (orderStatus != null) {
			_commerceOrderService.updateOrderStatus(
				commerceOrderId, orderStatus.intValue());
		}

		if (paymentStatus != null) {
			_commerceOrderService.updatePaymentStatus(
				commerceOrderId, paymentStatus.intValue(), serviceContext);
		}

		return _commerceOrderService.getCommerceOrder(commerceOrderId);
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}