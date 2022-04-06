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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 * @author Marko Cikos
 */
@Component(service = {})
public class ServletContextUtil {

	public static String getContextPath() {
		return _servletContext.getContextPath();
	}

	public static FDSFilterSerializer getFDSFilterSerializer() {
		return _fdsFilterSerializer;
	}

	public static String getFDSSettingsNamespace(
		HttpServletRequest httpServletRequest, String id) {

		StringBundler sb = new StringBundler(6);

		sb.append("FDS£");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		sb.append(_portal.getPortletNamespace(portletDisplay.getId()));

		sb.append(StringPool.POUND);
		sb.append(themeDisplay.getPlid());
		sb.append(StringPool.POUND);
		sb.append(id);

		return sb.toString();
	}

	public static FDSViewSerializer getFDSViewSerializer() {
		return _fdsViewSerializer;
	}

	public static ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(unbind = "-")
	protected void setFDSFilterSerializer(
		FDSFilterSerializer fdsFilterSerializer) {

		_fdsFilterSerializer = fdsFilterSerializer;
	}

	@Reference(unbind = "-")
	protected void setFDSViewSerializer(
		FDSViewSerializer fdsDisplayViewSerializer) {

		_fdsViewSerializer = fdsDisplayViewSerializer;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.data.set.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static FDSFilterSerializer _fdsFilterSerializer;
	private static FDSViewSerializer _fdsViewSerializer;
	private static Portal _portal;
	private static ServletContext _servletContext;

}