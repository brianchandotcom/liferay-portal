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

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

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
public class EditRankingDisplayBuilderTest extends BaseRankingsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpHttpServletRequest();

		_editRankingDisplayBuilder = new EditRankingDisplayBuilder(
			httpServletRequest, _renderRequest, _renderResponse);
	}

	@Test
	public void testBuild() throws Exception {
		_setUpRenderResponse();
		_setUpThemDisplay();

		setUpHttpServletRequestParamValue(
			httpServletRequest, "backURL", "backURL");
		setUpHttpServletRequestParamValue(
			httpServletRequest, "keywords", "keywords");
		setUpHttpServletRequestParamValue(
			httpServletRequest, "redirect", "redirect");
		setUpHttpServletRequestParamValue(
			httpServletRequest, "resultsRankingUid", "resultsRankingUid");

		setUpPropsUtil();

		EditRankingDisplayContext editRankingDisplayContext =
			_editRankingDisplayBuilder.build();

		Assert.assertEquals("backURL", editRankingDisplayContext.getBackURL());
		Assert.assertEquals(111L, editRankingDisplayContext.getCompanyId());
		Assert.assertEquals(
			"editResultRankingsFm", editRankingDisplayContext.getFormName());
		Assert.assertEquals(
			"keywords", editRankingDisplayContext.getKeywords());
		Assert.assertEquals(
			"redirect", editRankingDisplayContext.getRedirect());
		Assert.assertEquals(
			"resultsRankingUid",
			editRankingDisplayContext.getResultsRankingUid());

		Assert.assertFalse(editRankingDisplayContext.getInactive());

		Assert.assertNotNull(editRankingDisplayContext.getData());
	}

	protected HttpServletRequest httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	protected ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

	private void _setUpHttpServletRequest() {
		Mockito.doReturn(
			themeDisplay
		).when(
			httpServletRequest
		).getAttribute(
			Mockito.anyString()
		);
	}

	private void _setUpRenderResponse() {
		Mockito.doReturn(
			Mockito.mock(ResourceURL.class)
		).when(
			_renderResponse
		).createResourceURL();
	}

	private void _setUpThemDisplay() {
		Mockito.doReturn(
			111L
		).when(
			themeDisplay
		).getCompanyId();
	}

	private EditRankingDisplayBuilder _editRankingDisplayBuilder;
	private final RenderRequest _renderRequest = Mockito.mock(
		RenderRequest.class);
	private final RenderResponse _renderResponse = Mockito.mock(
		RenderResponse.class);

}