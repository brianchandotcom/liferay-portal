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

package com.liferay.wiki.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.web.internal.display.context.helper.WikiPageInfoPanelRequestHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class WikiPageInfoPanelDisplayContext {

	public WikiPageInfoPanelDisplayContext(
		HttpServletRequest httpServletRequest) {

		_wikiPageInfoPanelRequestHelper = new WikiPageInfoPanelRequestHelper(
			httpServletRequest);
	}

	public WikiPage getFirstPage() {
		List<WikiPage> pages = _wikiPageInfoPanelRequestHelper.getPages();

		if (pages.isEmpty()) {
			return _wikiPageInfoPanelRequestHelper.getPage();
		}

		return pages.get(0);
	}

	public String getPageRSSURL(WikiPage page) {
		ThemeDisplay themeDisplay =
			_wikiPageInfoPanelRequestHelper.getThemeDisplay();

		return StringBundler.concat(
			themeDisplay.getPathMain(), "/wiki/rss?nodeId=", page.getNodeId(),
			"&title=", page.getTitle());
	}

	public int getPagesCount() {
		WikiNode node = _wikiPageInfoPanelRequestHelper.getCurrentNode();

		return WikiPageLocalServiceUtil.getPagesCount(node.getNodeId(), true);
	}

	public int getSelectedPagesCount() {
		List<?> items = _getSelectedPages();

		return items.size();
	}

	public boolean isMultiplePageSelection() {
		List<?> items = _getSelectedPages();

		if (items.size() > 1) {
			return true;
		}

		return false;
	}

	public boolean isShowSidebarHeader() {
		return _wikiPageInfoPanelRequestHelper.isShowSidebarHeader();
	}

	public boolean isSinglePageSelection() {
		List<WikiPage> pages = _wikiPageInfoPanelRequestHelper.getPages();

		if (pages.size() == 1) {
			return true;
		}

		WikiPage page = _wikiPageInfoPanelRequestHelper.getPage();

		if (page != null) {
			return true;
		}

		return false;
	}

	private List<WikiPage> _getSelectedPages() {
		return _wikiPageInfoPanelRequestHelper.getPages();
	}

	private final WikiPageInfoPanelRequestHelper
		_wikiPageInfoPanelRequestHelper;

}