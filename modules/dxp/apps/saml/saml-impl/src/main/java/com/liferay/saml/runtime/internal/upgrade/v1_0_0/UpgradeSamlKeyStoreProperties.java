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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Carlos Sierra Andrés
 */
public class UpgradeSamlKeyStoreProperties extends UpgradeProcess {

	public UpgradeSamlKeyStoreProperties(
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