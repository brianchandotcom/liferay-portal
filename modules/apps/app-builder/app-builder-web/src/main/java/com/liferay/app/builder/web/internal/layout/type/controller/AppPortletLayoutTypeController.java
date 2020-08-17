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

package com.liferay.app.builder.web.internal.layout.type.controller;

import com.liferay.layout.type.controller.BaseLayoutTypeControllerImpl;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gabriel Albuquerque
 */
public class AppPortletLayoutTypeController
	extends BaseLayoutTypeControllerImpl {

	public AppPortletLayoutTypeController(
		ServletContext servletContext, String appName,
		Map<Locale, String> appNameMap, String portletName) {

		this.servletContext = servletContext;

		_appName = appName;
		_appNameMap = appNameMap;
		_url = StringBundler.concat(
			"${liferay:mainPath}/portal/layout?p_l_id=${liferay:plid}&",
			"p_p_state=pop_up&appName=", appName, "&portletName=", portletName);
	}

	@Override
	public String getType() {
		return LayoutConstants.TYPE_PORTLET;
	}

	@Override
	public String getURL() {
		return _url;
	}

	@Override
	public boolean isBrowsable() {
		return false;
	}

	@Override
	public boolean isFirstPageable() {
		return true;
	}

	@Override
	public boolean isFullPageDisplayable() {
		return false;
	}

	@Override
	public boolean isInstanceable() {
		return false;
	}

	@Override
	public boolean isParentable() {
		return false;
	}

	@Override
	public boolean isSitemapable() {
		return false;
	}

	@Override
	public boolean isURLFriendliable() {
		return true;
	}

	@Override
	protected void addAttributes(HttpServletRequest httpServletRequest) {
		super.addAttributes(httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortalUtil.setPageTitle(
			_appNameMap.getOrDefault(themeDisplay.getLocale(), _appName),
			httpServletRequest);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #createServletResponse(HttpServletResponse,
	 *             UnsyncStringWriter)}
	 */
	@Deprecated
	@Override
	protected ServletResponse createServletResponse(
		HttpServletResponse httpServletResponse,
		com.liferay.portal.kernel.io.unsync.UnsyncStringWriter
			unsyncStringWriter) {

		return new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);
	}

	@Override
	protected ServletResponse createServletResponse(
		HttpServletResponse httpServletResponse,
		UnsyncStringWriter unsyncStringWriter) {

		return new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);
	}

	@Override
	protected String getEditPage() {
		return _VIEW_PAGE;
	}

	@Override
	protected String getViewPage() {
		return _VIEW_PAGE;
	}

	private static final String _VIEW_PAGE = "/layout/view.jsp";

	private final String _appName;
	private final Map<Locale, String> _appNameMap;
	private final String _url;

}