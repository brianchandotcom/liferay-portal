/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.var.type;

import com.liferay.poshi.runner.PoshiRunnerTestCase;
import com.liferay.poshi.runner.util.TableUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Yi-Chen Tsai
 */
public class TableTest extends PoshiRunnerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		_rawDataList = TableUtil.getRawDataListFromString(_RAW_DATA_STRING);

		setUpPoshiRunner(_TEST_BASE_DIR_NAME);
	}

	@Test
	public void testEchoTablesPoshiScript() throws Exception {
		runPoshiTest("TableTest#echoTablePoshiScript");
	}

	@Test
	public void testEchoTablesXML() throws Exception {
		runPoshiTest("TableTest#echoTableXML");
	}

	@Test
	public void testHashesTable() throws Exception {
		if (_rawDataList.size() < 2) {
			return;
		}

		List<String> rowKeys = _rawDataList.get(0);

		List<Map<String, String>> expected = new ArrayList<>();

		for (int i = 1; i < _rawDataList.size(); i++) {
			List<String> rowEntries = _rawDataList.get(i);

			LinkedHashMap<String, String> hashesRow = new LinkedHashMap<>();

			for (int j = 0; j < rowEntries.size(); j++) {
				hashesRow.put(rowKeys.get(j), rowEntries.get(j));
			}

			expected.add(hashesRow);
		}

		HashesTable hashesTable = (HashesTable)TableFactory.newTable(
			_rawDataList, "HashesTable");

		List<Map<String, String>> actual = hashesTable.getTable();

		assertEquals(actual, expected);
	}

	@Test
	public void testRawTable() throws Exception {
		RawTable rawTable = (RawTable)TableFactory.newTable(
			_rawDataList, "RawTable");

		Iterable<List<String>> actual = rawTable.getTable();

		List<List<String>> expected = _rawDataList;

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testRowsHashTable() throws Exception {
		RowsHashTable rowsHashTable = (RowsHashTable)TableFactory.newTable(
			_rawDataList, "RowsHashTable");

		List<Map<String, String>> actual = rowsHashTable.getTable();

		List<Map<String, String>> expected = new ArrayList<>();

		Map<String, String> row = new LinkedHashMap<>();

		for (List<String> rawDataRow : _rawDataList) {
			row.put(rawDataRow.get(0), rawDataRow.get(1));
		}

		expected.add(row);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testTransposedTable() throws Exception {
		RawTable transposedTable = (RawTable)TableFactory.newTable(
			TableUtil.getTransposedRawDataList(_rawDataList), "RawTable");

		Iterable<List<String>> actual = transposedTable.getTable();

		List<List<String>> expected = new ArrayList<>();

		for (int i = 0; i < TableUtil.getRawDataListWidth(_rawDataList); i++) {
			List<String> column = new ArrayList<>();

			for (List<String> row : _rawDataList) {
				column.add(row.get(i));
			}

			expected.add(column);
		}

		Assert.assertEquals(expected, actual);
	}

	protected String read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	protected String read(File dir, String fileName) throws IOException {
		return read(new File(dir, fileName));
	}

	private static final String _RAW_DATA_STRING =
		"| 1 | 2 |\n| 3 | 4 |\n| 5 | 6 |\n| 7 | 8 |";

	private static final String _TEST_BASE_DIR_NAME =
		"src/test/resources/com/liferay/poshi/runner/dependencies/var/type/";

	private List<List<String>> _rawDataList;

}