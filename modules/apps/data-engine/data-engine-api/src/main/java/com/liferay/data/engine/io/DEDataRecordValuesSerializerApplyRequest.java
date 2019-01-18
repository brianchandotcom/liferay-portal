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
 * @author Leonardo Barros
 */
public final class DEDataRecordValuesSerializerApplyRequest {

	public DEDataDefinition getDEDataDefinition() {
		return _deDataDefinition;
	}

	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	public static final class Builder {

		public Builder(
			Map<String, Object> values, DEDataDefinition deDataDefinition) {

			_deDataRecordSerializerApplyRequest._values = values;
			_deDataRecordSerializerApplyRequest._deDataDefinition =
				deDataDefinition;
		}

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