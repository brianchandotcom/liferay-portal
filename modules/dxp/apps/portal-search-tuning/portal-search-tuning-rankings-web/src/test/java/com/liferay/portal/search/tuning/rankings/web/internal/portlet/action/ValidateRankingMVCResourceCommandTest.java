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

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class ValidateRankingMVCResourceCommandTest
	extends BaseRankingsPortletActionTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_validateRankingMVCResourceCommand =
			new ValidateRankingMVCResourceCommand();

		ReflectionTestUtil.setFieldValue(
			_validateRankingMVCResourceCommand, "duplicateQueryStringsDetector",
			duplicateQueryStringsDetector);
		ReflectionTestUtil.setFieldValue(
			_validateRankingMVCResourceCommand, "indexNameBuilder",
			indexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_validateRankingMVCResourceCommand, "portal", portal);
		ReflectionTestUtil.setFieldValue(
			_validateRankingMVCResourceCommand, "rankingIndexNameBuilder",
			rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_validateRankingMVCResourceCommand, "searchRequestBuilderFactory",
			searchRequestBuilderFactory);
	}

	@Test
	public void testServeResource() throws Exception {
		setUpDuplicateQueryStringsDetector();
		setUpIndexNameBuilder();
		setUpPortalUtil();
		setUpResourceResponse();

		HttpServletRequest httpServletRequest =
			setUpPortalGetHttpServletRequest();

		setUpHttpServletRequestParamValues(
			httpServletRequest, "aliase", new String[] {"aliase"});

		_validateRankingMVCResourceCommand.serveResource(
			resourceRequest, resourceResponse);

		Mockito.verify(
			resourceResponse, Mockito.times(1)
		).isCommitted();
	}

	private ValidateRankingMVCResourceCommand
		_validateRankingMVCResourceCommand;

}