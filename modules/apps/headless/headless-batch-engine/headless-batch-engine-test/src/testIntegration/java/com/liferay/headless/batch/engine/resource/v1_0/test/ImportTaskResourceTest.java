/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.batch.engine.client.dto.v1_0.FailedItem;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.entity.TestEntity;
import com.liferay.headless.batch.engine.util.ExportImportTaskUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alberto Javier Moreno Lage
 * @author Vendel Toreki
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

	@Test
	public void testPostImportTaskWithBatchExternalReferenceCode()
		throws Exception {

		String batchExternalReferenceCode = RandomTestUtil.randomString();

		ImportTask importTask = _postImportTask(
			batchExternalReferenceCode, null);

		Assert.assertEquals(batchExternalReferenceCode, _externalReferenceCode);
		Assert.assertNotEquals(
			batchExternalReferenceCode, importTask.getExternalReferenceCode());
	}

	@Test
	public void testPostImportTaskWithExternalReferenceCode() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		ImportTask importTask = _postImportTask(null, externalReferenceCode);

		Assert.assertEquals(externalReferenceCode, _externalReferenceCode);
		Assert.assertEquals(
			externalReferenceCode, importTask.getExternalReferenceCode());
	}

	@Test
	public void testPostImportTaskWithExternalReferenceCodes()
		throws Exception {

		String batchExternalReferenceCode = RandomTestUtil.randomString();
		String externalReferenceCode = RandomTestUtil.randomString();

		ImportTask importTask = _postImportTask(
			batchExternalReferenceCode, externalReferenceCode);

		Assert.assertEquals(batchExternalReferenceCode, _externalReferenceCode);
		Assert.assertEquals(
			externalReferenceCode, importTask.getExternalReferenceCode());
	}

	@Test
	public void testPostImportTaskWithoutExternalReferenceCodes()
		throws Exception {

		ImportTask importTask = _postImportTask(null, null);

		Assert.assertNull(_externalReferenceCode);
		Assert.assertNotNull(importTask.getExternalReferenceCode());
	}

	private ImportTask _postImportTask(
			String batchExternalReferenceCode, String externalReferenceCode)
		throws Exception {

		try (BatchEngineTaskItemDelegateAutoCloseable
				batchEngineTaskItemDelegateAutoCloseable =
					new BatchEngineTaskItemDelegateAutoCloseable()) {

			return ExportImportTaskUtil.postImportTask(
				JSONFactoryUtil.createJSONArray(
				).put(
					JSONUtil.put(
						"intValue", String.valueOf(RandomTestUtil.nextInt())
					).put(
						"textValue",
						StringUtil.getTitleCase(
							RandomTestUtil.randomString(
								8, UniqueStringRandomizerBumper.INSTANCE),
							true, "")
					)
				).toString(),
				"com.liferay.headless.batch.engine.entity.TestEntity",
				"COMPLETED",
				HashMapBuilder.put(
					"batchExternalReferenceCode", batchExternalReferenceCode
				).put(
					"createStrategy", "INSERT"
				).put(
					"externalReferenceCode", externalReferenceCode
				).put(
					"taskItemDelegateName",
					batchEngineTaskItemDelegateAutoCloseable.
						getTaskItemDelegateName()
				).build());
		}
	}

	private String _externalReferenceCode;

	private class BatchEngineTaskItemDelegateAutoCloseable
		implements AutoCloseable {

		public BatchEngineTaskItemDelegateAutoCloseable() throws Exception {
			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			BatchEngineTaskItemDelegate<TestEntity>
				batchEngineTaskItemDelegate =
					new BaseBatchEngineTaskItemDelegate<>() {

						@Override
						public TestEntity createItem(
							TestEntity testEntity,
							Map<String, Serializable> parameters) {

							_externalReferenceCode = (String)parameters.get(
								"externalReferenceCode");

							return new TestEntity();
						}

						@Override
						public Page<TestEntity> read(
							Filter filter, Pagination pagination, Sort[] sorts,
							Map<String, Serializable> parameters,
							String search) {

							return null;
						}

					};

			_taskItemDelegateName =
				"test-delegate-erc-" +
					StringUtil.lowerCase(RandomTestUtil.randomString(8));

			_serviceRegistration = bundleContext.registerService(
				BatchEngineTaskItemDelegate.class, batchEngineTaskItemDelegate,
				HashMapDictionaryBuilder.<String, Object>put(
					"batch.engine.task.item.delegate.name",
					_taskItemDelegateName
				).build());
		}

		@Override
		public void close() {
			_serviceRegistration.unregister();
		}

		public String getTaskItemDelegateName() {
			return _taskItemDelegateName;
		}

		private final ServiceRegistration<?> _serviceRegistration;
		private final String _taskItemDelegateName;

	}

}