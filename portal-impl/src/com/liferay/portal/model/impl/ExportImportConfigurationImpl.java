/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationConstants;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationImpl
	extends ExportImportConfigurationBaseImpl {

	public ExportImportConfigurationImpl() {
	}

	@Override
	public Map<String, Serializable> getSettingsMap() {
		if (_settingsMap != null) {
			return _settingsMap;
		}

		String settings = getSettings();

		_settingsMap = (Map<String, Serializable>)JSONFactoryUtil.deserialize(
			settings);

		return _settingsMap;
	}

	public String getTypeName(Locale locale) {
		switch (getType()) {
			case ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT:
				return LanguageUtil.get(
					locale,
					ExportImportConfigurationConstants.
						TYPE_EXPORT_LAYOUT_LABEL);

			case ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL:
				return LanguageUtil.get(
					locale,
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_LOCAL_LABEL);

			case ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE:
				return LanguageUtil.get(
					locale,
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_REMOTE_LABEL);

			case ExportImportConfigurationConstants.
				TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL:
					return LanguageUtil.get(
						locale,
						ExportImportConfigurationConstants.
							TYPE_SCHEDULED_PUBLISH_LAYOUT_LOCAL_LABEL);

			case ExportImportConfigurationConstants.
				TYPE_SCHEDULED_PUBLISH_LAYOUT_REMOTE:
					return LanguageUtil.get(
						locale,
						ExportImportConfigurationConstants.
							TYPE_SCHEDULED_PUBLISH_LAYOUT_REMOTE_LABEL);

			default :
				return LanguageUtil.get(locale, "undefined");
		}
	}

	private Map<String, Serializable> _settingsMap;

}