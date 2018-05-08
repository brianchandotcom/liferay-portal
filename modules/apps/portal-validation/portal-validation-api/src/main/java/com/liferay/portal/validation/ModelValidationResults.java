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

package com.liferay.portal.validation;

import aQute.bnd.annotation.ProviderType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
@ProviderType
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
			String message, Throwable cause) {

			modelValidationResults._exceptions.put(message, cause);

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
			String message, Throwable cause) {

			modelValidationResults._exceptions.put(message, cause);

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

	private Map<String, Throwable> _exceptions = new HashMap<>();
	private Map<String, Object> _fields = new HashMap<>();
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