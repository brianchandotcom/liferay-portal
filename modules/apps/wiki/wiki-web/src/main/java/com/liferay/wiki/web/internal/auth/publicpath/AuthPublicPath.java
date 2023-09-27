/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.web.internal.auth.publicpath;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"auth.public.path=/wiki/edit_page",
		"auth.public.path=/wiki/edit_page_attachment",
		"auth.public.path=/wiki/edit_page_discussion",
		"auth.public.path=/wiki/find_page",
		"auth.public.path=/wiki/get_page_attachment",
		"auth.public.path=/wiki/rss"
	},
	service = Object.class
)
public class AuthPublicPath {
}