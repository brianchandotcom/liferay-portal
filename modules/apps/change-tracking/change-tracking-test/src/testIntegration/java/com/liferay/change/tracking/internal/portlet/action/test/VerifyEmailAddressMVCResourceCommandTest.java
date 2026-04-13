/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class VerifyEmailAddressMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Test
	public void testServeResource() throws Exception {
		JSONObject jsonObject1 = _serveResource(
			RandomTestUtil.randomLong(), StringPool.BLANK);

		Assert.assertEquals(
			_language.get(LocaleUtil.US, "this-publication-no-longer-exists"),
			jsonObject1.getString("errorMessage"));

		User expectedUser = UserTestUtil.addUser();

		JSONObject jsonObject2 = _serveResource(
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			expectedUser.getEmailAddress());

		JSONObject userJSONObject = jsonObject2.getJSONObject("user");

		Assert.assertEquals(
			expectedUser.getEmailAddress(),
			userJSONObject.getString("emailAddress"));
		Assert.assertEquals(
			expectedUser.getUserId(), userJSONObject.getLong("userId"));
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
			long ctCollectionId, String emailAddress)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_CONFIG, null);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setUser(TestPropsValues.getUser());

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayResourceRequest.setParameter(
			"ctCollectionId", String.valueOf(ctCollectionId));
		mockLiferayResourceRequest.setParameter("emailAddress", emailAddress);

		return mockLiferayResourceRequest;
	}

	private JSONObject _serveResource(long ctCollectionId, String emailAddress)
		throws Exception {

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			_getMockLiferayResourceRequest(ctCollectionId, emailAddress),
			mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private Language _language;

	@Inject(filter = "mvc.command.name=/change_tracking/verify_email_address")
	private MVCResourceCommand _mvcResourceCommand;

}