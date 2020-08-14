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

package com.liferay.frontend.taglib.clay.internal.servlet;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayViewSerializer;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.DataSetDisplayTag;
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
 */
@Component(service = {})
public class ServletContextUtil {

	public static final String getClayDataSetDisplaySettingsNamespace(
		HttpServletRequest httpServletRequest, String id) {

		StringBundler sb = new StringBundler(7);

		sb.append(DataSetDisplayTag.class.getName());
		sb.append(StringPool.POUND);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletNamespace = _portal.getPortletNamespace(
			portletDisplay.getId());

		sb.append(portletNamespace);

		sb.append(StringPool.POUND);
		sb.append(themeDisplay.getPlid());
		sb.append(StringPool.POUND);
		sb.append(id);

		return sb.toString();
	}

	public static final ClayDataSetDisplayViewSerializer
		getClayDataSetDisplayViewSerializer() {

		return _clayDataSetDisplayViewSerializer;
	}

	public static final ClayDataSetFilterSerializer
		getClayDataSetFilterSerializer() {

		return _clayDataSetFilterSerializer;
	}

	public static final String getContextPath() {
		return _servletContext.getContextPath();
	}

	public static final ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(unbind = "-")
	protected void setClayDataSetDisplayViewSerializer(
		ClayDataSetDisplayViewSerializer clayDataSetDisplayViewSerializer) {

		_clayDataSetDisplayViewSerializer = clayDataSetDisplayViewSerializer;
	}

	@Reference(unbind = "-")
	protected void setClayDataSetFilterSerializer(
		ClayDataSetFilterSerializer clayDataSetFilterSerializer) {

		_clayDataSetFilterSerializer = clayDataSetFilterSerializer;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.taglib.clay)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static ClayDataSetDisplayViewSerializer
		_clayDataSetDisplayViewSerializer;
	private static ClayDataSetFilterSerializer _clayDataSetFilterSerializer;
	private static Portal _portal;
	private static ServletContext _servletContext;

}