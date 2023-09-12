/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.configuration;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.site.configuration.MenuAccessConfiguration;
import com.liferay.site.configuration.manager.MenuAccessConfigurationManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = MenuAccessConfigurationManager.class)
public class MenuAccessConfigurationManagerImpl
	implements MenuAccessConfigurationManager {

	@Override
	public void addAccessRoleToControlMenu(Role role) throws Exception {
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
			String[] accessToControlMenuRoleIds =
				menuAccessConfiguration.accessToControlMenuRoleIds();

			if (!ArrayUtil.contains(accessToControlMenuRoleIds, roleId)) {
				accessToControlMenuRoleIds = ArrayUtil.append(
					accessToControlMenuRoleIds, roleId);

				updateMenuAccessConfiguration(
					group.getGroupId(), accessToControlMenuRoleIds,
					menuAccessConfiguration.showControlMenuByRole());
			}
		}
	}

	@Override
	public void deleteRoleAccessToControlMenu(Role role) throws Exception {
		for (Group group :
				_groupLocalService.getGroups(
					role.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true)) {

			MenuAccessConfiguration menuAccessConfiguration =
				_configurationProvider.getGroupConfiguration(
					MenuAccessConfiguration.class, group.getGroupId());

			String roleId = String.valueOf(role.getRoleId());
			String[] accessToControlMenuRoleIds =
				menuAccessConfiguration.accessToControlMenuRoleIds();

			if (ArrayUtil.contains(accessToControlMenuRoleIds, roleId)) {
				accessToControlMenuRoleIds = ArrayUtil.remove(
					accessToControlMenuRoleIds, roleId);

				updateMenuAccessConfiguration(
					group.getGroupId(), accessToControlMenuRoleIds,
					menuAccessConfiguration.showControlMenuByRole());
			}
		}
	}

	@Override
	public String[] getAccessToControlMenuRoleIds(long groupId)
		throws Exception {

		MenuAccessConfiguration menuAccessConfiguration =
			_configurationProvider.getGroupConfiguration(
				MenuAccessConfiguration.class, groupId);

		return menuAccessConfiguration.accessToControlMenuRoleIds();
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
			long groupId, String[] accessToControlMenuRoleIds,
			boolean showControlMenuByRole)
		throws Exception {

		_configurationProvider.saveGroupConfiguration(
			MenuAccessConfiguration.class, groupId,
			HashMapDictionaryBuilder.<String, Object>put(
				"accessToControlMenuRoleIds", accessToControlMenuRoleIds
			).put(
				"showControlMenuByRole", showControlMenuByRole
			).build());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

}