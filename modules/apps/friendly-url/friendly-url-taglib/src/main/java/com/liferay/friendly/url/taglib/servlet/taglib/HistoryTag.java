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

package com.liferay.friendly.url.taglib.servlet.taglib;

import com.liferay.friendly.url.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Adolfo Pérez
 */
public class HistoryTag extends IncludeTag {

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String getElementId() {
		return _elementId;
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public boolean isLocalizable() {
		return _localizable;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setElementId(String elementId) {
		_elementId = elementId;
	}

	public void setLocalizable(boolean localizable) {
		_localizable = localizable;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
		_disabled = false;
		_elementId = null;
		_localizable = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-friendly-url:history:defaultLanguageId",
			_getDefaultLanguageId(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-friendly-url:history:disabled", isDisabled());
		httpServletRequest.setAttribute(
			"liferay-friendly-url:history:elementId", getElementId());
		httpServletRequest.setAttribute(
			"liferay-friendly-url:history:friendlyURLEntryURL",
			_getFriendlyURLEntryURL(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-friendly-url:history:localizable", isLocalizable());
	}

	private String _getDefaultLanguageId(
		HttpServletRequest httpServletRequest) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			User user = themeDisplay.getDefaultUser();

			return user.getLanguageId();
		}
		catch (Exception exception) {
			return LanguageUtil.getLanguageId(LocaleUtil.getDefault());
		}
	}

	private String _getFriendlyURLEntryURL(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return StringBundler.concat(
			themeDisplay.getPortalURL(), PortalUtil.getPathContext(),
			Portal.PATH_MODULE, "/friendly-url/", getClassName(),
			StringPool.SLASH, getClassPK());
	}

	private static final String _PAGE = "/history/page.jsp";

	private String _className;
	private long _classPK;
	private boolean _disabled;
	private String _elementId;
	private boolean _localizable = true;

}