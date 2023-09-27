/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.type;

import com.liferay.commerce.constants.CommerceSubscriptionNotificationConstants;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_RENEWED,
		"commerce.notification.type.order:Integer=200"
	},
	service = CommerceNotificationType.class
)
public class SubscriptionRenewedCommerceNotificationTypeImpl
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
		return CommerceSubscriptionNotificationConstants.SUBSCRIPTION_RENEWED;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale,
			CommerceSubscriptionNotificationConstants.SUBSCRIPTION_RENEWED);
	}

	@Reference
	private Language _language;

}