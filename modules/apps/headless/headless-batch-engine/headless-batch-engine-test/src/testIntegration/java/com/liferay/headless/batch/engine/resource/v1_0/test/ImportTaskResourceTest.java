/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ImportTaskSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@RunWith(Arquillian.class)
public class ImportTaskResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testPostImportTaskWithTestEntity() throws Exception {
		JSONArray bodyJSONArray = JSONUtil.putAll(
			JSONFactoryUtil.createJSONObject());

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(bodyJSONArray.toString(), "application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			StringBundler.concat(
				"http://localhost:8080/o/headless-batch-engine/v1.0/import-",
				"task/com.liferay.headless.batch.engine.resource.v1_0.test.",
				"TestEntity?createStrategy=INSERT&taskItemDelegateName=export-",
				"import-problem-thrower"));

		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR)) {

			HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

			ImportTask importTask = ImportTaskSerDes.toDTO(
				httpResponse.getContent());

			String externalReferenceCode =
				importTask.getExternalReferenceCode();

			while (true) {
				importTask = ImportTaskSerDes.toDTO(
					_invoke(
						"http://localhost:8080/o/headless-batch-engine/v1.0" +
							"/import-task/by-external-reference-code/" +
								externalReferenceCode));

				String executeStatus = importTask.getExecuteStatusAsString();

				Assert.assertNotEquals("COMPLETED", executeStatus);

				if (Objects.equals(
						importTask.getExecuteStatusAsString(), "FAILED")) {

					break;
				}
			}

			Assert.assertEquals(
				"Modified error message", importTask.getErrorMessage());
		}
	}

	private String _invoke(String url) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		Assert.assertEquals(200, httpResponse.getStatusCode());

		return httpResponse.getContent();
	}

}