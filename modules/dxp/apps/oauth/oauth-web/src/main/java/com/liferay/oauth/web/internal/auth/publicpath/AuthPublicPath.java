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

package com.liferay.oauth.web.internal.auth.publicpath;

import com.liferay.oauth.constants.OAuthConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(
	immediate = true,
	property = {
		"auth.public.path=" + OAuthConstants.PUBLIC_PATH_ACCESS_TOKEN,
		"auth.public.path=" + OAuthConstants.PUBLIC_PATH_AUTHORIZE,
		"auth.public.path=" + OAuthConstants.PUBLIC_PATH_REQUEST_TOKEN
	},
	service = Object.class
)
public class AuthPublicPath {
}