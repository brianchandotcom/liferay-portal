/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Sandro Chinea
 */
public class SearchUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			Mockito.mock(PermissionChecker.class));

		Hits hits = Mockito.mock(Hits.class);

		Mockito.when(
			hits.getDocs()
		).thenReturn(
			new Document[0]
		);

		Mockito.when(
			_indexer.search(Mockito.any(SearchContext.class))
		).thenReturn(
			hits
		);
	}

	@Test
	public void testSearch() throws Exception {
		_assertSearchContextWindow(QueryUtil.ALL_POS, null, QueryUtil.ALL_POS);

		Pagination pagination = Pagination.of(
			RandomTestUtil.randomInt(1, 10), RandomTestUtil.randomInt(1, 100));

		_assertSearchContextWindow(
			pagination.getEndPosition(), pagination,
			pagination.getStartPosition());
	}

	private void _assertSearchContextWindow(
			int end, Pagination pagination, int start)
		throws Exception {

		AtomicReference<SearchUtil.SearchContext> searchContextReference =
			new AtomicReference<>();

		SearchUtil.search(
			null,
			booleanQuery -> {
			},
			null, _indexer, null, pagination,
			queryConfig -> {
			},
			searchContextReference::set, null, document -> null);

		SearchUtil.SearchContext searchContext = searchContextReference.get();

		Assert.assertEquals(end, searchContext.getEnd());
		Assert.assertEquals(start, searchContext.getStart());
	}

	private final Indexer<?> _indexer = Mockito.mock(Indexer.class);

}