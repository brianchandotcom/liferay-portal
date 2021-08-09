/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.repository;

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.repository.RepositoryConfigurationBuilder;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.sharepoint.soap.repository.constants.SharepointWSConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = RepositoryDefiner.class)
public class SharepointWSRepositoryDefiner extends BaseRepositoryDefiner {

	@Override
	public String getClassName() {
		return SharepointWSRepository.class.getName();
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

	@Activate
	protected void activate() {
		ResourceBundleLoader resourceBundleLoader =
			locale -> ResourceBundleUtil.getBundle(locale, getClass());

		RepositoryConfigurationBuilder repositoryConfigurationBuilder =
			new RepositoryConfigurationBuilder(
				resourceBundleLoader,
				SharepointWSConstants.SHAREPOINT_LIBRARY_NAME,
				SharepointWSConstants.SHAREPOINT_LIBRARY_PATH,
				SharepointWSConstants.SHAREPOINT_SERVER_VERSION,
				SharepointWSConstants.SHAREPOINT_SITE_URL);

		_repositoryConfiguration = repositoryConfigurationBuilder.build();
	}

	@Deactivate
	protected void deactivate() {
		_repositoryConfiguration = null;
	}

	@Reference
	private PortalCapabilityLocator _portalCapabilityLocator;

	private RepositoryConfiguration _repositoryConfiguration;

	@Reference(
		target = "(repository.target.class.name=com.liferay.sharepoint.soap.repository.SharepointWSRepository)"
	)
	private RepositoryFactory _repositoryFactory;

}