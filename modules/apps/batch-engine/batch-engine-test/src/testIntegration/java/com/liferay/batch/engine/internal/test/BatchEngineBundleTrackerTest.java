/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.batch.engine.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

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
		_testProcessBatchEngineBundle("batch1", 1);
		_testProcessBatchEngineBundle("batch2", 0);
		_testProcessBatchEngineBundle("batch3", 2);
		_testProcessBatchEngineBundle("batch4", 3);
		_testProcessBatchEngineBundle("batch5", 1);
		_testProcessBatchEngineBundle("batch6", 2);
		_testProcessBatchEngineBundle("batch7", 1);
		_testProcessBatchEngineBundle("batch8", 3);
	}

	private void _testProcessBatchEngineBundle(
			String dirName, int expectedCount)
		throws Exception {

		Bundle bundle = _bundleContext.installBundle(
			RandomTestUtil.randomString(), _toInputStream(dirName));

		IntegerWrapper actualCount = new IntegerWrapper();
		BooleanWrapper processed = new BooleanWrapper();

		ServiceRegistration<BatchEngineUnitProcessor> serviceRegistration =
			_bundleContext.registerService(
				BatchEngineUnitProcessor.class,
				batchEngineUnits -> {
					for (BatchEngineUnit batchEngineUnit : batchEngineUnits) {
						if (batchEngineUnit.isValid()) {
							actualCount.increment();
						}
					}

					processed.setValue(true);
				},
				HashMapDictionaryBuilder.put(
					Constants.SERVICE_RANKING, 1000
				).build());

		try {
			bundle.start();

			Thread.sleep(2000);

			Assert.assertEquals(expectedCount, actualCount.getValue());
			Assert.assertTrue(processed.getValue());

			processed.setValue(false);

			bundle.stop();

			bundle.start();

			Thread.sleep(2000);

			Assert.assertEquals(expectedCount, actualCount.getValue());
			Assert.assertFalse(processed.getValue());
		}
		finally {
			bundle.uninstall();

			serviceRegistration.unregister();
		}
	}

	private InputStream _toInputStream(String dirName) throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

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

				zipWriter.addEntry(zipPath, url.openStream());
			}
		}

		return new FileInputStream(zipWriter.getFile());
	}

	private Bundle _bundle;
	private BundleContext _bundleContext;

}