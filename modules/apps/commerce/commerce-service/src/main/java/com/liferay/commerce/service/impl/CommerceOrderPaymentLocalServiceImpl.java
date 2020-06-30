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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderPayment;
import com.liferay.commerce.service.base.CommerceOrderPaymentLocalServiceBaseImpl;
import com.liferay.commerce.util.comparator.CommerceOrderPaymentCreateDateComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderPaymentLocalServiceImpl
	extends CommerceOrderPaymentLocalServiceBaseImpl {

	@Override
	public CommerceOrderPayment addCommerceOrderPayment(
			long commerceOrderId, int status, String result)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		User user = userLocalService.getUser(commerceOrder.getUserId());

		return _getCommerceOrderPayment(status, result, commerceOrder, user);
	}

	@Override
	public CommerceOrderPayment addCommerceOrderPayment(
			long commerceOrderId, int status, String content,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		return _getCommerceOrderPayment(
			status, content,
			commerceOrderLocalService.getCommerceOrder(commerceOrderId), user);
	}

	@Override
	public void deleteCommerceOrderPayments(long commerceOrderId) {
		commerceOrderPaymentPersistence.removeByCommerceOrderId(
			commerceOrderId);
	}

	@Override
	public CommerceOrderPayment fetchLatestCommerceOrderPayment(
			long commerceOrderId)
		throws PortalException {

		return commerceOrderPaymentPersistence.fetchByCommerceOrderId_First(
			commerceOrderId, new CommerceOrderPaymentCreateDateComparator());
	}

	@Override
	public List<CommerceOrderPayment> getCommerceOrderPayments(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {

		return commerceOrderPaymentPersistence.findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrderPaymentsCount(long commerceOrderId) {
		return commerceOrderPaymentPersistence.countByCommerceOrderId(
			commerceOrderId);
	}

	private CommerceOrderPayment _getCommerceOrderPayment(
		int status, String result, CommerceOrder commerceOrder, User user) {

		long commerceOrderPaymentId = counterLocalService.increment();

		CommerceOrderPayment commerceOrderPayment =
			commerceOrderPaymentPersistence.create(commerceOrderPaymentId);

		commerceOrderPayment.setGroupId(commerceOrder.getGroupId());
		commerceOrderPayment.setCompanyId(user.getCompanyId());
		commerceOrderPayment.setUserId(user.getUserId());
		commerceOrderPayment.setUserName(user.getFullName());
		commerceOrderPayment.setCommerceOrderId(
			commerceOrder.getCommerceOrderId());
		commerceOrderPayment.setCommercePaymentMethodKey(
			commerceOrder.getCommercePaymentMethodKey());
		commerceOrderPayment.setContent(result);
		commerceOrderPayment.setStatus(status);

		return commerceOrderPaymentPersistence.update(commerceOrderPayment);
	}

}