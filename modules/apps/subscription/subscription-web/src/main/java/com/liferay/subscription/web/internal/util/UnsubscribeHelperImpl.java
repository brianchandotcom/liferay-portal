/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.web.internal.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.subscription.util.UnsubscribeHelper;
import com.liferay.subscription.web.internal.configuration.SubscriptionConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.subscription.web.internal.configuration.SubscriptionConfiguration",
	service = UnsubscribeHelper.class
)
public class UnsubscribeHelperImpl implements UnsubscribeHelper {

	@Override
	public void registerHooks(SubscriptionSender subscriptionSender) {
		UnsubscribeHooks unsubscribeHooks = new UnsubscribeHooks(
			_subscriptionConfiguration, _ticketLocalService, _userLocalService,
			subscriptionSender);

		subscriptionSender.addHook(
			SubscriptionSender.Hook.Event.PERSISTED_SUBSCRIBER_FOUND,
			unsubscribeHooks::createUnsubscriptionTicket);

		subscriptionSender.addHook(
			SubscriptionSender.Hook.Event.MAIL_MESSAGE_CREATED,
			unsubscribeHooks::addUnsubscriptionLinks);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_subscriptionConfiguration = ConfigurableUtil.createConfigurable(
			SubscriptionConfiguration.class, properties);
	}

	private volatile SubscriptionConfiguration _subscriptionConfiguration;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}