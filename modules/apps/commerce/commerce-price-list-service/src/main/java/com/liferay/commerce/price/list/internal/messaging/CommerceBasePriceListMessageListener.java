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

package com.liferay.commerce.price.list.internal.messaging;

import com.liferay.commerce.price.list.internal.helper.CommerceBasePriceListHelper;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "destination.name=" + DestinationNames.COMMERCE_BASE_PRICE_LIST,
	service = MessageListener.class
)
public class CommerceBasePriceListMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(message.getPayload()));

		long commerceCatalogId = jsonObject.getLong("commerceCatalogId");

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalog(
				commerceCatalogId);

		if (commerceCatalog == null) {
			return;
		}

		try {
			_commerceBasePriceListHelper.addCatalogBaseCommercePriceList(
				commerceCatalog);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceBasePriceListMessageListener.class);

	@Reference
	private CommerceBasePriceListHelper _commerceBasePriceListHelper;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

}