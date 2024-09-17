/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.index.configuration.contributor.helper;

/**
 * @author Adam Brandizzi
 */
public interface MappingsHelper {

	/**
	 * Adds mappings to the underlying search engine based on the provided JSON
	 * source.
	 *
	 * @param source The JSON string representing the search engine mappings to
	 *        add.
	 */
	public void putMappings(String source);

}