/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.keymanager.internal.profile;

import com.liferay.keymanager.internal.profile.configuration.KeyManagerCustomProfileConfiguration;
import com.liferay.keymanager.spi.profile.KeyManagerProfile;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
@Component(
	configurationPid = "com.liferay.keymanager.internal.profile.configuration.KeyManagerCustomProfileConfiguration",
	property = "keymanager.profile.id=custom", service = KeyManagerProfile.class
)
public class CustomKeyManagerProfile implements KeyManagerProfile {

	@Override
	public void bootstrap() throws Exception {
	}

	@Override
	public String getCompanyDekProviderId() {
		return _keyManagerCustomProfileConfiguration.companyDekProviderId();
	}

	@Override
	public String getCompanyKekProviderId() {
		return _keyManagerCustomProfileConfiguration.companyKekProviderId();
	}

	@Override
	public String getCompanySecretProviderId() {
		return _keyManagerCustomProfileConfiguration.companySecretProviderId();
	}

	@Override
	public String getProfileId() {
		return "custom";
	}

	@Override
	public String getSystemDekProviderId() {
		return _keyManagerCustomProfileConfiguration.systemDekProviderId();
	}

	@Override
	public String getSystemKekProviderId() {
		return _keyManagerCustomProfileConfiguration.systemKekProviderId();
	}

	@Override
	public String getSystemSecretProviderId() {
		return _keyManagerCustomProfileConfiguration.systemSecretProviderId();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_keyManagerCustomProfileConfiguration =
			ConfigurableUtil.createConfigurable(
				KeyManagerCustomProfileConfiguration.class, properties);
	}

	private volatile KeyManagerCustomProfileConfiguration
		_keyManagerCustomProfileConfiguration;

}