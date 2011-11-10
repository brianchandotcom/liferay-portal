/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.facebook;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;

import javax.portlet.PortletRequest;

/**
 * @author Wilson Man
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
public interface FacebookConnect {

	public String getAccessToken(long companyId, String redirect, String code)
		throws SystemException;

	public String getAccessTokenURL(long companyId) throws SystemException;

	public String getAppId(long companyId) throws SystemException;

	public String getAppSecret(long companyId) throws SystemException;

	public String getAuthURL(long companyId) throws SystemException;

	public JSONObject getGraphResources(
		long companyId, String path, String accessToken, String fields);

	public String getGraphURL(long companyId) throws SystemException;

	public String getProfileImageURL(PortletRequest portletRequest);

	public String getRedirectURL(long companyId) throws SystemException;

	public boolean isEnabled(long companyId) throws SystemException;

}