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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.CSVUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	property = "ddm.form.instance.record.writer.type=csv",
	service = DDMFormInstanceRecordWriter.class
)
public class DDMFormInstanceRecordCSVWriter
	implements DDMFormInstanceRecordWriter {

	@Override
	public DDMFormInstanceRecordWriterResponse write(
			DDMFormInstanceRecordWriterRequest
				ddmFormInstanceRecordWriterRequest)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		Map<String, String> ddmFormFieldsLabel =
			ddmFormInstanceRecordWriterRequest.getDDMFormFieldsLabel();

		sb.append(writeValues(ddmFormFieldsLabel.values()));

		sb.append(StringPool.NEW_LINE);

		sb.append(
			writeRecords(
				ddmFormInstanceRecordWriterRequest.getDDMFormFieldValues()));

		String csv = sb.toString();

		DDMFormInstanceRecordWriterResponse.Builder builder =
			DDMFormInstanceRecordWriterResponse.Builder.newBuilder(
				csv.getBytes());

		return builder.build();
	}

	protected String writeRecords(
		List<Map<String, String>> ddmFormFieldValues) {

		Stream<Map<String, String>> stream = ddmFormFieldValues.stream();

		return stream.map(
			Map::values
		).map(
			this::writeValues
		).collect(
			Collectors.joining(StringPool.NEW_LINE)
		);
	}

	protected String writeValues(Collection<String> values) {
		Stream<String> stream = values.stream();

		return stream.map(
			CSVUtil::encode
		).collect(
			Collectors.joining(StringPool.COMMA)
		);
	}

}