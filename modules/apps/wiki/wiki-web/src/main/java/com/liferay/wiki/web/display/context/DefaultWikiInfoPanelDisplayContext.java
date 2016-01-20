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

package com.liferay.wiki.web.display.context;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.wiki.display.context.WikiInfoPanelDisplayContext;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.web.display.context.util.WikiInfoPanelRequestHelper;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class DefaultWikiInfoPanelDisplayContext
	implements WikiInfoPanelDisplayContext {

	public DefaultWikiInfoPanelDisplayContext(
		HttpServletRequest request, HttpServletResponse response) {

		_wikiInfoPanelRequestHelper = new WikiInfoPanelRequestHelper(request);
	}

	@Override
	public WikiNode getFirstNode() {
		List<WikiNode> nodes = _wikiInfoPanelRequestHelper.getNodes();

		if (nodes.isEmpty()) {
			return null;
		}

		return nodes.get(0);
	}

	@Override
	public WikiPage getFirstPage() {
		List<WikiPage> pages = _wikiInfoPanelRequestHelper.getPages();

		if (pages.isEmpty()) {
			return null;
		}

		return pages.get(0);
	}

	@Override
	public String getItemNameLabel() {
		if (_wikiInfoPanelRequestHelper.getNodeId() == 0) {
			return "wikis";
		}

		return "pages";
	}

	@Override
	public int getItemsCount() {
		WikiNode node = _wikiInfoPanelRequestHelper.getNode();

		if (node == null) {
			return WikiNodeLocalServiceUtil.getNodesCount(
				_wikiInfoPanelRequestHelper.getScopeGroupId());
		}

		return WikiPageLocalServiceUtil.getPagesCount(node.getNodeId(), true);
	}

	@Override
	public String getPageRSSURL(WikiPage page) {
		ThemeDisplay themeDisplay =
			_wikiInfoPanelRequestHelper.getThemeDisplay();

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathMain());
		sb.append("/wiki/rss?nodeId=");
		sb.append(_wikiInfoPanelRequestHelper.getNodeId());
		sb.append("&title=");
		sb.append(page.getTitle());

		return sb.toString();
	}

	@Override
	public int getSelectedItemsCount() {
		List<?> items = getSelectedItems();

		return items.size();
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isMultipleItemSelection() {
		List<?> items = getSelectedItems();

		if (items.size() > 1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isSingleNodeSelection() {
		List<WikiNode> nodes = _wikiInfoPanelRequestHelper.getNodes();

		if (nodes.size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isSinglePageSelection() {
		List<WikiPage> pages = _wikiInfoPanelRequestHelper.getPages();

		if (pages.size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	protected List<?> getSelectedItems() {
		if (_wikiInfoPanelRequestHelper.getNodeId() != 0) {
			return _wikiInfoPanelRequestHelper.getPages();
		}

		return _wikiInfoPanelRequestHelper.getNodes();
	}

	private static final UUID _UUID = UUID.fromString(
		"7099F1F8-ED73-47D8-9CDC-ED292BF7779F");

	private final WikiInfoPanelRequestHelper _wikiInfoPanelRequestHelper;

}