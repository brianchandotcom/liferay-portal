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

package com.liferay.frontend.data.set.taglib.internal.servlet;

import com.liferay.frontend.data.set.filter.FDSFilterSerializer;
import com.liferay.frontend.data.set.view.FDSViewSerializer;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Chema Balsas
 * @author Marko Cikos
 */
public class ServletContextUtil {

	public static FDSFilterSerializer getFDSFilterSerializer() {
		return _fdsFilterSerializerSnapshot.get();
	}

	public static String getFDSSettingsNamespace(
		HttpServletRequest httpServletRequest, String id) {

		StringBundler sb = new StringBundler(6);

		sb.append("FDS£");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		Portal portal = _portalSnapshot.get();

		sb.append(portal.getPortletNamespace(portletDisplay.getId()));

		sb.append(StringPool.POUND);
		sb.append(themeDisplay.getPlid());
		sb.append(StringPool.POUND);
		sb.append(id);

		return sb.toString();
	}

	public static FDSViewSerializer getFDSViewSerializer() {
		return _fdsViewSerializerSnapshot.get();
	}

	public static ServletContext getServletContext() {
		return _servletContextSnapshot.get();
	}

	private static final Snapshot<FDSFilterSerializer>
		_fdsFilterSerializerSnapshot = new Snapshot<>(
			ServletContextUtil.class, FDSFilterSerializer.class);
	private static final Snapshot<FDSViewSerializer>
		_fdsViewSerializerSnapshot = new Snapshot<>(
			ServletContextUtil.class, FDSViewSerializer.class);
	private static final Snapshot<Portal> _portalSnapshot = new Snapshot<>(
		ServletContextUtil.class, Portal.class);
	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			ServletContextUtil.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.frontend.data.set.taglib)");

}