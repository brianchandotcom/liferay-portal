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

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEvaluatorFieldContextKey {

	public DDMFormEvaluatorFieldContextKey(
		String fieldName, String instanceId) {

		_fieldName = fieldName;
		_instanceId = instanceId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormEvaluatorFieldContextKey)) {
			return false;
		}

		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey =
			(DDMFormEvaluatorFieldContextKey)obj;

		if (Objects.equals(_fieldName, ddmFormFieldContextKey._fieldName) &&
			Objects.equals(_instanceId, ddmFormFieldContextKey._instanceId)) {

			return true;
		}

		return false;
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getName() {
		return _fieldName;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _fieldName);

		return HashUtil.hash(hash, _instanceId);
	}

	protected void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	protected void setInstanceId(String instanceId) {
		_instanceId = instanceId;
	}

	private String _fieldName;
	private String _instanceId;

}