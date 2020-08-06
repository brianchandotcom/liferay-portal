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

package com.liferay.portal.search.tuning.synonyms.web.internal.display.context;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.DocumentToSynonymSetTranslator;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexNameBuilder;

import javax.portlet.ActionURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 */
@Ignore
public class SynonymsDisplayContextTest {

	public void mockSynonymSets(String... synonymSets) {
		Mockito.when(
			_synonymSetFilterReader.getSynonymSets(
				Matchers.anyString(), Matchers.anyString())
		).thenReturn(
			synonymSets
		);
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		Mockito.when(
			_portal.getCurrentURL(_httpServletRequest)
		).thenReturn(
			"/"
		);

		Mockito.when(
			_renderResponse.createActionURL()
		).thenReturn(
			_actionURL
		);

		Mockito.when(
			_renderResponse.createRenderURL()
		).thenReturn(
			_renderURL
		);

		PropsUtil.setProps(_props);
	}

	@Test
	public void testGetItemsTotal() {
		mockSynonymSets("car,automobile", "biscuit,cookie");

		SynonymsDisplayBuilder synonymsDisplayBuilder =
			new SynonymsDisplayBuilder(
				_documentToSynonymSetTranslator, _httpServletRequest, _language,
				_portal, _queries, _renderRequest, _renderResponse,
				_searchEngineAdapter, _searchEngineInformation, _sorts,
				_synonymSetIndexNameBuilder);

		SynonymsDisplayContext synonymsDisplayContext =
			synonymsDisplayBuilder.build();

		Assert.assertEquals(2, synonymsDisplayContext.getItemsTotal());
	}

	@Test
	public void testIsDisabled() {
		mockSynonymSets("car,automobile", "biscuit,cookie");

		SynonymsDisplayBuilder synonymsDisplayBuilder =
			new SynonymsDisplayBuilder(
				_documentToSynonymSetTranslator, _httpServletRequest, _language,
				_portal, _queries, _renderRequest, _renderResponse,
				_searchEngineAdapter, _searchEngineInformation, _sorts,
				_synonymSetIndexNameBuilder);

		SynonymsDisplayContext synonymsDisplayContext =
			synonymsDisplayBuilder.build();

		Assert.assertEquals(2, synonymsDisplayContext.getItemsTotal());
	}

	@Mock
	private ActionURL _actionURL;

	@Mock
	private DocumentToSynonymSetTranslator _documentToSynonymSetTranslator;

	@Mock
	private HttpServletRequest _httpServletRequest;

	@Mock
	private IndexNameBuilder _indexNameBuilder;

	@Mock
	private Language _language;

	@Mock
	private Portal _portal;

	@Mock
	private Props _props;

	@Mock
	private Queries _queries;

	@Mock
	private RenderRequest _renderRequest;

	@Mock
	private RenderResponse _renderResponse;

	@Mock
	private RenderURL _renderURL;

	@Mock
	private SearchEngineAdapter _searchEngineAdapter;

	@Mock
	private SearchEngineInformation _searchEngineInformation;

	@Mock
	private Sorts _sorts;

	@Mock
	private SynonymSetFilterReader _synonymSetFilterReader;

	@Mock
	private SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder;

	@Mock
	private ThemeDisplay _themeDisplay;

}