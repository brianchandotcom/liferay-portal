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
	protected void doUpgrade() throws Exception {
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