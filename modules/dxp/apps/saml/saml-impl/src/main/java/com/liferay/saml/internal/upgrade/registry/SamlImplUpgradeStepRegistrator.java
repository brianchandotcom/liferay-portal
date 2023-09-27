/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.internal.upgrade.registry;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.saml.internal.upgrade.v1_0_0.SamlConfigurationPreferencesUpgradeProcess;
import com.liferay.saml.internal.upgrade.v1_0_0.SamlIdpSsoSessionMaxAgePropertyUpgradeProcess;
import com.liferay.saml.internal.upgrade.v1_0_0.SamlKeyStorePropertiesUpgradeProcess;
import com.liferay.saml.internal.upgrade.v1_0_0.SamlProviderConfigurationPreferencesUpgradeProcess;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(service = UpgradeStepRegistrator.class)
public class SamlImplUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "0.0.2",
			new SamlConfigurationPreferencesUpgradeProcess(
				_configurationAdmin, _props),
			new SamlKeyStorePropertiesUpgradeProcess(
				_configurationAdmin, _prefsProps),
			new SamlProviderConfigurationPreferencesUpgradeProcess(
				_companyLocalService, _prefsProps, _props,
				_samlProviderConfigurationHelper));

		registry.register(
			"0.0.2", "1.0.0",
			new SamlIdpSsoSessionMaxAgePropertyUpgradeProcess(
				_configurationAdmin, _props));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private PrefsProps _prefsProps;

	@Reference
	private Props _props;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}