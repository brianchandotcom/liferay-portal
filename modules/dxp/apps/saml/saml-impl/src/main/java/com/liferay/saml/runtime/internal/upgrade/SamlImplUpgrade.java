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

package com.liferay.saml.runtime.internal.upgrade;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.internal.upgrade.v1_0_0.UpgradeSamlConfigurationPreferences;
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
			"com.liferay.saml.impl", "0.0.0", "1.0.0",
			new UpgradeSamlConfigurationPreferences(
				_configurationAdmin, _props),
			new UpgradeSamlKeyStoreProperties(_configurationAdmin, _prefsProps),
			new UpgradeSamlProviderConfigurationPreferences(
				_companyLocalService, _prefsProps, _props,
				_samlProviderConfigurationHelper));
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