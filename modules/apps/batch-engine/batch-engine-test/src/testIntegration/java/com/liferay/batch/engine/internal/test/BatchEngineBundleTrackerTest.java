/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class BatchEngineBundleTrackerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(BatchEngineBundleTrackerTest.class);

		_bundleContext = _bundle.getBundleContext();
	}

	@Test
	public void testProcessBatchEngineBundle() throws Exception {
		_testProcessBatchEngineBundle(
			"batch1", 1, Arrays.asList("/batch1/export.json"));
		_testProcessBatchEngineBundle("batch2", 0, Arrays.asList());
		_testProcessBatchEngineBundle(
			"batch3", 2,
			Arrays.asList(
				"/batch3/batch1/export.json", "/batch3/batch2/export.json"));
		_testProcessBatchEngineBundle(
			"batch4", 3,
			Arrays.asList(
				"/batch4/batch1/export.json", "/batch4/batch2/export.json",
				"/batch4/batch2/batch3/export.json"));
		_testProcessBatchEngineBundle(
			"batch5", 1, Arrays.asList("/batch5/data.batch-engine-data.json"));
		_testProcessBatchEngineBundle(
			"batch6", 2,
			Arrays.asList(
				"/batch6/data1.batch-engine-data.json",
				"/batch6/data2.batch-engine-data.json"));
		_testProcessBatchEngineBundle(
			"batch7", 1, Arrays.asList("/batch7/export.json"));
		_testProcessBatchEngineBundle(
			"batch8", 3,
			Arrays.asList(
				"/batch8/data1.batch-engine-data.json",
				"/batch8/data2.batch-engine-data.json",
				"/batch8/data3.batch-engine-data.json"));

		_testProcessBatchEngineBundle(
			"batch9", 1, Arrays.asList("/batch9/data.batch-engine-data.json"));

		_company = CompanyTestUtil.addCompany();

		_testProcessBatchEngineBundle(
			"batch9", 2,
			Arrays.asList(
				"/batch9/data.batch-engine-data.json",
				"/batch9/data.batch-engine-data.json"));
	}

	private void _testProcessBatchEngineBundle(
			String dirName, int expectedCount, List<String> dataFilesExpected)
		throws Exception {

		Class<?> clazz = _batchEngineImportTaskExecutor.getClass();

		ComponentDescriptionDTO componentDescriptionDTO =
			_serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(clazz), clazz.getName());

		Promise<Void> promise = _serviceComponentRuntime.disableComponent(
			componentDescriptionDTO);

		promise.getValue();

		IntegerWrapper actualCount = new IntegerWrapper();
		List<String> dataFilesProcessed = new CopyOnWriteArrayList<>();

		ServiceRegistration<BatchEngineImportTaskExecutor> serviceRegistration =
			_bundleContext.registerService(
				BatchEngineImportTaskExecutor.class,
				new BatchEngineImportTaskExecutor() {

					@Override
					public void execute(
						BatchEngineImportTask batchEngineImportTask) {

						actualCount.increment();
						dataFilesProcessed.add(
							BatchEngineUnitThreadLocal.getDataFileName());
					}

					@Override
					public void execute(
						BatchEngineImportTask batchEngineImportTask,
						BatchEngineTaskItemDelegate<?>
							batchEngineTaskItemDelegate,
						boolean checkPermissions) {

						actualCount.increment();
						dataFilesProcessed.add(
							BatchEngineUnitThreadLocal.getDataFileName());
					}

				},
				null);

		Bundle bundle = _bundleContext.installBundle(
			RandomTestUtil.randomString(), _toInputStream(dirName));

		try {
			bundle.start();

			Thread.sleep(2000);

			Assert.assertEquals(expectedCount, actualCount.getValue());

			Assert.assertTrue(
				StringBundler.concat(
					"Expected ", dataFilesExpected.size(), " was ",
					dataFilesProcessed.size()),
				dataFilesExpected.size() == dataFilesProcessed.size());
			Assert.assertTrue(
				StringBundler.concat(
					"Expected ", dataFilesExpected, " was ",
					dataFilesProcessed),
				dataFilesExpected.containsAll(dataFilesProcessed));

			bundle.stop();

			bundle.start();

			Thread.sleep(2000);

			Assert.assertEquals(expectedCount, actualCount.getValue());
		}
		finally {
			bundle.uninstall();

			serviceRegistration.unregister();

			promise = _serviceComponentRuntime.enableComponent(
				componentDescriptionDTO);

			promise.getValue();
		}
	}

	private InputStream _toInputStream(String dirName) throws Exception {
		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		String basePath = StringBundler.concat(
			"com/liferay/batch/engine/internal/test/dependencies/", dirName,
			StringPool.SLASH);

		Enumeration<URL> enumeration = _bundle.findEntries(basePath, "*", true);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				String urlPath = url.getPath();

				if (urlPath.endsWith(StringPool.SLASH)) {
					continue;
				}

				String zipPath = urlPath.substring(basePath.length());

				if (zipPath.startsWith(StringPool.SLASH)) {
					zipPath = zipPath.substring(1);
				}

				try (InputStream inputStream = url.openStream()) {
					zipWriter.addEntry(zipPath, inputStream);
				}
			}
		}

		return new FileInputStream(zipWriter.getFile());
	}

	@Inject
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	private Bundle _bundle;
	private BundleContext _bundleContext;

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private ServiceComponentRuntime _serviceComponentRuntime;

	@Inject
	private ZipWriterFactory _zipWriterFactory;

}