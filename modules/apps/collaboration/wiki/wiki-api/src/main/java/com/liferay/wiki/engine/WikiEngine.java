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

import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Jorge Ferrer
 */
public interface WikiEngine {

	/**
	 * Returns the content of the given page converted to HTML using the view
	 * and edit URLs to build links.
	 *
	 * @param  page the wiki page
	 * @param  viewPageURL the URL to view the page
	 * @param  editPageURL the URL to edit the page
	 * @param  attachmentURLPrefix the URL prefix to use for attachments to the
	 *         page
	 * @return the content of the given page converted to HTML
	 */
	public String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException;

	public String getFormat();

	public String getFormatLabel(Locale locale);

	/**
	 * Returns a map of the links included in the given page. The key of each
	 * map entry is the title of the linked page. The value is a Boolean object
	 * that indicates if the linked page exists or not.
	 *
	 * @param  page the page
	 * @return a map of links included in the given page
	 */
	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException;

	public String getToolbarSet();

	public void renderEditPage(
			ServletRequest servletRequest, ServletResponse servletResponse,
			WikiNode node, WikiPage page)
		throws IOException, ServletException;

	/**
	 * Returns <code>true</code> if the content of a wiki page for this engine
	 * is valid.
	 *
	 * @param  nodeId the ID of the wiki page node
	 * @param  content the page content
	 * @return <code>true</code> if the content of a wiki page for this engine
	 *         is valid; <code>false</code> otherwise
	 */
	public boolean validate(long nodeId, String content);

}