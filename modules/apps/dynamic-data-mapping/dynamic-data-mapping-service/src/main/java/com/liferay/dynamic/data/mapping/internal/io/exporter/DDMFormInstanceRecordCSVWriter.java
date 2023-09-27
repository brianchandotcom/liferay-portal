/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		StringBundler sb = new StringBundler(ddmFormFieldValues.size() * 2);

		for (Map<String, String> ddmFormFieldValue : ddmFormFieldValues) {
			sb.append(writeValues(ddmFormFieldValue.values()));
			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected String writeValues(Collection<String> values) {
		StringBundler sb = new StringBundler(values.size() * 2);

		for (String value : values) {
			sb.append(CSVUtil.encode(value));
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

}