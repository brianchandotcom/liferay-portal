/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import java.util.Collection;

/**
 * @author Bryan Engler
 */
public interface IndexConfigurationDynamicUpdatesExecutor {

	public void execute(long companyId);

	public void executePutMappings(
		String indexName, Collection<String> mappings);

	public void executeUpdateSettings(
		String indexName, Collection<String> settings);

}