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

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.kernel.repository.registry.RepositoryRegistryPlugin;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.repository.external.ExternalRepositoryRegistryPlugin;
import com.liferay.portal.repository.util.ExternalRepositoryFactory;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryImpl;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.util.PropsValues;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class RepositoryCatalogImpl implements RepositoryCatalog {

	public RepositoryCatalogImpl() {
		_externalRepositoriesClassNames = new ConcurrentHashSet<String>();
		_repositoryRegistryPlugins =
			new ConcurrentHashMap<Long, RepositoryConfiguration>();
	}

	@Override
	public RepositoryConfiguration getConfiguration(long classNameId) {
		return _repositoryRegistryPlugins.get(classNameId);
	}

	@Override
	public Collection<String> getExternalRepositoryClassNames() {
		return _externalRepositoriesClassNames;
	}

	public void loadBuiltinRegistryPlugins() {
		registerRepositoryRegistryPlugin(_liferayRepositoryRegistryPlugin);

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

		long classNameId = _classNameLocalService.getClassNameId(className);

		RepositoryRegistryPlugin repositoryRegistryPlugin =
			new ExternalRepositoryRegistryPlugin(
				className, classNameId, _externalRepositoryCreator);

		registerRepositoryRegistryPlugin(repositoryRegistryPlugin);
	}

	@Override
	public void registerRepositoryRegistryPlugin(
		RepositoryRegistryPlugin repositoryRegistryPlugin) {

		if (repositoryRegistryPlugin.isExternalRepository()) {
			_externalRepositoriesClassNames.add(
				repositoryRegistryPlugin.getClassName());
		}

		_repositoryRegistryPlugins.put(
			repositoryRegistryPlugin.getClassNameId(),
			createRepositoryConfiguration(repositoryRegistryPlugin));
	}

	public void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	public void setExternalRepositoryCreator(
		RepositoryCreator repositoryCreator) {

		_externalRepositoryCreator = repositoryCreator;
	}

	public void setLiferayRepositoryRegistryPlugin(
		RepositoryRegistryPlugin liferayRepositoryRegistryPlugin) {

		_liferayRepositoryRegistryPlugin = liferayRepositoryRegistryPlugin;
	}

	@Override
	public void unregisterLegacyExternalRepositoryFactory(String className) {
		ExternalRepositoryFactoryUtil.unregisterExternalRepositoryFactory(
			className);

		long classNameId = _classNameLocalService.getClassNameId(className);

		_repositoryRegistryPlugins.remove(classNameId);
	}

	@Override
	public void unregisterRepositoryRegistryPlugin(String className) {
		long classNameId = _classNameLocalService.getClassNameId(className);

		_repositoryRegistryPlugins.remove(classNameId);
		_externalRepositoriesClassNames.remove(className);
	}

	protected RepositoryConfiguration createRepositoryConfiguration(
		RepositoryRegistryPlugin repositoryRegistryPlugin) {

		DefaultRepositoryRegistry repositoryRegistry =
			new DefaultRepositoryRegistry();

		repositoryRegistryPlugin.registerRepositoryCreator(repositoryRegistry);
		repositoryRegistryPlugin.registerCapabilities(repositoryRegistry);
		repositoryRegistryPlugin.registerRepositoryEventListeners(
			repositoryRegistry);

		return repositoryRegistry;
	}

	private ClassNameLocalService _classNameLocalService;
	private Set<String> _externalRepositoriesClassNames;
	private RepositoryCreator _externalRepositoryCreator;
	private RepositoryRegistryPlugin _liferayRepositoryRegistryPlugin;
	private Map<Long, RepositoryConfiguration> _repositoryRegistryPlugins;

}