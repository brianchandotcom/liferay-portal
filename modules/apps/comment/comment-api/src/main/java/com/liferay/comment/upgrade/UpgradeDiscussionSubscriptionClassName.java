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

package com.liferay.comment.upgrade;

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.List;

/**
 * @author Roberto Díaz
 */
public class UpgradeDiscussionSubscriptionClassName extends UpgradeProcess {

	public UpgradeDiscussionSubscriptionClassName(
		SubscriptionLocalService subscriptionLocalService,
		String oldSubscriptionClassName, DeletionMode deletionMode) {

		_subscriptionLocalService = subscriptionLocalService;
		_oldSubscriptionClassName = oldSubscriptionClassName;
		_deletionMode = deletionMode;
	}

	public enum DeletionMode {

		ADD_NEW, DELETE_OLD

	}

	@Override
	protected void doUpgrade() throws Exception {
		_addSubscriptions();

		if (_deletionMode == DeletionMode.DELETE_OLD) {
			_deleteSubscriptions();
		}
	}

	private void _addSubscriptions() throws PortalException {
		String newSubscriptionClassName =
			MBDiscussion.class.getName() + StringPool.UNDERLINE +
				_oldSubscriptionClassName;

		int count = _subscriptionLocalService.getSubscriptionsCount(
			newSubscriptionClassName);

		if (count > 0) {
			return;
		}

		List<Subscription> subscriptions =
			_subscriptionLocalService.getSubscriptions(
				_oldSubscriptionClassName);

		for (Subscription subscription : subscriptions) {
			_subscriptionLocalService.addSubscription(
				subscription.getUserId(), subscription.getGroupId(),
				newSubscriptionClassName, subscription.getClassPK());
		}
	}

	private void _deleteSubscriptions() throws PortalException {
		List<Subscription> subscriptions =
			_subscriptionLocalService.getSubscriptions(
				_oldSubscriptionClassName);

		for (Subscription subscription : subscriptions) {
			_subscriptionLocalService.deleteSubscription(
				subscription.getSubscriptionId());
		}
	}

	private final DeletionMode _deletionMode;
	private final String _oldSubscriptionClassName;
	private final SubscriptionLocalService _subscriptionLocalService;

}