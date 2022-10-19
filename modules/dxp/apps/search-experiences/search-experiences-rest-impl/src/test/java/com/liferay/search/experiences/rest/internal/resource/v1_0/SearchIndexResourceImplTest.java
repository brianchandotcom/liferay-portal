/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.SearchIndex;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SearchIndexResourceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_searchIndexResourceImpl = new SearchIndexResourceImpl();

		_searchIndexResourceImpl.setContextCompany(_contextCompany);

		ReflectionTestUtil.setFieldValue(
			_searchIndexResourceImpl, "_indexNameBuilder", _indexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_searchIndexResourceImpl, "_searchEngineAdapter",
			_searchEngineAdapter);
	}

	@Test
	public void testGetSearchIndexes() {
		_setUpContextCompany(12345);
		_setUpIndexNameBuilder("prod-12345");
		_setUpSearchEngineAdapter(
			new String[] {
				"prod-12345-search-tuning-rankings",
				"prod-12345-search-tuning-synonyms"
			});

		List<SearchIndex> searchIndexes =
			_searchIndexResourceImpl.getSearchIndexes();

		Assert.assertEquals(searchIndexes.toString(), 2, searchIndexes.size());

		SearchIndex searchIndex = searchIndexes.get(0);

		Assert.assertEquals("search-tuning-rankings", searchIndex.getName());

		searchIndex = searchIndexes.get(1);

		Assert.assertEquals("search-tuning-synonyms", searchIndex.getName());
	}

	private void _setUpContextCompany(long companyId) {
		Mockito.doReturn(
			companyId
		).when(
			_contextCompany
		).getCompanyId();
	}

	private void _setUpIndexNameBuilder(String companyIndexName) {
		Mockito.doReturn(
			companyIndexName
		).when(
			_indexNameBuilder
		).getIndexName(
			Mockito.anyLong()
		);
	}

	private void _setUpSearchEngineAdapter(String[] indexNames) {
		GetIndexIndexResponse getIndexIndexResponse = Mockito.mock(
			GetIndexIndexResponse.class);

		Mockito.doReturn(
			indexNames
		).when(
			getIndexIndexResponse
		).getIndexNames();

		Mockito.doReturn(
			getIndexIndexResponse
		).when(
			_searchEngineAdapter
		).execute(
			Mockito.any(GetIndexIndexRequest.class)
		);
	}

	private final Company _contextCompany = Mockito.mock(Company.class);
	private final IndexNameBuilder _indexNameBuilder = Mockito.mock(
		IndexNameBuilder.class);
	private final SearchEngineAdapter _searchEngineAdapter = Mockito.mock(
		SearchEngineAdapter.class);
	private SearchIndexResourceImpl _searchIndexResourceImpl;

}