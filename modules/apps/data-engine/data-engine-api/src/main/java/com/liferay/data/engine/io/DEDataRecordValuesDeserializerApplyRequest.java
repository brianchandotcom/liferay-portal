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

import com.liferay.data.engine.model.DEDataDefinition;

/**
 * Data Record field values deserializer request
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordValuesDeserializerApplyRequest {

	/**
	 * The serialized Data Record field values.
	 * @return The serialized content
	 * @review
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * The {@link DEDataDefinition} used to deserialize the field values
	 * @return DEDataDefinition
	 * @review
	 */
	public DEDataDefinition getDEDataDefinition() {
		return _deDataDefinition;
	}

	/**
	 * Inner Builder
	 * @review
	 */
	public static final class Builder {

		/**
		 * Creates a Builder with the serialized content and a {@link DEDataDefinition}.
		 * @param content The serialized Data Record field values
		 * @param deDataDefinition The {@link DEDataDefinition} used to deserialize the field values
		 * @return the builder
		 * @review
		 */
		public Builder(String content, DEDataDefinition deDataDefinition) {
			_deDataRecordDeserializerApplyRequest._content = content;
			_deDataRecordDeserializerApplyRequest._deDataDefinition =
				deDataDefinition;
		}

		/**
		 * Creates the builder.
		 * @return the builder object.
		 * @review
		 */
		public DEDataRecordValuesDeserializerApplyRequest build() {
			return _deDataRecordDeserializerApplyRequest;
		}

		private final DEDataRecordValuesDeserializerApplyRequest
			_deDataRecordDeserializerApplyRequest =
				new DEDataRecordValuesDeserializerApplyRequest();

	}

	private DEDataRecordValuesDeserializerApplyRequest() {
	}

	private String _content;
	private DEDataDefinition _deDataDefinition;

}