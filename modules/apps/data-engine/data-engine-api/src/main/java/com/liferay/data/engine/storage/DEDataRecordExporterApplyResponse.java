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

package com.liferay.data.engine.storage;

/**
 * Response class used as a return value for the export operation
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordExporterApplyResponse {

	/**
	 * Returns the exported content
	 * @review
	 * @return The exported content
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * Inner builder that assembles the response.
	 * @review
	 */
	public static final class Builder {

		/**
		 * Returns the builder
		 * @param content The Data Records exported
		 * @return the builder
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
		public static DEDataRecordExporterApplyResponse of(String content) {
			return newBuilder(
				content
			).build();
		}

		/**
		 * Builds the response and returns the {@link DEDataRecordExporterApplyResponse}
		 * object.
		 *
		 * @return The response object
		 * @review
		 */
		public DEDataRecordExporterApplyResponse build() {
			return _deDataRecordExporterApplyResponse;
		}

		private Builder(String content) {
			_deDataRecordExporterApplyResponse._content = content;
		}

		private final DEDataRecordExporterApplyResponse
			_deDataRecordExporterApplyResponse =
				new DEDataRecordExporterApplyResponse();

	}

	private DEDataRecordExporterApplyResponse() {
	}

	private String _content;

}