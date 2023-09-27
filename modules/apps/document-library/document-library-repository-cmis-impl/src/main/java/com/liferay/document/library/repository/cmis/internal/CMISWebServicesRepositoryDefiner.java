/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class CMISWebServicesRepositoryDefiner
	extends BaseCMISRepositoryDefiner {

	public CMISWebServicesRepositoryDefiner() {
		RepositoryConfigurationBuilder repositoryConfigurationBuilder =
			new RepositoryConfigurationBuilder(
				getResourceBundleLoader(),
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

	@Override
	protected PortalCapabilityLocator getPortalCapabilityLocator() {
		return _portalCapabilityLocator;
	}

	@Reference
	private PortalCapabilityLocator _portalCapabilityLocator;

	private final RepositoryConfiguration _repositoryConfiguration;

	@Reference(
		target = "(repository.target.class.name=" + CMISRepositoryConstants.CMIS_WEB_SERVICES_REPOSITORY_CLASS_NAME + ")"
	)
	private RepositoryFactory _repositoryFactory;

}