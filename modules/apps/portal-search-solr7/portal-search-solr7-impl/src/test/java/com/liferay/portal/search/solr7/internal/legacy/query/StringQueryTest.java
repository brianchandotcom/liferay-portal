/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.solr7.internal.legacy.query;

import com.liferay.portal.search.solr7.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.legacy.query.BaseStringQueryTestCase;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class StringQueryTest extends BaseStringQueryTestCase {

	@Test
	public void testBooleanOperatorNotDeepSolr() throws Exception {
		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch("+(NOT alpha) +charlie", Collections.emptyList());
	}

	@Test
	public void testPrefixOperatorMustNotWithBooleanOperatorOrSolr()
		throws Exception {

		addDocuments("alpha bravo", "alpha charlie", "charlie delta");

		assertSearch(
			"(-bravo) OR (alpha)",
			Arrays.asList("alpha bravo", "alpha charlie"));
		assertSearch(
			"(-bravo) OR alpha", Arrays.asList("alpha bravo", "alpha charlie"));
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture();
	}

}