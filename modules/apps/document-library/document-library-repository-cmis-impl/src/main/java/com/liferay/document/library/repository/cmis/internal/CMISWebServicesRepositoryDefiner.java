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
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = RepositoryDefiner.class)
public class CMISWebServicesRepositoryDefiner
	extends BaseCMISRepositoryDefiner {

	public CMISWebServicesRepositoryDefiner() {
		RepositoryConfigurationBuilder repositoryConfigurationBuilder =
			new RepositoryConfigurationBuilder(
				getClass(), "content.Language",
				CMISRepositoryConstants.CMIS_WEBSERVICES_ACL_SERVICE_PARAMETER,
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_DISCOVERY_SERVICE_PARAMETER,
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_MULTIFILING_SERVICE_PARAMETER,
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_OBJECT_SERVICE_PARAMETER,
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_NAVIGATION_SERVICE_PARAMETER,
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_POLICY_SERVICE_PARAMETER,
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_RELATIONSHIP_SERVICE_PARAMETER,
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_REPOSITORY_ID_PARAMETER,
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_REPOSITORY_SERVICE_PARAMETER,
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_VERSIONING_SERVICE_PARAMETER);

		_repositoryConfiguration = repositoryConfigurationBuilder.build();
	}

	@Override
	public String getClassName() {
		return CMISWebServicesRepository.class.getName();
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

	@Reference(
		target = "(repository.targetClassName=" + CMISRepositoryConstants.CMIS_WEB_SERVICES_REPOSITORY_CLASSNAME + ")",
		unbind = "-"
	)
	protected void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	private final RepositoryConfiguration _repositoryConfiguration;
	private volatile RepositoryFactory _repositoryFactory;

}