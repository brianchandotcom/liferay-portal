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