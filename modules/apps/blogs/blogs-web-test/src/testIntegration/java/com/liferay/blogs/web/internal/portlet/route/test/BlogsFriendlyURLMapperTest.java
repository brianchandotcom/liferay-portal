/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.portlet.route.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class BlogsFriendlyURLMapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testBuildPath() {
		_testBuildPathUrlTitle();
		_testBuildPathUrlTitleWithCategoryIdAndTag();
	}

	private void _testBuildPathUrlTitle() {
		String urlTitle = RandomTestUtil.randomString();

		LiferayPortletURL liferayPortletURL = new TestMockLiferayPortletURL(
			HashMapBuilder.put(
				"mvcRenderCommandName", new String[] {"/blogs/view_entry"}
			).put(
				"p_p_lifecycle", new String[] {"0"}
			).put(
				"p_p_state", new String[] {"normal"}
			).put(
				"urlTitle", new String[] {urlTitle}
			).build());

		Assert.assertEquals(
			"/blogs/" + urlTitle,
			_friendlyURLMapper.buildPath(liferayPortletURL));
	}

	private void _testBuildPathUrlTitleWithCategoryIdAndTag() {
		String urlTitle = RandomTestUtil.randomString();

		LiferayPortletURL liferayPortletURL = new TestMockLiferayPortletURL(
			HashMapBuilder.put(
				"categoryId", new String[] {"1"}
			).put(
				"mvcRenderCommandName", new String[] {"/blogs/view_entry"}
			).put(
				"p_p_lifecycle", new String[] {"0"}
			).put(
				"p_p_state", new String[] {"normal"}
			).put(
				"tag", new String[] {"1"}
			).put(
				"urlTitle", new String[] {urlTitle}
			).build());

		Assert.assertEquals(
			"/blogs/" + urlTitle,
			_friendlyURLMapper.buildPath(liferayPortletURL));
	}

	@Inject(
		filter = "component.name=com.liferay.blogs.web.internal.portlet.route.BlogsFriendlyURLMapper"
	)
	private FriendlyURLMapper _friendlyURLMapper;

	private static class TestMockLiferayPortletURL
		extends MockLiferayPortletURL {

		public TestMockLiferayPortletURL(Map<String, String[]> parameterMap) {
			_parameterMap = parameterMap;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return _parameterMap;
		}

		@Override
		public String getPortletId() {
			return BlogsPortletKeys.BLOGS;
		}

		private final Map<String, String[]> _parameterMap;

	}

}