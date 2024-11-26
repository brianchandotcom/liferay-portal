/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.FailedItem;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.entity.TestEntity;
import com.liferay.headless.batch.engine.util.ExportImportTaskUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;

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
	public void testPostImportTask() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR)) {

			ImportTask importTask = ExportImportTaskUtil.postImportTask(
				JSONUtil.putAll(
					JSONUtil.put("textValue", "test")
				).toString(),
				TestEntity.class.getName(), "FAILED",
				HashMapBuilder.put(
					"createStrategy", "INSERT"
				).put(
					"taskItemDelegateName",
					"export-import-task-resource-exception"
				).build());

			Assert.assertEquals(
				"Modified error message for TestEntity 'test'",
				importTask.getErrorMessage());
		}

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

			ImportTask importTask = ExportImportTaskUtil.postImportTask(
				bodyJSONArray.toString(), TestEntity.class.getName(),
				"COMPLETED",
				HashMapBuilder.put(
					"createStrategy", "INSERT"
				).put(
					"importStrategy", "ON_ERROR_CONTINUE"
				).put(
					"taskItemDelegateName",
					"export-import-task-resource-exception"
				).build());

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

}