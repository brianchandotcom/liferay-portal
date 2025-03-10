/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.upload.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.configuration.DLFileEntryMimeTypeConfiguration;
import com.liferay.document.library.kernel.exception.FileMimeTypeException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockPortletRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
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
	public void testOnFailureWithFileMimeTypeException() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						DLFileEntryMimeTypeConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"fileMimeTypes", new String[] {"text/html"}
						).build())) {

			JSONObject jsonObject = _defaultUploadResponseHandler.onFailure(
				_getMockPortletRequest(), new FileMimeTypeException());

			JSONObject errorJSONObject = (JSONObject)jsonObject.get("error");

			Assert.assertEquals(
				"Please enter a file with a valid mime type (text/html).",
				errorJSONObject.get("message"));
		}
	}

	@Test
	public void testOnFailureWithFileSizeException() throws Exception {
		JSONObject jsonObject = _defaultUploadResponseHandler.onFailure(
			_getMockPortletRequest(), new FileSizeException(1024L));

		JSONObject errorJSONObject = (JSONObject)jsonObject.get("error");

		Assert.assertEquals(
			"Please enter a file with a valid file size no larger than 1 KB.",
			errorJSONObject.get("message"));
	}

	private MockPortletRequest _getMockPortletRequest() throws PortalException {
		MockPortletRequest mockPortletRequest = new MockPortletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getDefault());

		mockPortletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return mockPortletRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(filter = "upload.response.handler.system.default=true")
	private UploadResponseHandler _defaultUploadResponseHandler;

}