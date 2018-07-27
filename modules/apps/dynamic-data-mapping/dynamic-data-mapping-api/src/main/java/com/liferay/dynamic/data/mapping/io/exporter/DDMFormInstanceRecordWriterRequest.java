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

		private final DDMFormInstanceRecordWriterRequest
			_ddmFormInstanceRecordWriterRequest =
				new DDMFormInstanceRecordWriterRequest();

	}

	private DDMFormInstanceRecordWriterRequest() {
	}

	private Map<String, String> _ddmFormFieldsLabel;
	private List<Map<String, String>> _ddmFormFieldValues;

}