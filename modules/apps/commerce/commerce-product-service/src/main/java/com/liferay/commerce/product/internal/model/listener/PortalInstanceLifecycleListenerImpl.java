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

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.commerce.constants.CommerceDestinationNames;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Company;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	service = PortalInstanceLifecycleListener.class
)
public class PortalInstanceLifecycleListenerImpl
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		try {
			List<CommerceCatalog> commerceCatalogs =
				_commerceCatalogLocalService.getCommerceCatalogs(
					company.getCompanyId(), true);

			if (commerceCatalogs.isEmpty()) {
				CommerceCatalog commerceCatalog =
					_commerceCatalogLocalService.addDefaultCommerceCatalog(
						company.getCompanyId());

				Message message = new Message();

				message.put(
					"commerceCatalogId",
					commerceCatalog.getCommerceCatalogId());

				MessageBusUtil.sendMessage(
					CommerceDestinationNames.BASE_PRICE_LIST, message);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstanceLifecycleListenerImpl.class);

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

}