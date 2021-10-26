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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.DocumentToSynonymSetTranslatorImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
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
public class SynonymsDisplayBuilderTest extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpPortletPreferencesFactoryUtil();

		_synonymsDisplayBuilder = new SynonymsDisplayBuilder(
			_documentToSynonymSetTranslatorImpl, _httpServletRequest, _language,
			portal, _queries, _renderRequest, _renderResponse,
			searchEngineAdapter, _sorts, _synonymSetIndexNameBuilder);
	}

	@Test
	public void testBuilder() throws Exception {
		setUpHttpServletRequestAttribute(
			_httpServletRequest, WebKeys.THEME_DISPLAY,
			Mockito.mock(ThemeDisplay.class));
		setUpPortal(_httpServletRequest);
		setUpRenderResponse(_renderResponse);
		setUpSearchEngineAdapter(setUpSearchHits("car,automobile"));

		Mockito.when(
			_synonymSetIndexNameBuilder.getSynonymSetIndexName(
				Matchers.anyLong())
		).thenReturn(
			Mockito.mock(SynonymSetIndexName.class)
		);

		SynonymsDisplayContext synonymsDisplayContext =
			_synonymsDisplayBuilder.build();

		Assert.assertNotNull(synonymsDisplayContext.getCreationMenu());
		Assert.assertEquals(1, synonymsDisplayContext.getItemsTotal());
		Assert.assertNotNull(synonymsDisplayContext.getSearchContainer());
		Assert.assertFalse(synonymsDisplayContext.isDisabledManagementBar());
	}

	@Test
	public void testGetDisplayedSynonymSet() {
		_synonymsDisplayBuilder.getDisplayedSynonymSet("car,automobile");

		Assert.assertEquals(
			"car, automobile",
			_synonymsDisplayBuilder.getDisplayedSynonymSet("car,automobile"));
	}

	private final DocumentToSynonymSetTranslatorImpl
		_documentToSynonymSetTranslatorImpl =
			new DocumentToSynonymSetTranslatorImpl();

	@Mock
	private HttpServletRequest _httpServletRequest;

	@Mock
	private Language _language;

	@Mock
	private Queries _queries;

	@Mock
	private RenderRequest _renderRequest;

	@Mock
	private RenderResponse _renderResponse;

	@Mock
	private Sorts _sorts;

	private SynonymsDisplayBuilder _synonymsDisplayBuilder;

	@Mock
	private SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder;

}