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

package com.liferay.portal.kernel.repository.registry;

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.repository.RepositoryConfigurationBuilder;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseRepositoryDefiner implements RepositoryDefiner {

	public BaseRepositoryDefiner() {
		RepositoryConfigurationBuilder repositoryConfigurationBuilder =
			new RepositoryConfigurationBuilder();

		_repositoryConfiguration = repositoryConfigurationBuilder.build();
	}

	@Override
	public abstract String getClassName();

	@Override
	public RepositoryConfiguration getRepositoryConfiguration() {
		return _repositoryConfiguration;
	}

	@Override
	public String getRepositoryTypeLabel(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public abstract boolean isExternalRepository();

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {
	}

	@Override
	public abstract void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry);

	private final RepositoryConfiguration _repositoryConfiguration;

}