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

package com.liferay.saml.runtime.internal.upgrade;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.internal.upgrade.v1_0_0.UpgradeSamlConfigurationPreferences;
import com.liferay.saml.runtime.internal.upgrade.v1_0_0.UpgradeSamlIdpSsoSessionMaxAgeProperty;
import com.liferay.saml.runtime.internal.upgrade.v1_0_0.UpgradeSamlKeyStoreProperties;
import com.liferay.saml.runtime.internal.upgrade.v1_0_0.UpgradeSamlProviderConfigurationPreferences;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	service = {SamlImplUpgrade.class, UpgradeStepRegistrator.class}
)
public class SamlImplUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.saml.impl", "0.0.0", "0.0.1",
			new UpgradeSamlConfigurationPreferences(
				_configurationAdmin, _props),
			new UpgradeSamlKeyStoreProperties(_configurationAdmin, _prefsProps),
			new UpgradeSamlProviderConfigurationPreferences(
				_companyLocalService, _prefsProps, _props,
				_samlProviderConfigurationHelper));

		registry.register(
			"com.liferay.saml.impl", "0.0.1", "1.0.0",
			new UpgradeSamlIdpSsoSessionMaxAgeProperty(
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