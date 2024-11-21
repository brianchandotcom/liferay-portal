/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.FailedItem;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ImportTaskSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.List;

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
	public void testPostFailingImportTask() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR)) {

			ImportTask importTask = _testPostFailingImportTask(
				JSONUtil.putAll(JSONFactoryUtil.createJSONObject()), "FAILED",
				ListUtil.fromArray("createStrategy=INSERT"));

			Assert.assertEquals(
				"Modified error message", importTask.getErrorMessage());
		}
	}

	@Test
	public void testPostImportTaskWithMultipleFailures() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal.strategy." +
					"OnErrorContinueBatchEngineImportStrategy",
				LoggerTestUtil.ERROR)) {

			JSONArray bodyJSONArray = JSONUtil.putAll(
				JSONUtil.put(
					"intValue", RandomTestUtil.randomInt()
				).put(
					"textValue", RandomTestUtil.randomString()
				),
				JSONUtil.put(
					"intValue", RandomTestUtil.randomInt()
				).put(
					"textValue", RandomTestUtil.randomString()
				),
				JSONUtil.put(
					"intValue", RandomTestUtil.randomInt()
				).put(
					"textValue", RandomTestUtil.randomString()
				));

			ImportTask importTask = _testPostFailingImportTask(
				bodyJSONArray, "COMPLETED",
				ListUtil.fromArray(
					"createStrategy=INSERT",
					"importStrategy=ON_ERROR_CONTINUE"));

			Assert.assertEquals(3, (int)importTask.getProcessedItemsCount());

			for (FailedItem failedItem : importTask.getFailedItems()) {
				JSONObject jsonObject = (JSONObject)bodyJSONArray.get(
					failedItem.getItemIndex() - 1);

				Assert.assertEquals(
					"Modified error message for TestEntity '" +
						jsonObject.getString("textValue") + "'",
					failedItem.getMessage());
			}
		}
	}

	private ImportTask _testPostFailingImportTask(
			JSONArray bodyJSONArray, String expectedExecuteStatus,
			List<String> queryParameters)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(bodyJSONArray.toString(), "application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		StringBundler sb = new StringBundler();

		sb.append("http://localhost:8080/o/headless-batch-engine/v1.0");
		sb.append("/import-task/");
		sb.append(TestEntity.class.getName());
		sb.append("?");

		for (String queryParameter : queryParameters) {
			sb.append(queryParameter);
			sb.append("&");
		}

		sb.append("taskItemDelegateName=export-import-exception-thrower");

		httpInvoker.path(sb.toString());
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		ImportTask importTask = ImportTaskSerDes.toDTO(
			httpResponse.getContent());

		String externalReferenceCode = importTask.getExternalReferenceCode();

		String actualExecuteStatus;

		while (true) {
			httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
			httpInvoker.path(
				"http://localhost:8080/o/headless-batch-engine/v1.0" +
					"/import-task/by-external-reference-code/" +
						externalReferenceCode);
			httpInvoker.userNameAndPassword(
				"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

			httpResponse = httpInvoker.invoke();

			Assert.assertEquals(200, httpResponse.getStatusCode());

			importTask = ImportTaskSerDes.toDTO(httpResponse.getContent());

			actualExecuteStatus = importTask.getExecuteStatusAsString();

			if (StringUtil.equals(actualExecuteStatus, "COMPLETED") ||
				StringUtil.equals(actualExecuteStatus, "FAILED")) {

				break;
			}
		}

		Assert.assertEquals(expectedExecuteStatus, actualExecuteStatus);

		return importTask;
	}

}