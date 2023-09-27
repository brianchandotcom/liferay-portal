/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void testExtractValuesWithDoubleArray() throws Exception {
		ArraysAggregator arraysAggregator = new ArraysAggregator(
			new Double[] {43.2, 12.8, 33.17, 0.234, 5D},
			new String[] {"A,BC", "D\"EF", "GHI", "J'KL", "``NO,P"});

		ColumnValuesExtractor columnValuesExtractor = new ColumnValuesExtractor(
			ItemClassIndexUtil.index(arraysAggregator.getClass()),
			Arrays.asList("doubles", "length", "strings"));

		_assertHeaders(
			new String[] {"doubles", "length", "strings"},
			columnValuesExtractor.getHeaders());

		List<Object[]> valuesList = columnValuesExtractor.extractValues(
			arraysAggregator);

		Assert.assertFalse(valuesList.isEmpty());

		Object[] values = valuesList.get(0);

		Assert.assertEquals(values.toString(), 3, values.length);

		CSVRecord csvRecord = _toCSVRecord((String)values[0]);

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arraysAggregator.length; i++) {
			Assert.assertEquals(
				arraysAggregator.doubles[i], Double.valueOf(csvRecord.get(i)));
		}

		Assert.assertEquals(Integer.valueOf(5), values[1]);

		csvRecord = _toCSVRecord((String)values[2]);

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arraysAggregator.length; i++) {
			Assert.assertEquals(arraysAggregator.strings[i], csvRecord.get(i));
		}
	}

	@Test
	public void testExtractValuesWithNestedObjects() throws Exception {
		ArraysAggregator arraysAggregator = new ArraysAggregator(
			new Double[] {43.2, 12.8, 33.17, 0.234, 5D},
			new String[] {"A,BC", "D\"EF", "GHI", "J'KL", "``NO,P"});

		NestedObjectsAggregator nestedObjectsAggregator =
			new NestedObjectsAggregator(arraysAggregator, arraysAggregator);

		ColumnValuesExtractor columnValuesExtractor = new ColumnValuesExtractor(
			ItemClassIndexUtil.index(nestedObjectsAggregator.getClass()),
			Arrays.asList("arraysAggregator1", "arraysAggregator2", "length"));

		_assertHeaders(
			new String[] {
				"arraysAggregator1.doubles", "arraysAggregator1.length",
				"arraysAggregator1.strings", "arraysAggregator2.doubles",
				"arraysAggregator2.length", "arraysAggregator2.strings",
				"length"
			},
			columnValuesExtractor.getHeaders());

		List<Object[]> valuesList = columnValuesExtractor.extractValues(
			nestedObjectsAggregator);

		Assert.assertFalse(valuesList.isEmpty());

		Object[] values = valuesList.get(0);

		Assert.assertEquals(Arrays.toString(values), 7, values.length);
		Assert.assertEquals(Integer.valueOf(2), values[6]);

		values = valuesList.get(1);

		CSVRecord csvRecord = _toCSVRecord((String)values[0]);

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arraysAggregator.length; i++) {
			Assert.assertEquals(
				arraysAggregator.doubles[i], Double.valueOf(csvRecord.get(i)));
		}

		csvRecord = _toCSVRecord((String)values[2]);

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arraysAggregator.length; i++) {
			Assert.assertEquals(arraysAggregator.strings[i], csvRecord.get(i));
		}
	}

	private void _assertHeaders(String[] expected, String[] actual) {
		Assert.assertEquals(
			Arrays.toString(actual), expected.length, actual.length);

		for (int i = 0; i < expected.length; i++) {
			Assert.assertEquals(expected[i], actual[i]);
		}
	}

	private CSVRecord _toCSVRecord(String value) throws Exception {
		CSVParser csvParser = new CSVParser(
			new StringReader(value), CSVFormat.DEFAULT);

		List<CSVRecord> records = csvParser.getRecords();

		if (records.isEmpty()) {
			throw new IllegalArgumentException(
				"Unable to parse value " + value);
		}

		return records.get(0);
	}

	private class ArraysAggregator {

		public Double[] doubles;
		public int length;
		public String[] strings;

		private ArraysAggregator(Double[] doubles, String[] strings) {
			this.doubles = doubles;
			this.strings = strings;

			length = strings.length;
		}

	}

	private class NestedObjectsAggregator {

		public ArraysAggregator arraysAggregator1;
		public ArraysAggregator arraysAggregator2;
		public int length;

		private NestedObjectsAggregator(
			ArraysAggregator arraysAggregator1,
			ArraysAggregator arraysAggregator2) {

			this.arraysAggregator1 = arraysAggregator1;
			this.arraysAggregator2 = arraysAggregator2;

			length = 2;
		}

	}

}