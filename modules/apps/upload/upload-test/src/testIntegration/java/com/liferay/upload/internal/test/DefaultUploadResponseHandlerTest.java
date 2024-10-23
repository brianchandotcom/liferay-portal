/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.upload.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletRequest;
import com.liferay.upload.UploadResponseHandler;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge González
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class DefaultUploadResponseHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOnFailureWithFileSizeException() throws Exception {
		MockPortletRequest mockPortletRequest = new MockPortletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.getDefault());

		mockPortletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		JSONObject jsonObject = _defaultUploadResponseHandler.onFailure(
			mockPortletRequest, new FileSizeException(1024L));

		JSONObject errorJSONObject = (JSONObject)jsonObject.get("error");

		Assert.assertEquals(
			"Please enter a file with a valid file size no larger than 1 KB.",
			errorJSONObject.get("message"));
	}

	@Inject(filter = "upload.response.handler.system.default=true")
	private UploadResponseHandler _defaultUploadResponseHandler;

}