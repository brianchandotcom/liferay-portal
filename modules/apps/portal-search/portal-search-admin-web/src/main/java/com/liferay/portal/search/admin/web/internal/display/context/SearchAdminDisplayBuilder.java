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

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.index.IndexInformation;

import java.util.List;
import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayBuilder {

	public SearchAdminDisplayBuilder(
		Language language, Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_language = language;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public SearchAdminDisplayContext build() {
		SearchAdminDisplayContext searchAdminDisplayContext =
			new SearchAdminDisplayContext();

		searchAdminDisplayContext.setIndexReindexerClassNames(
			_indexReindexerClassNames);

		NavigationItemList navigationItemList = new NavigationItemList();
		String selectedTab = getSelectedTab();

		_addNavigationItemList(navigationItemList, "connections", selectedTab);

		_addNavigationItemList(
			navigationItemList, "index-actions", selectedTab);

		if (_isIndexInformationAvailable()) {
			_addNavigationItemList(
				navigationItemList, "field-mappings", selectedTab);
		}

		searchAdminDisplayContext.setNavigationItemList(navigationItemList);
		searchAdminDisplayContext.setSelectedTab(selectedTab);

		return searchAdminDisplayContext;
	}

	public void setIndexInformation(IndexInformation indexInformation) {
		_indexInformation = indexInformation;
	}

	public void setIndexReindexerClassNames(
		List<String> indexReindexerClassNames) {

		_indexReindexerClassNames = indexReindexerClassNames;
	}

	protected String getSelectedTab() {
		String selectedTab = ParamUtil.getString(
			_renderRequest, "tabs1", "connections");

		if (!Objects.equals(selectedTab, "field-mappings") &&
			!Objects.equals(selectedTab, "index-actions") &&
			!Objects.equals(selectedTab, "connections")) {

			return "connections";
		}

		if (Objects.equals(selectedTab, "field-mappings") &&
			!_isIndexInformationAvailable()) {

			return "connections";
		}

		return selectedTab;
	}

	private void _addNavigationItemList(
		NavigationItemList navigationItemList, String label,
		String selectedTab) {

		navigationItemList.add(
			navigationItem -> {
				navigationItem.setActive(selectedTab.equals(label));
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "tabs1", label);
				navigationItem.setLabel(
					_language.get(
						_portal.getHttpServletRequest(_renderRequest), label));
			});
	}

	private boolean _isIndexInformationAvailable() {
		if (_indexInformation != null) {
			return true;
		}

		return false;
	}

	private IndexInformation _indexInformation;
	private List<String> _indexReindexerClassNames;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}