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

import java.util.Map;

/**
 * Provides the request builders from the serialize/deserialize
 * process of the Data Record field values
 *
 * @author Leonardo Barros
 * @review
 */
public class DEDataRecordValuesRequestBuilder {

	/**
	 * Returns the deserialize request builder of the Data Record field values
	 *
	 * @param content The content to be deserialized
	 * @param deDataDefinition the Data Definition related do the Data Record
	 * @return the request builder
	 * @review
	 */
	public static DEDataRecordValuesDeserializerApplyRequest.Builder
		deserializeBuilder(String content, DEDataDefinition deDataDefinition) {

		return new DEDataRecordValuesDeserializerApplyRequest.Builder(
			content, deDataDefinition);
	}

	/**
	 * Returns the serialize request builder of the Data Record field values
	 *
	 * @param values A map of data record field values to be serialized
	 * @param deDataDefinition the Data Definition related do the Data Record
	 * @return the request builder
	 * @review
	 */
	public static DEDataRecordValuesSerializerApplyRequest.Builder
		serializeBuilder(
			Map<String, Object> values, DEDataDefinition deDataDefinition) {

		return new DEDataRecordValuesSerializerApplyRequest.Builder(
			values, deDataDefinition);
	}

}