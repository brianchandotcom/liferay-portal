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

package com.liferay.oauth.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Shinn Lok
 */
public class OAuthConfigurationValues {

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final String OAUTH_CLASS_NAME = GetterUtil.getString(
		OAuthConfigurationUtil.get("oauth.class.name"));

	public static final String OAUTH_REALM = GetterUtil.getString(
		OAuthConfigurationUtil.get("oauth.realm"));

}