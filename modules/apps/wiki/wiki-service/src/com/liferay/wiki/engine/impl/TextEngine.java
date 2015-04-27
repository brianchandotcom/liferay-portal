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

package com.liferay.wiki.engine.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.wiki.engine.BaseWikiEngine;
import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.model.WikiPage;

import java.io.IOException;

import java.util.Collections;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(service = WikiEngine.class)
public class TextEngine extends BaseWikiEngine {

	@Override
	public String convert(
		WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
		String attachmentURLPrefix) {

		if (page.getContent() == null) {
			return StringPool.BLANK;
		}
		else {
			return "<pre>" + page.getContent() + "</pre>";
		}
	}

	@Override
	public String getFormat() {
		return "plain_text";
	}

	@Override
	public Map<String, Boolean> getOutgoingLinks(WikiPage page) {
		return Collections.emptyMap();
	}

	@Override
	public void renderEditPage(
			ServletRequest servletRequest, ServletResponse servletResponse,
			WikiPage wikiPage)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			servletRequest.getRequestDispatcher(
				"/o/wiki-web/html/portlet/wiki/edit/plain_text.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

}