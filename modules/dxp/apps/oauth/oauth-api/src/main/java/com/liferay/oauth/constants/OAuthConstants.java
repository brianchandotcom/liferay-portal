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

package com.liferay.oauth.constants;

/**
 * @author Shinn Lok
 */
public class OAuthConstants {

	public static final String PUBLIC_PATH_ACCESS_TOKEN =
		"/portal/oauth/access_token";

	public static final String PUBLIC_PATH_AUTHORIZE =
		"/portal/oauth/authorize";

	public static final String PUBLIC_PATH_REQUEST_TOKEN =
		"/portal/oauth/request_token";

	public static final String[] PUBLIC_PATHS = {
		OAuthConstants.PUBLIC_PATH_ACCESS_TOKEN,
		OAuthConstants.PUBLIC_PATH_AUTHORIZE,
		OAuthConstants.PUBLIC_PATH_REQUEST_TOKEN
	};

}