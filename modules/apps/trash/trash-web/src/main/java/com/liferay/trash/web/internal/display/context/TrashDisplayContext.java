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

package com.liferay.trash.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashEntryList;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.trash.service.TrashEntryServiceUtil;
import com.liferay.trash.util.comparator.EntryCreateDateComparator;
import com.liferay.trash.web.internal.constants.TrashWebKeys;
import com.liferay.trash.web.internal.search.EntrySearch;
import com.liferay.trash.web.internal.search.EntrySearchTerms;
import com.liferay.trash.web.internal.servlet.taglib.util.TrashEntryActionDropdownItemsProvider;
import com.liferay.trash.web.internal.servlet.taglib.util.TrashViewContentActionDropdownItemsProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides utility methods moved from the Recycle Bin portlet's JSP files to
 * reduce the complexity of the views.
 *
 * @author Jürgen Kappler
 */
public class TrashDisplayContext {

	public TrashDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_trashHelper = (TrashHelper)httpServletRequest.getAttribute(
			TrashWebKeys.TRASH_HELPER);
	}

	public List<BreadcrumbEntry> getBaseModelBreadcrumbEntries()
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			LanguageUtil.get(_httpServletRequest, "recycle-bin"));

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		breadcrumbEntry.setURL(portletURL.toString());

		breadcrumbEntries.add(breadcrumbEntry);

		PortletURL containerModelURL =
			_liferayPortletResponse.createRenderURL();

		TrashHandler trashHandler = getTrashHandler();

		String trashHandlerContainerModelClassName =
			trashHandler.getContainerModelClassName(getClassPK());

		containerModelURL.setParameter("mvcPath", "/view_content.jsp");
		containerModelURL.setParameter(
			"classNameId",
			String.valueOf(
				PortalUtil.getClassNameId(
					trashHandlerContainerModelClassName)));

		breadcrumbEntries.addAll(
			getBreadcrumbEntries(
				getClassName(), getClassPK(), "classPK", containerModelURL,
				true));

		return breadcrumbEntries;
	}

	public String getClassName() {
		TrashEntry trashEntry = getTrashEntry();

		if (trashEntry != null) {
			return trashEntry.getClassName();
		}

		String className = StringPool.BLANK;

		long classNameId = getClassNameId();

		if (classNameId > 0) {
			className = PortalUtil.getClassName(getClassNameId());
		}

		return className;
	}

	public long getClassNameId() {
		TrashEntry trashEntry = getTrashEntry();

		if (trashEntry != null) {
			return trashEntry.getClassNameId();
		}

		return ParamUtil.getLong(_httpServletRequest, "classNameId");
	}

	public long getClassPK() {
		TrashEntry trashEntry = getTrashEntry();

		if (trashEntry != null) {
			return trashEntry.getClassPK();
		}

		return ParamUtil.getLong(_httpServletRequest, "classPK");
	}

	public List<BreadcrumbEntry> getContainerModelBreadcrumbEntries(
			String className, long classPK, PortletURL containerModelURL)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		String rootContainerModelTitle = LanguageUtil.get(
			themeDisplay.getLocale(), trashHandler.getRootContainerModelName());

		if (classPK == 0) {
			BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

			breadcrumbEntry.setTitle(rootContainerModelTitle);

			breadcrumbEntries.add(breadcrumbEntry);

			return breadcrumbEntries;
		}

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(rootContainerModelTitle);

		containerModelURL.setParameter("containerModelId", "0");

		breadcrumbEntry.setURL(containerModelURL.toString());

		breadcrumbEntries.add(breadcrumbEntry);

		breadcrumbEntries.addAll(
			getBreadcrumbEntries(
				className, classPK, "containerModelId", containerModelURL,
				false));

		return breadcrumbEntries;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public EntrySearch getEntrySearch() throws PortalException {
		if (_entrySearch != null) {
			return _entrySearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		EntrySearch entrySearch = new EntrySearch(
			_liferayPortletRequest, getPortletURL());

		entrySearch.setOrderByCol(getOrderByCol());

		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		entrySearch.setOrderByComparator(
			new EntryCreateDateComparator(orderByAsc));

		entrySearch.setOrderByType(getOrderByType());

		EmptyOnClickRowChecker emptyOnClickRowChecker =
			new EmptyOnClickRowChecker(_liferayPortletResponse);

		emptyOnClickRowChecker.setRememberCheckBoxStateURLRegex(
			"^(?!.*" + _liferayPortletResponse.getNamespace() +
				"redirect).*^(?!.*/entry/)");

		entrySearch.setRowChecker(emptyOnClickRowChecker);

		EntrySearchTerms searchTerms =
			(EntrySearchTerms)entrySearch.getSearchTerms();

		List trashEntries = null;

		if (Validator.isNotNull(searchTerms.getKeywords())) {
			Sort sort = SortFactoryUtil.getSort(
				TrashEntry.class, entrySearch.getOrderByCol(),
				entrySearch.getOrderByType());

			BaseModelSearchResult<TrashEntry> baseModelSearchResult =
				TrashEntryLocalServiceUtil.searchTrashEntries(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					themeDisplay.getUserId(), searchTerms.getKeywords(),
					entrySearch.getStart(), entrySearch.getEnd(), sort);

			entrySearch.setTotal(baseModelSearchResult.getLength());

			trashEntries = baseModelSearchResult.getBaseModels();
		}
		else {
			TrashEntryList trashEntryList = null;

			if (Objects.equals(getNavigation(), "all")) {
				trashEntryList = TrashEntryServiceUtil.getEntries(
					themeDisplay.getScopeGroupId(), entrySearch.getStart(),
					entrySearch.getEnd(), entrySearch.getOrderByComparator());
			}
			else {
				trashEntryList = TrashEntryServiceUtil.getEntries(
					themeDisplay.getScopeGroupId(), getNavigation(),
					entrySearch.getStart(), entrySearch.getEnd(),
					entrySearch.getOrderByComparator());
			}

			entrySearch.setTotal(trashEntryList.getCount());

			trashEntries = trashEntryList.getOriginalTrashEntries();

			_approximate = trashEntryList.isApproximate();
		}

		entrySearch.setResults(trashEntries);

		if ((entrySearch.getTotal() == 0) &&
			Validator.isNotNull(searchTerms.getKeywords())) {

			entrySearch.setEmptyResultsMessage(
				LanguageUtil.format(
					_httpServletRequest,
					"no-entries-were-found-that-matched-the-keywords-x",
					"<strong>" + HtmlUtil.escape(searchTerms.getKeywords()) +
						"</strong>",
					false));
		}

		_entrySearch = entrySearch;

		return _entrySearch;
	}

	public List<NavigationItem> getInfoPanelNavigationItems() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(themeDisplay.getURLCurrent());
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "details"));
					});
			}
		};
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "removed-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbEntries() {
		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			LanguageUtil.get(_httpServletRequest, "recycle-bin"));

		PortletURL portletURL = getPortletURL();

		breadcrumbEntry.setURL(portletURL.toString());

		breadcrumbEntries.add(breadcrumbEntry);

		return breadcrumbEntries;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		long trashEntryId = getTrashEntryId();

		if (trashEntryId > 0) {
			portletURL.setParameter("mvcPath", "/view_content.jsp");
			portletURL.setParameter(
				"trashEntryId", String.valueOf(trashEntryId));
		}

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String navigation = getNavigation();

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter("navigation", navigation);
		}

		return portletURL;
	}

	public SearchContainer getTrashContainerSearchContainer()
		throws PortalException {

		if (_trashContainerSearchContainer != null) {
			return _trashContainerSearchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String emptyResultsMessage = LanguageUtil.format(
			_httpServletRequest, "this-x-does-not-contain-an-entry",
			ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(), getClassName()),
			false);

		PortletURL iteratorURL = _liferayPortletResponse.createRenderURL();

		iteratorURL.setParameter("mvcPath", "/view_content.jsp");
		iteratorURL.setParameter(
			"classNameId", String.valueOf(getClassNameId()));
		iteratorURL.setParameter("classPK", String.valueOf(getClassPK()));

		SearchContainer searchContainer = new SearchContainer(
			_liferayPortletRequest, iteratorURL, null, emptyResultsMessage);

		searchContainer.setDeltaConfigurable(false);

		TrashHandler trashHandler = getTrashHandler();

		List results = trashHandler.getTrashModelTrashedModels(
			getClassPK(), searchContainer.getStart(), searchContainer.getEnd(),
			searchContainer.getOrderByComparator());

		searchContainer.setResults(results);

		searchContainer.setTotal(
			trashHandler.getTrashModelsCount(getClassPK()));

		_trashContainerSearchContainer = searchContainer;

		return _trashContainerSearchContainer;
	}

	public int getTrashContainerTotalItems() throws PortalException {
		SearchContainer searchContainer = getTrashContainerSearchContainer();

		return searchContainer.getTotal();
	}

	public TrashEntry getTrashEntry() {
		if (_trashEntry != null) {
			return _trashEntry;
		}

		long trashEntryId = ParamUtil.getLong(
			_httpServletRequest, "trashEntryId");

		long classNameId = ParamUtil.getLong(
			_httpServletRequest, "classNameId");
		long classPK = ParamUtil.getLong(_httpServletRequest, "classPK");

		if (trashEntryId > 0) {
			_trashEntry = TrashEntryLocalServiceUtil.fetchEntry(trashEntryId);
		}
		else if ((classNameId > 0) && (classPK > 0)) {
			String className = PortalUtil.getClassName(classNameId);

			if (Validator.isNotNull(className)) {
				_trashEntry = TrashEntryLocalServiceUtil.fetchEntry(
					className, classPK);
			}
		}

		return _trashEntry;
	}

	public List<DropdownItem> getTrashEntryActionDropdownItems(
			TrashEntry trashEntry)
		throws Exception {

		TrashEntryActionDropdownItemsProvider
			trashEntryActionDropdownItemsProvider =
				new TrashEntryActionDropdownItemsProvider(
					_liferayPortletRequest, _liferayPortletResponse,
					trashEntry);

		return trashEntryActionDropdownItemsProvider.getActionDropdownItems();
	}

	public long getTrashEntryId() {
		TrashEntry trashEntry = getTrashEntry();

		if (trashEntry != null) {
			return trashEntry.getEntryId();
		}

		return 0;
	}

	public TrashHandler getTrashHandler() {
		if (_trashHandler != null) {
			return _trashHandler;
		}

		_trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			getClassName());

		return _trashHandler;
	}

	public TrashRenderer getTrashRenderer() throws PortalException {
		if (_trashRenderer != null) {
			return _trashRenderer;
		}

		TrashHandler trashHandler = getTrashHandler();

		long classPK = getClassPK();

		if ((classPK > 0) && (trashHandler != null)) {
			_trashRenderer = trashHandler.getTrashRenderer(getClassPK());
		}

		return _trashRenderer;
	}

	public List<DropdownItem> getTrashViewContentActionDropdownItems(
			String className, long classPK)
		throws Exception {

		TrashViewContentActionDropdownItemsProvider
			trashViewContentActionDropdownItemsProvider =
				new TrashViewContentActionDropdownItemsProvider(
					_liferayPortletRequest, _liferayPortletResponse, className,
					classPK);

		return trashViewContentActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	public String getViewContentRedirectURL() throws PortalException {
		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNull(redirect)) {
			TrashHandler trashHandler = getTrashHandler();

			ContainerModel parentContainerModel =
				trashHandler.getParentContainerModel(getClassPK());

			PortletURL redirectURL = _liferayPortletResponse.createRenderURL();

			if ((parentContainerModel != null) && (getClassNameId() > 0)) {
				String parentContainerModelClassName =
					parentContainerModel.getModelClassName();

				redirectURL.setParameter("mvcPath", "/view_content.jsp");
				redirectURL.setParameter(
					"classNameId",
					String.valueOf(
						PortalUtil.getClassNameId(
							parentContainerModelClassName)));
				redirectURL.setParameter(
					"classPK",
					String.valueOf(parentContainerModel.getContainerModelId()));
			}

			redirect = redirectURL.toString();
		}

		return redirect;
	}

	public boolean isApproximate() {
		return _approximate;
	}

	public boolean isDescriptiveView() {
		if (Objects.equals(getDisplayStyle(), "descriptive")) {
			return true;
		}

		return false;
	}

	public boolean isIconView() {
		if (Objects.equals(getDisplayStyle(), "icon")) {
			return true;
		}

		return false;
	}

	public boolean isListView() {
		if (Objects.equals(getDisplayStyle(), "list")) {
			return true;
		}

		return false;
	}

	protected List<BreadcrumbEntry> getBreadcrumbEntries(
			String className, long classPK, String paramName,
			PortletURL containerModelURL, boolean checkInTrashContainers)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		PortletURL portletURL = PortletURLUtil.clone(
			containerModelURL, _liferayPortletResponse);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		List<ContainerModel> containerModels =
			trashHandler.getParentContainerModels(classPK);

		Collections.reverse(containerModels);

		for (ContainerModel containerModel : containerModels) {
			TrashHandler containerModelTrashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					containerModel.getModelClassName());

			if (checkInTrashContainers &&
				!containerModelTrashHandler.isInTrash(
					containerModel.getContainerModelId())) {

				continue;
			}

			BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

			String name = containerModel.getContainerModelName();

			if (containerModelTrashHandler.isInTrash(
					containerModel.getContainerModelId())) {

				name = _trashHelper.getOriginalTitle(name);
			}

			breadcrumbEntry.setTitle(name);

			portletURL.setParameter(
				paramName,
				String.valueOf(containerModel.getContainerModelId()));

			breadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(breadcrumbEntry);
		}

		TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			trashRenderer.getTitle(themeDisplay.getLocale()));

		breadcrumbEntries.add(breadcrumbEntry);

		return breadcrumbEntries;
	}

	private boolean _approximate;
	private String _displayStyle;
	private EntrySearch _entrySearch;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private SearchContainer _trashContainerSearchContainer;
	private TrashEntry _trashEntry;
	private TrashHandler _trashHandler;
	private final TrashHelper _trashHelper;
	private TrashRenderer _trashRenderer;

}