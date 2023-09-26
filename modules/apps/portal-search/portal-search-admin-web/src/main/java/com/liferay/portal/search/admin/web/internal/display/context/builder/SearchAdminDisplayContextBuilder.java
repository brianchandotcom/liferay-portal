/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context.builder;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerClassNameComparator;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.search.admin.web.internal.display.context.SearchAdminDisplayContext;
import com.liferay.portal.search.index.IndexInformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayContextBuilder {

	public SearchAdminDisplayContextBuilder(
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

		searchAdminDisplayContext.setIndexersMap(getIndexersMap());
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

	protected Map<String, List<Indexer<?>>> getIndexersMap() {
		Set<Indexer<?>> indexersSet = IndexerRegistryUtil.getIndexers();

		if (SetUtil.isEmpty(indexersSet)) {
			return Collections.emptyMap();
		}

		Map<String, List<Indexer<?>>> indexersMap = new HashMap<>();

		for (Indexer<?> indexer :
				ListUtil.sort(
					new ArrayList<Indexer<?>>(indexersSet),
					new IndexerClassNameComparator(true))) {

			String key = "com.liferay.custom";

			try {
				Matcher matcher = _pattern.matcher(indexer.getClassName());

				matcher.find();

				key = matcher.group(1);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to categorize indexer " +
							indexer.getClassName(),
						exception);
				}
			}

			if (indexersMap.containsKey(key)) {
				List<Indexer<?>> indexers = new ArrayList<>(
					indexersMap.get(key));

				indexers.add(indexer);

				indexersMap.put(key, indexers);
			}
			else {
				indexersMap.put(key, ListUtil.fromArray(indexer));
			}
		}

		return new TreeMap<>(indexersMap);
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

	private static final Log _log = LogFactoryUtil.getLog(
		SearchAdminDisplayContextBuilder.class);

	private static final Pattern _pattern = Pattern.compile(
		"([\\w\\.]+)\\.model\\.[\\w\\.]+");

	private IndexInformation _indexInformation;
	private List<String> _indexReindexerClassNames;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}