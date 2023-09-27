/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFormEvaluatorFieldContextKey)) {
			return false;
		}

		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey =
			(DDMFormEvaluatorFieldContextKey)object;

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