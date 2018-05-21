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