/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
public class ConfigurationModelIterator {

	public ConfigurationModelIterator(
		Collection<ConfigurationModel> configurationModels) {

		_configurationModels = new ArrayList<>(configurationModels);
	}

	public ConfigurationModelIterator(
		List<ConfigurationModel> configurationModels) {

		_configurationModels = configurationModels;
	}

	public List<ConfigurationModel> getResults() {
		return _configurationModels;
	}

	public List<ConfigurationModel> getResults(int start, int end) {
		return ListUtil.subList(_configurationModels, start, end);
	}

	public int getTotal() {
		return _configurationModels.size();
	}

	private final List<ConfigurationModel> _configurationModels;

}