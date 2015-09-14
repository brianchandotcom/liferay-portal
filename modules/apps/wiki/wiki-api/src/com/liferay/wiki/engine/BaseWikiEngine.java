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

package com.liferay.wiki.engine;

import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiPage;

import java.io.IOException;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Iván Zaera
 */
public abstract class BaseWikiEngine implements WikiEngine {

	public static WikiPage getWikiPage(ServletRequest servletRequest) {
		return (WikiPage)servletRequest.getAttribute(_WIKI_PAGE);
	}

	@Override
	public String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException {

		return page.getContent();
	}

	@Override
	public String getFormatLabel(Locale locale) {
		try {
			Class<?> clazz = getClass();

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content/Language", locale, clazz.getClassLoader());

			return resourceBundle.getString(getFormat());
		}
		catch (MissingResourceException mre) {
			return getFormat();
		}
	}

	@Override
	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		return Collections.emptyMap();
	}

	@Override
	public String getToolbarSet() {
		return "creole";
	}

	@Override
	public void renderEditPage(
			ServletRequest servletRequest, ServletResponse servletResponse,
			WikiPage page)
		throws IOException, ServletException {

		ServletContext servletContext = getEditPageServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getEditPageJSP());

		servletRequest.setAttribute(_WIKI_PAGE, page);

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Override
	public boolean validate(long nodeId, String newContent) {
		return true;
	}

	protected String getEditPageJSP() {
		return "/edit_page.jsp";
	}

	protected abstract ServletContext getEditPageServletContext();

	private static final String _WIKI_PAGE =
		BaseWikiEngine.class.getName() + "#WIKI_PAGE";

}