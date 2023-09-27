/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.portlet.action;

import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.searcher.helper.RankingSearchRequestHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class GetResultsMVCResourceCommandTest
	extends BaseRankingsPortletActionTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_getResultsMVCResourceCommand = new GetResultsMVCResourceCommand();

		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "complexQueryPartBuilderFactory",
			complexQueryPartBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "dlAppLocalService",
			dlAppLocalService);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "fastDateFormatFactory",
			fastDateFormatFactory);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "portal", portal);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "queries", queries);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "rankingIndexNameBuilder",
			rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "rankingIndexReader",
			rankingIndexReader);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "rankingSearchRequestHelper",
			_rankingSearchRequestHelper);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "resourceActions", _resourceActions);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "searchEngineAdapter",
			searchEngineAdapter);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "searcher", searcher);
		ReflectionTestUtil.setFieldValue(
			_getResultsMVCResourceCommand, "searchRequestBuilderFactory",
			searchRequestBuilderFactory);
	}

	@Test
	public void testServeResourceGetHiddenResultsJSONObject() throws Exception {
		setUpDLAppLocalService();
		setUpFastDateFormatFactory();
		setUpPortletRequestParamValue(
			resourceRequest, "getHiddenResultsJSONObject", Constants.CMD);
		setUpRankingIndexReader();
		setUpResourceRequest();
		setUpResourceResponse();

		setUpSearchEngineAdapter(
			setUpGetDocumentResponseGetDocument(
				setUpDocumentWithGetString(), setUpGetDocumentResponse()));

		_getResultsMVCResourceCommand.serveResource(
			resourceRequest, resourceResponse);

		Mockito.verify(
			resourceResponse, Mockito.times(1)
		).isCommitted();
	}

	@Test
	public void testServeResourceGetSearchResultsJSONObject() throws Exception {
		_setUpServeResources();

		setUpPortletRequestParamValue(
			resourceRequest, "getSearchResultsJSONObject", Constants.CMD);

		_getResultsMVCResourceCommand.serveResource(
			resourceRequest, resourceResponse);

		Mockito.verify(
			resourceResponse, Mockito.times(1)
		).isCommitted();
	}

	@Test
	public void testServeResourceGetVisibleResultsJSONObject()
		throws Exception {

		setUpPortletRequestParamValue(
			resourceRequest, "getVisibleResultsJSONObject", Constants.CMD);

		_setUpServeResources();

		_getResultsMVCResourceCommand.serveResource(
			resourceRequest, resourceResponse);

		Mockito.verify(
			resourceResponse, Mockito.times(1)
		).isCommitted();
	}

	private void _setUpServeResources() throws Exception {
		setUpDLAppLocalService();
		setUpComplexQueryPartBuilderFactory(setUpComplexQueryPartBuilder());
		setUpFastDateFormatFactory();
		setUpQuery();
		setUpRankingIndexReader();
		setUpResourceRequest();
		setUpResourceResponse();
		setUpSearcher(setUpSearchResponse(setUpDocumentWithGetString()));

		SearchRequestBuilder searchRequestBuilder = setUpSearchRequestBuilder();

		setUpSearchRequestBuilderFactory(searchRequestBuilder);
	}

	private GetResultsMVCResourceCommand _getResultsMVCResourceCommand;
	private final RankingSearchRequestHelper _rankingSearchRequestHelper =
		Mockito.mock(RankingSearchRequestHelper.class);
	private final ResourceActions _resourceActions = Mockito.mock(
		ResourceActions.class);

}