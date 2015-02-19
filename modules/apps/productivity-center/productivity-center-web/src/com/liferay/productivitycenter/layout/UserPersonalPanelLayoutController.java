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

package com.liferay.productivitycenter.layout;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypeController;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, property = { "layout.type=user_personal_panel" },
	service = LayoutTypeController.class
)
public class UserPersonalPanelLayoutController implements LayoutTypeController {

	@Override
	public String[] getConfigurationActionDelete() {
		return _NO_CONFIGURATION_ACTIONS;
	}

	@Override
	public String[] getConfigurationActionUpdate() {
		return _NO_CONFIGURATION_ACTIONS;
	}

	@Override
	public String getEditPage() {
		return StrutsUtil.TEXT_HTML_DIR + _EDIT_PAGE;
	}

	@Override
	public String getURL() {
		return _URL;
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		String portletId = ParamUtil.getString(request, "p_p_id");

		String path = getViewPath(portletId, BrowserSnifferUtil.isWap(request));

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		String contentType = pipingServletResponse.getContentType();

		requestDispatcher.include(request, pipingServletResponse);

		if (contentType != null) {
			response.setContentType(contentType);
		}

		request.setAttribute(
			WebKeys.LAYOUT_CONTENT, unsyncStringWriter.getStringBundler());

		return false;
	}

	@Override
	public boolean isFirstPageable() {
		return true;
	}

	@Override
	public boolean isParentable() {
		return false;
	}

	@Override
	public boolean isSitemapable() {
		return _SITEMAPABLE;
	}

	@Override
	public boolean isURLFriendliable() {
		return _URL_FRIENDLIABLE;
	}

	@Override
	public boolean matches(
		HttpServletRequest request, String friendlyURL, Layout layout) {

		try {
			Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

			Collection<String> values = friendlyURLMap.values();

			return values.contains(friendlyURL);
		}
		catch (SystemException e) {
			throw new RuntimeException(e);
		}
	}

	protected String getViewPath(String portletId, boolean wap) {
		if (wap) {
			return StrutsUtil.TEXT_WAP_DIR + _VIEW_PATH;
		}

		return StrutsUtil.TEXT_HTML_DIR + _VIEW_PATH;
	}

	private static final String _EDIT_PAGE =
		"/layout/edit/user_personal_panel.jsp";

	private static final String[] _NO_CONFIGURATION_ACTIONS = new String[0];

	private static final boolean _SITEMAPABLE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.LAYOUT_SITEMAPABLE), true);

	private static final String _URL = GetterUtil.getString(
		PropsUtil.get(PropsKeys.LAYOUT_URL));

	private static final boolean _URL_FRIENDLIABLE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.LAYOUT_URL_FRIENDLIABLE), true);

	private static final String _VIEW_PATH =
		"/layout/view/user_personal_panel.jsp";

}