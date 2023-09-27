/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.settings;

/**
 * @author André de Oliveira
 */
public interface ClientSettingsHelper {

	public void put(String setting, String value);

	public void putArray(String setting, String... values);

}