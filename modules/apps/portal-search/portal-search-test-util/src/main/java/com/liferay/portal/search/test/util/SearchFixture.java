/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util;

import com.liferay.portal.search.index.IndexStatusManager;

import java.util.concurrent.TimeUnit;

/**
 * @author André de Oliveira
 */
public class SearchFixture {

	public static void setUp(IndexStatusManager indexStatusManager) {
		SearchFixture searchFixture = new SearchFixture(indexStatusManager);

		searchFixture.setUp();
	}

	public static void tearDown(IndexStatusManager indexStatusManager) {
		SearchFixture searchFixture = new SearchFixture(indexStatusManager);

		searchFixture.tearDown();
	}

	public SearchFixture(IndexStatusManager indexStatusManager) {
		_indexStatusManager = indexStatusManager;
	}

	public void setUp() {
		retry(() -> _indexStatusManager.requireIndexReadWrite(true));
	}

	public void tearDown() {
		_indexStatusManager.requireIndexReadWrite(false);
	}

	protected void retry(Runnable runnable) {
		SearchRetryFixture.builder(
		).timeout(
			10, TimeUnit.SECONDS
		).build(
		).assertSearch(
			runnable
		);
	}

	private final IndexStatusManager _indexStatusManager;

}