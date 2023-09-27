/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.settings;

/**
 * @author Adam Brandizzi
 */
public interface IndexSettingsContributor {

	public void contribute(
		String indexName, TypeMappingsHelper typeMappingsHelper);

	public void populate(IndexSettingsHelper indexSettingsHelper);

}