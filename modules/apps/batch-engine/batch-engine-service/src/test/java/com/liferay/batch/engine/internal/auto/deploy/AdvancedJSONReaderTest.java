/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.auto.deploy;

import com.liferay.batch.engine.internal.json.AdvancedJSONReader;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class AdvancedJSONReaderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	@Test
	public void testGetObject() throws Exception {
		try (InputStream inputStream = new FileInputStream(
				_getFile("advanced_sample.json"))) {

			AdvancedJSONReader<BatchEngineUnitConfiguration>
				advancedJSONReader = new AdvancedJSONReader<>(inputStream);

			BatchEngineUnitConfiguration batchEngineUnitConfiguration =
				advancedJSONReader.getObject(
					"configuration", BatchEngineUnitConfiguration.class);

			Assert.assertEquals(
				2410, batchEngineUnitConfiguration.getCompanyId());
			Assert.assertEquals(
				245647, batchEngineUnitConfiguration.getUserId());
			Assert.assertEquals(
				"v10.0", batchEngineUnitConfiguration.getVersion());
			Assert.assertTrue(
				batchEngineUnitConfiguration.isCheckPermissions());
		}
	}

	@Test
	public void testWriteData() throws Exception {
		try (InputStream inputStream = new FileInputStream(
				_getFile("advanced_sample.json"))) {

			AdvancedJSONReader<BatchEngineUnitConfiguration>
				advancedJSONReader = new AdvancedJSONReader<>(inputStream);

			try (ByteArrayOutputStream byteArrayOutputStream =
					new ByteArrayOutputStream()) {

				advancedJSONReader.transferJSONArray(
					"items", byteArrayOutputStream);

				String content = byteArrayOutputStream.toString();

				Assert.assertTrue(content.contains("Array Element 4"));
				Assert.assertTrue(content.contains("innerArray1"));
				Assert.assertTrue(content.endsWith(StringPool.CLOSE_BRACKET));
				Assert.assertTrue(content.startsWith(StringPool.OPEN_BRACKET));
			}
		}
	}

	private File _getFile(String fileName) throws Exception {
		URL url = AdvancedJSONReaderTest.class.getResource(fileName);

		Assert.assertEquals("file", url.getProtocol());

		Path path = Paths.get(url.toURI());

		return path.toFile();
	}

}