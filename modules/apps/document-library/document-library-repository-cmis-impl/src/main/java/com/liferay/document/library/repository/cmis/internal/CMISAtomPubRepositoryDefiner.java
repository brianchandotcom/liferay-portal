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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.document.library.repository.cmis.internal.constants.CMISRepositoryConstants;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.repository.RepositoryConfigurationBuilder;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = RepositoryDefiner.class)
public class CMISAtomPubRepositoryDefiner extends BaseCMISRepositoryDefiner {

	public CMISAtomPubRepositoryDefiner() {
		RepositoryConfigurationBuilder repositoryConfigurationBuilder =
			new RepositoryConfigurationBuilder(
				getResourceBundleLoader(),
				CMISRepositoryConstants.CMIS_ATOMPUB_REPOSITORY_ID_PARAMETER,
				CMISRepositoryConstants.CMIS_ATOMPUB_URL_PARAMETER);

		_repositoryConfiguration = repositoryConfigurationBuilder.build();
	}

	@Override
	public String getClassName() {
		return CMISAtomPubRepository.class.getName();
	}

	@Override
	public RepositoryConfiguration getRepositoryConfiguration() {
		return _repositoryConfiguration;
	}

	@Override
	public boolean isExternalRepository() {
		return true;
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	@Override
	protected PortalCapabilityLocator getPortalCapabilityLocator() {
		return _portalCapabilityLocator;
	}

	@Reference(
		target = "(repository.target.class.name=" + CMISRepositoryConstants.CMIS_ATOMPUB_REPOSITORY_CLASSNAME + ")",
		unbind = "-"
	)
	protected void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	@Reference
	private PortalCapabilityLocator _portalCapabilityLocator;

	private final RepositoryConfiguration _repositoryConfiguration;
	private RepositoryFactory _repositoryFactory;

}