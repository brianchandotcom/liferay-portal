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

package com.liferay.configuration.admin.web.internal.display;

import com.liferay.configuration.admin.category.ConfigurationCategory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationCategoryDisplay {

	public ConfigurationCategoryDisplay(
		ConfigurationCategory configurationCategory) {

		_configurationCategory = configurationCategory;
	}

	public String getCategoryIcon() {
		return _configurationCategory.getCategoryIcon();
	}

	public String getCategoryKey() {
		return _configurationCategory.getCategoryKey();
	}

	public String getCategoryLabel(Locale locale) {
		return _getMessage(
			locale, "category." + _configurationCategory.getCategoryKey());
	}

	public String getSectionLabel(Locale locale) {
		return _getMessage(
			locale,
			"category-section." + _configurationCategory.getCategorySection());
	}

	private String _getMessage(Locale locale, String key) {
		ResourceBundle resourceBundle;

		try {
			resourceBundle = new AggregateResourceBundle(
				ResourceBundleUtil.getBundle(
					locale, _configurationCategory.getClass()),
				ResourceBundleUtil.getBundle(
					locale, ConfigurationCategoryDisplay.class));
		}
		catch (MissingResourceException mre) {
			if (_log.isWarnEnabled()) {
				_log.warn(mre, mre);
			}

			resourceBundle = ResourceBundleUtil.getBundle(
				locale, ConfigurationCategoryDisplay.class);
		}

		return LanguageUtil.get(resourceBundle, key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationCategoryDisplay.class);

	private final ConfigurationCategory _configurationCategory;

}