/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.mail.messaging.MailMessageListener;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lily Chi
 */
@RunWith(Arquillian.class)
public class AddUsersByAPIPerformanceTest {

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

				httpInvoker.userNameAndPassword("test@liferay.com:test");

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

	private static final String _EMAIL_PREFIX = "@VodafoneIdea.com";

	private static final String _EMAILADDRESS_TOKEN = "@emailAddress@";

	private static final Log _log = LogFactoryUtil.getLog(
		AddUsersByAPIPerformanceTest.class);

	private static String _jsonTemplate;

}