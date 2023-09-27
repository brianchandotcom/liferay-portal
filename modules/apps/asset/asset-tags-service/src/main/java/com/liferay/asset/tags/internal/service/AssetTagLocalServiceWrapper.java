/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.service;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(service = ServiceWrapper.class)
public class AssetTagLocalServiceWrapper
	extends com.liferay.asset.kernel.service.AssetTagLocalServiceWrapper {

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