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

import com.liferay.commerce.cloud.client.exception.CommerceCloudClientException;
import com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync;
import com.liferay.commerce.cloud.client.service.base.CommerceCloudOrderForecastSyncLocalServiceBaseImpl;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;

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
		commerceCloudOrderForecastSync.setCreateDate(new Date());
		commerceCloudOrderForecastSync.setCommerceOrderId(
			commerceOrder.getCommerceOrderId());

		return commerceCloudOrderForecastSyncPersistence.update(
			commerceCloudOrderForecastSync);
	}

	@Override
	public void checkCommerceCloudOrderForecastSyncs() {
		if (_lockManager.isLocked(_CHECK_LOCK_CLASS_NAME, _CHECK_LOCK_KEY)) {
			if (_log.isInfoEnabled()) {
				_log.info("Skipping check, another job is still in progress");
			}

			return;
		}

		try {
			_lockManager.lock(_CHECK_LOCK_CLASS_NAME, _CHECK_LOCK_KEY, null);

			doCheckCommerceCloudOrderForecastSyncs();
		}
		finally {
			_lockManager.unlock(_CHECK_LOCK_CLASS_NAME, _CHECK_LOCK_KEY);
		}
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

	protected void doCheckCommerceCloudOrderForecastSyncs() {
		while (true) {
			List<CommerceCloudOrderForecastSync>
				commerceCloudOrderForecastSyncs =
					commerceCloudOrderForecastSyncPersistence.findBySyncDate(
						null, 0, _CHECK_LIMIT);

			if (commerceCloudOrderForecastSyncs.isEmpty()) {
				break;
			}

			try {
				_commerceCloudClient.syncOrders(
					commerceCloudOrderForecastSyncs);
			}
			catch (CommerceCloudClientException ccce) {
				_log.error(
					"Unable to synchronize orders " +
						ListUtil.toString(
							commerceCloudOrderForecastSyncs,
							CommerceCloudOrderForecastSync.
								COMMERCE_ORDER_ID_ACCESSOR,
							StringPool.COMMA_AND_SPACE),
					ccce);

				break;
			}

			Date now = new Date();

			for (CommerceCloudOrderForecastSync commerceCloudOrderForecastSync :
					commerceCloudOrderForecastSyncs) {

				commerceCloudOrderForecastSync.setSyncDate(now);

				commerceCloudOrderForecastSyncPersistence.update(
					commerceCloudOrderForecastSync);
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Succesfully synchronized orders " +
						ListUtil.toString(
							commerceCloudOrderForecastSyncs,
							CommerceCloudOrderForecastSync.
								COMMERCE_ORDER_ID_ACCESSOR,
							StringPool.COMMA_AND_SPACE));
			}
		}
	}

	private static final int _CHECK_LIMIT = 100;

	private static final String _CHECK_LOCK_CLASS_NAME =
		CommerceCloudOrderForecastSync.class.getName();

	private static final String _CHECK_LOCK_KEY = "check";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCloudOrderForecastSyncLocalServiceImpl.class);

	@ServiceReference(type = CommerceCloudClient.class)
	private CommerceCloudClient _commerceCloudClient;

	@ServiceReference(type = CommerceOrderLocalService.class)
	private CommerceOrderLocalService _commerceOrderLocalService;

	@ServiceReference(type = LockManager.class)
	private LockManager _lockManager;

}