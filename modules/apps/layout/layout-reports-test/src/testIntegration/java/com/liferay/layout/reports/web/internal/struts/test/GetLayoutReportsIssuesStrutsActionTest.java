/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.reports.web.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.reports.web.internal.util.LayoutReportsTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class GetLayoutReportsIssuesStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetCachedLayoutReportsIssues() throws Exception {
		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedGroupConfiguration(
				RandomTestUtil.randomString(), true,
				TestPropsValues.getGroupId(),
				() -> {
					PortalCache<Serializable, Object> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM,
							ClassUtil.getClassName(
								_getLayoutReportsIssuesStrutsAction));

					String url = "http://localhost:8080/test";

					String key = LocaleUtil.getDefault() + "-" + url;

					try {
						portalCache.put(key, JSONUtil.put("test", "test"));

						JSONObject jsonObject = _serveResource(
							LayoutTestUtil.addTypePortletLayout(
								TestPropsValues.getGroupId()),
							false, url);

						JSONObject layoutReportsIssuesJSONObject =
							jsonObject.getJSONObject("layoutReportsIssues");

						Assert.assertEquals(
							"test",
							layoutReportsIssuesJSONObject.getString("test"));
					}
					finally {
						portalCache.remove(key);
					}
				});
	}

	@Test
	public void testGetLayoutReportsIssuesError() throws Exception {
		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedGroupConfiguration(
				"apiKey", true, TestPropsValues.getGroupId(),
				() -> {
					try (LogCapture logCapture =
							LoggerTestUtil.configureLog4JLogger(
								ClassUtil.getClassName(
									_getLayoutReportsIssuesStrutsAction),
								LoggerTestUtil.ERROR)) {

						JSONObject jsonObject = _serveResource(
							LayoutTestUtil.addTypePortletLayout(
								TestPropsValues.getGroupId()),
							true, "http://localhost:8080/test");

						JSONObject layoutReportsIssuesJSONObject =
							jsonObject.getJSONObject("layoutReportsIssues");

						Assert.assertNull(layoutReportsIssuesJSONObject);

						Assert.assertNotNull(jsonObject.getString("error"));

						JSONObject googlePageSpeedErrorJSONObject =
							jsonObject.getJSONObject("googlePageSpeedError");

						JSONObject errorJSONObject =
							googlePageSpeedErrorJSONObject.getJSONObject(
								"error");

						Assert.assertEquals(
							400, errorJSONObject.getInt("code"));
						Assert.assertEquals(
							"INVALID_ARGUMENT",
							errorJSONObject.getString("status"));
					}
				});
	}

	private JSONObject _serveResource(
			Layout layout, boolean refreshCache, String url)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		mockHttpServletRequest.setParameter(
			"refreshCache", String.valueOf(refreshCache));
		mockHttpServletRequest.setParameter("url", url);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(layout);
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_getLayoutReportsIssuesStrutsAction.execute(
			mockHttpServletRequest, mockHttpServletResponse);

		mockHttpServletResponse.getContentAsString();

		return JSONFactoryUtil.createJSONObject(
			mockHttpServletResponse.getContentAsString());
	}

	@Inject(
		filter = "component.name=com.liferay.layout.reports.web.internal.struts.GetLayoutReportsIssuesStrutsAction"
	)
	private StrutsAction _getLayoutReportsIssuesStrutsAction;

}