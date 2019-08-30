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

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordJSONWriterTest {

	@Test
	public void testWrite() throws Exception {
		DDMFormInstanceRecordJSONWriter ddmFormInstanceRecordJSONWriter =
			new DDMFormInstanceRecordJSONWriter();

		ddmFormInstanceRecordJSONWriter.jsonFactory = new JSONFactoryImpl();

		List<Map<String, String>> ddmFormFieldValues =
			new ArrayList<Map<String, String>>() {
				{
					Map<String, String> map1 = new HashMap<String, String>() {
						{
							put("field1", "2");
							put("field2", "false");
							put("field3", "11.7");
						}
					};

					add(map1);

					Map<String, String> map2 = new HashMap<String, String>() {
						{
							put("field1", "1");
							put("field2", "");
							put("field3", "10");
						}
					};

					add(map2);
				}
			};

		DDMFormInstanceRecordWriterRequest.Builder builder =
			DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
				Collections.emptyMap(), ddmFormFieldValues);

		DDMFormInstanceRecordWriterResponse
			ddmFormInstanceRecordWriterResponse =
				ddmFormInstanceRecordJSONWriter.write(builder.build());

		StringBundler sb = new StringBundler(3);

		sb.append("[{\"field1\":\"2\",\"field3\":\"11.7\",\"field2\":");
		sb.append("\"false\"},{\"field1\":\"1\",\"field3\":\"10\",\"field2\":");
		sb.append("\"\"}]");

		String expectedJSON = sb.toString();

		Assert.assertArrayEquals(
			expectedJSON.getBytes(),
			ddmFormInstanceRecordWriterResponse.getContent());
	}

}