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

package com.liferay.polls.web.internal.portlet.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalService;
import com.liferay.polls.util.comparator.PollsQuestionCreateDateComparator;
import com.liferay.polls.util.comparator.PollsQuestionTitleComparator;
import com.liferay.polls.web.internal.portlet.display.context.util.PollsRequestHelper;
import com.liferay.polls.web.internal.portlet.search.PollsQuestionSearch;
import com.liferay.polls.web.internal.security.permission.resource.PollsPermission;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class PollsDisplayContext {

	public PollsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		PollsQuestionLocalService pollsQuestionLocalService) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_pollsQuestionLocalService = pollsQuestionLocalService;

		_pollsRequestHelper = new PollsRequestHelper(renderRequest);
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteQuestions");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_pollsRequestHelper.getRequest(), "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLBuilder.create(
			PortletURLUtil.clone(getPortletURL(), _renderResponse)
		).setParameter(
			"keywords", StringPool.BLANK
		).build();

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		if (!isShowAddPollButton()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				HttpServletRequest httpServletRequest =
					_pollsRequestHelper.getRequest();

				dropdownItem.setHref(
					_renderResponse.createRenderURL(), "mvcPath",
					"/polls/edit_question.jsp", "redirect",
					PortalUtil.getCurrentURL(httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-poll"));
			}
		).build();
	}

	public String getDisplayStyle() {
		return "list";
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest httpServletRequest =
			_pollsRequestHelper.getRequest();

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);

				ThemeDisplay themeDisplay = getThemeDisplay();

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				navigationItem.setLabel(portletDisplay.getPortletDisplayName());
			}
		).build();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(_renderRequest, "orderByCol", "create-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(_renderRequest, "orderByType", "desc");
	}

	public OrderByComparator<PollsQuestion> getPollsQuestionOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<PollsQuestion> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new PollsQuestionCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new PollsQuestionTitleComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/polls/view.jsp"
		).build();

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public SearchContainer<?> getSearch() {
		if (_pollsQuestionSearch != null) {
			return _pollsQuestionSearch;
		}

		PortletURL portletURL = PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"displayStyle", getDisplayStyle()
		).build();

		_pollsQuestionSearch = new PollsQuestionSearch(
			_renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<PollsQuestion> orderByComparator =
			getPollsQuestionOrderByComparator(orderByCol, orderByType);

		_pollsQuestionSearch.setOrderByCol(orderByCol);
		_pollsQuestionSearch.setOrderByComparator(orderByComparator);
		_pollsQuestionSearch.setOrderByType(orderByType);

		if (_pollsQuestionSearch.isSearch()) {
			_pollsQuestionSearch.setEmptyResultsMessage("no-polls-were-found");

			_populateWithSearchIndex(_pollsQuestionSearch);
		}
		else {
			_pollsQuestionSearch.setEmptyResultsMessage("there-are-no-polls");

			_populateWithDatabase(_pollsQuestionSearch);
		}

		return _pollsQuestionSearch;
	}

	public String getSearchActionURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/polls/view.jsp"
		).build();

		return portletURL.toString();
	}

	public String getSearchContainerId() {
		return "poll";
	}

	public String getSortingURL() throws Exception {
		PortletURL sortingURL = PortletURLBuilder.create(
			PortletURLUtil.clone(getPortletURL(), _renderResponse)
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = ParamUtil.getString(
					_renderRequest, "orderByType");

				return orderByType.equals("asc") ? "desc" : "asc";
			}
		).build();

		return sortingURL.toString();
	}

	public int getTotalItems() {
		SearchContainer<?> searchContainer = getSearch();

		return searchContainer.getTotal();
	}

	public boolean isDisabledManagementBar() {
		if (hasResults()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isShowAddPollButton() {
		return PollsPermission.contains(
			_pollsRequestHelper.getPermissionChecker(),
			_pollsRequestHelper.getScopeGroupId(), ActionKeys.ADD_QUESTION);
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(true);

				dropdownItem.setHref(getPortletURL(), "navigation", "all");

				dropdownItem.setLabel(
					LanguageUtil.get(_pollsRequestHelper.getRequest(), "all"));
			}
		).build();
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected UnsafeConsumer<DropdownItem, Exception> getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(_pollsRequestHelper.getRequest(), orderByCol));
		};
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			getOrderByDropdownItem("create-date")
		).add(
			getOrderByDropdownItem("title")
		).build();
	}

	protected ThemeDisplay getThemeDisplay() {
		return (ThemeDisplay)_renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	protected boolean hasResults() {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	private SearchContext _buildSearchContext(
		PollsQuestionSearch pollsQuestionSearch) {

		SearchContext searchContext = SearchContextFactory.getInstance(
			_pollsRequestHelper.getRequest());

		searchContext.setAttribute("paginationType", "none");
		searchContext.setCompanyId(_pollsRequestHelper.getCompanyId());
		searchContext.setEnd(pollsQuestionSearch.getEnd());
		searchContext.setGroupIds(
			new long[] {_pollsRequestHelper.getScopeGroupId()});
		searchContext.setKeywords(getKeywords());

		OrderByComparator<PollsQuestion> orderByComparator =
			pollsQuestionSearch.getOrderByComparator();

		if (orderByComparator != null) {
			searchContext.setSorts(_getSortFromComparator(orderByComparator));
		}

		searchContext.setStart(pollsQuestionSearch.getStart());

		return searchContext;
	}

	private Sort _getSortFromComparator(
		OrderByComparator<PollsQuestion> orderByComparator) {

		boolean reverse = !orderByComparator.isAscending();

		String[] orderByFields = orderByComparator.getOrderByFields();

		String orderByField = orderByFields[0];

		if (orderByField.equals(Field.CREATE_DATE)) {
			return new Sort(orderByField, Sort.LONG_TYPE, reverse);
		}

		return new Sort(orderByField, reverse);
	}

	private void _populateWithDatabase(
		PollsQuestionSearch pollsQuestionSearch) {

		pollsQuestionSearch.setResults(
			_pollsQuestionLocalService.search(
				_pollsRequestHelper.getScopeGroupId(),
				pollsQuestionSearch.getStart(), pollsQuestionSearch.getEnd(),
				pollsQuestionSearch.getOrderByComparator()));
		pollsQuestionSearch.setTotal(
			_pollsQuestionLocalService.searchCount(
				_pollsRequestHelper.getScopeGroupId()));
	}

	private void _populateWithSearchIndex(
		PollsQuestionSearch pollsQuestionSearch) {

		try {
			Indexer<PollsQuestion> indexer = IndexerRegistryUtil.getIndexer(
				PollsQuestion.class);

			Hits hits = indexer.search(
				_buildSearchContext(pollsQuestionSearch));

			List<SearchResult> searchResults =
				SearchResultUtil.getSearchResults(
					hits, LocaleUtil.getDefault());

			Stream<SearchResult> stream = searchResults.stream();

			pollsQuestionSearch.setResults(
				stream.map(
					SearchResult::getClassPK
				).map(
					_pollsQuestionLocalService::fetchPollsQuestion
				).collect(
					Collectors.toList()
				));

			pollsQuestionSearch.setTotal(hits.getLength());
		}
		catch (SearchException searchException) {
			if (_log.isDebugEnabled()) {
				_log.debug(searchException, searchException);
			}

			pollsQuestionSearch.setResults(Collections.emptyList());

			pollsQuestionSearch.setTotal(0);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollsDisplayContext.class);

	private final PollsQuestionLocalService _pollsQuestionLocalService;
	private PollsQuestionSearch _pollsQuestionSearch;
	private final PollsRequestHelper _pollsRequestHelper;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}