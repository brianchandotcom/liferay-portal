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
 * Response class used as a return value for the export data records operation
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordCollectionExportRecordsResponse {

	/**
	 * Returns the exported content.
	 * @review
	 * @return exported content
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * Inner builder that assembles the response.
	 * @review
	 */
	public static class Builder {

		/**
		 * Instantiate the builder providing the content
		 *
		 * @param content
		 * @return The builder
		 * @review
		 */
		public static Builder newBuilder(String content) {
			return new Builder(content);
		}

		/**
		 * Builds a response directly from a content.
		 *
		 * @param content the exported content
		 * @return The response object
		 * @review
		 */
		public static DEDataRecordCollectionExportRecordsResponse of(
			String content) {

			return newBuilder(
				content
			).build();
		}

		/**
		 * Builds the response and returns the {@link DEDataRecordCollectionExportRecordsResponse}
		 * object.
		 *
		 * @return The response object
		 * @review
		 */
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