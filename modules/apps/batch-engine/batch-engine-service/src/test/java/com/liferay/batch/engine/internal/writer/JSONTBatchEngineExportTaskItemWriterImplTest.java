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

import com.liferay.batch.engine.internal.auto.deploy.BatchEngineAutoDeployListener;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Igor Beslic
 */
public class JSONTBatchEngineExportTaskItemWriterImplTest
	extends BaseBatchEngineExportTaskItemWriterImplTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testWriteRowsWithDefinedFieldNames1() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "description", "id"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames2() throws Exception {
		_testWriteRows(
			Arrays.asList("createDate", "description", "id", "name"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames3() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "id", "name"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames4() throws Exception {
		_testWriteRows(
			Arrays.asList("id", "name", "description", "createDate"));
	}

	@Test
	public void testWriteRowsWithEmptyFieldNames() throws Exception {
		_testWriteRows(Collections.emptyList());
	}

	private String _getExpectedContent(
		BatchEngineAutoDeployListener.BatchEngineImportConfiguration
			batchEngineImportConfiguration,
		List<String> fieldNames, List<Item> items) {

		StringBundler sb = new StringBundler();

		sb.append("{\"configuration\":{\"className\":\"");
		sb.append(batchEngineImportConfiguration.getClassName());
		sb.append("\",\n\"userId\":");
		sb.append(batchEngineImportConfiguration.getUserId());
		sb.append(",\n\"companyId\":");
		sb.append(batchEngineImportConfiguration.getCompanyId());
		sb.append(",\n\"version\":\"");
		sb.append(batchEngineImportConfiguration.getVersion());
		sb.append("\"\n},");

		if (fieldNames.isEmpty()) {
			fieldNames = jsonFieldNames;
		}

		sb.append("\"items\":[\n");

		Iterator<Item> iterator = items.iterator();

		while (iterator.hasNext()) {
			Item item = iterator.next();

			sb.append(getItemJSONContent(fieldNames, item));

			if (iterator.hasNext()) {
				sb.append(",\n");
			}
		}

		sb.append("\n]\n}");

		return sb.toString();
	}

	private void _testWriteRows(List<String> fieldNames) throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		BatchEngineAutoDeployListener.BatchEngineImportConfiguration
			batchEngineImportConfiguration =
				new BatchEngineAutoDeployListener.
					BatchEngineImportConfiguration();

		Class<Item> itemClass = Item.class;

		batchEngineImportConfiguration.setClassName(itemClass.getName());

		batchEngineImportConfiguration.setVersion("v1.0");

		try (JSONTBatchEngineExportTaskItemWriterImpl
				jsontBatchEngineExportTaskItemWriterImpl =
					new JSONTBatchEngineExportTaskItemWriterImpl(
						fieldsMap.keySet(), batchEngineImportConfiguration,
						fieldNames, unsyncByteArrayOutputStream)) {

			for (Item[] items : getItemGroups()) {
				jsontBatchEngineExportTaskItemWriterImpl.write(
					Arrays.asList(items));
			}
		}

		String content = unsyncByteArrayOutputStream.toString();

		System.out.println("Content: \n" + content);

		JSONAssert.assertEquals(
			_getExpectedContent(
				batchEngineImportConfiguration, fieldNames, getItems()),
			content, true);
	}

}