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

package com.liferay.config.admin.web.util;

import com.liferay.config.admin.web.model.ConfigurationModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
public class ConfigurationIterator {

	public ConfigurationIterator(List<ConfigurationModel> models) {
		_models = models;
	}

	public List<ConfigurationModel> getResults(int start, int end)
		throws PortalException {

		return ListUtil.subList(_models, start, end);
	}

	public int getTotal() throws PortalException {
		return _models.size();
	}

	private final List<ConfigurationModel> _models;

}