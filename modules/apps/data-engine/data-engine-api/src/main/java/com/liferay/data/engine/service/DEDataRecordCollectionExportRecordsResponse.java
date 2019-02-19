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

package com.liferay.data.engine.service;

/**
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionExportRecordsResponse {

	public String getContent() {
		return _content;
	}

	public static class Builder {

		public static Builder newBuilder(String content) {
			return new Builder(content);
		}

		public static DEDataRecordCollectionExportRecordsResponse of(
			String content) {

			return newBuilder(
				content
			).build();
		}

		public DEDataRecordCollectionExportRecordsResponse build() {
			return _deDataRecordCollectionExportRecordsResponse;
		}

		private Builder(String content) {
			_deDataRecordCollectionExportRecordsResponse._content = content;
		}

		private DEDataRecordCollectionExportRecordsResponse
			_deDataRecordCollectionExportRecordsResponse =
				new DEDataRecordCollectionExportRecordsResponse();

	}

	private DEDataRecordCollectionExportRecordsResponse() {
	}

	private String _content;

}