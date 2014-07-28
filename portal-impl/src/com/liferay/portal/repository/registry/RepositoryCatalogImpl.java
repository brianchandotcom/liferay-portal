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
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.repository.external.LegacyExternalRepositoryRegistryPlugin;
import com.liferay.portal.repository.util.ExternalRepositoryFactory;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryImpl;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class RepositoryCatalogImpl implements RepositoryCatalog {

	@Override
	public Collection<String> getExternalRepositoryClassNames() {
		return Arrays.asList(
			ExternalRepositoryFactoryUtil.getExternalRepositoryClassNames());
	}

	@Override
	public RepositoryConfiguration getRepositoryConfiguration(
		String className) {

		return _repositoryConfigurations.get(className);
	}

	public void loadDefaultRepositoryRegistryPlugins() {
		_repositoryConfigurations.put(
			_liferayRepositoryRegistryPlugin.getClassName(),
			createRepositoryConfiguration(_liferayRepositoryRegistryPlugin));

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		for (String className : PropsValues.DL_REPOSITORY_IMPL) {
			ExternalRepositoryFactory externalRepositoryFactory =
				new ExternalRepositoryFactoryImpl(className, classLoader);

			registerLegacyExternalRepositoryFactory(
				className, externalRepositoryFactory);
		}
	}

	@Override
	public void registerLegacyExternalRepositoryFactory(
		String className, ExternalRepositoryFactory externalRepositoryFactory) {

		ExternalRepositoryFactoryUtil.registerExternalRepositoryFactory(
			className, externalRepositoryFactory);

		RepositoryRegistryPlugin repositoryRegistryPlugin =
			new LegacyExternalRepositoryRegistryPlugin(
				className, _legacyExternalRepositoryCreator);

		_repositoryConfigurations.put(
			repositoryRegistryPlugin.getClassName(),
			createRepositoryConfiguration(repositoryRegistryPlugin));
	}

	public void setLegacyExternalRepositoryCreator(
		RepositoryCreator legacyExternalRepositoryCreator) {

		_legacyExternalRepositoryCreator = legacyExternalRepositoryCreator;
	}

	public void setLiferayRepositoryRegistryPlugin(
		RepositoryRegistryPlugin liferayRepositoryRegistryPlugin) {

		_liferayRepositoryRegistryPlugin = liferayRepositoryRegistryPlugin;
	}

	@Override
	public void unregisterLegacyExternalRepositoryFactory(String className) {
		ExternalRepositoryFactoryUtil.unregisterExternalRepositoryFactory(
			className);

		_repositoryConfigurations.remove(className);
	}

	protected RepositoryConfiguration createRepositoryConfiguration(
		RepositoryRegistryPlugin repositoryRegistryPlugin) {

		DefaultRepositoryRegistry defaultRepositoryRegistry =
			new DefaultRepositoryRegistry();

		repositoryRegistryPlugin.registerCapabilities(
			defaultRepositoryRegistry);
		repositoryRegistryPlugin.registerRepositoryCreator(
			defaultRepositoryRegistry);
		repositoryRegistryPlugin.registerRepositoryEventListeners(
			defaultRepositoryRegistry);

		return defaultRepositoryRegistry;
	}

	private RepositoryCreator _legacyExternalRepositoryCreator;
	private RepositoryRegistryPlugin _liferayRepositoryRegistryPlugin;
	private Map<String, RepositoryConfiguration> _repositoryConfigurations =
		new ConcurrentHashMap<String, RepositoryConfiguration>();

}