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

package com.liferay.sync.internal.servlet.filter;

import com.liferay.portal.servlet.filters.authverifier.AuthVerifierFilter;

import javax.servlet.Filter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(
	immediate = true,
	property = {
		"filter.init.auth.verifier.OAuthVerifier.urls.includes=/sync/download/*",
		"filter.init.auth.verifier.SyncAuthVerifier.urls.includes=/sync/download/*",
		"osgi.http.whiteboard.filter.name=com.liferay.sync.internal.servlet.filter.SyncAuthVerifierFilter",
		"osgi.http.whiteboard.filter.pattern=/sync/download/*"
	},
	service = Filter.class
)
public class SyncAuthVerifierFilter extends AuthVerifierFilter {
}