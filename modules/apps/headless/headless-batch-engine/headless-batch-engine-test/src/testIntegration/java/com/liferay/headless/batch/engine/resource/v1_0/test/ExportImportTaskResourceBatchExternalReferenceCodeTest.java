/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.batch.engine.entity.TestEntity;
import com.liferay.headless.batch.engine.exception.TestEntityException;
import com.liferay.headless.batch.engine.util.ExportImportTaskUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class ExportImportTaskResourceBatchExternalReferenceCodeTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_batchExternalReferenceCode = RandomTestUtil.randomString();
		_externalReferenceCode = RandomTestUtil.randomString();
	}

	@Test
	public void testImportWithBatchExternalReferenceCode() throws Exception {
		try (BatchEngineTaskItemDelegateAutoCloseable
				batchEngineTaskItemDelegateAutoCloseable =
					new BatchEngineTaskItemDelegateAutoCloseable(
						_batchExternalReferenceCode)) {

			_testPostImportTask(
				_batchExternalReferenceCode, null,
				batchEngineTaskItemDelegateAutoCloseable.
					getTaskItemDelegateName());
		}
	}

	@Test
	public void testImportWithBothExternalReferenceCodes() throws Exception {
		try (BatchEngineTaskItemDelegateAutoCloseable
				batchEngineTaskItemDelegateAutoCloseable =
					new BatchEngineTaskItemDelegateAutoCloseable(
						_batchExternalReferenceCode)) {

			_testPostImportTask(
				_batchExternalReferenceCode, _externalReferenceCode,
				batchEngineTaskItemDelegateAutoCloseable.
					getTaskItemDelegateName());
		}
	}

	@Test
	public void testImportWithExternalReferenceCode() throws Exception {
		try (BatchEngineTaskItemDelegateAutoCloseable
				batchEngineTaskItemDelegateAutoCloseable =
					new BatchEngineTaskItemDelegateAutoCloseable(
						_externalReferenceCode)) {

			_testPostImportTask(
				null, _externalReferenceCode,
				batchEngineTaskItemDelegateAutoCloseable.
					getTaskItemDelegateName());
		}
	}

	@Test
	public void testImportWithNoExternalReferenceCodesFail() throws Exception {
		try (BatchEngineTaskItemDelegateAutoCloseable
				batchEngineTaskItemDelegateAutoCloseable =
					new BatchEngineTaskItemDelegateAutoCloseable(
						_externalReferenceCode);
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.ERROR)) {

			_testPostImportTask(
				null, null,
				batchEngineTaskItemDelegateAutoCloseable.
					getTaskItemDelegateName(),
				false);
		}
	}

	@Test
	public void testImportWithNoExternalReferenceCodesSuccess()
		throws Exception {

		try (BatchEngineTaskItemDelegateAutoCloseable
				batchEngineTaskItemDelegateAutoCloseable =
					new BatchEngineTaskItemDelegateAutoCloseable(null)) {

			_testPostImportTask(
				null, null,
				batchEngineTaskItemDelegateAutoCloseable.
					getTaskItemDelegateName());
		}
	}

	private void _testPostImportTask(
			String batchExternalReferenceCode, String externalReferenceCode,
			String taskItemDelegateName)
		throws Exception {

		_testPostImportTask(
			batchExternalReferenceCode, externalReferenceCode,
			taskItemDelegateName, true);
	}

	private void _testPostImportTask(
			String batchExternalReferenceCode, String externalReferenceCode,
			String taskItemDelegateName, boolean expectSuccess)
		throws Exception {

		ExportImportTaskUtil.postImportTask(
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
			expectSuccess ? "COMPLETED" : "FAILED",
			HashMapBuilder.put(
				"batchExternalReferenceCode",
				() -> {
					if (Validator.isNotNull(batchExternalReferenceCode)) {
						return batchExternalReferenceCode;
					}

					return null;
				}
			).put(
				"createStrategy", "INSERT"
			).put(
				"externalReferenceCode",
				() -> {
					if (Validator.isNotNull(externalReferenceCode)) {
						return externalReferenceCode;
					}

					return null;
				}
			).put(
				"taskItemDelegateName", taskItemDelegateName
			).build());
	}

	private String _batchExternalReferenceCode;
	private String _externalReferenceCode;

	private static class BatchEngineTaskItemDelegateAutoCloseable
		implements AutoCloseable {

		public BatchEngineTaskItemDelegateAutoCloseable(
				String expectedExternalReferenceCode)
			throws Exception {

			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			BatchEngineTaskItemDelegate<TestEntity> delegate =
				new BaseBatchEngineTaskItemDelegate<TestEntity>() {

					@Override
					public TestEntity createItem(
							TestEntity testEntity,
							Map<String, Serializable> parameters)
						throws Exception {

						String externalReferenceCode = (String)parameters.get(
							"externalReferenceCode");

						if (!Objects.equals(
								expectedExternalReferenceCode,
								externalReferenceCode)) {

							throw new TestEntityException(
								StringBundler.concat(
									"Received externalReferenceCode ",
									externalReferenceCode,
									" does not match expected ",
									expectedExternalReferenceCode));
						}

						return new TestEntity();
					}

					@Override
					public Page<TestEntity> read(
							Filter filter, Pagination pagination, Sort[] sorts,
							Map<String, Serializable> parameters, String search)
						throws Exception {

						return null;
					}

				};

			_taskItemDelegateName =
				"test-delegate-erc-" +
					StringUtil.lowerCase(RandomTestUtil.randomString(8));

			_serviceRegistration = bundleContext.registerService(
				BatchEngineTaskItemDelegate.class, delegate,
				HashMapDictionaryBuilder.<String, Object>put(
					"batch.engine.task.item.delegate.name",
					_taskItemDelegateName
				).build());
		}

		@Override
		public void close() throws Exception {
			_serviceRegistration.unregister();
		}

		public String getTaskItemDelegateName() {
			return _taskItemDelegateName;
		}

		private final ServiceRegistration<?> _serviceRegistration;
		private final String _taskItemDelegateName;

	}

}