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

package com.liferay.wiki.display.context;

import com.liferay.portal.kernel.display.context.DisplayContextFactory;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public interface WikiDisplayContextFactory extends DisplayContextFactory {

	public WikiEditPageDisplayContext getWikiEditPageDisplayContext(
		WikiEditPageDisplayContext parentWikiEditPageDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		WikiPage wikiPage);

	public WikiInfoPanelDisplayContext getWikiInfoPanelDisplayContext(
		WikiInfoPanelDisplayContext parentWikiInfoPanelDisplayContext,
		HttpServletRequest request, HttpServletResponse response);

	public WikiListPagesDisplayContext getWikiListPagesDisplayContext(
		WikiListPagesDisplayContext parentWikiListPagesDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		WikiNode wikiNode);

	public WikiViewPageDisplayContext getWikiViewPageDisplayContext(
		WikiViewPageDisplayContext parentWikiViewPageDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		WikiPage wikiPage);

}