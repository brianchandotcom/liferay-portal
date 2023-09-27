/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = new AggregateResourceBundle(
				ResourceBundleUtil.getBundle(
					locale, _configurationCategory.getClass()),
				ResourceBundleUtil.getBundle(
					locale, ConfigurationCategoryDisplay.class));
		}
		catch (MissingResourceException missingResourceException) {
			if (_log.isWarnEnabled()) {
				_log.warn(missingResourceException);
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