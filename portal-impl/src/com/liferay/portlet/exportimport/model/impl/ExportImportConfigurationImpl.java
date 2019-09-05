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

package com.liferay.portlet.exportimport.model.impl;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationImpl
	extends ExportImportConfigurationBaseImpl {

	@Override
	public Map<String, Serializable> getSettingsMap() {
		if (_settingsMap != null) {
			return _settingsMap;
		}

		_settingsMap = (Map<String, Serializable>)JSONFactoryUtil.deserialize(
			getSettings());

		return _settingsMap;
	}

	private Map<String, Serializable> _settingsMap;

}