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

package com.liferay.data.engine.io;

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionFieldsSerializerApplyResponse {

	public String getContent() {
		return _content;
	}

	public static final class Builder {

		public static Builder newBuilder(String content) {
			return new Builder(content);
		}

		public static DataDefinitionFieldsSerializerApplyResponse of(
			String content) {

			return newBuilder(
				content
			).build();
		}

		public DataDefinitionFieldsSerializerApplyResponse build() {
			return _dataDefinitionFieldsSerializerApplyResponse;
		}

		private Builder(String content) {
			_dataDefinitionFieldsSerializerApplyResponse._content = content;
		}

		private final DataDefinitionFieldsSerializerApplyResponse
			_dataDefinitionFieldsSerializerApplyResponse =
				new DataDefinitionFieldsSerializerApplyResponse();

	}

	private DataDefinitionFieldsSerializerApplyResponse() {
	}

	private String _content;

}