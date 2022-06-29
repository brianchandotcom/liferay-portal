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
@Component(
	immediate = true,
	service = {
		SamlImplUpgradeStepRegistrator.class, UpgradeStepRegistrator.class
	}
)
public class SamlImplUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "0.0.1",
			new SamlConfigurationPreferencesUpgradeProcess(
				_configurationAdmin, _props),
			new SamlKeyStorePropertiesUpgradeProcess(
				_configurationAdmin, _prefsProps),
			new SamlProviderConfigurationPreferencesUpgradeProcess(
				_companyLocalService, _prefsProps, _props,
				_samlProviderConfigurationHelper));

		registry.register(
			"0.0.1", "1.0.0",
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