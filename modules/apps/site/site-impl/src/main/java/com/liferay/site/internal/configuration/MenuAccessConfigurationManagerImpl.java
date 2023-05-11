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

package com.liferay.site.internal.configuration;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.site.configuration.MenuAccessConfiguration;
import com.liferay.site.configuration.MenuAccessConfigurationManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = MenuAccessConfigurationManager.class)
public class MenuAccessConfigurationManagerImpl
	implements MenuAccessConfigurationManager {

	@Override
	public void addRoleCanAccessControlMenu(Role role) throws Exception {
		for (Group group :
				_groupLocalService.getGroups(
					role.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true)) {

			MenuAccessConfiguration menuAccessConfiguration =
				_configurationProvider.getGroupConfiguration(
					MenuAccessConfiguration.class, group.getGroupId());

			if (!menuAccessConfiguration.showControlMenuByRole()) {
				continue;
			}

			String roleId = String.valueOf(role.getRoleId());
			String[] roleIdsCanAccessControlMenu =
				menuAccessConfiguration.roleIdsCanAccessControlMenu();

			if (!ArrayUtil.contains(roleIdsCanAccessControlMenu, roleId)) {
				roleIdsCanAccessControlMenu = ArrayUtil.append(
					roleIdsCanAccessControlMenu, roleId);

				updateMenuAccessConfiguration(
					group.getGroupId(), roleIdsCanAccessControlMenu,
					menuAccessConfiguration.showControlMenuByRole());
			}
		}
	}

	@Override
	public void deleteRoleCanAccessControlMenu(Role role) throws Exception {
		for (Group group :
				_groupLocalService.getGroups(
					role.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true)) {

			MenuAccessConfiguration menuAccessConfiguration =
				_configurationProvider.getGroupConfiguration(
					MenuAccessConfiguration.class, group.getGroupId());

			String roleId = String.valueOf(role.getRoleId());
			String[] roleIdsCanAccessControlMenu =
				menuAccessConfiguration.roleIdsCanAccessControlMenu();

			if (ArrayUtil.contains(roleIdsCanAccessControlMenu, roleId)) {
				roleIdsCanAccessControlMenu = ArrayUtil.remove(
					roleIdsCanAccessControlMenu, roleId);

				updateMenuAccessConfiguration(
					group.getGroupId(), roleIdsCanAccessControlMenu,
					menuAccessConfiguration.showControlMenuByRole());
			}
		}
	}

	@Override
	public String[] getRoleIdsCanAccessControlMenu(long groupId)
		throws Exception {

		MenuAccessConfiguration menuAccessConfiguration =
			_configurationProvider.getGroupConfiguration(
				MenuAccessConfiguration.class, groupId);

		return menuAccessConfiguration.roleIdsCanAccessControlMenu();
	}

	@Override
	public boolean isShowControlMenuByRole(long groupId) throws Exception {
		MenuAccessConfiguration menuAccessConfiguration =
			_configurationProvider.getGroupConfiguration(
				MenuAccessConfiguration.class, groupId);

		return menuAccessConfiguration.showControlMenuByRole();
	}

	@Override
	public void updateMenuAccessConfiguration(
			long groupId, String[] roleIdsCanAccessControlMenu,
			boolean showControlMenuByRole)
		throws Exception {

		_configurationProvider.saveGroupConfiguration(
			MenuAccessConfiguration.class, groupId,
			HashMapDictionaryBuilder.<String, Object>put(
				"roleIdsCanAccessControlMenu", roleIdsCanAccessControlMenu
			).put(
				"showControlMenuByRole", showControlMenuByRole
			).build());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

}