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

package com.liferay.saml.runtime.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.internal.constants.LegacySamlPropsKeys;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Tomas Polesovsky
 */
public class UpgradeSamlIdpSsoSessionMaxAgeProperty extends BaseUpgradeSaml {

	public UpgradeSamlIdpSsoSessionMaxAgeProperty(
		ConfigurationAdmin configurationAdmin, Props props) {

		_configurationAdmin = configurationAdmin;
		_props = props;
	}

	@Override
	public void doUpgrade() throws Exception {
		String samlIdpSsoSessionMaxAge = _props.get(
			LegacySamlPropsKeys.SAML_IDP_SSO_SESSION_MAX_AGE);

		if (Validator.isNull(samlIdpSsoSessionMaxAge)) {
			samlIdpSsoSessionMaxAge = getDefaultValue(
				LegacySamlPropsKeys.SAML_IDP_SSO_SESSION_MAX_AGE);
		}

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=com.liferay.saml.runtime.configuration." +
				"SamlProviderConfiguration)");

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				properties = new Hashtable<>();
			}

			String samlIdpSessionMaximumAgeProperty = (String)properties.get(
				LegacySamlPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE);

			long samlIdpSessionMaximumAge = GetterUtil.getLong(
				samlIdpSessionMaximumAgeProperty);

			if (samlIdpSessionMaximumAge > 0) {
				continue;
			}

			properties.put(
				LegacySamlPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE,
				samlIdpSsoSessionMaxAge);

			configuration.update(properties);
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final Props _props;

}