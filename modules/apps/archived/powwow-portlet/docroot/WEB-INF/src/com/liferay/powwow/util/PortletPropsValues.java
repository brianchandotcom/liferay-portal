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

package com.liferay.powwow.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * @author Matthew Kong
 */
public class PortletPropsValues {

	public static final String POWWOW_INVITATION_EMAIL_BODY = PortletProps.get(
		PortletPropsKeys.POWWOW_INVITATION_EMAIL_BODY);

	public static final String POWWOW_INVITATION_EMAIL_SUBJECT =
		PortletProps.get(PortletPropsKeys.POWWOW_INVITATION_EMAIL_SUBJECT);

	public static final String POWWOW_INVITATION_GROUP_NAME = PortletProps.get(
		PortletPropsKeys.POWWOW_INVITATION_GROUP_NAME);

	public static final String POWWOW_INVITATION_LAYOUT_FRIENDLY_URL =
		PortletProps.get(
			PortletPropsKeys.POWWOW_INVITATION_LAYOUT_FRIENDLY_URL);

	public static final boolean POWWOW_INVITATION_LAYOUT_PRIVATE =
		GetterUtil.getBoolean(
			PortletProps.get(
				PortletPropsKeys.POWWOW_INVITATION_LAYOUT_PRIVATE));

	public static final int POWWOW_PROVIDER_API_RETRY_ATTEMPTS =
		GetterUtil.getInteger(
			PortletProps.get(
				PortletPropsKeys.POWWOW_PROVIDER_API_RETRY_ATTEMPTS));

	public static final int POWWOW_PROVIDER_API_RETRY_INTERVAL =
		GetterUtil.getInteger(
			PortletProps.get(
				PortletPropsKeys.POWWOW_PROVIDER_API_RETRY_INTERVAL));

	public static final String[] POWWOW_PROVIDER_TYPES = PortletProps.getArray(
		PortletPropsKeys.POWWOW_PROVIDER_TYPES);

	public static final String ZOOM_API_CALLBACK_LOGIN = PortletProps.get(
		PortletPropsKeys.ZOOM_API_CALLBACK_LOGIN);

	public static final String ZOOM_API_CALLBACK_PASSWORD = PortletProps.get(
		PortletPropsKeys.ZOOM_API_CALLBACK_PASSWORD);

}