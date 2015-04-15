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

package com.liferay.portlet.layoutsadmin.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * @author Raymond Augé
 */
public class SitemapUtil {

	public static String encodeXML(String input) {
		return getSitemap().encodeXML(input);
	}

	public static Sitemap getSitemap() {
		PortalRuntimePermission.checkGetBeanProperty(SitemapUtil.class);

		return _sitemap;
	}

	public static String getSitemap(long groupId, ThemeDisplay themeDisplay)
		throws PortalException {

		return getSitemap().getSitemap(groupId, themeDisplay);
	}

	public void setSitemap(Sitemap sitemap) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_sitemap = sitemap;
	}

	private static Sitemap _sitemap;

}