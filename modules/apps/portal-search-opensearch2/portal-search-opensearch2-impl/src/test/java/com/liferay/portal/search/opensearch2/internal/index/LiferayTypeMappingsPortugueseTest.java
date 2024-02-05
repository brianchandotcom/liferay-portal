/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.connection.IndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class LiferayTypeMappingsPortugueseTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_liferayIndexFixture = new LiferayIndexFixture(
			new IndexName(testName.getMethodName()));

		_liferayIndexFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_liferayIndexFixture.tearDown();
	}

	@Test
	public void testPortugueseDynamicTemplatesMatchAnalyzers()
		throws Exception {

		String field_pt = RandomTestUtil.randomString() + "_pt";
		String field_pt_BR = RandomTestUtil.randomString() + "_pt_BR";
		String field_pt_PT = RandomTestUtil.randomString() + "_pt_PT";

		_liferayIndexFixture.index(
			HashMapBuilder.<String, Object>put(
				field_pt, RandomTestUtil.randomString()
			).put(
				field_pt_BR, RandomTestUtil.randomString()
			).put(
				field_pt_PT, RandomTestUtil.randomString()
			).build());

		assertAnalyzer("portuguese", field_pt);
		assertAnalyzer("brazilian", field_pt_BR);
		assertAnalyzer("portuguese", field_pt_PT);
	}

	@Rule
	public TestName testName = new TestName();

	protected void assertAnalyzer(String analyzer, String field)
		throws Exception {

		_liferayIndexFixture.assertAnalyzer(analyzer, field);
	}

	private LiferayIndexFixture _liferayIndexFixture;

}