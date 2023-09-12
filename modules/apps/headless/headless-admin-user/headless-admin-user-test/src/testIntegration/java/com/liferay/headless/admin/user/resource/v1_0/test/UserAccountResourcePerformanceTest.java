/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.mail.messaging.MailMessageListener;
import com.liferay.oauth.client.LocalOAuthClient;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lily Chi
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class UserAccountResourcePerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Assume.assumeTrue(Validator.isNull(System.getenv("JENKINS_HOME")));

		_json = JSONUtil.put(
			"additionalName", ""
		).put(
			"alternateName", "[$ALTERNATE_NAME$]"
		).put(
			"birthDate", "1977-01-01T00:00:00Z"
		).put(
			"customFields", JSONFactoryUtil.createJSONArray()
		).put(
			"dashboardURL", ""
		).put(
			"dateCreated", "2021-05-19T16:04:46Z"
		).put(
			"dateModified", "2021-05-19T16:04:46Z"
		).put(
			"emailAddress", "[$EMAIL_ADDRESS$]"
		).put(
			"familyName", "Foo"
		).put(
			"givenName", "hgh"
		).put(
			"id", 39321
		).put(
			"jobTitle", ""
		).put(
			"keywords", JSONFactoryUtil.createJSONArray()
		).put(
			"name", "Able Foo"
		).put(
			"organizationBriefs", JSONFactoryUtil.createJSONArray()
		).put(
			"profileURL", ""
		).put(
			"roleBriefs",
			JSONUtil.put(
				JSONUtil.put(
					"id", 20113
				).put(
					"name", "User"
				))
		).put(
			"siteBriefs",
			JSONUtil.put(
				JSONUtil.merge(
					JSONUtil.put(
						"id", 20127
					).put(
						"name", "Global"
					),
					JSONUtil.put(
						"id", 20125
					).put(
						"name", "Guest"
					)))
		).put(
			"userAccountContactInformation",
			JSONUtil.put(
				"emailAddresses", JSONFactoryUtil.createJSONArray()
			).put(
				"facebook", ""
			).put(
				"postalAddresses", JSONFactoryUtil.createJSONArray()
			).put(
				"skype", ""
			).put(
				"sms", ""
			).put(
				"telephones", JSONFactoryUtil.createJSONArray()
			).put(
				"twitter", ""
			).put(
				"webUrls", JSONFactoryUtil.createJSONArray()
			)
		).toString();

		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.oauth2.provider.rest.internal.spi.bearer.token." +
				"provider.configuration." +
					"DefaultBearerTokenProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"access.token.expires.in", Integer.MAX_VALUE
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_pid);
	}

	@Before
	public void setUp() throws Exception {
		User user = TestPropsValues.getUser();

		_oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				user.getCompanyId(), user.getUserId(), user.getFullName(),
				Arrays.asList(
					GrantType.CLIENT_CREDENTIALS, GrantType.REFRESH_TOKEN,
					GrantType.JWT_BEARER, GrantType.RESOURCE_OWNER_PASSWORD,
					GrantType.AUTHORIZATION_CODE),
				"client_secret_post", user.getUserId(),
				RandomTestUtil.randomString(), 0, RandomTestUtil.randomString(),
				"", Collections.emptyList(), "", 0, "", "rest_token", "",
				Arrays.asList("http://localhost:8080"), false,
				Arrays.asList(
					"Liferay.Headless.Admin.User.everything",
					"Liferay.Headless.Admin.User.everything.read",
					"Liferay.Headless.Admin.User.everything.write"),
				false, new ServiceContext());

		_jsonObject = JSONFactoryUtil.createJSONObject(
			_localOAuthClient.requestTokens(
				_oAuth2Application, user.getUserId()));
	}

	@Test
	public void testMultipleThreadsAddUsers() throws Exception {
		int threadsCount = 10;
		int usersCount = 10;

		List<List<String>> jsonsList = new ArrayList<>();

		for (int i = 0; i < threadsCount; i++) {
			jsonsList.add(_createJSONs(usersCount));
		}

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				MailMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			long startTime = System.currentTimeMillis();

			ExecutorService executorService = Executors.newFixedThreadPool(
				threadsCount);

			List<Future<?>> futures = new ArrayList<>();

			for (List<String> jsons : jsonsList) {
				futures.add(executorService.submit(() -> _addUsers(jsons)));
			}

			for (Future<?> future : futures) {
				future.get();
			}

			long endTime = System.currentTimeMillis() - startTime;

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						threadsCount, " threads each added ", usersCount,
						" users in ", endTime, " ms"));

				double tps = (double)usersCount * threadsCount / endTime * 1000;

				_log.info("TPS: " + String.format("%.2f", tps));
			}
		}
	}

	@Test
	public void testSingleThreadAddUsers() {
		int usersCount = 100;

		List<String> jsons = _createJSONs(usersCount);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				MailMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			long startTime = System.currentTimeMillis();

			_addUsers(jsons);

			long endTime = System.currentTimeMillis() - startTime;

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Single thread added ", usersCount, " users in ",
						endTime, " ms"));

				double tps = (double)usersCount / endTime * 1000;

				_log.info("TPS: " + String.format("%.2f", tps));
			}
		}
	}

	private void _addUsers(List<String> jsons) {
		try {
			for (String json : jsons) {
				HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

				httpInvoker.body(json, "application/json");
				httpInvoker.header(
					"Authorization",
					"Bearer " + _jsonObject.getString("access_token"));
				httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
				httpInvoker.path(
					"http://localhost:8080/o/headless-admin-user/v1.0" +
						"/user-accounts");

				httpInvoker.invoke();
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private List<String> _createJSONs(int usersCount) {
		List<String> jsons = new ArrayList<>(usersCount);

		for (int i = 0; i < usersCount; i++) {
			String alternateName = PwdGenerator.getPassword(8);

			String json = StringUtil.replace(
				_json, "[$ALTERNATE_NAME$]", alternateName);

			jsons.add(
				StringUtil.replace(
					json, "[$EMAIL_ADDRESS$]",
					StringBundler.concat(
						alternateName, "@", RandomTestUtil.randomString(),
						".com")));
		}

		return jsons;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserAccountResourcePerformanceTest.class);

	private static String _json;
	private static String _pid;

	@Inject
	private CompanyLocalService _companyLocalService;

	private JSONObject _jsonObject;

	@Inject
	private LocalOAuthClient _localOAuthClient;

	@DeleteAfterTestRun
	private OAuth2Application _oAuth2Application;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private UserLocalService _userLocalService;

}