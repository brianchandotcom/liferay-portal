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

import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class EditSynonymSetsDisplayBuilderTest extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_editSynonymSetsDisplayBuilder = new EditSynonymSetsDisplayBuilder(
			_httpServletRequest, portal, _renderRequest, _renderResponse,
			_synonymSetIndexNameBuilder, synonymSetIndexReader);
	}

	@Test
	public void testBulder() {
		setUpHttpServletRequestParameterValue(
			_httpServletRequest, "redirect", "redirect");
		setUpPortletRequestParameterValue(_renderRequest, "synonymSetId", "id");
		setUpRenderResponse(_renderResponse);
		setUpSynonymSetIndexReader("id", "car,automobile");

		EditSynonymSetsDisplayContext editSynonymSetsDisplayContext =
			_editSynonymSetsDisplayBuilder.build();

		Assert.assertEquals(
			"id", editSynonymSetsDisplayContext.getSynonymSetId());
		Assert.assertEquals(
			"redirect", editSynonymSetsDisplayContext.getBackURL());
		Assert.assertEquals(
			"redirect", editSynonymSetsDisplayContext.getRedirect());
		Assert.assertEquals(
			"synonymSet", editSynonymSetsDisplayContext.getInputName());
		Assert.assertEquals(
			"synonymSetsForm", editSynonymSetsDisplayContext.getFormName());

		Map<String, Object> data = editSynonymSetsDisplayContext.getData();

		Assert.assertEquals("car,automobile", data.get("synonymSets"));
		Assert.assertEquals("namespace-synonymSet", data.get("inputName"));
		Assert.assertEquals("namespace-synonymSetsForm", data.get("formName"));
	}

	private EditSynonymSetsDisplayBuilder _editSynonymSetsDisplayBuilder;
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final RenderRequest _renderRequest = Mockito.mock(
		RenderRequest.class);
	private final RenderResponse _renderResponse = Mockito.mock(
		RenderResponse.class);
	private final SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder =
		Mockito.mock(SynonymSetIndexNameBuilder.class);

}