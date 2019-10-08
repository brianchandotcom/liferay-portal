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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.Subscription;
import com.liferay.headless.admin.user.resource.v1_0.SubscriptionResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/subscription.properties",
	scope = ServiceScope.PROTOTYPE, service = SubscriptionResource.class
)
public class SubscriptionResourceImpl extends BaseSubscriptionResourceImpl {

	@Override
	public void deleteMyUserAccountSubscription(Long subscriptionId)
		throws Exception {

		_subscriptionLocalService.deleteSubscription(subscriptionId);
	}

	@Override
	public Subscription getMyUserAccountSubscription(Long subscriptionId)
		throws Exception {

		return _toSubscription(
			_subscriptionLocalService.getSubscription(subscriptionId));
	}

	@Override
	public Page<Subscription> getMyUserAccountSubscriptionsPage(
		String contentType, Pagination pagination) {

		long userId = contextUser.getUserId();

		if (contentType != null) {
			return Page.of(
				transform(
					_subscriptionLocalService.getUserSubscriptions(
						userId, contentType),
					this::_toSubscription));
		}

		return Page.of(
			transform(
				_subscriptionLocalService.getUserSubscriptions(
					userId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toSubscription),
			pagination,
			_subscriptionLocalService.getUserSubscriptionsCount(userId));
	}

	private Subscription _toSubscription(
		com.liferay.subscription.model.Subscription subscription) {

		return new Subscription() {
			{
				contentId = subscription.getClassPK();
				contentType = subscription.getClassName();
				dateCreated = subscription.getCreateDate();
				dateModified = subscription.getModifiedDate();
				frequency = subscription.getFrequency();
				id = subscription.getSubscriptionId();
				siteId = subscription.getGroupId();
			}
		};
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}