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

package com.liferay.commerce.payment.internal.notification.type;

import com.liferay.commerce.constants.CommerceSubscriptionNotificationConstants;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_ACTIVATED,
		"commerce.notification.type.order:Integer=210"
	},
	service = CommerceNotificationType.class
)
public class SubscriptionActivatedCommerceNotificationTypeImpl
	implements CommerceNotificationType {

	@Override
	public String getClassName(Object object) {
		if (!(object instanceof CommerceSubscriptionEntry)) {
			return null;
		}

		return CommerceSubscriptionEntry.class.getName();
	}

	@Override
	public long getClassPK(Object object) {
		if (!(object instanceof CommerceSubscriptionEntry)) {
			return 0;
		}

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			(CommerceSubscriptionEntry)object;

		return commerceSubscriptionEntry.getCommerceSubscriptionEntryId();
	}

	@Override
	public String getKey() {
		return CommerceSubscriptionNotificationConstants.SUBSCRIPTION_ACTIVATED;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale,
			CommerceSubscriptionNotificationConstants.SUBSCRIPTION_ACTIVATED);
	}

	@Reference
	private Language _language;

}