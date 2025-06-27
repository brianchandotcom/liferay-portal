/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.internal.sort.FieldSortImpl;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Rodrigo Guedes de Souza
 */
public class SearchSolrQueryAssemblerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSearchSolrQueryAssemblerWithCustomFieldAscDesc() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		FieldSort fieldSort = new FieldSortImpl(Field.ENTRY_CLASS_PK);

		fieldSort.setSortOrder(SortOrder.DESC);

		searchSearchRequest.addSorts(fieldSort);

		SolrQuery solrQuery = new SolrQuery();

		SearchSolrQueryAssemblerImpl searchSolrQueryAssemblerImpl =
			_setUpSearchSolrQueryAssemblerImpl();

		searchSolrQueryAssemblerImpl.assemble(solrQuery, searchSearchRequest);

		_assertFirstItemSort(
			solrQuery.getSorts(), Field.ENTRY_CLASS_PK, SolrQuery.ORDER.desc);
	}

	@Test
	public void testSearchSolrQueryAssemblerWithCustomFieldAscOrder() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addSorts(new FieldSortImpl(Field.ENTRY_CLASS_PK));

		SolrQuery solrQuery = new SolrQuery();

		SearchSolrQueryAssemblerImpl searchSolrQueryAssemblerImpl =
			_setUpSearchSolrQueryAssemblerImpl();

		searchSolrQueryAssemblerImpl.assemble(solrQuery, searchSearchRequest);

		_assertFirstItemSort(
			solrQuery.getSorts(), Field.ENTRY_CLASS_PK, SolrQuery.ORDER.asc);
	}

	@Test
	public void testSearchSolrQueryAssemblerWithEntryClassNameField() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addSorts(new FieldSortImpl(Field.ENTRY_CLASS_NAME));

		SolrQuery solrQuery = new SolrQuery();

		SearchSolrQueryAssemblerImpl searchSolrQueryAssemblerImpl =
			_setUpSearchSolrQueryAssemblerImpl();

		searchSolrQueryAssemblerImpl.assemble(solrQuery, searchSearchRequest);

		_assertFirstItemSort(
			solrQuery.getSorts(), Field.ENTRY_CLASS_NAME, SolrQuery.ORDER.asc);
	}

	@Test
	public void testSearchSolrQueryAssemblerWithoutSorts() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		SolrQuery solrQuery = new SolrQuery();

		SearchSolrQueryAssemblerImpl searchSolrQueryAssemblerImpl =
			_setUpSearchSolrQueryAssemblerImpl();

		searchSolrQueryAssemblerImpl.assemble(solrQuery, searchSearchRequest);

		Assert.assertTrue(
			solrQuery.getSorts(
			).isEmpty());
	}

	@Test
	public void testSearchSolrQueryAssemblerWithPriorityField() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addSorts(new FieldSortImpl(Field.PRIORITY));

		SolrQuery solrQuery = new SolrQuery();

		SearchSolrQueryAssemblerImpl searchSolrQueryAssemblerImpl =
			_setUpSearchSolrQueryAssemblerImpl();

		searchSolrQueryAssemblerImpl.assemble(solrQuery, searchSearchRequest);

		_assertFirstItemSort(
			solrQuery.getSorts(), Field.PRIORITY, SolrQuery.ORDER.asc);
	}

	private void _assertFirstItemSort(
		List<SolrQuery.SortClause> sorts, String expectedItem,
		SolrQuery.ORDER expectedOrder) {

		Assert.assertNotNull(sorts);

		SolrQuery.SortClause sort = sorts.get(0);

		Assert.assertEquals(expectedItem, sort.getItem());
		Assert.assertEquals(expectedOrder, sort.getOrder());
	}

	private SearchSolrQueryAssemblerImpl _setUpSearchSolrQueryAssemblerImpl() {
		SearchSolrQueryAssemblerImpl searchSolrQueryAssemblerImpl =
			new SearchSolrQueryAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			searchSolrQueryAssemblerImpl, "_baseSolrQueryAssembler",
			_baseSolrQueryAssembler);

		return searchSolrQueryAssemblerImpl;
	}

	@Mock
	private BaseSolrQueryAssembler _baseSolrQueryAssembler;

}