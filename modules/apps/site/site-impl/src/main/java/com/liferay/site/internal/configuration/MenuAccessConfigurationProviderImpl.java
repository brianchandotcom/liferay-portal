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
import com.liferay.site.configuration.MenuAccessConfigurationProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = MenuAccessConfigurationProvider.class)
public class MenuAccessConfigurationProviderImpl
	implements MenuAccessConfigurationProvider {

	@Override
	public void addRoleToMenuAccess(Role role) throws Exception {
		_manageRoleOfMenuAccess(true, role);
	}

	@Override
	public void deleteRoleFromMenuAccess(Role role) throws Exception {
		_manageRoleOfMenuAccess(false, role);
	}

	@Override
	public String[] getRolesCanSeeControlMenu(long groupId) throws Exception {
		MenuAccessConfiguration menuAccessConfiguration =
			_configurationProvider.getGroupConfiguration(
				MenuAccessConfiguration.class, groupId);

		return menuAccessConfiguration.rolesCanSeeControlMenu();
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
			long groupId, String[] rolesCanSeeControlMenu,
			boolean showControlMenuByRole)
		throws Exception {

		_configurationProvider.saveGroupConfiguration(
			MenuAccessConfiguration.class, groupId,
			HashMapDictionaryBuilder.<String, Object>put(
				"rolesCanSeeControlMenu", rolesCanSeeControlMenu
			).put(
				"showControlMenuByRole", showControlMenuByRole
			).build());
	}

	private void _manageRoleOfMenuAccess(boolean add, Role role)
		throws Exception {

		for (Group group :
				_groupLocalService.getGroups(
					role.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true)) {

			MenuAccessConfiguration menuAccessConfiguration =
				_configurationProvider.getGroupConfiguration(
					MenuAccessConfiguration.class, group.getGroupId());

			String roleId = String.valueOf(role.getRoleId());
			String[] rolesCanSeeControlMenu =
				menuAccessConfiguration.rolesCanSeeControlMenu();

			if (ArrayUtil.contains(rolesCanSeeControlMenu, roleId)) {
				if (!add) {
					rolesCanSeeControlMenu = ArrayUtil.remove(
						rolesCanSeeControlMenu, roleId);
				}
			}
			else if (add) {
				rolesCanSeeControlMenu = ArrayUtil.append(
					rolesCanSeeControlMenu, roleId);
			}

			_configurationProvider.saveGroupConfiguration(
				MenuAccessConfiguration.class, group.getGroupId(),
				HashMapDictionaryBuilder.<String, Object>put(
					"rolesCanSeeControlMenu", rolesCanSeeControlMenu
				).put(
					"showControlMenuByRole",
					menuAccessConfiguration.showControlMenuByRole()
				).build());
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

}