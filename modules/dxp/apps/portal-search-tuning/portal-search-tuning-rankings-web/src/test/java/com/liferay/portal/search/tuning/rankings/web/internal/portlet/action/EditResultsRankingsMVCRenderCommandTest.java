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

package com.liferay.portal.search.tuning.rankings.web.internal.portlet.action;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class EditResultsRankingsMVCRenderCommandTest
	extends BaseRankingsPortletActionTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_editResultsRankingsMVCRenderCommand =
			new EditResultsRankingsMVCRenderCommand();

		ReflectionTestUtil.setFieldValue(
			_editResultsRankingsMVCRenderCommand, "_portal", portal);
	}

	@Test
	public void testRender() throws Exception {
		_setUpRenderResponse();

		setUpPortal();

		Assert.assertEquals(
			"/edit_results_rankings.jsp",
			_editResultsRankingsMVCRenderCommand.render(
				_renderRequest, _renderResponse));
	}

	@Test(expected = NullPointerException.class)
	public void testRenderException() throws Exception {
		setUpPortal();

		_editResultsRankingsMVCRenderCommand.render(
			_renderRequest, _renderResponse);
	}

	private void _setUpRenderResponse() {
		Mockito.doReturn(
			Mockito.mock(ResourceURL.class)
		).when(
			_renderResponse
		).createResourceURL();
	}

	private EditResultsRankingsMVCRenderCommand
		_editResultsRankingsMVCRenderCommand;
	private final RenderRequest _renderRequest = Mockito.mock(
		RenderRequest.class);
	private final RenderResponse _renderResponse = Mockito.mock(
		RenderResponse.class);

}