/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.search.engine;

import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.search.index.IndexNameBuilder;

/**
 * @author Adam Brandizzi
 */
public interface SearchEngineFixture {

	public IndexNameBuilder getIndexNameBuilder();

	public SearchEngine getSearchEngine();

	public void setUp() throws Exception;

	public void tearDown() throws Exception;

}