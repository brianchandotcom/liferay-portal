/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.zip.internal.reader.factory;

import com.liferay.portal.kernel.test.util.DependenciesTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.zip.internal.reader.BaseReaderImplTestCase;
import com.liferay.portal.zip.internal.reader.NioZipReaderImpl;
import com.liferay.portal.zip.internal.reader.ZipReaderImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class ZipReaderFactoryImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	@Test
	public void testNioZipReader() throws Exception {
		_testNioZipReader(NioZipReaderImpl.class, true);
		_testNioZipReader(ZipReaderImpl.class, false);
	}

	@Test
	public void testZipReader() throws Exception {
		ZipReaderFactory zipReaderFactory = new ZipReaderFactoryImpl();

		try (ZipReader zipReader = zipReaderFactory.getZipReader(
				DependenciesTestUtil.getDependencyAsFile(
					BaseReaderImplTestCase.class, "file.zip"))) {

			Assert.assertTrue(zipReader instanceof ZipReaderImpl);
		}

		try (ZipReader zipReader = zipReaderFactory.getZipReader(
				DependenciesTestUtil.getDependencyAsInputStream(
					BaseReaderImplTestCase.class, "file.zip"))) {

			Assert.assertTrue(zipReader instanceof ZipReaderImpl);
		}
	}

	private void _testNioZipReader(
			Class<? extends ZipReader> expectedClass,
			boolean featureFlagEnabled)
		throws Exception {

		String featureFlagKey = "feature.flag.LPD-75525";

		String originalValue = PropsUtil.get(featureFlagKey);

		try {
			PropsUtil.set(featureFlagKey, Boolean.toString(featureFlagEnabled));

			ZipReaderFactory zipReaderFactory = new ZipReaderFactoryImpl();

			try (ZipReader zipReader = zipReaderFactory.getZipReader(
					DependenciesTestUtil.getDependencyAsFile(
						BaseReaderImplTestCase.class, "file.zip"))) {

				Assert.assertTrue(expectedClass.isInstance(zipReader));
			}

			try (ZipReader zipReader = zipReaderFactory.getZipReader(
					DependenciesTestUtil.getDependencyAsInputStream(
						BaseReaderImplTestCase.class, "file.zip"))) {

				Assert.assertTrue(expectedClass.isInstance(zipReader));
			}
		}
		finally {
			PropsUtil.set(featureFlagKey, originalValue);
		}
	}

}