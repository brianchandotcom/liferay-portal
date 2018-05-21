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