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

package com.liferay.portal.reports.engine.console.web.internal.admin.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.DefinitionServiceUtil;
import com.liferay.portal.reports.engine.console.service.EntryServiceUtil;
import com.liferay.portal.reports.engine.console.service.SourceServiceUtil;
import com.liferay.portal.reports.engine.console.web.internal.admin.configuration.ReportsEngineAdminWebConfiguration;
import com.liferay.portal.reports.engine.console.web.internal.admin.display.context.util.ReportsEngineRequestHelper;
import com.liferay.portal.reports.engine.console.web.internal.admin.search.DefinitionDisplayTerms;
import com.liferay.portal.reports.engine.console.web.internal.admin.search.DefinitionSearch;
import com.liferay.portal.reports.engine.console.web.internal.admin.search.EntryDisplayTerms;
import com.liferay.portal.reports.engine.console.web.internal.admin.search.EntrySearch;
import com.liferay.portal.reports.engine.console.web.internal.admin.search.SourceDisplayTerms;
import com.liferay.portal.reports.engine.console.web.internal.admin.search.SourceSearch;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class ReportsEngineDisplayContext {

	public ReportsEngineDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);

		_reportsEngineRequestHelper = new ReportsEngineRequestHelper(
			_httpServletRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		if (isReportsTabSelected()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			() -> isDefinitionsTabSelected(),
			dropdownItem -> {
				dropdownItem.setHref(
					_liferayPortletResponse.createRenderURL(), "mvcPath",
					"/admin/definition/edit_definition.jsp", "redirect",
					PortalUtil.getCurrentURL(
						_reportsEngineRequestHelper.getRequest()));

				dropdownItem.setLabel(
					LanguageUtil.get(
						_reportsEngineRequestHelper.getRequest(), "add"));
			}
		).addPrimaryDropdownItem(
			() -> isSourcesTabSelected(),
			dropdownItem -> {
				dropdownItem.setHref(
					_liferayPortletResponse.createRenderURL(), "mvcPath",
					"/admin/data_source/edit_data_source.jsp", "redirect",
					PortalUtil.getCurrentURL(
						_reportsEngineRequestHelper.getRequest()));

				dropdownItem.setLabel(
					LanguageUtil.get(
						_reportsEngineRequestHelper.getRequest(), "add"));
			}
		).build();
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					_liferayPortletRequest);

			_displayStyle = ParamUtil.getString(
				_liferayPortletRequest, "displayStyle");

			if (Validator.isNull(_displayStyle)) {
				ReportsEngineAdminWebConfiguration
					reportsEngineAdminWebConfiguration =
						(ReportsEngineAdminWebConfiguration)
							_liferayPortletRequest.getAttribute(
								ReportsEngineAdminWebConfiguration.class.
									getName());

				_displayStyle = portalPreferences.getValue(
					ReportsEngineConsolePortletKeys.REPORTS_ADMIN,
					"display-style",
					reportsEngineAdminWebConfiguration.defaultDisplayView());
			}
			else if (ArrayUtil.contains(_DISPLAY_VIEWS, _displayStyle)) {
				portalPreferences.setValue(
					ReportsEngineConsolePortletKeys.REPORTS_ADMIN,
					"display-style", _displayStyle);
			}

			if (!ArrayUtil.contains(_DISPLAY_VIEWS, _displayStyle)) {
				_displayStyle = _DISPLAY_VIEWS[0];
			}
		}

		return _displayStyle;
	}

	public DropdownItemList getFilterOptions() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getFilterNavigationDropdownItem("all")
					).build());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_reportsEngineRequestHelper.getRequest(), "filter"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getOrderByDropdownItem("create-date")
					).build());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_reportsEngineRequestHelper.getRequest(), "order-by"));
			}
		).build();
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_httpServletRequest, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = _portalPreferences.getValue(
				ReportsEngineConsolePortletKeys.REPORTS_ADMIN, "order-by-type",
				"asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					ReportsEngineConsolePortletKeys.REPORTS_ADMIN,
					"order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", _getTabs1());

		String navigation = ParamUtil.getString(
			_httpServletRequest, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter("navigation", _getNavigation());
		}

		return portletURL;
	}

	public SearchContainer<?> getSearchContainer() throws PortalException {
		if (_searchContainer == null) {
			if (isDefinitionsTabSelected()) {
				_searchContainer = _getDefinitionSearch();
			}
			else if (isReportsTabSelected()) {
				_searchContainer = _getEntrySearch();
			}
			else if (isSourcesTabSelected()) {
				_searchContainer = _getSourceSearch();
			}
		}

		return _searchContainer;
	}

	public String getSearchURL() {
		PortletURL portletURL = getPortletURL();

		ThemeDisplay themeDisplay =
			_reportsEngineRequestHelper.getThemeDisplay();

		portletURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

		return portletURL.toString();
	}

	public String getSortingURL() {
		LiferayPortletResponse liferayPortletResponse =
			_reportsEngineRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", _getTabs1());
		portletURL.setParameter("orderByCol", _getOrderByCol());

		portletURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return portletURL.toString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<?> searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	public ViewTypeItemList getViewTypes() {
		return new ViewTypeItemList(getPortletURL(), getDisplayStyle()) {
			{
				addTableViewTypeItem();
			}
		};
	}

	public boolean isAdminPortlet() {
		String portletName = _getPortletName();

		return portletName.equals(
			ReportsEngineConsolePortletKeys.REPORTS_ADMIN);
	}

	public boolean isDefinitionsTabSelected() {
		String tabs1 = _getTabs1();

		if (tabs1.equals("definitions")) {
			return true;
		}

		return false;
	}

	public boolean isDisabled() throws PortalException {
		if (getTotalItems() == 0) {
			return true;
		}

		return false;
	}

	public boolean isReportsTabSelected() {
		String tabs1 = _getTabs1();

		if (tabs1.equals("reports")) {
			return true;
		}

		return false;
	}

	public boolean isSourcesTabSelected() {
		String tabs1 = _getTabs1();

		if (tabs1.equals("sources")) {
			return true;
		}

		return false;
	}

	private DefinitionSearch _getDefinitionSearch() throws PortalException {
		DefinitionSearch definitionSearch = new DefinitionSearch(
			_reportsEngineRequestHelper.getRenderRequest(), getPortletURL());

		DefinitionDisplayTerms displayTerms =
			(DefinitionDisplayTerms)definitionSearch.getDisplayTerms();

		if (displayTerms.isAdvancedSearch()) {
			int total = DefinitionServiceUtil.getDefinitionsCount(
				_themeDisplay.getSiteGroupId(),
				displayTerms.getDefinitionName(), displayTerms.getDescription(),
				displayTerms.getSourceId(), displayTerms.getReportName(),
				displayTerms.isAndOperator());

			definitionSearch.setTotal(total);

			List<Definition> results = DefinitionServiceUtil.getDefinitions(
				_themeDisplay.getSiteGroupId(),
				displayTerms.getDefinitionName(), displayTerms.getDescription(),
				displayTerms.getSourceId(), displayTerms.getReportName(),
				displayTerms.isAndOperator(), definitionSearch.getStart(),
				definitionSearch.getEnd(),
				definitionSearch.getOrderByComparator());

			definitionSearch.setResults(results);
		}
		else {
			int total = DefinitionServiceUtil.getDefinitionsCount(
				_themeDisplay.getSiteGroupId(), displayTerms.getKeywords(),
				displayTerms.getKeywords(), null, displayTerms.getKeywords(),
				false);

			definitionSearch.setTotal(total);

			List<Definition> results = DefinitionServiceUtil.getDefinitions(
				_themeDisplay.getSiteGroupId(), displayTerms.getKeywords(),
				displayTerms.getKeywords(), null, displayTerms.getKeywords(),
				false, definitionSearch.getStart(), definitionSearch.getEnd(),
				definitionSearch.getOrderByComparator());

			definitionSearch.setResults(results);
		}

		if (definitionSearch.isSearch()) {
			definitionSearch.setEmptyResultsMessage(
				"no-definitions-were-found");
		}

		return definitionSearch;
	}

	private EntrySearch _getEntrySearch() throws PortalException {
		EntrySearch entrySearch = new EntrySearch(
			_reportsEngineRequestHelper.getRenderRequest(), getPortletURL());

		EntryDisplayTerms displayTerms =
			(EntryDisplayTerms)entrySearch.getDisplayTerms();

		if (displayTerms.isAdvancedSearch()) {
			Date startDate = PortalUtil.getDate(
				displayTerms.getStartDateMonth(),
				displayTerms.getStartDateDay(), displayTerms.getStartDateYear(),
				_themeDisplay.getTimeZone(), null);
			Date endDate = PortalUtil.getDate(
				displayTerms.getEndDateMonth(),
				displayTerms.getEndDateDay() + 1, displayTerms.getEndDateYear(),
				_themeDisplay.getTimeZone(), null);

			int total = EntryServiceUtil.getEntriesCount(
				_themeDisplay.getSiteGroupId(),
				displayTerms.getDefinitionName(), null, startDate, endDate,
				displayTerms.isAndOperator());

			entrySearch.setTotal(total);

			List<Entry> results = EntryServiceUtil.getEntries(
				_themeDisplay.getSiteGroupId(),
				displayTerms.getDefinitionName(), null, startDate, endDate,
				displayTerms.isAndOperator(), entrySearch.getStart(),
				entrySearch.getEnd(), entrySearch.getOrderByComparator());

			entrySearch.setResults(results);
		}
		else {
			int total = EntryServiceUtil.getEntriesCount(
				_themeDisplay.getSiteGroupId(), displayTerms.getKeywords(),
				null, null, null, false);

			entrySearch.setTotal(total);

			List<Entry> results = EntryServiceUtil.getEntries(
				_themeDisplay.getSiteGroupId(), displayTerms.getKeywords(),
				null, null, null, false, entrySearch.getStart(),
				entrySearch.getEnd(), entrySearch.getOrderByComparator());

			entrySearch.setResults(results);
		}

		if (entrySearch.isSearch()) {
			entrySearch.setEmptyResultsMessage("no-reports-were-found");
		}

		return entrySearch;
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getFilterNavigationDropdownItem(String navigation) {

		return dropdownItem -> {
			dropdownItem.setActive(
				Objects.equals(_getNavigation(), navigation));
			dropdownItem.setHref(
				getPortletURL(), "navigation", navigation, "mvcPath",
				"/admin/view.jsp", "tabs1", _getTabs1());
			dropdownItem.setLabel(
				LanguageUtil.get(
					_reportsEngineRequestHelper.getRequest(), navigation));
		};
	}

	private String _getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");

		return _navigation;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				ReportsEngineConsolePortletKeys.REPORTS_ADMIN, "order-by-col",
				"create-date");
		}

		return _orderByCol;
	}

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(
				Objects.equals(_getOrderByCol(), orderByCol));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_reportsEngineRequestHelper.getRequest(), orderByCol));
		};
	}

	private String _getPortletName() {
		return _reportsEngineRequestHelper.getPortletName();
	}

	private SourceSearch _getSourceSearch() throws PortalException {
		SourceSearch sourceSearch = new SourceSearch(
			_reportsEngineRequestHelper.getRenderRequest(), getPortletURL());

		SourceDisplayTerms displayTerms =
			(SourceDisplayTerms)sourceSearch.getDisplayTerms();

		if (displayTerms.isAdvancedSearch()) {
			int total = SourceServiceUtil.getSourcesCount(
				_themeDisplay.getSiteGroupId(), displayTerms.getName(),
				displayTerms.getDriverUrl(), displayTerms.isAndOperator());

			sourceSearch.setTotal(total);

			List<Source> results = SourceServiceUtil.getSources(
				_themeDisplay.getSiteGroupId(), displayTerms.getName(),
				displayTerms.getDriverUrl(), displayTerms.isAndOperator(),
				sourceSearch.getStart(), sourceSearch.getEnd(),
				sourceSearch.getOrderByComparator());

			sourceSearch.setResults(results);
		}
		else {
			int total = SourceServiceUtil.getSourcesCount(
				_themeDisplay.getSiteGroupId(), displayTerms.getKeywords(),
				displayTerms.getKeywords(), false);

			sourceSearch.setTotal(total);

			List<Source> results = SourceServiceUtil.getSources(
				_themeDisplay.getSiteGroupId(), displayTerms.getKeywords(),
				displayTerms.getKeywords(), false, sourceSearch.getStart(),
				sourceSearch.getEnd(), sourceSearch.getOrderByComparator());

			sourceSearch.setResults(results);
		}

		if (sourceSearch.isSearch()) {
			sourceSearch.setEmptyResultsMessage("no-sources-were-found");
		}

		return sourceSearch;
	}

	private String _getTabs1() {
		return ParamUtil.getString(_liferayPortletRequest, "tabs1", "reports");
	}

	private static final String[] _DISPLAY_VIEWS = {"list"};

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final ReportsEngineRequestHelper _reportsEngineRequestHelper;
	private SearchContainer<?> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}