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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.FileInputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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
	public void testBatchEngineBundles() throws Exception {
		_testBatchEngineBundle("batch1", 1);
		_testBatchEngineBundle("batch2", 0);
		_testBatchEngineBundle("batch3", 2);
		_testBatchEngineBundle("batch4", 3);
		_testBatchEngineBundle("batch5", 1);
		_testBatchEngineBundle("batch6", 2);
		_testBatchEngineBundle("batch7", 1);
		_testBatchEngineBundle("batch8", 3);
	}

	private void _testBatchEngineBundle(
			String batchResourcePath, int expectedValidUnits)
		throws Exception {

		AtomicInteger validUnits = new AtomicInteger();

		TestBatchEngineUnitProcessor testBatchEngineUnitProcessor =
			new TestBatchEngineUnitProcessor(
				units -> {
					for (BatchEngineUnit batchEngineUnit : units) {
						if (batchEngineUnit.isValid()) {
							validUnits.incrementAndGet();
						}
					}
				});

		ServiceRegistration<BatchEngineUnitProcessor> registration =
			_bundleContext.registerService(
				BatchEngineUnitProcessor.class, testBatchEngineUnitProcessor,
				HashMapDictionaryBuilder.put(
					Constants.SERVICE_RANKING, 1000
				).build());

		String bundleSymbolicName = RandomTestUtil.randomString();

		File zipFile = _toJarFile(
			StringBundler.concat(
				_PATH_DEPENDENCIES, batchResourcePath, StringPool.SLASH));

		Bundle bundle = _bundleContext.installBundle(
			bundleSymbolicName, new FileInputStream(zipFile));

		try {
			bundle.start();

			CountDownLatch countDownLatch =
				testBatchEngineUnitProcessor.getCountDownLatch();

			boolean countReached = countDownLatch.await(2, TimeUnit.SECONDS);

			Assert.assertTrue(countReached);

			// Make sure the bundle is only processed on first start

			countDownLatch =
				testBatchEngineUnitProcessor.resetAndGetCountDownLatch();

			bundle.stop();

			bundle.start();

			countReached = countDownLatch.await(2, TimeUnit.SECONDS);

			Assert.assertFalse(countReached);

			Assert.assertEquals(expectedValidUnits, validUnits.intValue());
		}
		finally {
			bundle.uninstall();

			registration.unregister();
		}
	}

	private File _toJarFile(String basePath) throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		Enumeration<URL> enumeration = _bundle.findEntries(basePath, "*", true);

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				String path = url.getPath();

				if (path.endsWith(StringPool.SLASH)) {
					continue;
				}

				String zipPath = path.substring(basePath.length());

				if (zipPath.startsWith(StringPool.SLASH)) {
					zipPath = zipPath.substring(1);
				}

				zipWriter.addEntry(zipPath, url.openStream());
			}
		}

		return zipWriter.getFile();
	}

	private static final String _PATH_DEPENDENCIES =
		"com/liferay/batch/engine/internal/test/dependencies/";

	private Bundle _bundle;
	private BundleContext _bundleContext;

	private class TestBatchEngineUnitProcessor
		implements BatchEngineUnitProcessor {

		public TestBatchEngineUnitProcessor(
			Consumer<Iterable<BatchEngineUnit>> consumer) {

			_consumer = consumer;
		}

		public CountDownLatch getCountDownLatch() {
			return _countDownLatch;
		}

		@Override
		public void processBatchEngineUnits(
			Iterable<BatchEngineUnit> batchEngineUnits) {

			_consumer.accept(batchEngineUnits);

			_countDownLatch.countDown();
		}

		public CountDownLatch resetAndGetCountDownLatch() {
			_countDownLatch = new CountDownLatch(1);

			return _countDownLatch;
		}

		private final Consumer<Iterable<BatchEngineUnit>> _consumer;
		private volatile CountDownLatch _countDownLatch = new CountDownLatch(1);

	}

}