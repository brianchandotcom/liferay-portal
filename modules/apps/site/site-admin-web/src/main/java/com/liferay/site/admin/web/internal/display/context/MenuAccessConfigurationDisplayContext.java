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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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

/**
 * @author Mikel Lorza
 */
public class MenuAccessConfigurationDisplayContext {

	public MenuAccessConfigurationDisplayContext(
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		MenuAccessConfigurationProvider menuAccessConfigurationProvider,
		Portal portal, RoleLocalService roleLocalService) {

		_httpServletRequest = httpServletRequest;
		_itemSelector = itemSelector;
		_menuAccessConfigurationProvider = menuAccessConfigurationProvider;
		_roleLocalService = roleLocalService;

		_liferayPortletRequest = portal.getLiferayPortletRequest(
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST));
		_liferayPortletResponse = portal.getLiferayPortletResponse(
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE));
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getEventName() {
		return _liferayPortletResponse.getNamespace() + "selectRole";
	}

	public String getRoleItemSelectorURL() throws Exception {
		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>();

		RegularRoleItemSelectorCriterion regularRoleItemSelectorCriterion =
			new RegularRoleItemSelectorCriterion();

		regularRoleItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.singletonList(new UUIDItemSelectorReturnType()));

		itemSelectorCriteria.add(regularRoleItemSelectorCriterion);

		String[] roleIds =
			_menuAccessConfigurationProvider.getAccessToControlMenuRoleIds(
				_themeDisplay.getScopeGroupId());

		long[] checkedRoleIds = new long[roleIds.length];

		for (int i = 0; i < roleIds.length; i++) {
			Role role = _roleLocalService.fetchRole(
				GetterUtil.getLong(roleIds[i]));

			if (role != null) {
				checkedRoleIds[i] = role.getRoleId();
			}
		}

		regularRoleItemSelectorCriterion.setCheckedRoleIds(checkedRoleIds);
		regularRoleItemSelectorCriterion.setExcludedRoleNames(
			new String[] {
				RoleConstants.ADMINISTRATOR, RoleConstants.GUEST,
				RoleConstants.OWNER
			});

		SiteRoleItemSelectorCriterion siteRoleItemSelectorCriterion =
			new SiteRoleItemSelectorCriterion();

		siteRoleItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.singletonList(new UUIDItemSelectorReturnType()));

		siteRoleItemSelectorCriterion.setCheckedRoleIds(checkedRoleIds);
		siteRoleItemSelectorCriterion.setExcludedRoleNames(
			new String[] {
				RoleConstants.SITE_ADMINISTRATOR, RoleConstants.SITE_OWNER
			});

		itemSelectorCriteria.add(siteRoleItemSelectorCriterion);

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
				getEventName(),
				itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]))
		).buildString();
	}

	public SearchContainer<Role> getSearchContainer() throws Exception {
		PortletURL currentURL = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		SearchContainer<Role> searchContainer = new RoleSearch(
			_liferayPortletRequest, currentURL);

		searchContainer.setEmptyResultsMessage("no-roles-selected");

		List<Role> roles = new ArrayList<>();

		String[] accessToControlMenuRoleIds =
			_menuAccessConfigurationProvider.getAccessToControlMenuRoleIds(
				_themeDisplay.getScopeGroupId());

		for (String roleId : accessToControlMenuRoleIds) {
			Role role = _roleLocalService.fetchRole(GetterUtil.getLong(roleId));

			if (role != null) {
				roles.add(role);
			}
		}

		Role administratorRole = _roleLocalService.getRole(
			_themeDisplay.getCompanyId(), RoleConstants.ADMINISTRATOR);

		if (!ArrayUtil.contains(
				accessToControlMenuRoleIds,
				String.valueOf(administratorRole.getRoleId()))) {

			roles.add(administratorRole);
		}

		Role siteAdministratorRole = _roleLocalService.getRole(
			_themeDisplay.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		if (!ArrayUtil.contains(
				accessToControlMenuRoleIds,
				String.valueOf(siteAdministratorRole.getRoleId()))) {

			roles.add(siteAdministratorRole);
		}

		searchContainer.setResultsAndTotal(roles);

		return searchContainer;
	}

	public boolean isShowControlMenuByRole() throws Exception {
		return _menuAccessConfigurationProvider.isShowControlMenuByRole(
			_themeDisplay.getScopeGroupId());
	}

	public boolean isShowDeleteButton(Role role) throws Exception {
		if (role == null) {
			return true;
		}

		if (RoleConstants.ADMINISTRATOR.equals(role.getName()) ||
			RoleConstants.SITE_ADMINISTRATOR.equals(role.getName())) {

			return false;
		}

		return true;
	}

	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final MenuAccessConfigurationProvider
		_menuAccessConfigurationProvider;
	private final RoleLocalService _roleLocalService;
	private final ThemeDisplay _themeDisplay;

}