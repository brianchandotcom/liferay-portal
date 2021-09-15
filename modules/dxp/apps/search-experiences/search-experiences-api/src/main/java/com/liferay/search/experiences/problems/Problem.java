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

package com.liferay.search.experiences.problems;

import com.liferay.petra.string.StringBundler;

/**
 * @author Petteri Karttunen
 * @author Brian Wing Shun Chan
 */
public class Problem {

	public Problem(Problem problem) {
		_className = problem._className;
		_elementId = problem._elementId;
		_localizationKey = problem._localizationKey;
		_msg = problem._msg;
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

	public String getElementId() {
		return _elementId;
	}

	public String getLocalizationKey() {
		return _localizationKey;
	}

	public String getMsg() {
		return _msg;
	}

	public Object getRootConfiguration() {
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

	public void setElementId(String elementId) {
		_elementId = elementId;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("Problem [_className=");
		sb.append(_className);
		sb.append(", _elementId=");
		sb.append(_elementId);
		sb.append(", _localizationKey=");
		sb.append(_localizationKey);
		sb.append(", _msg=");
		sb.append(_msg);
		sb.append(", _rootConfiguration=");
		sb.append(_rootConfiguration);
		sb.append(", _rootObject=");
		sb.append(_rootObject);
		sb.append(", _rootProperty=");
		sb.append(_rootProperty);
		sb.append(", _rootValue=");
		sb.append(_rootValue);
		sb.append(", _severity=");
		sb.append(_severity);
		sb.append(", _throwable=");
		sb.append(_throwable);
		sb.append("]");

		return sb.toString();
	}

	public static class Builder {

		public Builder() {
			_problem = new Problem();
		}

		public Builder(Problem message) {
			_problem = message;
		}

		public Problem build() {
			return new Problem(_problem);
		}

		public Builder className(String className) {
			_problem._className = className;

			return this;
		}

		public Builder elementId(String elementId) {
			_problem._elementId = elementId;

			return this;
		}

		public Builder localizationKey(String localizationKey) {
			_problem._localizationKey = localizationKey;

			return this;
		}

		public Builder msg(String msg) {
			_problem._msg = msg;

			return this;
		}

		public Builder rootConfiguration(String rootConfiguration) {
			_problem._rootConfiguration = rootConfiguration;

			return this;
		}

		public Builder rootObject(Object object) {
			_problem._rootObject = object;

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
	private String _elementId;
	private String _localizationKey;
	private String _msg;
	private String _rootConfiguration;
	private Object _rootObject;
	private String _rootProperty;
	private String _rootValue;
	private Severity _severity;
	private Throwable _throwable;

}