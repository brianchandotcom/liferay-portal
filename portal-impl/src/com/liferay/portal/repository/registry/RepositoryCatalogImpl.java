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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.kernel.repository.registry.RepositoryRegistryPlugin;
import com.liferay.portal.repository.external.LegacyExternalRepositoryRegistryPlugin;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class RepositoryCatalogImpl implements RepositoryCatalog {

	@Override
	public RepositoryConfiguration getRepositoryConfiguration(
		String className) {

		return _repositoryConfigurations.get(className);
	}

	public void loadDefaultRepositoryRegistryPlugins() {
		_repositoryConfigurations.put(
			_liferayRepositoryRegistryPlugin.getClassName(),
			createRepositoryConfiguration(_liferayRepositoryRegistryPlugin));

		for (String className : PropsValues.DL_REPOSITORY_IMPL) {
			RepositoryRegistryPlugin repositoryRegistryPlugin =
				new LegacyExternalRepositoryRegistryPlugin(
					className, _legacyExternalRepositoryCreator);

			_repositoryConfigurations.put(
				repositoryRegistryPlugin.getClassName(),
				createRepositoryConfiguration(repositoryRegistryPlugin));
		}
	}

	public void setLegacyExternalRepositoryCreator(
		RepositoryCreator legacyExternalRepositoryCreator) {

		_legacyExternalRepositoryCreator = legacyExternalRepositoryCreator;
	}

	public void setLiferayRepositoryRegistryPlugin(
		RepositoryRegistryPlugin liferayRepositoryRegistryPlugin) {

		_liferayRepositoryRegistryPlugin = liferayRepositoryRegistryPlugin;
	}

	protected RepositoryConfiguration createRepositoryConfiguration(
		RepositoryRegistryPlugin repositoryRegistryPlugin) {

		DefaultRepositoryRegistry defaultRepositoryRegistry =
			new DefaultRepositoryRegistry();

		repositoryRegistryPlugin.registerCapabilities(
			defaultRepositoryRegistry);
		repositoryRegistryPlugin.registerRepositoryCreator(
			defaultRepositoryRegistry);

		return defaultRepositoryRegistry;
	}

	private RepositoryCreator _legacyExternalRepositoryCreator;
	private RepositoryRegistryPlugin _liferayRepositoryRegistryPlugin;
	private Map<String, RepositoryConfiguration> _repositoryConfigurations =
		new HashMap<String, RepositoryConfiguration>();

}