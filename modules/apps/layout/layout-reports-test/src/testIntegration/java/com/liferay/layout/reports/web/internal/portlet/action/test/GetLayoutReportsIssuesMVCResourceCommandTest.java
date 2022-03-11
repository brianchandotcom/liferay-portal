/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.reports.web.internal.portlet.action.test;

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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
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

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class GetLayoutReportsIssuesMVCResourceCommandTest {

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
								_getLayoutReportsIssuesMVCResourceCommand));

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
									_getLayoutReportsIssuesMVCResourceCommand),
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

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		mockLiferayResourceRequest.setParameter(
			"refreshCache", String.valueOf(refreshCache));
		mockLiferayResourceRequest.setParameter("url", url);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(layout);
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_getLayoutReportsIssuesMVCResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));
	}

	@Inject(
		filter = "mvc.command.name=/layout_reports/get_layout_reports_issues"
	)
	private MVCResourceCommand _getLayoutReportsIssuesMVCResourceCommand;

}