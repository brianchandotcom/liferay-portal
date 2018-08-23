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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashEntryList;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.trash.service.TrashEntryServiceUtil;
import com.liferay.trash.util.comparator.EntryCreateDateComparator;
import com.liferay.trash.web.internal.constants.TrashPortletKeys;
import com.liferay.trash.web.internal.search.EntrySearch;
import com.liferay.trash.web.internal.search.EntrySearchTerms;

import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
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
		HttpServletRequest request, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_request = request;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteSelectedEntries");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
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

		return ParamUtil.getLong(_request, "classNameId");
	}

	public long getClassPK() {
		TrashEntry trashEntry = getTrashEntry();

		if (trashEntry != null) {
			return trashEntry.getClassPK();
		}

		return ParamUtil.getLong(_request, "classPK");
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public String getContainerModelRedirectURL() {
		if (Validator.isNotNull(_containerModelRedirectURL)) {
			return _containerModelRedirectURL;
		}

		String redirect = ParamUtil.getString(_request, "redirect");

		if (Validator.isNull(redirect)) {
			PortletURL redirectURL = _liferayPortletResponse.createRenderURL();

			redirectURL.setParameter("mvcPath", "/view_content.jsp");

			long trashEntryId = getTrashEntryId();

			long classNameId = getClassNameId();
			long classPK = getClassPK();

			if (trashEntryId > 0) {
				redirectURL.setParameter(
					"trashEntryId", String.valueOf(trashEntryId));
			}
			else if ((classNameId > 0) && (classPK > 0)) {
				redirectURL.setParameter(
					"classNameId", String.valueOf(classNameId));
				redirectURL.setParameter("classPK", String.valueOf(classPK));
			}

			redirect = redirectURL.toString();
		}

		_containerModelRedirectURL = redirect;

		return redirect;
	}

	public String getContentClearResultsURL() {
		PortletURL clearResultsURL = getContentPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public List<DropdownItem> getContentFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getContentFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});
			}
		};
	}

	public PortletURL getContentPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		return portletURL;
	}

	public String getContentSearchActionURL() {
		PortletURL searchActionURL = getContentPortletURL();

		return searchActionURL.toString();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			TrashPortletKeys.TRASH, "display-style", "list");

		return _displayStyle;
	}

	public EntrySearch getEntrySearch() throws PortalException {
		if (_entrySearch != null) {
			return _entrySearch;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
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
					_request,
					"no-entries-were-found-that-matched-the-keywords-x",
					"<strong>" + HtmlUtil.escape(searchTerms.getKeywords()) +
						"</strong>",
					false));
		}

		_entrySearch = entrySearch;

		return _entrySearch;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public List<NavigationItem> getInfoPanelNavigationItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(themeDisplay.getURLCurrent());
						navigationItem.setLabel(
							LanguageUtil.get(_request, "details"));
					});
			}
		};
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_request, "navigation", "all");

		return _navigation;
	}

	public List<NavigationItem> getNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(
							_liferayPortletResponse.createRenderURL());
						navigationItem.setLabel(
							LanguageUtil.get(_request, "entries"));
					});
			}
		};
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "removed-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
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

		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String navigation = getNavigation();

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter("navigation", navigation);
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() throws PortalException {
		EntrySearch entrySearch = getEntrySearch();

		return entrySearch.getTotal();
	}

	public TrashEntry getTrashEntry() {
		if (_trashEntry != null) {
			return _trashEntry;
		}

		long trashEntryId = ParamUtil.getLong(_request, "trashEntryId");

		long classNameId = ParamUtil.getLong(_request, "classNameId");
		long classPK = ParamUtil.getLong(_request, "classPK");

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

	public String getViewContentRedirectURL() throws PortalException {
		String redirect = ParamUtil.getString(_request, "redirect");

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

	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = _liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "changeDisplayStyle");
		portletURL.setParameter("redirect", PortalUtil.getCurrentURL(_request));

		return new ViewTypeItemList(portletURL, getDisplayStyle()) {
			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
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

	public boolean isDisabledManagementBar() throws PortalException {
		if (getTotalItems() > 0) {
			return false;
		}

		if (Validator.isNotNull(getKeywords())) {
			return false;
		}

		return true;
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

	private List<DropdownItem> _getContentFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getNavigation(), "all"));
						dropdownItem.setHref(
							getPortletURL(), "navigation", "all");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});
			}
		};
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getNavigation(), "all"));
						dropdownItem.setHref(
							getPortletURL(), "navigation", "all");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});

				for (TrashHandler trashHandler :
						TrashHandlerRegistryUtil.getTrashHandlers()) {

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								Objects.equals(
									getNavigation(),
									trashHandler.getClassName()));
							dropdownItem.setHref(
								getPortletURL(), "navigation",
								trashHandler.getClassName());
							dropdownItem.setLabel(
								ResourceActionsUtil.getModelResource(
									themeDisplay.getLocale(),
									trashHandler.getClassName()));
						});
				}
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "removed-date"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "removed-date");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "removed-date"));
					});
			}
		};
	}

	private boolean _approximate;
	private String _containerModelRedirectURL;
	private String _displayStyle;
	private EntrySearch _entrySearch;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final HttpServletRequest _request;
	private TrashEntry _trashEntry;
	private TrashHandler _trashHandler;
	private TrashRenderer _trashRenderer;

}