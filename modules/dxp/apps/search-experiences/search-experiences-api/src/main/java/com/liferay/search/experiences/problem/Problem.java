/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.problem;

import com.liferay.petra.string.StringBundler;

/**
 * @author Petteri Karttunen
 * @author Brian Wing Shun Chan
 */
public class Problem {

	public Problem(Problem problem) {
		_className = problem._className;
		_elementKey = problem._elementKey;
		_languageKey = problem._languageKey;
		_message = problem._message;
		_throwable = problem._throwable;
		_rootConfiguration = problem._rootConfiguration;
		_rootObject = problem._rootObject;
		_rootProperty = problem._rootProperty;
		_rootValue = problem._rootValue;
		_severity = problem._severity;
	}

	public String getClassName() {
		return _className;
	}

	public String getElementKey() {
		return _elementKey;
	}

	public String getLanguageKey() {
		return _languageKey;
	}

	public String getMessage() {
		return _message;
	}

	public String getRootConfiguration() {

		// TODO Remove this

		return _rootConfiguration;
	}

	public Object getRootObject() {
		return _rootObject;
	}

	public String getRootProperty() {
		return _rootProperty;
	}

	public String getRootValue() {
		return _rootValue;
	}

	public Severity getSeverity() {
		return _severity;
	}

	public Throwable getThrowable() {
		return _throwable;
	}

	public void setElementKey(String elementKey) {
		_elementKey = elementKey;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{_className=", _className, ", _elementKey=", _elementKey,
			", _languageKey=", _languageKey, ", _message=", _message,
			", _rootConfiguration=", _rootConfiguration, ", _rootObject=",
			_rootObject, ", _rootProperty=", _rootProperty, ", _rootValue=",
			_rootValue, ", _severity=", _severity, ", _throwable=", _throwable,
			"}");
	}

	public static class Builder {

		public Builder() {
			_problem = new Problem();
		}

		public Builder(Problem problem) {
			_problem = problem;
		}

		public Problem build() {
			return new Problem(_problem);
		}

		public Builder className(String className) {
			_problem._className = className;

			return this;
		}

		public Builder elementKey(String elementKey) {
			_problem._elementKey = elementKey;

			return this;
		}

		public Builder languageKey(String languageKey) {
			_problem._languageKey = languageKey;

			return this;
		}

		public Builder message(String message) {
			_problem._message = message;

			return this;
		}

		public Builder rootConfiguration(String rootConfiguration) {
			_problem._rootConfiguration = rootConfiguration;

			return this;
		}

		public Builder rootObject(Object rootObject) {
			_problem._rootObject = rootObject;

			return this;
		}

		public Builder rootProperty(String rootProperty) {
			_problem._rootProperty = rootProperty;

			return this;
		}

		public Builder rootValue(String rootValue) {
			_problem._rootValue = rootValue;

			return this;
		}

		public Builder severity(Severity severity) {
			_problem._severity = severity;

			return this;
		}

		public Builder throwable(Throwable throwable) {
			_problem._throwable = throwable;

			return this;
		}

		private final Problem _problem;

	}

	private Problem() {
	}

	private String _className;
	private String _elementKey;
	private String _languageKey;
	private String _message;
	private String _rootConfiguration;
	private Object _rootObject;
	private String _rootProperty;
	private String _rootValue;
	private Severity _severity;
	private Throwable _throwable;

}