/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.email.provider;

import com.liferay.notification.context.NotificationContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Carolina Barbosa
 */
public class SubscribersEmailProvider implements EmailProvider {

	public SubscribersEmailProvider(
		SubscriptionLocalService subscriptionLocalService,
		UserLocalService userLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public String provide(NotificationContext notificationContext, Object value)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-42577")) {
			return null;
		}

		Set<String> emailAddresses = new HashSet<>();

		for (Subscription subscription :
				_subscriptionLocalService.getSubscriptions(
					notificationContext.getCompanyId(),
					notificationContext.getClassName(),
					notificationContext.getClassPK())) {

			User user = _userLocalService.fetchUser(subscription.getUserId());

			emailAddresses.add(user.getEmailAddress());
		}

		return StringUtil.merge(emailAddresses);
	}

	private final SubscriptionLocalService _subscriptionLocalService;
	private final UserLocalService _userLocalService;

}