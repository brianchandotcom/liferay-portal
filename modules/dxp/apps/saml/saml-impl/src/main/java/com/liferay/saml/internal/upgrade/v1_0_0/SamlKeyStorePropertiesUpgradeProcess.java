/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Carlos Sierra Andrés
 */
public class SamlKeyStorePropertiesUpgradeProcess extends UpgradeProcess {

	public SamlKeyStorePropertiesUpgradeProcess(
		ConfigurationAdmin configurationAdmin, PrefsProps prefsProps) {

		_configurationAdmin = configurationAdmin;
		_prefsProps = prefsProps;
	}

	@Override
	public void doUpgrade() throws Exception {
		String samlKeyStoreManagerImpl = _prefsProps.getString(
			"saml.keystore.manager.impl");

		if (Validator.isNull(samlKeyStoreManagerImpl)) {
			return;
		}

		Configuration configuration = _configurationAdmin.getConfiguration(
			"com.liferay.saml.runtime.configuration." +
				"SamlKeyStoreManagerConfiguration",
			StringPool.QUESTION);

		Dictionary<String, Object> properties = new Hashtable<>();

		String filterString = String.format(
			"(component.name=%s)", samlKeyStoreManagerImpl);

		properties.put("KeyStoreManager.target", filterString);

		configuration.update(properties);
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}