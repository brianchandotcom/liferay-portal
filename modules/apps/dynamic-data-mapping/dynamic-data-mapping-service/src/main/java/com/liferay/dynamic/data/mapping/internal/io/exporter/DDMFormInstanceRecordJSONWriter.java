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
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	property = "ddm.form.instance.record.writer.type=json",
	service = DDMFormInstanceRecordWriter.class
)
public class DDMFormInstanceRecordJSONWriter
	implements DDMFormInstanceRecordWriter {

	@Override
	public DDMFormInstanceRecordWriterResponse write(
			DDMFormInstanceRecordWriterRequest
				ddmFormInstanceRecordWriterRequest)
		throws Exception {

		String json = String.valueOf(
			JSONUtil.toJSONArray(
				ddmFormInstanceRecordWriterRequest.getDDMFormFieldValues(),
				this::_createJSONObject));

		DDMFormInstanceRecordWriterResponse.Builder builder =
			DDMFormInstanceRecordWriterResponse.Builder.newBuilder(
				json.getBytes());

		return builder.build();
	}

	@Reference
	protected JSONFactory jsonFactory;

	private JSONObject _createJSONObject(
		Map<String, String> ddmFormFieldsValue) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		for (Map.Entry<String, String> entry : ddmFormFieldsValue.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

}