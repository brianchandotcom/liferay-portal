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

package com.liferay.marketplace.store.web.messaging;

import com.liferay.marketplace.service.AppLocalService;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 */
@Component(
	immediate = true,
	property = {"destination.name=" + DestinationNames.HOT_DEPLOY},
	service = MessageListener.class
)
public class HotDeployMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		_appLocalService.clearInstalledAppsCache();
	}

	@Reference(unbind = "-")
	protected void setAppLocalService(AppLocalService appLocalService) {
		_appLocalService = appLocalService;
	}

	@Reference(
		target = "(destination.name=" + DestinationNames.HOT_DEPLOY + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	private volatile AppLocalService _appLocalService;

}