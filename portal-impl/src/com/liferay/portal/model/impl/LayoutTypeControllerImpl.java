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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public class LayoutTypeControllerImpl implements LayoutTypeController {

	public LayoutTypeControllerImpl(String type) {
		_type = type;

		Filter filter = new Filter(type);

		_browsable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_BROWSABLE, filter), true);
		_configurationActionDelete = StringUtil.split(
			GetterUtil.getString(
				PropsUtil.get(
					PropsKeys.LAYOUT_CONFIGURATION_ACTION_DELETE, filter)));
		_configurationActionUpdate = StringUtil.split(
			GetterUtil.getString(
				PropsUtil.get(
					PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE, filter)));
		_editPage = GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_EDIT_PAGE, filter));
		_firstPageable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_FIRST_PAGEABLE, filter));
		_fullPageDisplayable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.FULL_PAGE_DISPLAYABLE, filter));
		_parentable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_PARENTABLE, filter), true);
		_sitemapable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_SITEMAPABLE, filter), true);
		_url = GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_URL, filter));
		_urlFriendliable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_URL_FRIENDLIABLE, filter), true);
		_viewPage =
			StrutsUtil.TEXT_HTML_DIR +
				GetterUtil.getString(
					PropsUtil.get(PropsKeys.LAYOUT_VIEW_PAGE, filter));
	}

	@Override
	public String[] getConfigurationActionDelete() {
		return _configurationActionDelete;
	}

	@Override
	public String[] getConfigurationActionUpdate() {
		return _configurationActionUpdate;
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getURL() {
		return _url;
	}

	public String getViewPath(String portletId) {

		// Manually check the p_p_id. See LEP-1724.

		if (Validator.isNull(portletId)) {
			return _viewPage;
		}

		if (_type.equals(LayoutConstants.TYPE_PANEL)) {
			return StrutsUtil.TEXT_HTML_DIR + "/portal/layout/view/panel.jsp";
		}

		return StrutsUtil.TEXT_HTML_DIR + "/portal/layout/view/portlet.jsp";
	}

	@Override
	public String includeEditContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		request.setAttribute(WebKeys.SEL_LAYOUT, layout);

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				servletContext, getEditPage());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		requestDispatcher.include(request, pipingServletResponse);

		return unsyncStringWriter.toString();
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		String portletId = ParamUtil.getString(request, "p_p_id");

		String path = getViewPath(portletId);

		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				servletContext, path);

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
	public boolean isBrowsable() {
		return _browsable;
	}

	@Override
	public boolean isCheckLayoutViewPermission() {
		return true;
	}

	@Override
	public boolean isFirstPageable() {
		return _firstPageable;
	}

	@Override
	public boolean isFullPageDisplayable() {
		return _fullPageDisplayable;
	}

	@Override
	public boolean isInstanceable() {
		return true;
	}

	@Override
	public boolean isParentable() {
		return _parentable;
	}

	@Override
	public boolean isSitemapable() {
		return _sitemapable;
	}

	@Override
	public boolean isURLFriendliable() {
		return _urlFriendliable;
	}

	@Override
	public boolean matches(
		HttpServletRequest request, String friendlyURL, Layout layout) {

		try {
			Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

			Collection<String> values = friendlyURLMap.values();

			return values.contains(friendlyURL);
		}
		catch (SystemException se) {
			throw new RuntimeException(se);
		}
	}

	protected String getEditPage() {
		return StrutsUtil.TEXT_HTML_DIR + _editPage;
	}

	private final boolean _browsable;
	private final String[] _configurationActionDelete;
	private final String[] _configurationActionUpdate;
	private final String _editPage;
	private final boolean _firstPageable;
	private final boolean _fullPageDisplayable;
	private final boolean _parentable;
	private final boolean _sitemapable;
	private final String _type;
	private final String _url;
	private final boolean _urlFriendliable;
	private final String _viewPage;

}