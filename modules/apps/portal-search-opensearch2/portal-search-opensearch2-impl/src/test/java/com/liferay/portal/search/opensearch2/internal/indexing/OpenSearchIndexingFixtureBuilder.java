/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.indexing;

import com.liferay.portal.search.opensearch2.internal.connection.TestOpenSearchConnectionManager;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class OpenSearchIndexingFixtureBuilder {

	public OpenSearchIndexingFixture build() {
		OpenSearchIndexingFixture openSearchIndexingFixture =
			new OpenSearchIndexingFixture();

		openSearchIndexingFixture.setTestOpenSearchConnectionManager(
			_getTestOpenSearchConnectionManager());
		openSearchIndexingFixture.setLiferayMappingsAddedToIndex(
			_liferayMappingsAddedToIndex);
		openSearchIndexingFixture.setUseLiferayMappings(_useLiferayMappings);

		return openSearchIndexingFixture;
	}

	public OpenSearchIndexingFixtureBuilder liferayMappingsAddedToIndex(
		boolean liferayMappingsAddedToIndex) {

		_liferayMappingsAddedToIndex = liferayMappingsAddedToIndex;

		return this;
	}

	public OpenSearchIndexingFixtureBuilder testOpenSearchConnectionManager(
		TestOpenSearchConnectionManager testOpenSearchConnectionManager) {

		_testOpenSearchConnectionManager = testOpenSearchConnectionManager;

		return this;
	}

	public OpenSearchIndexingFixtureBuilder useLiferayIndex(
		boolean useLiferayIndex) {

		_useLiferayMappings = useLiferayIndex;

		return this;
	}

	private TestOpenSearchConnectionManager
		_getTestOpenSearchConnectionManager() {

		if (_testOpenSearchConnectionManager != null) {
			return _testOpenSearchConnectionManager;
		}

		return new TestOpenSearchConnectionManager();
	}

	private boolean _liferayMappingsAddedToIndex;
	private TestOpenSearchConnectionManager _testOpenSearchConnectionManager;
	private boolean _useLiferayMappings;

}