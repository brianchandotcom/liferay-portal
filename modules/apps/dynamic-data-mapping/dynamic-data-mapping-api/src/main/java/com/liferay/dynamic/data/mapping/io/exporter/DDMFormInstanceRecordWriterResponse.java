/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.io.exporter;

/**
 * @author Leonardo Barros
 */
public final class DDMFormInstanceRecordWriterResponse {

	public byte[] getContent() {
		return _content;
	}

	public static class Builder {

		public static Builder newBuilder(byte[] content) {
			return new Builder(content);
		}

		public DDMFormInstanceRecordWriterResponse build() {
			return _ddmFormInstanceRecordWriterResponse;
		}

		private Builder(byte[] content) {
			_ddmFormInstanceRecordWriterResponse._content = content;
		}

		private final DDMFormInstanceRecordWriterResponse
			_ddmFormInstanceRecordWriterResponse =
				new DDMFormInstanceRecordWriterResponse();

	}

	private DDMFormInstanceRecordWriterResponse() {
	}

	private byte[] _content;

}