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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderPayment;
import com.liferay.commerce.service.base.CommerceOrderPaymentLocalServiceBaseImpl;
import com.liferay.commerce.util.comparator.CommerceOrderPaymentCreateDateComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderPaymentLocalServiceImpl
	extends CommerceOrderPaymentLocalServiceBaseImpl {

	@Override
	public CommerceOrderPayment addCommerceOrderPayment(
			long commerceOrderId, int status, String content,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceOrderPaymentId = counterLocalService.increment();

		CommerceOrderPayment commerceOrderPayment =
			commerceOrderPaymentPersistence.create(commerceOrderPaymentId);

		commerceOrderPayment.setGroupId(commerceOrder.getGroupId());
		commerceOrderPayment.setCompanyId(user.getCompanyId());
		commerceOrderPayment.setUserId(user.getUserId());
		commerceOrderPayment.setUserName(user.getFullName());
		commerceOrderPayment.setCommerceOrderId(
			commerceOrder.getCommerceOrderId());
		commerceOrderPayment.setCommercePaymentMethodId(
			commerceOrder.getCommercePaymentMethodId());
		commerceOrderPayment.setStatus(status);
		commerceOrderPayment.setContent(content);

		commerceOrderPaymentPersistence.update(commerceOrderPayment);

		return commerceOrderPayment;
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

}