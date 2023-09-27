/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.web.internal.display.ConfigurationEntry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
public class ConfigurationEntryIterator {

	public ConfigurationEntryIterator(
		Collection<ConfigurationEntry> configurationEntries) {

		_configurationEntries = new ArrayList<>(configurationEntries);
	}

	public ConfigurationEntryIterator(
		List<ConfigurationEntry> configurationEntries) {

		_configurationEntries = configurationEntries;
	}

	public List<ConfigurationEntry> getResults() {
		return _configurationEntries;
	}

	public List<ConfigurationEntry> getResults(int start, int end) {
		return ListUtil.subList(_configurationEntries, start, end);
	}

	public int getTotal() {
		return _configurationEntries.size();
	}

	private final List<ConfigurationEntry> _configurationEntries;

}