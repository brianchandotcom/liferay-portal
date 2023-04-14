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

package com.liferay.site.admin.web.internal.display.context;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.rolesadmin.search.RoleSearch;
import com.liferay.roles.item.selector.regular.role.RegularRoleItemSelectorCriterion;
import com.liferay.roles.item.selector.site.role.SiteRoleItemSelectorCriterion;
import com.liferay.site.configuration.MenuAccessConfigurationProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mikel Lorza
 */
public class MenuAccessConfigurationDisplayContext {

	public MenuAccessConfigurationDisplayContext(
		ConfigurationProvider configurationProvider,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, ItemSelector itemSelector,
		MenuAccessConfigurationProvider menuAccessConfigurationProvider,
		Portal portal, RoleLocalService roleLocalService) {

		_configurationProvider = configurationProvider;
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_itemSelector = itemSelector;
		_menuAccessConfigurationProvider = menuAccessConfigurationProvider;
		_portal = portal;
		_roleLocalService = roleLocalService;

		_liferayPortletRequest = portal.getLiferayPortletRequest(
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST));
		_liferayPortletResponse = portal.getLiferayPortletResponse(
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE));
		_themeDisplay = (ThemeDisplay)_liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getEventName() {
		return _liferayPortletResponse.getNamespace() + "selectRole";
	}

	public String getRoleItemSelectorURL() throws ConfigurationException {
		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>();

		RegularRoleItemSelectorCriterion regularRoleItemSelectorCriterion =
			new RegularRoleItemSelectorCriterion();

		regularRoleItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.singletonList(new UUIDItemSelectorReturnType()));

		itemSelectorCriteria.add(regularRoleItemSelectorCriterion);

		String[] roles = getRolesCanSeeControlMenu();

		long[] roleIds = new long[roles.length];

		for (int i = 0; i < roles.length; i++) {
			Role role = _roleLocalService.fetchRole(
				_themeDisplay.getCompanyId(), roles[i]);

			if (role != null) {
				roleIds[i] = role.getRoleId();
			}
		}

		regularRoleItemSelectorCriterion.setCheckedRoleIds(roleIds);

		SiteRoleItemSelectorCriterion siteRoleItemSelectorCriterion =
			new SiteRoleItemSelectorCriterion();

		siteRoleItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.singletonList(new UUIDItemSelectorReturnType()));

		siteRoleItemSelectorCriterion.setCheckedRoleIds(roleIds);

		itemSelectorCriteria.add(siteRoleItemSelectorCriterion);

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
				getEventName(),
				itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]))
		).buildString();
	}

	public String[] getRolesCanSeeControlMenu() throws ConfigurationException {
		return _menuAccessConfigurationProvider.getRolesCanSeeControlMenu(
			_themeDisplay.getScopeGroupId());
	}

	public SearchContainer<Role> getSearchContainer() throws PortalException {
		PortletURL currentURL = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		SearchContainer<Role> searchContainer = new RoleSearch(
			_liferayPortletRequest, currentURL);

		searchContainer.setEmptyResultsMessage("no-roles-selected");

		List<Role> roles = new ArrayList<>();

		for (String roleName : getRolesCanSeeControlMenu()) {
			Role role = _roleLocalService.fetchRole(
				_themeDisplay.getCompanyId(), roleName);

			if (role != null) {
				roles.add(role);
			}
		}

		searchContainer.setResultsAndTotal(roles);

		return searchContainer;
	}

	public boolean isShowControlMenuByRole() throws ConfigurationException {
		return _menuAccessConfigurationProvider.isShowControlMenuByRole(
			_themeDisplay.getScopeGroupId());
	}

	private final ConfigurationProvider _configurationProvider;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final ItemSelector _itemSelector;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final MenuAccessConfigurationProvider
		_menuAccessConfigurationProvider;
	private final Portal _portal;
	private final RoleLocalService _roleLocalService;
	private final ThemeDisplay _themeDisplay;

}