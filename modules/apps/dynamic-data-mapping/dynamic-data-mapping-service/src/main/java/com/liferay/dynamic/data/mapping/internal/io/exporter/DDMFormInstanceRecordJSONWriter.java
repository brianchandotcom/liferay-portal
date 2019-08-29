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

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.instance.record.writer.type=json",
	service = DDMFormInstanceRecordWriter.class
)
public class DDMFormInstanceRecordJSONWriter
	implements DDMFormInstanceRecordWriter {

	@Override
	public DDMFormInstanceRecordWriterResponse write(
			DDMFormInstanceRecordWriterRequest
				ddmFormInstanceRecordWriterRequest)
		throws Exception {

		List<Map<String, String>> ddmFormFieldsValueList =
			ddmFormInstanceRecordWriterRequest.getDDMFormFieldValues();

		JSONArray jsonArray = jsonFactory.createJSONArray();

		Stream<Map<String, String>> stream = ddmFormFieldsValueList.stream();

		stream.map(
			this::createJSONObject
		).forEach(
			jsonArray::put
		);

		String json = jsonArray.toJSONString();

		DDMFormInstanceRecordWriterResponse.Builder builder =
			DDMFormInstanceRecordWriterResponse.Builder.newBuilder(
				json.getBytes());

		return builder.build();
	}

	protected JSONObject createJSONObject(
		Map<String, String> ddmFormFieldsValue) {

		Set<Map.Entry<String, String>> entrySet = ddmFormFieldsValue.entrySet();

		Stream<Map.Entry<String, String>> stream = entrySet.stream();

		JSONObject jsonObject = jsonFactory.createJSONObject();

		stream.forEach(
			entry -> jsonObject.put(entry.getKey(), entry.getValue()));

		return jsonObject;
	}

	@Reference
	protected JSONFactory jsonFactory;

}