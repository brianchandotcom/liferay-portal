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

package com.liferay.productivity.center.layout;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypeController;
import com.liferay.portal.util.WebKeys;
import com.liferay.productivity.center.constants.ProductivityCenterWebKeys;
import com.liferay.productivity.center.panel.PanelAppRegistry;
import com.liferay.productivity.center.panel.PanelCategoryRegistry;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"layout.type=" + LayoutConstants.TYPE_USER_PERSONAL_PANEL},
	service = LayoutTypeController.class
)
public class UserPersonalPanelLayoutController implements LayoutTypeController {

	@Override
	public String[] getConfigurationActionDelete() {
		return StringPool.EMPTY_ARRAY;
	}

	@Override
	public String[] getConfigurationActionUpdate() {
		return StringPool.EMPTY_ARRAY;
	}

	@Override
	public String getURL() {
		return _URL;
	}

	@Override
	public String includeEditContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(getEditPage());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		try {
			setPanelEntryRegistries(request);

			requestDispatcher.include(request, pipingServletResponse);
		}
		finally {
			removePanelEntryRegistries(request);
		}

		return unsyncStringWriter.toString();
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(_VIEW_PATH);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		String contentType = pipingServletResponse.getContentType();

		try {
			setPanelEntryRegistries(request);

			requestDispatcher.include(request, pipingServletResponse);
		}
		finally {
			removePanelEntryRegistries(request);
		}

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

	protected String getEditPage() {
		return _EDIT_PAGE;
	}

	protected void removePanelEntryRegistries(HttpServletRequest request) {
		request.removeAttribute(ProductivityCenterWebKeys.PANEL_APP_REGISTRY);
		request.removeAttribute(
			ProductivityCenterWebKeys.PANEL_CATEGORY_REGISTRY);
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	@Reference(unbind = "-")
	protected void setPanelCategoryRegistry(
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelCategoryRegistry = panelCategoryRegistry;
	}

	protected void setPanelEntryRegistries(HttpServletRequest request) {
		request.setAttribute(
			ProductivityCenterWebKeys.PANEL_APP_REGISTRY, _panelAppRegistry);
		request.setAttribute(
			ProductivityCenterWebKeys.PANEL_CATEGORY_REGISTRY,
			_panelCategoryRegistry);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.productivity.center.web)"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final String _EDIT_PAGE =
		"/layout/edit/user_personal_panel.jsp";

	private static final boolean _SITEMAPABLE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.LAYOUT_SITEMAPABLE), true);

	private static final String _URL = GetterUtil.getString(
		PropsUtil.get(PropsKeys.LAYOUT_URL));

	private static final boolean _URL_FRIENDLIABLE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.LAYOUT_URL_FRIENDLIABLE), true);

	private static final String _VIEW_PATH =
		"/layout/view/user_personal_panel.jsp";

	private PanelAppRegistry _panelAppRegistry;
	private PanelCategoryRegistry _panelCategoryRegistry;
	private ServletContext _servletContext;

}