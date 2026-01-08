/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.zip.internal.reader.factory;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.DependenciesTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.zip.internal.reader.BaseReaderImplTestCase;
import com.liferay.portal.zip.internal.reader.NioZipReaderImpl;
import com.liferay.portal.zip.internal.reader.ZipReaderImpl;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class ZipReaderFactoryImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void test() throws Exception {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

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

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "ZIP_FILE_READER_NIO_ENABLED", true);

		try (ZipReader zipReader = zipReaderFactory.getZipReader(
				DependenciesTestUtil.getDependencyAsFile(
					BaseReaderImplTestCase.class, "file.zip"))) {

			Assert.assertTrue(zipReader instanceof NioZipReaderImpl);
		}

		try (ZipReader zipReader = zipReaderFactory.getZipReader(
				DependenciesTestUtil.getDependencyAsInputStream(
					BaseReaderImplTestCase.class, "file.zip"))) {

			Assert.assertTrue(zipReader instanceof NioZipReaderImpl);
		}
	}

}