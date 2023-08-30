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
@RunWith(Arquillian.class)
public class AddUsersByAPIPerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Assume.assumeTrue(Validator.isNull(System.getenv("JENKINS_HOME")));

		_jsonTemplate = JSONUtil.put(
			"additionalName", ""
		).put(
			"alternateName", "@alternateName@"
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
			"emailAddress", "@emailAddress@"
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
			_FACTORY_PID,
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
		long companyId = _companyLocalService.getCompanyIdByUserId(0);

		User user = _userLocalService.getUserByEmailAddress(
			companyId, "test@liferay.com");

		long userId = user.getUserId();

		String userName = user.getFullName();

		List<GrantType> allowedGrantTypesList = new ArrayList<>(5);

		allowedGrantTypesList.add(GrantType.CLIENT_CREDENTIALS);
		allowedGrantTypesList.add(GrantType.REFRESH_TOKEN);
		allowedGrantTypesList.add(GrantType.JWT_BEARER);
		allowedGrantTypesList.add(GrantType.RESOURCE_OWNER_PASSWORD);
		allowedGrantTypesList.add(GrantType.AUTHORIZATION_CODE);

		List<String> scopeAliasesList = new ArrayList<>(3);

		scopeAliasesList.add("Liferay.Headless.Admin.User.everything");
		scopeAliasesList.add("Liferay.Headless.Admin.User.everything.read");
		scopeAliasesList.add("Liferay.Headless.Admin.User.everything.write");

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				companyId, userId, userName, allowedGrantTypesList,
				_CLIENT_AUTHENTICATION_METHOD, userId, _CLIENT_ID, 0,
				_CLIENT_SECRET, "", Collections.emptyList(), "", 0, "",
				_OAUTH1_APPLICATION_NAME, "",
				Arrays.asList("http://localhost:8080"), false, scopeAliasesList,
				false, new ServiceContext());

		_jsonObject = JSONFactoryUtil.createJSONObject(
			_localOAuthClient.requestTokens(oAuth2Application, userId));
	}

	@Test
	public void testMultipleThreadsAddUsers() throws Exception {
		int threadCount = 10;
		int userCount = 10;

		List<List<String>> jsonsList = new ArrayList<>();

		for (int i = 0; i < threadCount; i++) {
			jsonsList.add(_createJSONs(userCount));
		}

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				MailMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			ExecutorService executorService = Executors.newFixedThreadPool(
				threadCount);

			List<Future<?>> futures = new ArrayList<>();

			long startTime = System.currentTimeMillis();

			for (List<String> jsons : jsonsList) {
				futures.add(executorService.submit(() -> _addUsers(jsons)));
			}

			for (Future<?> future : futures) {
				future.get();
			}

			long duration = System.currentTimeMillis() - startTime;

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						threadCount, " threads each added ", userCount,
						" users, used ", duration, "ms"));

				double tps = (double)userCount * threadCount / duration * 1000;

				_log.info(
					"Average speed : " + String.format("%.2f", tps) + " tps");
			}
		}
	}

	@Test
	public void testSingleThreadAddUsers() {
		int userCount = 100;

		List<String> jsons = _createJSONs(userCount);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				MailMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			long startTime = System.currentTimeMillis();

			_addUsers(jsons);

			long duration = System.currentTimeMillis() - startTime;

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Single thread added ", userCount, " users, used ",
						duration, "ms"));

				double tps = (double)userCount / duration * 1000;

				_log.info(
					"Average speed : " + String.format("%.2f", tps) + " tps");
			}
		}
	}

	private void _addUsers(List<String> jsons) {
		try {
			for (String json : jsons) {
				HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

				httpInvoker.body(json, "application/json");
				httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
				httpInvoker.path(
					"http://localhost:8080/o/headless-admin-user/v1.0" +
						"/user-accounts");

				httpInvoker.header(
					"Authorization",
					_TOKEN_TYPE + " " + _jsonObject.getString("access_token"));

				httpInvoker.invoke();
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private List<String> _createJSONs(int userCount) {
		List<String> jsons = new ArrayList<>(userCount);

		for (int i = 0; i < userCount; i++) {
			String alternateName = PwdGenerator.getPassword(8);

			String json = StringUtil.replace(
				_jsonTemplate, _ALTER_NAME_TOKEN, alternateName);

			jsons.add(
				StringUtil.replace(
					json, _EMAILADDRESS_TOKEN, alternateName + _EMAIL_PREFIX));
		}

		return jsons;
	}

	private static final String _ALTER_NAME_TOKEN = "@alternateName@";

	private static final String _CLIENT_AUTHENTICATION_METHOD =
		"client_secret_post";

	private static final String _CLIENT_ID =
		"id-bea359e9-7f19-732a-d423-093137a5515";

	private static final String _CLIENT_SECRET =
		"secret-1c7a64e0-9de0-22c9-f6ad-17a1dfb6575a";

	private static final String _EMAIL_PREFIX = "@VodafoneIdea.com";

	private static final String _EMAILADDRESS_TOKEN = "@emailAddress@";

	private static final String _FACTORY_PID =
		"com.liferay.oauth2.provider.rest.internal.spi.bearer.token.provider." +
			"configuration.DefaultBearerTokenProviderConfiguration";

	private static final String _OAUTH1_APPLICATION_NAME = "rest_token";

	private static final String _TOKEN_TYPE = "Bearer";

	private static final Log _log = LogFactoryUtil.getLog(
		AddUsersByAPIPerformanceTest.class);

	private static String _jsonTemplate;
	private static String _pid;

	@Inject
	private CompanyLocalService _companyLocalService;

	private JSONObject _jsonObject;

	@Inject
	private LocalOAuthClient _localOAuthClient;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private UserLocalService _userLocalService;

}