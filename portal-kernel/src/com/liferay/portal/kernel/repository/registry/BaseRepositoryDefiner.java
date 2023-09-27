/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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