/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Murilo Stodolni
 */
@ExtendedObjectClassDefinition(
	category = "notifications",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	description = "notification-queue-configuration-description",
	id = "com.liferay.notification.internal.configuration.NotificationQueueConfiguration",
	localization = "content/Language",
	name = "notification-queue-configuration-name"
)
public interface NotificationQueueConfiguration {

	@Meta.AD(
		deflt = "43200",
		description = "notification-queue-delete-interval-description",
		min = "1", name = "notification-queue-entry-delete-interval",
		required = false, type = Meta.Type.Integer
	)
	public int deleteInterval();

}