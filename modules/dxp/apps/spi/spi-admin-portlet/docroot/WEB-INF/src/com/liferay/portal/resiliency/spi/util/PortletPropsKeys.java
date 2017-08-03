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

package com.liferay.portal.resiliency.spi.util;

/**
 * @author Michael C. Han
 */
public interface PortletPropsKeys {

	public static final String SPI_BLACKLIST_PORTLET_IDS =
		"spi.blacklist.portlet.ids";

	public static final String SPI_BLACKLIST_SERVLET_CONTEXT_NAMES =
		"spi.blacklist.servlet.context.names";

	public static final String SPI_NOTIFICATION_EMAIL_BODY =
		"spi.notification.email.body";

	public static final String SPI_NOTIFICATION_EMAIL_FROM_ADDRESS =
		"spi.notification.email.from.address";

	public static final String SPI_NOTIFICATION_EMAIL_FROM_NAME =
		"spi.notification.email.from.name";

	public static final String SPI_NOTIFICATION_EMAIL_SUBJECT =
		"spi.notification.email.subject";

	public static final String SPI_START_ON_PORTAL_STARTUP =
		"spi.start.on.portal.startup";

}