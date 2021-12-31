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

package com.liferay.portal.search.tuning.synonyms.web.internal.request;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.search.tuning.synonyms.web.internal.display.context.SynonymSetDisplayContext;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SearchSynonymSetRequestTest extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpPortletPreferencesFactoryUtil();
	}

	@Test
	public void testSearchWithEmptyKeywords() {
		setUpPortal(_httpServletRequest);

		SearchHits searchHits = Mockito.mock(SearchHits.class);

		setUpSearchEngineAdapter(searchHits);

		_searchSynonymSetRequest = new SearchSynonymSetRequest(
			_synonymSetIndexName, _httpServletRequest, _queries, _sorts,
			_searchContainer, searchEngineAdapter);

		SearchSynonymSetResponse searchSynonymSetResponse =
			_searchSynonymSetRequest.search();

		Assert.assertEquals(
			searchHits, searchSynonymSetResponse.getSearchHits());

		Mockito.verify(
			_queries, Mockito.never()
		).match(
			Mockito.anyString(), Mockito.anyString()
		);
		Mockito.verify(
			_queries, Mockito.times(1)
		).matchAll();
	}

	@Test
	public void testSearchWithNotEmptyKeywords() {
		setUpHttpServletRequestParameterValue(
			_httpServletRequest, "keywords", "keywords");
		setUpPortal(_httpServletRequest);

		SearchHits searchHits = Mockito.mock(SearchHits.class);

		setUpSearchEngineAdapter(searchHits);

		_searchSynonymSetRequest = new SearchSynonymSetRequest(
			_synonymSetIndexName, _httpServletRequest, _queries, _sorts,
			_searchContainer, searchEngineAdapter);

		SearchSynonymSetResponse searchSynonymSetResponse =
			_searchSynonymSetRequest.search();

		Assert.assertEquals(
			searchHits, searchSynonymSetResponse.getSearchHits());

		Mockito.verify(
			_queries, Mockito.times(1)
		).match(
			Mockito.anyString(), Mockito.anyString()
		);
		Mockito.verify(
			_queries, Mockito.never()
		).matchAll();
	}

	@Mock
	private HttpServletRequest _httpServletRequest;

	@Mock
	private Queries _queries;

	@Mock
	private SearchContainer<SynonymSetDisplayContext> _searchContainer;

	private SearchSynonymSetRequest _searchSynonymSetRequest;

	@Mock
	private Sorts _sorts;

	@Mock
	private SynonymSetIndexName _synonymSetIndexName;

}