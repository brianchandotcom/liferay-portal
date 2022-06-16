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

package com.liferay.portal.search.tuning.synonyms.web.internal.portlet.action;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
public class EditSynonymSetsMVCRenderCommandTest
	extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_editSynonymSetsMVCRenderCommand =
			new EditSynonymSetsMVCRenderCommand();

		ReflectionTestUtil.setFieldValue(
			_editSynonymSetsMVCRenderCommand, "_portal", portal);
		ReflectionTestUtil.setFieldValue(
			_editSynonymSetsMVCRenderCommand, "_synonymSetIndexNameBuilder",
			synonymSetIndexNameBuilder);
	}

	@Test
	public void testRender() throws Exception {
		setUpPortal(_httpServletRequest);

		Assert.assertEquals(
			"/edit_synonym_sets.jsp",
			_editSynonymSetsMVCRenderCommand.render(
				_renderRequest, _renderResponse));
	}

	private EditSynonymSetsMVCRenderCommand _editSynonymSetsMVCRenderCommand;
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final RenderRequest _renderRequest = Mockito.mock(
		RenderRequest.class);
	private final RenderResponse _renderResponse = Mockito.mock(
		RenderResponse.class);

}