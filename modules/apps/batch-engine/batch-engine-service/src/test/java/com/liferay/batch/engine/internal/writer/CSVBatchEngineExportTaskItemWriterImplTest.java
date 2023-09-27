/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.writer;

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineExportTaskItemWriterImplTest
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
		_testWriteRows(
			Arrays.asList("createDate", "description", "id", "name"),
			HashMapBuilder.<String, Serializable>put(
				"containsHeaders", "false"
			).build());
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames3() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "id", "name"));
		_testWriteRows(
			Arrays.asList("createDate", "id", "name"),
			HashMapBuilder.<String, Serializable>put(
				"containsHeaders", "false"
			).build());
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames4() throws Exception {
		_testWriteRows(
			Arrays.asList("id", "name", "description", "createDate"));
	}

	@Test
	public void testWriteRowsWithEmptyFieldNames() throws Exception {
		try {
			_testWriteRows(Collections.emptyList());

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}
	}

	private String _formatValue(Object value, int fieldIndex) {
		if (value == null) {
			if (fieldIndex == 0) {
				return "\"\"";
			}

			return StringPool.BLANK;
		}

		if (value instanceof Date) {
			return dateFormat.format(value);
		}

		return value.toString();
	}

	private String _getExpectedContent(
			List<String> fieldNames, List<Item> items,
			Map<String, Serializable> parameters)
		throws Exception {

		StringBundler sb = new StringBundler();

		if (Boolean.valueOf(
				(String)parameters.getOrDefault(
					"containsHeaders", StringPool.TRUE))) {

			sb.append(StringUtil.merge(fieldNames, StringPool.COMMA));

			sb.append(StringPool.RETURN_NEW_LINE);
		}

		for (Item item : items) {
			for (int i = 0; i < fieldNames.size(); i++) {
				String fieldName = fieldNames.get(i);

				Field field = fieldsMap.get(fieldName);

				if (Objects.equals(field.getType(), Map.class)) {
					Map<?, ?> map = (Map<?, ?>)field.get(item);

					Set<? extends Map.Entry<?, ?>> entries = map.entrySet();

					Iterator<? extends Map.Entry<?, ?>> iterator =
						entries.iterator();

					if (iterator.hasNext()) {
						sb.append(StringPool.QUOTE);
					}

					while (iterator.hasNext()) {
						Map.Entry<?, ?> entry = iterator.next();

						sb.append(CSVUtil.encode(entry.getKey()));

						sb.append(StringPool.COLON);

						if (entry.getValue() != null) {
							sb.append(CSVUtil.encode(entry.getValue()));
						}
						else {
							sb.append(StringPool.BLANK);
						}

						if (iterator.hasNext()) {
							sb.append(StringPool.COMMA_AND_SPACE);
						}
						else {
							sb.append(StringPool.QUOTE);
						}
					}
				}
				else {
					sb.append(_formatValue(field.get(item), i));
				}

				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			sb.append(StringPool.RETURN_NEW_LINE);
		}

		return sb.toString();
	}

	private void _testWriteRows(List<String> fieldNames) throws Exception {
		_testWriteRows(fieldNames, Collections.emptyMap());
	}

	private void _testWriteRows(
			List<String> fieldNames, Map<String, Serializable> parameters)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (CSVBatchEngineExportTaskItemWriterImpl
				csvBatchEngineExportTaskItemWriterImpl =
					new CSVBatchEngineExportTaskItemWriterImpl(
						StringPool.COMMA, fieldsMap, fieldNames,
						unsyncByteArrayOutputStream, parameters)) {

			for (Item[] items : getItemGroups()) {
				csvBatchEngineExportTaskItemWriterImpl.write(
					Arrays.asList(items));
			}
		}

		String content = unsyncByteArrayOutputStream.toString();

		Assert.assertEquals(
			_getExpectedContent(fieldNames, getItems(), parameters), content);
	}

}