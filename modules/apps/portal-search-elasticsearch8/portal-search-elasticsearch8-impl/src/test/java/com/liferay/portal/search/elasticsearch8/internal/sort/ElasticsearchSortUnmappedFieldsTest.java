/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.sort;

import com.liferay.portal.search.elasticsearch8.internal.indexing.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.sort.BaseSortUnmappedFieldsTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class ElasticsearchSortUnmappedFieldsTest
	extends BaseSortUnmappedFieldsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Ignore
	@Override
	@Test
	public void testDefaultIsTextMappingWithEmptyResults() {
	}

	@Ignore
	@Override
	@Test
	public void testFirstNameIsTextMappingWithEmptyResults() {
	}

	@Ignore
	@Override
	@Test
	public void testFirstNameOnNewIndex() {
	}

	@Ignore
	@Override
	@Test
	public void testSortableKeyword() {
	}

	@Ignore
	@Override
	@Test
	public void testSortableNumber() {
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

}