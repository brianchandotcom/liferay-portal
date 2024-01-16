/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.display.page;

import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;

/**
 * @author Mikel Lorza
 */
public abstract class BaseLayoutDisplayPageProvider<T>
	implements LayoutDisplayPageProvider<T> {

	@Override
	public String getURLSeparator() {
		FriendlyURLResolver friendlyURLResolver =
			FriendlyURLResolverRegistryUtil.
				getFriendlyURLResolverByDefaultURLSeparator(
					getDefaultURLSeparator());

		if (friendlyURLResolver == null) {
			return getDefaultURLSeparator();
		}

		return friendlyURLResolver.getURLSeparator();
	}

}