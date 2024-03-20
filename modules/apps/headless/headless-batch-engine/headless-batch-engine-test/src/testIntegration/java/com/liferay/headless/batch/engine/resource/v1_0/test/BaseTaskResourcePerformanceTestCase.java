/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Closeable;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Vendel Toreki
 */
public abstract class BaseTaskResourcePerformanceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(Validator.isNull(System.getenv("JENKINS_HOME")));
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class<?> clazz = BaseTaskResourcePerformanceTestCase.class;

		Properties properties = PropertiesUtil.load(
			clazz.getResourceAsStream(
				"dependencies/batch-export-import-performance.properties"),
			"UTF-8");

		recordsCount = GetterUtil.getInteger(
			properties.getProperty("records.count"));
	}

	protected String getHttpResponseContent(String url) throws IOException {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.userNameAndPassword("test@liferay.com:test");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);

		HttpInvoker.HttpResponse response = httpInvoker.invoke();

		Assert.assertEquals(200, response.getStatusCode());

		return response.getContent();
	}

	protected Map<String, String> splitClassName(String className) {
		Map<String, String> classNamePartsMap = new HashMap<>();

		if (className.contains("#")) {
			String[] classNameParts = className.split("#");

			classNamePartsMap.put("className", classNameParts[0]);
			classNamePartsMap.put("taskItemDelegateName", classNameParts[1]);
		}
		else {
			classNamePartsMap.put("className", className);
		}

		return classNamePartsMap;
	}

	protected Closeable startTimer(Log log) {
		return startTimer(log, null);
	}

	protected Closeable startTimer(Log log, String message) {
		Thread thread = Thread.currentThread();

		StackTraceElement stackTraceElement = thread.getStackTrace()[3];

		String invokerName = StringBundler.concat(
			stackTraceElement.getClassName(), StringPool.POUND,
			stackTraceElement.getMethodName());

		long startTime = System.currentTimeMillis();

		return () -> {
			if (log.isInfoEnabled()) {
				long totalTimeMillis = System.currentTimeMillis() - startTime;

				double speed = (totalTimeMillis > 0) ?
					(double)(recordsCount * 1000) / (double)totalTimeMillis :
						Double.NaN;

				log.info(
					StringBundler.concat(
						invokerName,
						Validator.isNotNull(message) ? (" (" + message + ") ") :
							"",
						" used ", totalTimeMillis, " ms, for ", recordsCount,
						" records, speed: ", String.format("%.2f", speed),
						" records/s"));
			}
		};
	}

	protected static int recordsCount;

}