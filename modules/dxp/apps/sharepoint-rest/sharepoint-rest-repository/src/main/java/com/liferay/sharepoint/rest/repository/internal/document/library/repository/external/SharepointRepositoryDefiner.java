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

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external;

import com.liferay.document.library.repository.authorization.capability.AuthorizationCapability;
import com.liferay.document.library.repository.authorization.oauth2.TokenStore;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.repository.RepositoryConfigurationBuilder;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.util.CacheResourceBundleLoader;
import com.liferay.portal.kernel.util.ClassResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.sharepoint.rest.repository.internal.configuration.SharepointRepositoryConfiguration;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.capability.SharepointRepositoryAuthorizationCapability;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.SharepointRepositoryTokenBrokerFactory;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.sharepoint.rest.repository.internal.configuration.SharepointRepositoryConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = RepositoryDefiner.class
)
public class SharepointRepositoryDefiner implements RepositoryDefiner {

	@Override
	public String getClassName() {
		return SharepointExtRepository.class.getName() +
			_sharepointRepositoryConfiguration.name();
	}

	@Override
	public RepositoryConfiguration getRepositoryConfiguration() {
		RepositoryConfigurationBuilder repositoryConfigurationBuilder =
			new RepositoryConfigurationBuilder(_resourceBundleLoader);

		repositoryConfigurationBuilder.addParameter("library-path");
		repositoryConfigurationBuilder.addParameter("site-absolute-url");

		return repositoryConfigurationBuilder.build();
	}

	@Override
	public String getRepositoryTypeLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		String label = ResourceBundleUtil.getString(
			resourceBundle, "sharepoint");

		return String.format(
			"%s (%s)", label, _sharepointRepositoryConfiguration.name());
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
				ProcessorCapability.
					ResourceGenerationStrategy.ALWAYS_GENERATE));

		capabilityRegistry.addExportedCapability(
			AuthorizationCapability.class,
			new SharepointRepositoryAuthorizationCapability(
				_tokenStore, _sharepointRepositoryConfiguration,
				_sharepointRepositoryTokenBrokerFactory.create(
					_sharepointRepositoryConfiguration)));
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(
			_repositoryFactoryProvider.createForConfiguration(
				_sharepointRepositoryConfiguration));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_sharepointRepositoryConfiguration =
			ConfigurableUtil.createConfigurable(
				SharepointRepositoryConfiguration.class, properties);
	}

	@Reference
	private PortalCapabilityLocator _portalCapabilityLocator;

	@Reference
	private SharepointRepositoryFactoryProvider _repositoryFactoryProvider;

	private final ResourceBundleLoader _resourceBundleLoader =
		new CacheResourceBundleLoader(
			new ClassResourceBundleLoader(
				"content.Language", SharepointRepositoryDefiner.class));
	private SharepointRepositoryConfiguration
		_sharepointRepositoryConfiguration;

	@Reference
	private SharepointRepositoryTokenBrokerFactory
		_sharepointRepositoryTokenBrokerFactory;

	@Reference
	private TokenStore _tokenStore;

}