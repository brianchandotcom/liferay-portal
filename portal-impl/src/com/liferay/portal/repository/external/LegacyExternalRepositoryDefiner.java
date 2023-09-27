/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.external;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.repository.RepositoryConfigurationBuilder;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;

/**
 * @author Adolfo Pérez
 */
public class LegacyExternalRepositoryDefiner extends BaseRepositoryDefiner {

	public LegacyExternalRepositoryDefiner(
		String className, RepositoryFactory repositoryFactory,
		ResourceBundleLoader resourceBundleLoader) {

		_className = className;
		_repositoryFactory = repositoryFactory;
		_resourceBundleLoader = resourceBundleLoader;
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public RepositoryConfiguration getRepositoryConfiguration() {
		try {
			if (_repositoryConfiguration != null) {
				return _repositoryConfiguration;
			}

			BaseRepository baseRepository =
				ExternalRepositoryFactoryUtil.getInstance(getClassName());

			@SuppressWarnings("deprecation")
			String[][] supportedParameters =
				baseRepository.getSupportedParameters();

			int size = 0;

			if ((supportedParameters != null) &&
				(supportedParameters[0] != null)) {

				size = supportedParameters[0].length;
			}

			RepositoryConfigurationBuilder repositoryConfigurationBuilder =
				new RepositoryConfigurationBuilder(_resourceBundleLoader);

			for (int i = 0; i < size; i++) {
				repositoryConfigurationBuilder.addParameter(
					supportedParameters[0][i]);
			}

			_repositoryConfiguration = repositoryConfigurationBuilder.build();

			return _repositoryConfiguration;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public boolean isExternalRepository() {
		return true;
	}

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		capabilityRegistry.addSupportedCapability(
			ProcessorCapability.class,
			_portalCapabilityLocator.getProcessorCapability(
				capabilityRegistry.getTarget(),
				ProcessorCapability.ResourceGenerationStrategy.
					ALWAYS_GENERATE));
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	private static volatile PortalCapabilityLocator _portalCapabilityLocator =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortalCapabilityLocator.class,
			LegacyExternalRepositoryDefiner.class, "_portalCapabilityLocator",
			false, true);

	private final String _className;
	private RepositoryConfiguration _repositoryConfiguration;
	private final RepositoryFactory _repositoryFactory;
	private final ResourceBundleLoader _resourceBundleLoader;

}