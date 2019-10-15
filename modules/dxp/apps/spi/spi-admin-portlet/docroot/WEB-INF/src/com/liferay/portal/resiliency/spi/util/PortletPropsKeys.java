/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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