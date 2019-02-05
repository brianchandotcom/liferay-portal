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

import java.util.Map;

/**
 * Provides the response builders from the serialize/deserialize
 * process of the Data Record field values
 *
 * @author Leonardo Barros
 * @review
 */
public class DEDataRecordValuesResponseBuilder {

	/**
	 * Returns the deserialize response builder of the Data Record field values
	 *
	 * @param values The deserialized data record field values
	 * @return the response builder
	 * @review
	 */
	public static DEDataRecordValuesDeserializerApplyResponse.Builder
		deserializeBuilder(Map<String, Object> values) {

		return new DEDataRecordValuesDeserializerApplyResponse.Builder(values);
	}

	/**
	 * Returns the serialize response builder of the Data Record field values
	 *
	 * @param content The serialized data record field values
	 * @param deDataDefinition the Data Definition related do the Data Record
	 * @return the response builder
	 * @review
	 */
	public static DEDataRecordValuesSerializerApplyResponse.Builder
		serializeBuilder(String content) {

		return new DEDataRecordValuesSerializerApplyResponse.Builder(content);
	}

}