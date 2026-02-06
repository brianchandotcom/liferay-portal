/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.scim.rest.util.ScimClientUtil;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuele Castro
 */
@RunWith(Arquillian.class)
public class ScimClientBearerTokenProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		User user = TestPropsValues.getUser();

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				TestPropsValues.getCompanyId(), user.getUserId(),
				user.getFullName(),
				Collections.singletonList(GrantType.CLIENT_CREDENTIALS),
				"client_secret_post", user.getUserId(),
				RandomTestUtil.randomString(), 0, RandomTestUtil.randomString(),
				null, null, null, 0, null, RandomTestUtil.randomString(), null,
				null, false, null, false, new ServiceContext());

		String scimOAuth2ApplicationName = RandomTestUtil.randomString();

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.scim.rest.internal.configuration." +
				"ScimClientOAuth2ApplicationConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"matcherField", "email"
			).put(
				"oAuth2ApplicationName", scimOAuth2ApplicationName
			).put(
				"userId", user.getUserId()
			).build());

		OAuth2Application scimOAuth2Application =
			_oAuth2ApplicationLocalService.getOAuth2Application(
				TestPropsValues.getCompanyId(),
				ScimClientUtil.generateScimClientId(scimOAuth2ApplicationName));

		Assert.assertEquals(
			600,
			_getExpiresIn(
				oAuth2Application.getClientId(),
				oAuth2Application.getClientSecret()));
		Assert.assertEquals(
			TimeUnit.DAYS.toSeconds(365),
			_getExpiresIn(
				scimOAuth2Application.getClientId(),
				scimOAuth2Application.getClientSecret()));

		ConfigurationTestUtil.deleteConfiguration(pid);
	}

	private int _getExpiresIn(String clientId, String clientSecret)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(
			"Content-Type", ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED);
		options.addPart("client_id", clientId);
		options.addPart("client_secret", clientSecret);
		options.addPart("grant_type", "client_credentials");

		Company company = _companyLocalService.getCompanyById(
			TestPropsValues.getCompanyId());

		options.setLocation(
			StringBundler.concat(
				"http://", company.getVirtualHostname(),
				":8080/o/oauth2/token"));

		options.setMethod(Http.Method.POST);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			HttpUtil.URLtoString(options));

		return jsonObject.getInt("expires_in");
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

}