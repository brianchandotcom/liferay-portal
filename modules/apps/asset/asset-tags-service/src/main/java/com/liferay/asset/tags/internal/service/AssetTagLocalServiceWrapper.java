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

package com.liferay.asset.tags.internal.service;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class AssetTagLocalServiceWrapper
	extends com.liferay.asset.kernel.service.AssetTagLocalServiceWrapper {

	public AssetTagLocalServiceWrapper() {
		super(null);
	}

	public AssetTagLocalServiceWrapper(
		AssetTagLocalService assetTagLocalService) {

		super(assetTagLocalService);
	}

	@Override
	public void subscribeTag(long userId, long groupId, long tagId)
		throws PortalException {

		_subscriptionLocalService.addSubscription(
			userId, groupId, AssetTag.class.getName(), tagId);
	}

	@Override
	public void unsubscribeTag(long userId, long tagId) throws PortalException {
		_subscriptionLocalService.deleteSubscription(
			userId, AssetTag.class.getName(), tagId);
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}