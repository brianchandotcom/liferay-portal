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

package com.liferay.portal.security.sso.google;

import com.liferay.portal.kernel.model.User;

import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * @author Stian Sigvartsen
 */
public interface GoogleAuthorization {

	public User addOrUpdateUser(
			HttpSession session, long companyId, String authorizationCode,
			String returnRequestUri, List<String> scopes)
		throws Exception;

	public String getLoginRedirect(
			long companyId, String returnRequestUri, List<String> scopes)
		throws Exception;

	public boolean isEnabled(long companyId);

}