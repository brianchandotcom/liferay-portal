/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.auth.publicpath;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"auth.public.path=/blogs/find_entry", "auth.public.path=/blogs/rss",
		"auth.public.path=/blogs/trackback",
		"auth.public.path=/blogs_aggregator/rss"
	},
	service = Object.class
)
public class AuthPublicPath {
}