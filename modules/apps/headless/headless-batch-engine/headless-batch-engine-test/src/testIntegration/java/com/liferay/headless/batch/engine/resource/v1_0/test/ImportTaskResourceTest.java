/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.FailedItem;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ImportTaskSerDes;
import com.liferay.headless.batch.engine.entity.TestEntity;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
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
				JSONUtil.putAll(JSONUtil.put("textValue", "test")), "FAILED",
				ListUtil.fromArray("createStrategy=INSERT"));

			Assert.assertEquals(
				"Modified error message for TestEntity 'test'",
				importTask.getErrorMessage());
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

			FailedItem[] failedItems = importTask.getFailedItems();

			Assert.assertEquals(
				Arrays.toString(failedItems), 3, failedItems.length);

			for (FailedItem failedItem : failedItems) {
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

		StringBundler sb = new StringBundler();

		sb.append("headless-batch-engine/v1.0/import-task/");
		sb.append(TestEntity.class.getName());
		sb.append("?");

		for (String queryParameter : queryParameters) {
			sb.append(queryParameter);
			sb.append("&");
		}

		sb.append("taskItemDelegateName=export-import-task-resource-exception");

		ImportTask importTask = ImportTaskSerDes.toDTO(
			HTTPTestUtil.invokeToString(
				bodyJSONArray.toString(), sb.toString(), Http.Method.POST));

		String externalReferenceCode = importTask.getExternalReferenceCode();

		while (true) {
			importTask = ImportTaskSerDes.toDTO(
				HTTPTestUtil.invokeToString(
					null,
					"headless-batch-engine/v1.0/import-task/by-external-" +
						"reference-code/" + externalReferenceCode,
					Http.Method.GET));

			if (!StringUtil.equals(
					importTask.getExecuteStatusAsString(), "STARTED")) {

				Assert.assertEquals(
					expectedExecuteStatus,
					importTask.getExecuteStatusAsString());

				break;
			}
		}

		return importTask;
	}

}