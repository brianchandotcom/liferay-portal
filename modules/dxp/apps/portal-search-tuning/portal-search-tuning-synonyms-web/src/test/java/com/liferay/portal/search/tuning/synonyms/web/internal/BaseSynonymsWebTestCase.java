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

package com.liferay.portal.search.tuning.synonyms.web.internal;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DocumentResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndexResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.name.SynonymSetFilterNameHolder;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetFields;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexReader;
import com.liferay.portal.search.tuning.synonyms.web.internal.storage.SynonymSetStorageAdapter;

import java.util.Arrays;
import java.util.Optional;

import javax.portlet.ActionURL;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

import org.mockito.AdditionalAnswers;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Wade Cao
 */
public abstract class BaseSynonymsWebTestCase {

	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	protected Document setUpDocument(String synonyms) {
		Document document = Mockito.mock(Document.class);

		Mockito.doReturn(
			synonyms
		).when(
			document
		).getString(
			Matchers.eq(SynonymSetFields.SYNONYMS)
		);

		return document;
	}

	protected void setUpHttpServletRequestAttribute(
		HttpServletRequest httpServletRequest, String paramName,
		Object object) {

		Mockito.doReturn(
			object
		).when(
			httpServletRequest
		).getAttribute(
			Matchers.eq(paramName)
		);
	}

	protected void setUpHttpServletRequestParameterValue(
		HttpServletRequest httpServletRequest, String paramName, String value) {

		Mockito.doReturn(
			value
		).when(
			httpServletRequest
		).getParameter(
			Matchers.eq(paramName)
		);
	}

	protected void setUpHttpServletRequestParameterValues(
		HttpServletRequest httpServletRequest, String paramName,
		String[] returnValue) {

		Mockito.doReturn(
			returnValue
		).when(
			httpServletRequest
		).getParameterValues(
			Matchers.eq(paramName)
		);
	}

	protected IndexResponse setUpIndexResponse() {
		return Mockito.mock(IndexResponse.class);
	}

	protected void setUpPortal(HttpServletRequest httpServletRequest) {
		setUpHttpServletRequestAttribute(
			httpServletRequest, WebKeys.THEME_DISPLAY,
			Mockito.mock(ThemeDisplay.class));

		_setUpPortalGetCurrentURL();
		_setUpPortalGetHttpServletRequest(httpServletRequest);
		_setUpPortalGetLiferayPortletRequest();
		_setUpPortalGetOriginalServletRequest(httpServletRequest);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);
	}

	protected void setUpPortletPreferencesFactoryUtil() throws Exception {
		PortletPreferencesFactoryUtil portletPreferencesFactoryUtil =
			new PortletPreferencesFactoryUtil();

		PortletPreferencesFactory portletPreferencesFactory = Mockito.mock(
			PortletPreferencesFactory.class);

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(
			portletPreferencesFactory);

		PortalPreferences portalPreferences = Mockito.mock(
			PortalPreferences.class);

		Mockito.when(
			portletPreferencesFactory.getPortalPreferences(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			portalPreferences
		);

		Mockito.when(
			portalPreferences.getValue(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString())
		).then(
			AdditionalAnswers.returnsLastArg()
		);
	}

	protected void setUpPortletRequest(PortletRequest portletRequest) {
		Layout layout = Mockito.mock(Layout.class);

		_setUpLayoutIsTypeControlPanel(layout, true);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		_setUpThemeDisplayGetLayout(themeDisplay, layout);

		LayoutTypePortlet layoutTypePortlet = Mockito.mock(
			LayoutTypePortlet.class);

		_setUpLayoutTypePortletHasPortletId(layoutTypePortlet, true);

		_setUpThemeDisplayGetLayoutTypePortlet(themeDisplay, layoutTypePortlet);

		_setUpPortletRequestGetAttribute(
			portletRequest, Mockito.mock(PortletConfig.class),
			JavaConstants.JAVAX_PORTLET_CONFIG);
		_setUpPortletRequestGetAttribute(
			portletRequest, themeDisplay, WebKeys.THEME_DISPLAY);
	}

	@SuppressWarnings("deprecation")
	protected void setUpPortletRequestParameterValue(
		PortletRequest portletRequest, String paramName, String value) {

		Mockito.doReturn(
			value
		).when(
			portletRequest
		).getParameter(
			Matchers.eq(paramName)
		);
	}

	protected void setUpRenderResponse(MimeResponse mimeResponse) {
		RenderURL renderURL = Mockito.mock(RenderURL.class);

		Mockito.doReturn(
			""
		).when(
			renderURL
		).toString();

		Mockito.doReturn(
			Mockito.mock(ActionURL.class)
		).when(
			mimeResponse
		).createActionURL();

		Mockito.doReturn(
			renderURL
		).when(
			mimeResponse
		).createRenderURL();

		Mockito.doReturn(
			"namespace-"
		).when(
			mimeResponse
		).getNamespace();
	}

	@SuppressWarnings("unchecked")
	protected void setUpSearchEngineAdapter() {
		Mockito.doReturn(
			setUpIndexResponse()
		).when(
			searchEngineAdapter
		).execute(
			(IndexRequest<IndexResponse>)Mockito.anyObject()
		);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	protected void setUpSearchEngineAdapter(DocumentResponse documentResponse) {
		Mockito.doReturn(
			documentResponse
		).when(
			searchEngineAdapter
		).execute(
			(DocumentRequest)Mockito.anyObject()
		);
	}

	protected SearchHits setUpSearchEngineAdapter(SearchHits searchHits) {
		Mockito.doReturn(
			3L
		).when(
			searchHits
		).getTotalHits();

		SearchSearchResponse searchSearchResponse =
			_setUpSearchSearchResponse();

		Mockito.doReturn(
			searchHits
		).when(
			searchSearchResponse
		).getSearchHits();

		Mockito.doReturn(
			searchSearchResponse
		).when(
			searchEngineAdapter
		).execute(
			(SearchSearchRequest)Mockito.anyObject()
		);

		return searchHits;
	}

	protected SearchHits setUpSearchHits(String synonyms) {
		Document document = setUpDocument(synonyms);

		SearchHit searchHit = Mockito.mock(SearchHit.class);

		Mockito.doReturn(
			document
		).when(
			searchHit
		).getDocument();

		Mockito.doReturn(
			"id"
		).when(
			searchHit
		).getId();

		SearchHits searchHits = Mockito.mock(SearchHits.class);

		Mockito.doReturn(
			Arrays.asList(searchHit)
		).when(
			searchHits
		).getSearchHits();

		return searchHits;
	}

	protected void setUpSynonymSetFilterNameHolder(String[] synonyms) {
		Mockito.doReturn(
			synonyms
		).when(
			synonymSetFilterNameHolder
		).getFilterNames();
	}

	protected void setUpSynonymSetFilterReader(String[] synonyms) {
		Mockito.doReturn(
			synonyms
		).when(
			synonymSetFilterReader
		).getSynonymSets(
			Mockito.anyString(), Mockito.anyString()
		);
	}

	protected void setUpSynonymSetIndexNameBuilder() {
		Mockito.doReturn(
			Mockito.mock(SynonymSetIndexName.class)
		).when(
			synonymSetIndexNameBuilder
		).getSynonymSetIndexName(
			Mockito.anyLong()
		);
	}

	protected void setUpSynonymSetIndexReader(boolean exists) {
		Mockito.doReturn(
			exists
		).when(
			synonymSetIndexReader
		).isExists(
			Mockito.anyObject()
		);
	}

	protected void setUpSynonymSetIndexReader(String id, String synonyms) {
		SynonymSet.SynonymSetBuilder synonymSetBuilder =
			new SynonymSet.SynonymSetBuilder();

		Mockito.doReturn(
			Optional.of(
				synonymSetBuilder.synonyms(
					synonyms
				).synonymSetDocumentId(
					id
				).build())
		).when(
			synonymSetIndexReader
		).fetchOptional(
			Mockito.anyObject(), Mockito.anyString()
		);

		Mockito.doReturn(
			Arrays.asList(
				synonymSetBuilder.synonyms(
					synonyms
				).synonymSetDocumentId(
					id
				).build())
		).when(
			synonymSetIndexReader
		).search(
			Mockito.anyObject()
		);
	}

	@Mock
	protected Portal portal;

	@Mock
	protected SearchEngineAdapter searchEngineAdapter;

	@Mock
	protected SynonymSetFilterNameHolder synonymSetFilterNameHolder;

	@Mock
	protected SynonymSetFilterReader synonymSetFilterReader;

	@Mock
	protected SynonymSetIndexNameBuilder synonymSetIndexNameBuilder;

	@Mock
	protected SynonymSetIndexReader synonymSetIndexReader;

	@Mock
	protected SynonymSetStorageAdapter synonymSetStorageAdapter;

	private void _setUpLayoutIsTypeControlPanel(
		Layout layout, boolean returnValue) {

		Mockito.doReturn(
			returnValue
		).when(
			layout
		).isTypeControlPanel();
	}

	private void _setUpLayoutTypePortletHasPortletId(
		LayoutTypePortlet layoutTypePortlet, boolean returnValue) {

		Mockito.doReturn(
			returnValue
		).when(
			layoutTypePortlet
		).hasPortletId(
			Mockito.anyString()
		);
	}

	private void _setUpPortalGetCurrentURL() {
		Mockito.doReturn(
			"currentURL"
		).when(
			portal
		).getCurrentURL(
			Matchers.any(HttpServletRequest.class)
		);
	}

	private void _setUpPortalGetHttpServletRequest(
		HttpServletRequest httpServletRequest) {

		Mockito.doReturn(
			httpServletRequest
		).when(
			portal
		).getHttpServletRequest(
			Matchers.any(PortletRequest.class)
		);
	}

	private void _setUpPortalGetLiferayPortletRequest() {
		Mockito.doReturn(
			Mockito.mock(LiferayPortletRequest.class)
		).when(
			portal
		).getLiferayPortletRequest(
			Matchers.any(PortletRequest.class)
		);
	}

	private void _setUpPortalGetOriginalServletRequest(
		HttpServletRequest httpServletRequest) {

		Mockito.doReturn(
			httpServletRequest
		).when(
			portal
		).getOriginalServletRequest(
			Matchers.any(HttpServletRequest.class)
		);
	}

	private void _setUpPortletRequestGetAttribute(
		PortletRequest portletRequest, Object object, String keyValue) {

		Mockito.doReturn(
			object
		).when(
			portletRequest
		).getAttribute(
			Matchers.eq(keyValue)
		);
	}

	private SearchSearchResponse _setUpSearchSearchResponse() {
		return Mockito.mock(SearchSearchResponse.class);
	}

	private void _setUpThemeDisplayGetLayout(
		ThemeDisplay themeDisplay, Layout layout) {

		Mockito.doReturn(
			layout
		).when(
			themeDisplay
		).getLayout();
	}

	private void _setUpThemeDisplayGetLayoutTypePortlet(
		ThemeDisplay themeDisplay, LayoutTypePortlet layoutTypePortlet) {

		Mockito.doReturn(
			layoutTypePortlet
		).when(
			themeDisplay
		).getLayoutTypePortlet();
	}

}