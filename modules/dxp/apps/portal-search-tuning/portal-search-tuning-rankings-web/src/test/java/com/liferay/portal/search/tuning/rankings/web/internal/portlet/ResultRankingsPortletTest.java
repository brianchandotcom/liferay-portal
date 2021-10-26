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

package com.liferay.portal.search.tuning.rankings.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCCommandCache;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.index.DocumentToRankingTranslator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class ResultRankingsPortletTest extends BaseRankingsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_resultRankingsPortlet = new ResultRankingsPortlet();

		_setUpMVCCommanCache();

		setUpPortletPreferencesFactoryUtil();

		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "documentToRankingTranslator",
			_documentToRankingTranslator);
		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "indexNameBuilder", _indexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "language", language);
		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "portal", portal);
		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "queries", queries);
		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "rankingIndexNameBuilder",
			rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "searchEngineAdapter", searchEngineAdapter);
		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "searchRequestBuilderFactory",
			_searchRequestBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "sorts", _sorts);
	}

	@Test
	public void testRender() throws Exception {
		_setUpRenderRequest();

		setUpLanguageUtil("");
		setUpPortal();
		setUpPortalUtil();
		setUpPropsUtil();
		setUpRankingIndexNameBuilder();
		setUpRenderResponse(_renderResponse);
		setUpSearchEngineAdapter(Mockito.mock(SearchHits.class));

		_resultRankingsPortlet.render(_renderRequest, _renderResponse);

		Mockito.verify(
			_renderRequest, Mockito.times(1)
		).getWindowState();
	}

	private void _setUpMVCCommanCache() {
		MVCCommandCache<?> mvcCommandCache = Mockito.mock(
			MVCCommandCache.class);

		Mockito.doReturn(
			MVCRenderCommand.EMPTY
		).when(
			mvcCommandCache
		).getMVCCommand(
			Matchers.anyString()
		);

		ReflectionTestUtil.setFieldValue(
			_resultRankingsPortlet, "_renderMVCCommandCache", mvcCommandCache);
	}

	private void _setUpRenderRequest() {
		Mockito.doReturn(
			WindowState.MINIMIZED
		).when(
			_renderRequest
		).getWindowState();
	}

	@Mock
	private DocumentToRankingTranslator _documentToRankingTranslator;

	@Mock
	private IndexNameBuilder _indexNameBuilder;

	@Mock
	private RenderRequest _renderRequest;

	@Mock
	private RenderResponse _renderResponse;

	private ResultRankingsPortlet _resultRankingsPortlet;

	@Mock
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Mock
	private Sorts _sorts;

}