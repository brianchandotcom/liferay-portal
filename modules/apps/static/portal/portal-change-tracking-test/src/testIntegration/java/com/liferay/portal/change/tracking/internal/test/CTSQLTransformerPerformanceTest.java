/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class CTSQLTransformerPerformanceTest
	extends BaseCTSQLTransformerTestCase {

	@Test
	public void testJoinCountPerformance() throws Exception {
		assertPerformance("join_count_in.sql", 0, 10);

		assertPerformance("join_count_in.sql", getCTCollectionId(6), 10);
	}

	@Test
	public void testJoinSelectPerformance() throws Exception {
		assertPerformance("join_select_in.sql", 0, 10);

		assertPerformance("join_select_in.sql", getCTCollectionId(6), 10);
	}

	@Test
	public void testLeftJoinPerformance() throws Exception {
		assertPerformance("left_join_in.sql", 0, 10);

		assertPerformance("left_join_in.sql", getCTCollectionId(6), 10);
	}

	@Test
	public void testSelfJoinPerformance() throws Exception {
		assertPerformance("self_join_in.sql", 0, 10);

		assertPerformance("self_join_in.sql", getCTCollectionId(6), 10);
	}

	@Test
	public void testSimpleCountPerformance() throws Exception {
		assertPerformance("simple_count_in.sql", 0, 10);

		assertPerformance("simple_count_in.sql", getCTCollectionId(6), 10);
	}

	@Test
	public void testSimpleSelectPerformance() throws Exception {
		assertPerformance("simple_select_in.sql", 0, 10);

		assertPerformance("simple_select_in.sql", getCTCollectionId(6), 10);
	}

	@Test
	public void testSubqueryCountPerformance() throws Exception {
		assertPerformance("subquery_count_in.sql", 0, 10);

		assertPerformance("subquery_count_in.sql", getCTCollectionId(6), 10);
	}

	@Test
	public void testSubquerySelectPerformance() throws Exception {
		assertPerformance("subquery_select_in.sql", 0, 10);

		assertPerformance("subquery_select_in.sql", getCTCollectionId(6), 10);
	}

	@Test
	public void testUnionCountPerformance() throws Exception {
		assertPerformance("union_select_count_in.sql", 0, 10);

		assertPerformance(
			"union_select_count_in.sql", getCTCollectionId(6), 10);
	}

	@Test
	public void testUpdateAndDeletePerformance() throws Exception {
		assertPerformance("update_in.sql", 0, 10);

		assertPerformance("update_in.sql", getCTCollectionId(6), 10);

		assertPerformance("delete_in.sql", 0, 10);

		assertPerformance("delete_in.sql", getCTCollectionId(6), 10);
	}

}