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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.usersadmin.search.OrganizationSearch;
import com.liferay.portlet.usersadmin.search.OrganizationSearchTerms;
import com.liferay.users.admin.web.internal.search.OrganizationChecker;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ViewOrganizationsManagementToolbarDisplayContext {

	public ViewOrganizationsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse, String displayStyle) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_displayStyle = displayStyle;

		_currentURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							StringBundler.concat(
								"javascript:", _renderResponse.getNamespace(),
								"deleteOrganizations();"));
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		return new CreationMenu() {
			{
				for (String organizationType :
						OrganizationLocalServiceUtil.getTypes()) {

					addDropdownItem(
						dropdownItem -> {
							dropdownItem.setHref(
								_renderResponse.createRenderURL(),
								"mvcRenderCommandName",
								"/users_admin/edit_organization", "redirect",
								_getViewUsersURL(), "type", organizationType);
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, organizationType));
						});
				}
			}
		};
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "order-by"));
					});
			}
		};
	}

	public String getOrderByCol() {
		return _organizationSearch.getOrderByCol();
	}

	public String getOrderByType() {
		return _organizationSearch.getOrderByType();
	}

	public PortletURL getPortletURL() {
		try {
			PortletURL portletURL = PortletURLUtil.clone(
				_currentURL, _renderResponse);

			portletURL.setParameter("orderByCol", getOrderByCol());
			portletURL.setParameter("orderByType", getOrderByType());

			return portletURL;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return _renderResponse.createRenderURL();
		}
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	public SearchContainer getSearchContainer(
			LinkedHashMap<String, Object> organizationParams,
			boolean filterManageableOrganizations)
		throws Exception {

		if (_organizationSearch != null) {
			return _organizationSearch;
		}

		PortletURL portletURL = (PortletURL)_httpServletRequest.getAttribute(
			"view.jsp-portletURL");

		OrganizationSearch organizationSearch = new OrganizationSearch(
			_renderRequest, portletURL);

		RowChecker rowChecker = new OrganizationChecker(_renderResponse);

		rowChecker.setRowIds("rowIdsOrganization");

		organizationSearch.setRowChecker(rowChecker);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long parentOrganizationId =
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

		OrganizationSearchTerms organizationSearchTerms =
			(OrganizationSearchTerms)organizationSearch.getSearchTerms();

		String keywords = organizationSearchTerms.getKeywords();

		if (Validator.isNotNull(keywords) || filterManageableOrganizations) {
			parentOrganizationId =
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
		}
		else {
			parentOrganizationId = ParamUtil.getLong(
				_httpServletRequest, "parentOrganizationId",
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
		}

		List<Organization> results = null;
		int total = 0;

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			Organization.class);

		if (indexer.isIndexerEnabled() &&
			PropsValues.ORGANIZATIONS_SEARCH_WITH_INDEX) {

			organizationParams.put("expandoAttributes", keywords);

			Sort sort = SortFactoryUtil.getSort(
				Organization.class, organizationSearch.getOrderByCol(),
				organizationSearch.getOrderByType());

			BaseModelSearchResult<Organization> baseModelSearchResult =
				OrganizationLocalServiceUtil.searchOrganizations(
					themeDisplay.getCompanyId(), parentOrganizationId, keywords,
					organizationParams, organizationSearch.getStart(),
					organizationSearch.getEnd(), sort);

			results = baseModelSearchResult.getBaseModels();
			total = baseModelSearchResult.getLength();
		}
		else {
			total = OrganizationLocalServiceUtil.searchCount(
				themeDisplay.getCompanyId(), parentOrganizationId, keywords,
				organizationSearchTerms.getType(),
				organizationSearchTerms.getRegionIdObj(),
				organizationSearchTerms.getCountryIdObj(), organizationParams);

			results = OrganizationLocalServiceUtil.search(
				themeDisplay.getCompanyId(), parentOrganizationId, keywords,
				organizationSearchTerms.getType(),
				organizationSearchTerms.getRegionIdObj(),
				organizationSearchTerms.getCountryIdObj(), organizationParams,
				organizationSearch.getStart(), organizationSearch.getEnd(),
				organizationSearch.getOrderByComparator());
		}

		organizationSearch.setResults(results);
		organizationSearch.setTotal(total);

		_organizationSearch = organizationSearch;

		return _organizationSearch;
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), _displayStyle) {
			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean showCreationMenu() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return PortalPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), ActionKeys.ADD_ORGANIZATION);
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(StringPool.BLANK);
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "all"));
					});
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "name"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "name");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "name"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "type"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "type");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "type"));
					});
			}
		};
	}

	private String _getViewUsersURL() {
		String toolbarItem = ParamUtil.getString(
			_httpServletRequest, "toolbarItem", "view-all-organizations");
		String usersListView = (String)_httpServletRequest.getAttribute(
			"view.jsp-usersListView");

		PortletURL viewUsersURL = _renderResponse.createRenderURL();

		viewUsersURL.setParameter("toolbarItem", toolbarItem);
		viewUsersURL.setParameter("usersListView", usersListView);

		return viewUsersURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewOrganizationsManagementToolbarDisplayContext.class);

	private final PortletURL _currentURL;
	private final String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private OrganizationSearch _organizationSearch;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}