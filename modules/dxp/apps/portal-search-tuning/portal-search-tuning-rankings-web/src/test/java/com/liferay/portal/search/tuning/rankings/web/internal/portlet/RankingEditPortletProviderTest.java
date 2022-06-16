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

import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
public class RankingEditPortletProviderTest extends BaseRankingsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingEditPortletProvider = new RankingEditPortletProvider();
	}

	@Test
	public void testGetPortletName() {
		Assert.assertEquals(
			ResultRankingsPortletKeys.RESULT_RANKINGS,
			_rankingEditPortletProvider.getPortletName());
	}

	@Test
	public void testGetPortletURL() throws Exception {
		setUpPortalUtil();

		Assert.assertEquals(
			setUpPortalPortletURL(),
			_rankingEditPortletProvider.getPortletURL(
				Mockito.mock(HttpServletRequest.class)));
	}

	private RankingEditPortletProvider _rankingEditPortletProvider;

}