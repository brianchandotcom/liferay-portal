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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.portlet.PortletProps;

import java.util.Arrays;

/**
 * @author Michael C. Han
 */
public class PortletPropsValues {

	public static final String[] SPI_BLACKLIST_PORTLET_IDS =
		PortletProps.getArray(PortletPropsKeys.SPI_BLACKLIST_PORTLET_IDS);

	public static final String[] SPI_BLACKLIST_SERVLET_CONTEXT_NAMES =
		PortletProps.getArray(
			PortletPropsKeys.SPI_BLACKLIST_SERVLET_CONTEXT_NAMES);

	public static final String SPI_NOTIFICATION_EMAIL_BODY =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.SPI_NOTIFICATION_EMAIL_BODY));

	public static final String SPI_NOTIFICATION_EMAIL_FROM_ADDRESS =
		GetterUtil.getString(
			PortletProps.get(
				PortletPropsKeys.SPI_NOTIFICATION_EMAIL_FROM_ADDRESS));

	public static final String SPI_NOTIFICATION_EMAIL_FROM_NAME =
		GetterUtil.getString(
			PortletProps.get(
				PortletPropsKeys.SPI_NOTIFICATION_EMAIL_FROM_NAME));

	public static final String SPI_NOTIFICATION_EMAIL_SUBJECT =
		GetterUtil.getString(
			PortletProps.get(PortletPropsKeys.SPI_NOTIFICATION_EMAIL_SUBJECT));

	static {
		Arrays.sort(SPI_BLACKLIST_PORTLET_IDS);
		Arrays.sort(SPI_BLACKLIST_SERVLET_CONTEXT_NAMES);
	}

}