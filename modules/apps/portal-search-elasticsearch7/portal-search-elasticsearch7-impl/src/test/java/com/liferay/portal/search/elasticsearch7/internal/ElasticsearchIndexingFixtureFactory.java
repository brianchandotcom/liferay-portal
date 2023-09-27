/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal;

/**
 * @author André de Oliveira
 */
public class ElasticsearchIndexingFixtureFactory {

	public static ElasticsearchIndexingFixtureBuilder builder() {
		return new ElasticsearchIndexingFixtureBuilder();
	}

	public static ElasticsearchIndexingFixture getInstance() {
		return _elasticsearchIndexingFixture;
	}

	private static ElasticsearchIndexingFixture _buildInstance() {
		ElasticsearchIndexingFixtureBuilder
			elasticsearchIndexingFixtureBuilder = builder();

		return elasticsearchIndexingFixtureBuilder.build();
	}

	private static final ElasticsearchIndexingFixture
		_elasticsearchIndexingFixture = _buildInstance();

}