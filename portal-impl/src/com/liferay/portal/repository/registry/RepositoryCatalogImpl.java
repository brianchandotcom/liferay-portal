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
import com.liferay.portal.repository.external.ExternalRepositoryRegistryPlugin;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class RepositoryCatalogImpl implements RepositoryCatalog {

	@Override
	public RepositoryConfiguration getConfiguration(long classNameId) {
		return _repositoryRegistryPlugins.get(classNameId);
	}

	public void loadBuiltinRegistryPlugins() {
		_repositoryRegistryPlugins =
			new HashMap<Long, RepositoryConfiguration>();

		_repositoryRegistryPlugins.put(
			_liferayRepositoryRegistryPlugin.getClassNameId(),
			createRepositoryConfiguration(_liferayRepositoryRegistryPlugin));

		for (String className : PropsValues.DL_REPOSITORY_IMPL) {
			long classNameId = _classNameLocalService.getClassNameId(className);

			RepositoryRegistryPlugin repositoryRegistryPlugin =
				new ExternalRepositoryRegistryPlugin(
					classNameId, _externalRepositoryCreator);

			_repositoryRegistryPlugins.put(
				repositoryRegistryPlugin.getClassNameId(),
				createRepositoryConfiguration(repositoryRegistryPlugin));
		}
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
	private RepositoryCreator _externalRepositoryCreator;
	private RepositoryRegistryPlugin _liferayRepositoryRegistryPlugin;
	private Map<Long, RepositoryConfiguration> _repositoryRegistryPlugins;

}