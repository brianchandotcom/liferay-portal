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