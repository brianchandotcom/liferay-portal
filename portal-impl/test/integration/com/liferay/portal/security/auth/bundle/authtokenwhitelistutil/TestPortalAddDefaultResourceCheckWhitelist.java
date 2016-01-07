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

package com.liferay.portal.security.auth.bundle.authtokenwhitelistutil;

import com.liferay.portal.kernel.util.PropsKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = {
		PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST + "=" + TestPortalAddDefaultResourceCheckWhitelist.TEST_PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_URL,
		"service.ranking:Integer=" + Integer.MAX_VALUE
	},
	service = Object.class
)
public class TestPortalAddDefaultResourceCheckWhitelist {

	public static final String
		TEST_PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_URL =
			"TEST_PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_URL";

}