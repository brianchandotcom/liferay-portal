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

package com.liferay.depot.web.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.servlet.taglib.clay.DepotEntryVerticalCard;
import com.liferay.depot.web.internal.servlet.taglib.util.DepotActionDropdownItemsProvider;
import com.liferay.depot.web.internal.util.DepotAdminGroupSearchProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.util.GroupURLProvider;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DepotAdminDisplayContext {

	public DepotAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_depotAdminGroupSearchProvider =
			(DepotAdminGroupSearchProvider)httpServletRequest.getAttribute(
				DepotAdminWebKeys.DEPOT_ADMIN_GROUP_SEARCH_PROVIDER);

		_depotEntryLocalService =
			(DepotEntryLocalService)httpServletRequest.getAttribute(
				DepotEntryLocalService.class.getName());

		_groupURLProvider = (GroupURLProvider)httpServletRequest.getAttribute(
			DepotAdminWebKeys.DEPOT_ADMIN_GROUP_URL_PROVIDER);
	}

	public List<DropdownItem> getActionDropdownItems(DepotEntry depotEntry) {
		DepotActionDropdownItemsProvider depotActionDropdownItemsProvider =
			new DepotActionDropdownItemsProvider(
				depotEntry, _liferayPortletRequest, _liferayPortletResponse);

		return depotActionDropdownItemsProvider.getActionDropdownItems();
	}

	public String getDefaultDisplayStyle() {
		return "icon";
	}

	public DepotEntryVerticalCard getDepotEntryVerticalCard(
			DepotEntry depotEntry)
		throws PortalException {

		SearchContainer<DepotEntry> searchContainer = searchContainer();

		return new DepotEntryVerticalCard(
			depotEntry, _groupURLProvider, _liferayPortletRequest,
			_liferayPortletResponse, searchContainer.getRowChecker());
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_liferayPortletRequest, "displayStyle", getDefaultDisplayStyle());

		return _displayStyle;
	}

	public PortletURL getIteratorURL() {
		SearchContainer<DepotEntry> searchContainer = searchContainer();

		return searchContainer.getIteratorURL();
	}

	public String getSearchContainerId() {
		return "depotEntries";
	}

	public String getViewDepotURL(DepotEntry depotEntry)
		throws PortalException {

		return _groupURLProvider.getGroupURL(
			GroupLocalServiceUtil.getGroup(depotEntry.getGroupId()),
			_liferayPortletRequest);
	}

	public boolean isDisplayStyleDescriptive() {
		return Objects.equals(getDisplayStyle(), "descriptive");
	}

	public boolean isDisplayStyleIcon() {
		return Objects.equals(getDisplayStyle(), "icon");
	}

	public SearchContainer<DepotEntry> searchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_liferayPortletRequest, _getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage(
			LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					_liferayPortletRequest.getLocale(), getClass()),
				"no-repositories-were-found"));

		_searchContainer.setId(getSearchContainerId());

		GroupSearch groupSearch = _depotAdminGroupSearchProvider.getGroupSearch(
			_liferayPortletRequest, _getPortletURL());

		List<Group> groups = groupSearch.getResults();

		Stream<Group> stream = groups.stream();

		_searchContainer.setResults(
			stream.map(
				Group::getClassPK
			).map(
				_depotEntryLocalService::fetchDepotEntry
			).collect(
				Collectors.toList()
			));

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		_searchContainer.setTotal(groupSearch.getTotal());

		return _searchContainer;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("displayStyle", getDisplayStyle());

		return portletURL;
	}

	private final DepotAdminGroupSearchProvider _depotAdminGroupSearchProvider;
	private final DepotEntryLocalService _depotEntryLocalService;
	private String _displayStyle;
	private final GroupURLProvider _groupURLProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<DepotEntry> _searchContainer;

}