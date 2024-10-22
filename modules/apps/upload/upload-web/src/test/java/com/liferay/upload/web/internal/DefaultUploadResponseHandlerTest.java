/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.upload.web.internal;

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jorge González
 */
public class DefaultUploadResponseHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void test() throws Exception {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.translate(Mockito.anyString())
		).thenReturn(
			"please-enter-a-file-with-a-valid-file-size-no-larger-than-x"
		);

		JSONObject jsonObject = _defaultUploadResponseHandler.onFailure(
			new MockPortletRequest(), new FileSizeException(1234L));

		Assert.assertEquals(
			"please-enter-a-file-with-a-valid-file-size-no-larger-than-x",
			jsonObject.get("message"));
	}

	DefaultUploadResponseHandler _defaultUploadResponseHandler =
		new DefaultUploadResponseHandler();

}