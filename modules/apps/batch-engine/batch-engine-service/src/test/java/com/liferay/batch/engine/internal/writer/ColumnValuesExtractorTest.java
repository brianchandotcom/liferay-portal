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
	public void testExtractValuesWithDoubleArray() throws Exception {
		ArrayContainer<Double> arrayContainer = new ArrayContainer(
			new Double[] {43.2, 12.8, 33.17, 0.234, 5D},
			new String[] {"A,BC", "D\"EF", "GHI", "J'KL", "``NO,P"});

		ColumnValuesExtractor columnValuesExtractor = new ColumnValuesExtractor(
			ItemClassIndexUtil.index(arrayContainer.getClass()),
			Arrays.asList("doubles", "length", "strings"));

		List<Object> objects = columnValuesExtractor.extractValues(
			arrayContainer);

		Assert.assertEquals(Integer.valueOf(5), objects.get(1));

		CSVRecord csvRecord = _parseCSV((String)objects.get(0));

		Assert.assertEquals(1, csvRecord.size());

		csvRecord = _parseCSV(csvRecord.get(0));

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arrayContainer.length; i++) {
			Assert.assertEquals(
				arrayContainer.doubles[i], Double.valueOf(csvRecord.get(i)));
		}

		csvRecord = _parseCSV((String)objects.get(2));

		Assert.assertEquals(1, csvRecord.size());

		csvRecord = _parseCSV(csvRecord.get(0));

		Assert.assertEquals(5, csvRecord.size());

		for (int i = 0; i < arrayContainer.length; i++) {
			Assert.assertEquals(arrayContainer.strings[i], csvRecord.get(i));
		}
	}

	private CSVRecord _parseCSV(String stringToParse) throws Exception {
		CSVParser csvRecords = new CSVParser(
			new StringReader(stringToParse), CSVFormat.DEFAULT);

		List<CSVRecord> records = csvRecords.getRecords();

		if (records.isEmpty()) {
			throw new IllegalArgumentException(
				"Unable to parse value " + stringToParse);
		}

		return records.get(0);
	}

	private class ArrayContainer<T> {

		public Double[] doubles;
		public int length;
		public String[] strings;

		private ArrayContainer(Double[] doubles, String[] strings) {
			this.doubles = doubles;
			length = strings.length;
			this.strings = strings;
		}

	}

}