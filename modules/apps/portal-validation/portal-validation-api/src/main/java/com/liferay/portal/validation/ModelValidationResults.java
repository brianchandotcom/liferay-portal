/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.validation;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class ModelValidationResults {

	public static FailureBuilder failure() {
		return new FailureBuilder();
	}

	public static ModelValidationResults success() {
		return new ModelValidationResults(_SUCCESS);
	}

	public static WarningBuilder warning() {
		return new WarningBuilder();
	}

	public Map<String, Throwable> getExceptions() {
		return _exceptions;
	}

	public String getExceptionsString() {
		StringBundler sb = new StringBundler((_exceptions.size() * 4) + 2);

		sb.append("Exceptions: {");

		for (Map.Entry<String, Throwable> exception : _exceptions.entrySet()) {
			sb.append("Message: ");
			sb.append(exception.getKey());
			sb.append("Exception: ");
			sb.append(exception.getValue());
		}

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	public Map<String, Object> getFields() {
		return _fields;
	}

	public String getFieldsString() {
		StringBundler sb = new StringBundler((_fields.size() * 4) + 2);

		sb.append("Fields: {");

		for (Map.Entry<String, Object> field : _fields.entrySet()) {
			sb.append("Field: ");
			sb.append(field.getKey());
			sb.append("Value: ");
			sb.append(field.getValue());
		}

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	public boolean isFailure() {
		if (_outcome == _FAILURE) {
			return true;
		}

		return false;
	}

	public boolean isSuccess() {
		if (_outcome == _SUCCESS) {
			return true;
		}

		return false;
	}

	public boolean isWarning() {
		if (_outcome == _WARNING) {
			return true;
		}

		return false;
	}

	public static class FailureBuilder extends Builder {

		public FailureBuilder() {
			super(_FAILURE);
		}

		public FailureBuilder exceptionFailure(
			String message, Throwable throwable) {

			modelValidationResults._exceptions.put(message, throwable);

			return this;
		}

		public FailureBuilder fieldFailure(String name, Object value) {
			modelValidationResults._fields.put(name, value);

			return this;
		}

	}

	public static class WarningBuilder extends Builder {

		public WarningBuilder() {
			super(_WARNING);
		}

		public WarningBuilder exceptionWarning(
			String message, Throwable throwable) {

			modelValidationResults._exceptions.put(message, throwable);

			return this;
		}

		public WarningBuilder fieldWarning(String name, Object value) {
			modelValidationResults._fields.put(name, value);

			return this;
		}

	}

	private ModelValidationResults(int outcome) {
		_outcome = outcome;
	}

	private static final int _FAILURE = 0;

	private static final int _SUCCESS = 1;

	private static final int _WARNING = 2;

	private final Map<String, Throwable> _exceptions = new HashMap<>();
	private final Map<String, Object> _fields = new HashMap<>();
	private final int _outcome;

	private static class Builder {

		public Builder(int outcome) {
			modelValidationResults = new ModelValidationResults(outcome);
		}

		public ModelValidationResults getResults() {
			return modelValidationResults;
		}

		protected ModelValidationResults modelValidationResults;

	}

}