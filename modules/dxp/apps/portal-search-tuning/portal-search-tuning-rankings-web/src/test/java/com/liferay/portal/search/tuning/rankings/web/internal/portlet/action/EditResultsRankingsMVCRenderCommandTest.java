/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
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

		_setUpLearnMessages();

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

	private void _setUpLearnMessages() {
		MockedStatic<WebCachePoolUtil> mockedStatic = Mockito.mockStatic(
			WebCachePoolUtil.class);

		mockedStatic.when(
			() -> WebCachePoolUtil.get(Mockito.anyString(), Mockito.any())
		).thenReturn(
			JSONUtil.put(
				"result-rankings",
				JSONUtil.put(
					"en_US",
					JSONUtil.put(
						"message", "Learn more."
					).put(
						"url", "https://learn.liferay.com"
					)))
		);
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