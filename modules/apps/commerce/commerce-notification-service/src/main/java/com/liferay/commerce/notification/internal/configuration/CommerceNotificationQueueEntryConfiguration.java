/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(category = "orders")
@Meta.OCD(
	id = "com.liferay.commerce.notification.internal.configuration.CommerceNotificationQueueEntryConfiguration",
	localization = "content/Language",
	name = "commerce-notification-queue-entry-configuration-name"
)
public interface CommerceNotificationQueueEntryConfiguration {

	@Meta.AD(
		deflt = "15", name = "notification-queue-entry-check-interval",
		required = false
	)
	public int checkInterval();

	@Meta.AD(
		deflt = "43200", name = "notification-queue-entry-delete-interval",
		required = false
	)
	public int deleteInterval();

}