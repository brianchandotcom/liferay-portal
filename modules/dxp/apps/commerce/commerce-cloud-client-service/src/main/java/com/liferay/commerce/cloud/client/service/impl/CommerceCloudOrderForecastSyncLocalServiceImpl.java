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

package com.liferay.commerce.cloud.client.service.impl;

import com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync;
import com.liferay.commerce.cloud.client.service.base.CommerceCloudOrderForecastSyncLocalServiceBaseImpl;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.spring.extender.service.ServiceReference;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCloudOrderForecastSyncLocalServiceImpl
	extends CommerceCloudOrderForecastSyncLocalServiceBaseImpl {

	@Override
	public CommerceCloudOrderForecastSync addCommerceCloudOrderForecastSync(
			long commerceOrderId)
		throws PortalException {

		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync =
			commerceCloudOrderForecastSyncPersistence.fetchByCommerceOrderId(
				commerceOrderId);

		if (commerceCloudOrderForecastSync != null) {
			return commerceCloudOrderForecastSync;
		}

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		long commerceCloudOrderForecastSyncId = counterLocalService.increment();

		commerceCloudOrderForecastSync =
			commerceCloudOrderForecastSyncPersistence.create(
				commerceCloudOrderForecastSyncId);

		commerceCloudOrderForecastSync.setGroupId(commerceOrder.getGroupId());
		commerceCloudOrderForecastSync.setCompanyId(
			commerceOrder.getCompanyId());
		commerceCloudOrderForecastSync.setCommerceOrderId(
			commerceOrder.getCommerceOrderId());

		return commerceCloudOrderForecastSyncPersistence.update(
			commerceCloudOrderForecastSync);
	}

	@Override
	public CommerceCloudOrderForecastSync
			deleteCommerceCloudOrderForecastSyncByCommerceOrderId(
				long commerceOrderId)
		throws PortalException {

		return
			commerceCloudOrderForecastSyncPersistence.removeByCommerceOrderId(
				commerceOrderId);
	}

	@ServiceReference(type = CommerceOrderLocalService.class)
	private CommerceOrderLocalService _commerceOrderLocalService;

}