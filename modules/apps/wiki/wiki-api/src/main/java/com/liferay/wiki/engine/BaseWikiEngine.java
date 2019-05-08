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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.LanguageResources;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import java.io.IOException;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public abstract class BaseWikiEngine implements WikiEngine {

	public static BaseWikiEngine getBaseWikiEngine(
		ServletRequest servletRequest) {

		return (BaseWikiEngine)servletRequest.getAttribute(_BASE_WIKI_ENGINE);
	}

	public static WikiNode getWikiNode(ServletRequest servletRequest) {
		return (WikiNode)servletRequest.getAttribute(_WIKI_NODE);
	}

	public static WikiPage getWikiPage(ServletRequest servletRequest) {
		return (WikiPage)servletRequest.getAttribute(_WIKI_PAGE);
	}

	@Override
	public String convert(
		WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
		String attachmentURLPrefix) {

		return page.getContent();
	}

	public abstract String getEditorName();

	@Override
	public String getFormatLabel(Locale locale) {
		ResourceBundleLoader resourceBundleLoader = getResourceBundleLoader();

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		String format = getFormat();

		String formatLabel = ResourceBundleUtil.getString(
			resourceBundle, format);

		if (formatLabel != null) {
			return formatLabel;
		}

		return format;
	}

	public String getHelpPageHTML(PageContext pageContext)
		throws IOException, ServletException {

		if (!isHelpPageDefined()) {
			return StringPool.BLANK;
		}

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)pageContext.getResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);

		ServletContext servletContext = getHelpPageServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getHelpPageJSP());

		requestDispatcher.include(
			pageContext.getRequest(), pipingServletResponse);

		StringBundler sb = unsyncStringWriter.getStringBundler();

		return sb.toString();
	}

	public String getHelpPageTitle(HttpServletRequest httpServletRequest) {
		return LanguageUtil.format(
			httpServletRequest, "x-syntax-help",
			getFormatLabel(httpServletRequest.getLocale()), false);
	}

	public abstract String getHelpURL();

	@Override
	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		return Collections.emptyMap();
	}

	@Override
	public String getToolbarSet() {
		return "creole";
	}

	public boolean isHelpPageDefined() {
		if ((getHelpPageServletContext() == null) ||
			Validator.isNull(getHelpPageJSP())) {

			return false;
		}

		return true;
	}

	@Override
	public void renderEditPage(
			ServletRequest servletRequest, ServletResponse servletResponse,
			WikiNode node, WikiPage page)
		throws IOException, ServletException {

		ServletContext servletContext = getEditPageServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getEditPageJSP());

		servletRequest.setAttribute(_BASE_WIKI_ENGINE, this);
		servletRequest.setAttribute(_WIKI_NODE, node);
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

	protected String getHelpPageJSP() {
		return "/help_page.jsp";
	}

	protected abstract ServletContext getHelpPageServletContext();

	protected ResourceBundleLoader getResourceBundleLoader() {
		return LanguageResources.RESOURCE_BUNDLE_LOADER;
	}

	private static final String _BASE_WIKI_ENGINE =
		BaseWikiEngine.class.getName() + "#BASE_WIKI_ENGINE";

	private static final String _WIKI_NODE =
		BaseWikiEngine.class.getName() + "#WIKI_NODE";

	private static final String _WIKI_PAGE =
		BaseWikiEngine.class.getName() + "#WIKI_PAGE";

}