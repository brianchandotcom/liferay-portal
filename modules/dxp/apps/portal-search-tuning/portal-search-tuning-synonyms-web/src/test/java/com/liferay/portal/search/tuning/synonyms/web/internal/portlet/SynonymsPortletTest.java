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

package com.liferay.portal.search.tuning.synonyms.web.internal.portlet;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCCommandCache;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.DocumentToSynonymSetTranslator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymsPortletTest extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpPortletPreferencesFactoryUtil();

		_synonymsPortlet = new SynonymsPortlet();

		ReflectionTestUtil.setFieldValue(
			_synonymsPortlet, "_documentToSynonymSetTranslator",
			_documentToSynonymSetTranslator);
		ReflectionTestUtil.setFieldValue(
			_synonymsPortlet, "_indexNameBuilder", _indexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_synonymsPortlet, "_language", _language);
		ReflectionTestUtil.setFieldValue(_synonymsPortlet, "_portal", portal);
		ReflectionTestUtil.setFieldValue(
			_synonymsPortlet, "_queries", _queries);
		ReflectionTestUtil.setFieldValue(
			_synonymsPortlet, "_renderMVCCommandCache",
			Mockito.mock(MVCCommandCache.class));
		ReflectionTestUtil.setFieldValue(
			_synonymsPortlet, "_searchEngineAdapter", searchEngineAdapter);
		ReflectionTestUtil.setFieldValue(_synonymsPortlet, "_sorts", _sorts);
		ReflectionTestUtil.setFieldValue(
			_synonymsPortlet, "_synonymSetIndexNameBuilder",
			synonymSetIndexNameBuilder);
	}

	@Test
	public void testRender() throws Exception {
		_setUpPortletConfig();
		_setUpRenderMVCCommandCache();
		_setUpRenderRequest();

		setUpPortal(Mockito.mock(HttpServletRequest.class));
		setUpRenderResponse(_renderResponse);
		setUpSearchEngineAdapter(setUpSearchHits("car,automobile"));
		setUpSynonymSetIndexNameBuilder();

		_synonymsPortlet.render(_renderRequest, _renderResponse);
		Mockito.verify(
			_renderRequest, Mockito.times(1)
		).getWindowState();
	}

	private void _setUpPortletConfig() {
		ResourceBundle resourceBundle = _setUpResourceBundle();

		PortletConfig config = Mockito.mock(PortletConfig.class);

		Mockito.doReturn(
			resourceBundle
		).when(
			config
		).getResourceBundle(
			Mockito.anyObject()
		);

		ReflectionTestUtil.setFieldValue(_synonymsPortlet, "config", config);
	}

	@SuppressWarnings("unchecked")
	private void _setUpRenderMVCCommandCache() {
		MVCCommandCache<MVCRenderCommand> mvcCommandCache = Mockito.mock(
			MVCCommandCache.class);

		Mockito.doReturn(
			MVCRenderCommand.EMPTY
		).when(
			mvcCommandCache
		).getMVCCommand(
			Mockito.anyString()
		);

		ReflectionTestUtil.setFieldValue(
			_synonymsPortlet, "_renderMVCCommandCache", mvcCommandCache);
	}

	private void _setUpRenderRequest() {
		Mockito.doReturn(
			WindowState.MINIMIZED
		).when(
			_renderRequest
		).getWindowState();
	}

	private ResourceBundle _setUpResourceBundle() {
		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.emptyEnumeration();
			}

			@Override
			protected Object handleGetObject(String key) {
				return "value";
			}

		};
	}

	@Mock
	private DocumentToSynonymSetTranslator _documentToSynonymSetTranslator;

	@Mock
	private IndexNameBuilder _indexNameBuilder;

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

	private SynonymsPortlet _synonymsPortlet;

}