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

import java.util.Collections;
import java.util.Map;

/**
 * Data Record field values deserializer response
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordValuesDeserializerApplyResponse {

	/**
	 * Returns the deserialized Data Record field values
	 * @return the Data Record field values
	 * @review
	 */
	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	/**
	 * Inner Builder
	 * @review
	 */
	public static class Builder {

		/**
		 * Creates a Builder with the deserialized content.
		 * @param values The deserialized Data Record field values object.
		 * @return the builder
		 * @review
		 */
		public Builder(Map<String, Object> values) {
			_deDataRecordValuesDeserializerApplyResponse._values = values;
		}

		/**
		 * Creates the builder.
		 * @return The builder Object
		 * @review
		 */
		public DEDataRecordValuesDeserializerApplyResponse build() {
			return _deDataRecordValuesDeserializerApplyResponse;
		}

		private DEDataRecordValuesDeserializerApplyResponse
			_deDataRecordValuesDeserializerApplyResponse =
				new DEDataRecordValuesDeserializerApplyResponse();

	}

	private DEDataRecordValuesDeserializerApplyResponse() {
	}

	private Map<String, Object> _values;

}