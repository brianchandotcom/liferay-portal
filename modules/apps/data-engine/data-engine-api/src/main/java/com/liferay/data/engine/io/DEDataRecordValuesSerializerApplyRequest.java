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

import java.util.Collections;
import java.util.Map;

/**
 * Data Record field values serialize request class.
 *
 * @author Leonardo Barros
 *
 * @review
 */
public final class DEDataRecordValuesSerializerApplyRequest {

	/**
	 * Returns the {@link DEDataDefinition} used to serialize the Data Record field values
	 * @return the DEDataDefinition object
	 * @review
	 */
	public DEDataDefinition getDEDataDefinition() {
		return _deDataDefinition;
	}

	/**
	 * Returns the Data Record field values that will be serialized
	 * @return the field values
	 * @review
	 */
	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	/**
	 * Inner builder that assembles the request.
	 * @review
	 */
	public static final class Builder {

		/**
		 * Creates a new Builder with a deDataDefinition and field values as parameters
		 * @param deDataDefinition The {@link DEDataDefinition} used to serialize the Data Record field values
		 * @param deDataLayout The Data Record field values that will be serialized
		 * @return the builder object
		 * @review
		 */
		public Builder(
			Map<String, Object> values, DEDataDefinition deDataDefinition) {

			_deDataRecordSerializerApplyRequest._values = values;
			_deDataRecordSerializerApplyRequest._deDataDefinition =
				deDataDefinition;
		}

		/**
		 * Builds the request.
		 * @return the {@link DEDataRecordValuesSerializerApplyRequest} object.
		 * @review
		 */
		public DEDataRecordValuesSerializerApplyRequest build() {
			return _deDataRecordSerializerApplyRequest;
		}

		private final DEDataRecordValuesSerializerApplyRequest
			_deDataRecordSerializerApplyRequest =
				new DEDataRecordValuesSerializerApplyRequest();

	}

	private DEDataRecordValuesSerializerApplyRequest() {
	}

	private DEDataDefinition _deDataDefinition;
	private Map<String, Object> _values;

}