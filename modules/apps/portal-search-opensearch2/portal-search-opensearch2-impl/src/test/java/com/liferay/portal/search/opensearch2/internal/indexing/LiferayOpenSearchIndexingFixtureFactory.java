/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.indexing;

/**
 * @author André de Oliveira
 */
public class LiferayOpenSearchIndexingFixtureFactory {

	public static OpenSearchIndexingFixtureBuilder builder() {
		return OpenSearchIndexingFixtureFactory.builder(
		).liferayMappingsAddedToIndex(
			true
		).useLiferayIndex(
			true
		);
	}

	public static OpenSearchIndexingFixture getInstance() {
		return _openSearchIndexingFixture;
	}

	private static OpenSearchIndexingFixture _buildInstance() {
		OpenSearchIndexingFixtureBuilder openSearchIndexingFixtureBuilder =
			builder();

		return openSearchIndexingFixtureBuilder.build();
	}

	private static final OpenSearchIndexingFixture _openSearchIndexingFixture =
		_buildInstance();

}