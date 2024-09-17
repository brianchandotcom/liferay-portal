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
	 * @param key The name of the setting whose value is returned.
	 */
	public String get(String key);

	/**
	 * Adds one or more search engine settings from contributors.
	 *
	 * @param source The settings in either JSON or YAML format.
	 */
	public void loadFromSource(String source);

	/**
	 * Adds a single search engine setting from contributors.
	 *
	 * @param key The name of the setting to be added.
	 * @param value The value of the setting to be added.
	 */
	public void put(String key, String value);

}