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

package com.liferay.sharepoint.soap.repository.constants;

import com.liferay.sharepoint.soap.repository.connector.SharepointConnection;

/**
 * @author Cristina González
 */
public class SharepointWSConstants {

	public static final String SHAREPOINT_2013_VALUE =
		SharepointConnection.ServerVersion.SHAREPOINT_2013.getValue();

	public static final String SHAREPOINT_CONFIGURATION_WS = "SHAREPOINT_WS";

	public static final String SHAREPOINT_LIBRARY_NAME = "LIBRARY_NAME";

	public static final String SHAREPOINT_LIBRARY_PATH = "LIBRARY_PATH";

	public static final String SHAREPOINT_SERVER_VERSION = "SERVER_VERSION";

	public static final String SHAREPOINT_SITE_URL = "SITE_URL";

}