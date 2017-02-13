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

package com.liferay.subscription.internal.service;

import com.liferay.message.boards.kernel.service.MBDiscussionLocalService;
import com.liferay.message.boards.kernel.service.MBDiscussionLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class SubscriptionMBDiscussionLocalServiceWrapper
	extends MBDiscussionLocalServiceWrapper {

	public SubscriptionMBDiscussionLocalServiceWrapper() {
		super(null);
	}

	public SubscriptionMBDiscussionLocalServiceWrapper(
		MBDiscussionLocalService mbDiscussionLocalService) {

		super(mbDiscussionLocalService);
	}

	@Override
	public void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		super.subscribeDiscussion(userId, groupId, className, classPK);

		_subscriptionLocalService.addSubscription(
			userId, groupId, className, classPK);
	}

	@Override
	public void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		super.unsubscribeDiscussion(userId, className, classPK);

		_subscriptionLocalService.deleteSubscription(
			userId, className, classPK);
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	private SubscriptionLocalService _subscriptionLocalService;

}