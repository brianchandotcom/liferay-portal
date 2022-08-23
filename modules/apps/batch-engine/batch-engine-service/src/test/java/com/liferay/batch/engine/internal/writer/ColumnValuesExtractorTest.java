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

package com.liferay.batch.engine.internal.writer;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.StringReader;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class ColumnValuesExtractorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExtractValuesWithChildObjects() throws Exception {
		ArrayContainer arrayContainer = new ArrayContainer(
			new Double[] {43.2, 12.8, 33.17, 0.234, 5D},
			new String[] {"A,BC", "D\"EF", "GHI", "J'KL", "``NO,P"});

		ObjectContainer objectContainer = new ObjectContainer(
			arrayContainer, arrayContainer);

		ColumnValuesExtractor columnValuesExtractor = new ColumnValuesExtractor(
			ItemClassIndexUtil.index(objectContainer.getClass()),
			Arrays.asList("arrayContainer1", "arrayContainer2", "length"));

		_assertHeaders(
			new String[] {
				"arrayContainer1.doubles", "arrayContainer1.length",
				"arrayContainer1.strings", "arrayContainer2.doubles",
				"arrayContainer2.length", "arrayContainer2.strings", "length"
			},
			columnValuesExtractor.getHeaders());

		List<Object[]> rows = columnValuesExtractor.extractValues(
			objectContainer);

		List<Object[]> testResults = columnValuesExtractor.extractValues(
			objectContainer);

		Assert.assertFalse(rows.isEmpty());

		Object[] row = testResults.get(0);

		Assert.assertEquals(Arrays.toString(row), 7, row.length);

		Assert.assertEquals(Integer.valueOf(2), row[6]);

		row = testResults.get(1);

		CSVRecord csvRecord = _parseCSV((String)row[0]);

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arrayContainer.length; i++) {
			Assert.assertEquals(
				arrayContainer.doubles[i], Double.valueOf(csvRecord.get(i)));
		}

		csvRecord = _parseCSV((String)row[2]);

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arrayContainer.length; i++) {
			Assert.assertEquals(arrayContainer.strings[i], csvRecord.get(i));
		}
	}

	@Test
	public void testExtractValuesWithDoubleArray() throws Exception {
		ArrayContainer arrayContainer = new ArrayContainer(
			new Double[] {43.2, 12.8, 33.17, 0.234, 5D},
			new String[] {"A,BC", "D\"EF", "GHI", "J'KL", "``NO,P"});

		ColumnValuesExtractor columnValuesExtractor = new ColumnValuesExtractor(
			ItemClassIndexUtil.index(arrayContainer.getClass()),
			Arrays.asList("doubles", "length", "strings"));

		_assertHeaders(
			new String[] {"doubles", "length", "strings"},
			columnValuesExtractor.getHeaders());

		List<Object[]> rows = columnValuesExtractor.extractValues(
			arrayContainer);

		Assert.assertFalse(rows.isEmpty());

		Object[] objects = rows.get(0);

		Assert.assertEquals(objects.toString(), 3, objects.length);

		CSVRecord csvRecord = _parseCSV((String)objects[0]);

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arrayContainer.length; i++) {
			Assert.assertEquals(
				arrayContainer.doubles[i], Double.valueOf(csvRecord.get(i)));
		}

		Assert.assertEquals(Integer.valueOf(5), objects[1]);

		csvRecord = _parseCSV((String)objects[2]);

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arrayContainer.length; i++) {
			Assert.assertEquals(arrayContainer.strings[i], csvRecord.get(i));
		}
	}

	private void _assertHeaders(String[] expected, String[] actual) {
		Assert.assertEquals(
			Arrays.toString(actual), expected.length, actual.length);

		for (int i = 0; i < expected.length; i++) {
			Assert.assertEquals(expected[i], actual[i]);
		}
	}

	private CSVRecord _parseCSV(String stringToParse) throws Exception {
		CSVParser csvParser = new CSVParser(
			new StringReader(stringToParse), CSVFormat.DEFAULT);

		List<CSVRecord> records = csvParser.getRecords();

		if (records.isEmpty()) {
			throw new IllegalArgumentException(
				"Unable to parse value " + stringToParse);
		}

		return records.get(0);
	}

	private class ArrayContainer {

		public Double[] doubles;
		public int length;
		public String[] strings;

		private ArrayContainer(Double[] doubles, String[] strings) {
			this.doubles = doubles;
			length = strings.length;
			this.strings = strings;
		}

	}

	private class ObjectContainer {

		public ArrayContainer arrayContainer1;
		public ArrayContainer arrayContainer2;
		public int length;

		private ObjectContainer(
			ArrayContainer arrayContainer1, ArrayContainer arrayContainer2) {

			this.arrayContainer1 = arrayContainer1;
			this.arrayContainer2 = arrayContainer2;

			length = 2;
		}

	}

}