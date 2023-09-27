/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.display;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationScopeDisplay {

	public ConfigurationScopeDisplay(String scope) {
		_scope = scope;
	}

	public void add(ConfigurationEntry configurationEntry) {
		_configurationEntries.add(configurationEntry);
	}

	public List<ConfigurationEntry> getConfigurationEntries() {
		return _configurationEntries;
	}

	public String getScope() {
		return _scope;
	}

	public boolean isEmpty() {
		return _configurationEntries.isEmpty();
	}

	private final List<ConfigurationEntry> _configurationEntries =
		new ArrayList<>();
	private final String _scope;

}