/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tika.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.portal.kernel.metadata.RawMetadataProcessor;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.FileInputStream;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class TikaRawMetadataProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_file = new File("test-x-matlab.js");

		Files.write(
			Paths.get(_file.getPath()),
			"function TikaRawMetadataProcessorTest() {}".getBytes());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_file.delete();
	}

	@Test
	public void testGetRawMetadataMapWithXMatlab() throws Exception {
		Map<String, DDMFormValues> rawMetadataMap =
			_rawMetadataProcessor.getRawMetadataMap(
				ContentTypes.APPLICATION_JAVASCRIPT,
				new FileInputStream(_file));

		DDMFormValues ddmFormValues = rawMetadataMap.get(
			RawMetadataProcessor.TIKA_RAW_METADATA);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> httpHeadersContentTypeValues =
			ddmFormFieldValuesMap.get("HttpHeaders_CONTENT_TYPE");

		DDMFormFieldValue httpHeadersContentTypeValue =
			httpHeadersContentTypeValues.get(0);

		Value value = httpHeadersContentTypeValue.getValue();

		String valueString = value.getString(value.getDefaultLocale());

		Assert.assertTrue(valueString.startsWith("application/javascript;"));
	}

	private static File _file;

	@Inject
	private RawMetadataProcessor _rawMetadataProcessor;

}