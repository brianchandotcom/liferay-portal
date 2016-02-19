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

package com.liferay.wiki.web.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class WikiPageInfoPanelRequestHelper extends BaseRequestHelper {

	public WikiPageInfoPanelRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public WikiNode getCurrentNode() {
		HttpServletRequest request = getRequest();

		WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

		if (node == null) {
			WikiPage page = getPage();

			if (page != null) {
				return page.getNode();
			}
		}

		return node;
	}

	public WikiPage getPage() {
		if (_page != null) {
			return _page;
		}

		HttpServletRequest request = getRequest();

		_page = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

		return _page;
	}

	public List<WikiPage> getPages() {
		if (_pages != null) {
			return _pages;
		}

		HttpServletRequest request = getRequest();

		_pages = (List<WikiPage>)request.getAttribute(WikiWebKeys.WIKI_PAGES);

		if (_pages == null) {
			_pages = Collections.emptyList();
		}

		return _pages;
	}

	public boolean isShowSidebarHeader() {
		HttpServletRequest request = getRequest();

		boolean showSidebarHeader = GetterUtil.getBoolean(
			request.getAttribute(WikiWebKeys.SHOW_SIDEBAR_HEADER));

		return ParamUtil.getBoolean(
			request, "showSidebarHeader", showSidebarHeader);
	}

	private WikiPage _page;
	private List<WikiPage> _pages;

}