/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection.helper;

import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;

import org.elasticsearch.client.indices.CreateIndexRequest;

/**
 * @author André de Oliveira
 */
public interface IndexCreationHelper {

	public void contribute(CreateIndexRequest createIndexRequest);

	public void contributeIndexSettings(SettingsBuilder settingsBuilder);

	public void whenIndexCreated(String indexName);

}