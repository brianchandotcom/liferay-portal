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

package com.liferay.subscription.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(category = "collaboration")
@Meta.OCD(
	id = "com.liferay.subscription.web.configuration.SubscriptionConfiguration",
	localization = "content/Language", name = "subscription-configuration-name"
)
public interface SubscriptionConfiguration {

	/**
	 * Set the interval in hours on how often
	 * DeleteExpiredTicketsMessageListener will run to check for expired tickets
	 * and delete them.
	 */
	@Meta.AD(deflt = "24", required = false)
	public int deleteExpiredTicketsInterval();

	/**
	 * Set the time in days when the unsubscription tickets will expire.
	 */
	@Meta.AD(deflt = "31", required = false)
	public int unsubscriptionTicketExpirationTime();

}