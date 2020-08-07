/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.rankings.web.internal.index.DocumentToRankingTranslator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingFields;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.request.SearchRankingRequest;
import com.liferay.portal.search.tuning.rankings.web.internal.request.SearchRankingResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 */
public class RankingPortletDisplayBuilder {

	public RankingPortletDisplayBuilder(
		DocumentToRankingTranslator documentToRankingTranslator,
		HttpServletRequest httpServletRequest, Language language, Portal portal,
		Queries queries, RankingIndexNameBuilder rankingIndexNameBuilder,
		Sorts sorts, RenderRequest renderRequest, RenderResponse renderResponse,
		SearchEngineAdapter searchEngineAdapter,
		SearchEngineInformation searchEngineInformation) {

		_documentToRankingTranslator = documentToRankingTranslator;
		_httpServletRequest = httpServletRequest;
		_language = language;
		_portal = portal;
		_queries = queries;
		_rankingIndexNameBuilder = rankingIndexNameBuilder;
		_sorts = sorts;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_searchEngineAdapter = searchEngineAdapter;
		_searchEngineInformation = searchEngineInformation;
	}

	public RankingPortletDisplayContext build() {
		RankingPortletDisplayContext rankingPortletDisplayContext =
			new RankingPortletDisplayContext();

		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return rankingPortletDisplayContext;
		}

		SearchContainer<RankingEntryDisplayContext> searchContainer = _search();

		rankingPortletDisplayContext.setActionDropdownItems(
			getActionDropdownItems());
		rankingPortletDisplayContext.setClearResultsURL(getClearResultsURL());
		rankingPortletDisplayContext.setCreationMenu(getCreationMenu());
		rankingPortletDisplayContext.setDisabledManagementBar(
			isDisabledManagementBar(searchContainer));
		rankingPortletDisplayContext.setDisplayStyle(getDisplayStyle());
		rankingPortletDisplayContext.setFilterItemsDropdownItems(
			getFilterItemsDropdownItems());
		rankingPortletDisplayContext.setOrderByType(getOrderByType());
		rankingPortletDisplayContext.setSearchActionURL(getSearchActionURL());
		rankingPortletDisplayContext.setSearchContainer(searchContainer);
		rankingPortletDisplayContext.setSortingURL(getSortingURL());
		rankingPortletDisplayContext.setTotalItems(searchContainer.getTotal());

		return rankingPortletDisplayContext;
	}

	protected RankingEntryDisplayContext buildDisplayContext(
		SearchHit searchHit) {

		Ranking ranking = _documentToRankingTranslator.translate(
			searchHit.getDocument(), searchHit.getId());

		RankingEntryDisplayContextBuilder rankingEntryDisplayContextBuilder =
			new RankingEntryDisplayContextBuilder(ranking);

		return rankingEntryDisplayContextBuilder.build();
	}

	protected RankingIndexName buildRankingIndexName() {
		return _rankingIndexNameBuilder.getRankingIndexName(
			_portal.getCompanyId(_httpServletRequest));
	}

	protected List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData(
					"action", "deactivateResultsRankingsEntries");
				dropdownItem.setIcon("hidden");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "deactivate"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData(
					"action", "activateResultsRankingsEntries");
				dropdownItem.setIcon("undo");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "activate"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteResultsRankingsEntries");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@SuppressWarnings("deprecation")
	protected String getClearResultsURL() {
		PortletURL clearResultsURL = _getPortletURL(getKeywords());

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	protected CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"addResultsRankingEntry", "redirect",
					PortalUtil.getCurrentURL(_httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "new-ranking"));
			}
		).build();
	}

	protected String getDisplayStyle() {
		return ParamUtil.getString(_renderRequest, "displayStyle", "list");
	}

	protected List<DropdownItem> getFilterItemsDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getOrderByDropdownItems(getKeywords()));
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	protected String getKeywords() {
		return ParamUtil.getString(_httpServletRequest, "keywords");
	}

	protected String getOrderByType() {
		return ParamUtil.getString(_httpServletRequest, "orderByType", "asc");
	}

	protected List<RankingEntryDisplayContext> getRankingEntryDisplayContexts(
		List<SearchHit> searchHits) {

		Stream<SearchHit> stream = searchHits.stream();

		return stream.map(
			this::buildDisplayContext
		).collect(
			Collectors.toList()
		);
	}

	protected String getSearchActionURL() {
		PortletURL portletURL = _getPortletURL(getKeywords());

		return portletURL.toString();
	}

	protected SearchContainer<RankingEntryDisplayContext> getSearchContainer(
		String keywords) {

		Html html = HtmlUtil.getHtml();

		String emptyResultMessage = _language.format(
			_httpServletRequest, "no-custom-results-yet",
			"<strong>" + html.escape(keywords) + "</strong>", false);

		SearchContainer<RankingEntryDisplayContext> searchContainer =
			new SearchContainer<>(
				_renderRequest, _getPortletURL(keywords), null,
				emptyResultMessage);

		searchContainer.setId("resultRankingsEntries");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		return searchContainer;
	}

	@SuppressWarnings("deprecation")
	protected String getSortingURL() {
		PortletURL sortingURL = _getPortletURL(getKeywords());

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	protected boolean isDisabledManagementBar(
		SearchContainer<RankingEntryDisplayContext> searchContainer) {

		if (_hasResults(searchContainer)) {
			return false;
		}

		if (_isSearch(getKeywords())) {
			return false;
		}

		return true;
	}

	protected Boolean isShowCreationMenu() {
		return true;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(true);
				dropdownItem.setHref(_renderResponse.createRenderURL());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "all"));
			}
		).build();
	}

	private String _getOrderByCol() {
		return ParamUtil.getString(_renderRequest, "orderByCol", _ORDER_BY_COL);
	}

	private List<DropdownItem> _getOrderByDropdownItems(String keywords) {
		PortletURL portletURL = _getPortletURL(keywords);

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(_getOrderByCol(), "keywords"));
				dropdownItem.setHref(portletURL, "orderByCol", "keywords");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "search-query"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(_getOrderByCol(), _ORDER_BY_COL));
				dropdownItem.setHref(portletURL, "orderByCol", _ORDER_BY_COL);
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, _ORDER_BY_COL));
			}
		).build();
	}

	@SuppressWarnings("deprecation")
	private PortletURL _getPortletURL(String keywords) {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");

		if (!Validator.isBlank(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		portletURL.setParameter("displayStyle", getDisplayStyle());
		portletURL.setParameter("orderByCol", _getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	private boolean _hasResults(
		SearchContainer<RankingEntryDisplayContext> searchContainer) {

		if (searchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	private boolean _isSearch(String keywords) {
		if (!Validator.isBlank(keywords)) {
			return true;
		}

		return false;
	}

	private SearchContainer<RankingEntryDisplayContext> _search() {
		SearchContainer<RankingEntryDisplayContext> searchContainer =
			getSearchContainer(getKeywords());

		SearchRankingRequest searchRankingRequest = new SearchRankingRequest(
			_httpServletRequest, _queries, buildRankingIndexName(), _sorts,
			searchContainer, _searchEngineAdapter);

		SearchRankingResponse searchRankingResponse =
			searchRankingRequest.search();

		SearchHits searchHits = searchRankingResponse.getSearchHits();

		searchContainer.setResults(
			getRankingEntryDisplayContexts(searchHits.getSearchHits()));

		searchContainer.setSearch(true);
		searchContainer.setTotal(searchRankingResponse.getTotalHits());

		return searchContainer;
	}

	private static final String _ORDER_BY_COL =
		RankingFields.QUERY_STRING_KEYWORD;

	private final DocumentToRankingTranslator _documentToRankingTranslator;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final Queries _queries;
	private final RankingIndexNameBuilder _rankingIndexNameBuilder;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final SearchEngineInformation _searchEngineInformation;
	private final Sorts _sorts;

}