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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.site.configuration.MenuAccessConfiguration;
import com.liferay.site.configuration.MenuAccessConfigurationProvider;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
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
		Configuration configuration = _getScopedConfiguration(groupId);

		if (configuration != null) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			return GetterUtil.getStringValues(
				properties.get("rolesCanSeeControlMenu"));
		}

		return new String[0];
	}

	@Override
	public boolean isShowControlMenuByRole(long groupId) throws Exception {
		Configuration configuration = _getScopedConfiguration(groupId);

		if (configuration != null) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			return GetterUtil.getBoolean(
				properties.get("showControlMenuByRole"));
		}

		return false;
	}

	@Override
	public void updateMenuAccessConfiguration(
			long groupId, String[] rolesCanSeeControlMenu,
			boolean showControlMenuByRole)
		throws Exception {

		Dictionary<String, Object> properties;
		Configuration configuration = _getScopedConfiguration(groupId);

		if (configuration == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				MenuAccessConfiguration.class.getName() + ".scoped",
				StringPool.QUESTION);

			properties = HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
				groupId
			).build();
		}
		else {
			properties = configuration.getProperties();
		}

		properties.put("rolesCanSeeControlMenu", rolesCanSeeControlMenu);
		properties.put("showControlMenuByRole", showControlMenuByRole);

		configuration.update(properties);
	}

	private Configuration _getScopedConfiguration(long groupId)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s)(%s=%d))",
				MenuAccessConfiguration.class.getName() + ".scoped",
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
				groupId));

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	private void _manageRoleOfMenuAccess(boolean add, Role role)
		throws Exception {

		for (Group group :
				_groupLocalService.getGroups(
					role.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true)) {

			Dictionary<String, Object> properties;
			Configuration configuration = _getScopedConfiguration(
				group.getGroupId());

			if (configuration == null) {
				configuration = _configurationAdmin.createFactoryConfiguration(
					MenuAccessConfiguration.class.getName() + ".scoped",
					StringPool.QUESTION);

				properties = HashMapDictionaryBuilder.<String, Object>put(
					ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(),
					group.getGroupId()
				).build();
			}
			else {
				properties = configuration.getProperties();
			}

			String roleName = String.valueOf(role.getName());
			String[] rolesCanSeeControlMenu = (String[])properties.get(
				"rolesCanSeeControlMenu");

			if (add) {
				if (rolesCanSeeControlMenu == null) {
					rolesCanSeeControlMenu = new String[] {roleName};
				}
				else if (!ArrayUtil.contains(
							rolesCanSeeControlMenu, roleName)) {

					rolesCanSeeControlMenu = ArrayUtil.append(
						rolesCanSeeControlMenu, roleName);
				}
			}
			else {
				if ((rolesCanSeeControlMenu != null) &&
					ArrayUtil.contains(rolesCanSeeControlMenu, roleName)) {

					rolesCanSeeControlMenu = ArrayUtil.remove(
						rolesCanSeeControlMenu, roleName);
				}
			}

			properties.put("rolesCanSeeControlMenu", rolesCanSeeControlMenu);

			configuration.update(properties);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private GroupLocalService _groupLocalService;

}