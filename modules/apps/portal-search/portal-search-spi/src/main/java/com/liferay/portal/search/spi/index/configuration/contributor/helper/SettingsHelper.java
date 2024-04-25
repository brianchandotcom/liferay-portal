/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.index.configuration.contributor.helper;

/**
 * @author Adam Brandizzi
 */
public interface SettingsHelper {

	/**
	 * Returns a setting value based on the setting key.
	 *
	 * @param key The name of the setting whose value will be returned.
	 */
	public String get(String key);

	/**
	 * This method loads the passed in settings into the Settings.Builder.
	 *
	 * @param source Either the JSON or YAML settings.
	 */
	public void loadFromSource(String source);

	/**
	 * This method allows contributors to add a single search engine setting.
	 *
	 * @param key The name of the setting to be added.
	 * @param value The value of the setting to be added.
	 * @throws Exception if there is an error adding the setting to the search engine.
	 */
	public void put(String key, String value);

}