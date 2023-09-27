/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(category = "community-tools")
@Meta.OCD(
	id = "com.liferay.subscription.web.internal.configuration.SubscriptionConfiguration",
	localization = "content/Language", name = "subscription-configuration-name"
)
public interface SubscriptionConfiguration {

	/**
	 * Set the interval in hours on how often to check for expired tickets and
	 * delete them.
	 */
	@Meta.AD(
		deflt = "24", name = "delete-expired-tickets-interval", required = false
	)
	public int deleteExpiredTicketsInterval();

	/**
	 * Set the time in days when the unsubscription tickets will expire.
	 */
	@Meta.AD(
		deflt = "31", name = "unsubscription-ticket-expiration-time",
		required = false
	)
	public int unsubscriptionTicketExpirationTime();

}