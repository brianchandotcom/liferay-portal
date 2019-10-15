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

package com.liferay.dynamic.data.mapping.io.exporter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DDMFormInstanceRecordWriterRequest {

	public Map<String, String> getDDMFormFieldsLabel() {
		return _ddmFormFieldsLabel;
	}

	public List<Map<String, String>> getDDMFormFieldValues() {
		return _ddmFormFieldValues;
	}

	public static class Builder {

		public static Builder newBuilder(
			Map<String, String> ddmFormFieldsLabel,
			List<Map<String, String>> ddmFormFieldValues) {

			return new Builder(ddmFormFieldsLabel, ddmFormFieldValues);
		}

		public DDMFormInstanceRecordWriterRequest build() {
			_formatLabels(
				_ddmFormInstanceRecordWriterRequest._ddmFormFieldsLabel);

			return _ddmFormInstanceRecordWriterRequest;
		}

		private Builder(
			Map<String, String> ddmFormFieldsLabel,
			List<Map<String, String>> ddmFormFieldValues) {

			_ddmFormInstanceRecordWriterRequest._ddmFormFieldsLabel =
				ddmFormFieldsLabel;

			_ddmFormInstanceRecordWriterRequest._ddmFormFieldValues =
				ddmFormFieldValues;
		}

		private Map<String, String> _formatLabels(
			Map<String, String> ddmFormFieldsLabel) {

			Map<String, String> currentDDMFormFieldsLabel = new HashMap<>();

			ddmFormFieldsLabel.forEach(
				(fieldName, label) -> {
					if (!currentDDMFormFieldsLabel.containsKey(label)) {
						ddmFormFieldsLabel.put(fieldName, label);
					}
					else {
						String previousFieldName =
							currentDDMFormFieldsLabel.get(label);

						ddmFormFieldsLabel.put(
							previousFieldName,
							_formatLabelString(previousFieldName, label));

						ddmFormFieldsLabel.put(
							fieldName, _formatLabelString(fieldName, label));
					}

					currentDDMFormFieldsLabel.put(label, fieldName);
				});

			return ddmFormFieldsLabel;
		}

		private String _formatLabelString(String fieldName, String label) {
			StringBundler sb = new StringBundler(5);

			sb.append(label);
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(fieldName);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		private final DDMFormInstanceRecordWriterRequest
			_ddmFormInstanceRecordWriterRequest =
				new DDMFormInstanceRecordWriterRequest();

	}

	private DDMFormInstanceRecordWriterRequest() {
	}

	private Map<String, String> _ddmFormFieldsLabel;
	private List<Map<String, String>> _ddmFormFieldValues;

}