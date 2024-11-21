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

import java.util.ArrayList;
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
	public void testPostImportTaskWithMultipleTestEntitiesFailing()
		throws Exception {

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

		List<String> queryParameters = new ArrayList<>();

		queryParameters.add("createStrategy=INSERT");
		queryParameters.add("importStrategy=ON_ERROR_CONTINUE");

		HttpInvoker httpInvoker = _getHttpInvoker(
			bodyJSONArray, TestEntity.class.getName(), queryParameters,
			"export-import-problem-thrower");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal.strategy." +
					"OnErrorContinueBatchEngineImportStrategy",
				LoggerTestUtil.ERROR)) {

			ImportTask importTask = _postImportTask("COMPLETED", httpInvoker);

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

	@Test
	public void testPostImportTaskWithTestEntityFailing() throws Exception {
		HttpInvoker httpInvoker = _getHttpInvoker(
			JSONUtil.putAll(JSONFactoryUtil.createJSONObject()),
			TestEntity.class.getName(),
			ListUtil.fromArray("createStrategy=INSERT"),
			"export-import-problem-thrower");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR)) {

			ImportTask importTask = _postImportTask("FAILED", httpInvoker);

			Assert.assertEquals(
				"Modified error message", importTask.getErrorMessage());
		}
	}

	private HttpInvoker _getHttpInvoker(
			JSONArray bodyJSONArray, String className,
			List<String> queryParameters, String taskItemDelegateName)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(bodyJSONArray.toString(), "application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		StringBundler sb = new StringBundler();

		sb.append("http://localhost:8080/o/headless-batch-engine/v1.0");
		sb.append("/import-task/");
		sb.append(className);
		sb.append("?");

		for (String queryParameter : queryParameters) {
			sb.append(queryParameter);
			sb.append("&");
		}

		sb.append("taskItemDelegateName=");
		sb.append(taskItemDelegateName);

		httpInvoker.path(sb.toString());
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		return httpInvoker;
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

	private ImportTask _postImportTask(
			String expectedExecuteStatus, HttpInvoker httpInvoker)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		ImportTask importTask = ImportTaskSerDes.toDTO(
			httpResponse.getContent());

		String externalReferenceCode = importTask.getExternalReferenceCode();

		String actualExecuteStatus = null;

		while (true) {
			importTask = ImportTaskSerDes.toDTO(
				_invoke(
					"http://localhost:8080/o/headless-batch-engine/v1.0" +
						"/import-task/by-external-reference-code/" +
							externalReferenceCode));

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