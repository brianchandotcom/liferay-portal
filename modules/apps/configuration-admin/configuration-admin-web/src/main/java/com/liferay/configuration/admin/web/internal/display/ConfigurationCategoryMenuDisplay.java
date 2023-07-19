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

package com.liferay.configuration.admin.web.internal.display;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.VerticalNavItemList;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationCategoryMenuDisplay {

	public ConfigurationCategoryMenuDisplay(
		ConfigurationCategoryDisplay configurationCategoryDisplay,
		Set<ConfigurationEntry> configurationEntries) {

		_configurationCategoryDisplay = configurationCategoryDisplay;

		for (ConfigurationEntry configurationEntry : configurationEntries) {
			_addConfigurationEntry(configurationEntry);
		}
	}

	public ConfigurationCategoryDisplay getConfigurationCategoryDisplay() {
		return _configurationCategoryDisplay;
	}

	public Collection<ConfigurationScopeDisplay>
		getConfigurationScopeDisplays() {

		return _configurationScopeDisplays.values();
	}

	public ConfigurationEntry getFirstConfigurationEntry() {
		for (ConfigurationScopeDisplay configurationScopeDisplay :
				_configurationScopeDisplays.values()) {

			List<ConfigurationEntry> configurationEntries =
				configurationScopeDisplay.getConfigurationEntries();

			if (!configurationEntries.isEmpty()) {
				return configurationEntries.get(0);
			}
		}

		return null;
	}

	public VerticalNavItemList getVerticalNavItemList(
		ConfigurationScopeDisplay configurationScopeDisplay,
		ConfigurationEntry configurationEntry, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		List<ConfigurationEntry> configurationEntries =
			configurationScopeDisplay.getConfigurationEntries();

		VerticalNavItemList verticalNavItemList = new VerticalNavItemList();

		for (ConfigurationEntry curConfigurationEntry : configurationEntries) {
			verticalNavItemList.add(
				verticalNavItem -> {
					String name = curConfigurationEntry.getName();

					verticalNavItem.setHref(
						curConfigurationEntry.getEditURL(
							renderRequest, renderResponse));
					verticalNavItem.setLabel(name);
					verticalNavItem.setId(name);
					verticalNavItem.setActive(
						configurationEntry.equals(curConfigurationEntry));
				});
		}

		return verticalNavItemList;
	}

	public boolean isEmpty() {
		for (ConfigurationScopeDisplay configurationScopeDisplay :
				_configurationScopeDisplays.values()) {

			if (!configurationScopeDisplay.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	private void _addConfigurationEntry(ConfigurationEntry configurationEntry) {
		ConfigurationScopeDisplay configurationScopeDisplay =
			_configurationScopeDisplays.get(configurationEntry.getScope());

		if (configurationScopeDisplay == null) {
			configurationScopeDisplay = new ConfigurationScopeDisplay(
				configurationEntry.getScope());

			_configurationScopeDisplays.put(
				configurationEntry.getScope(), configurationScopeDisplay);
		}

		configurationScopeDisplay.add(configurationEntry);
	}

	private static final String[] _UI_ORDERED_SCOPES = {
		ExtendedObjectClassDefinition.Scope.SYSTEM.toString(),
		ExtendedObjectClassDefinition.Scope.COMPANY.toString(),
		ExtendedObjectClassDefinition.Scope.GROUP.toString(),
		ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.toString()
	};

	private final ConfigurationCategoryDisplay _configurationCategoryDisplay;
	private final Map<String, ConfigurationScopeDisplay>
		_configurationScopeDisplays = LinkedHashMapBuilder.put(
			Arrays.asList(_UI_ORDERED_SCOPES), ConfigurationScopeDisplay::new
		).build();

}