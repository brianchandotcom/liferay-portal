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

package com.liferay.portal.search.tuning.blueprints.message;

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Petteri Karttunen
 */
public class Message implements Serializable {

	public Message(Message message) {
		_className = message._className;
		_elementId = message._elementId;
		_localizationKey = message._localizationKey;
		_msg = message._msg;
		_throwable = message._throwable;
		_rootObject = message._rootObject;
		_rootProperty = message._rootProperty;
		_rootValue = message._rootValue;
		_severity = message._severity;
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
		StringBundler sb = new StringBundler(19);

		sb.append("Message [_className=");
		sb.append(_className);
		sb.append(", _elementId=");
		sb.append(_elementId);
		sb.append(", _localizationKey=");
		sb.append(_localizationKey);
		sb.append(", _msg=");
		sb.append(_msg);
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
			_message = new Message();
		}

		public Builder(Message message) {
			_message = message;
		}

		public Message build() {
			return new Message(_message);
		}

		public Builder className(String className) {
			_message._className = className;

			return this;
		}

		public Builder elementId(String elementId) {
			_message._elementId = elementId;

			return this;
		}

		public Builder localizationKey(String localizationKey) {
			_message._localizationKey = localizationKey;

			return this;
		}

		public Builder msg(String msg) {
			_message._msg = msg;

			return this;
		}

		public Builder rootObject(Object object) {
			_message._rootObject = object;

			return this;
		}

		public Builder rootProperty(String rootProperty) {
			_message._rootProperty = rootProperty;

			return this;
		}

		public Builder rootValue(String rootValue) {
			_message._rootValue = rootValue;

			return this;
		}

		public Builder severity(Severity severity) {
			_message._severity = severity;

			return this;
		}

		public Builder throwable(Throwable throwable) {
			_message._throwable = throwable;

			return this;
		}

		private final Message _message;

	}

	private Message() {
	}

	private static final long serialVersionUID = 1L;

	private String _className;
	private String _elementId;
	private String _localizationKey;
	private String _msg;
	private Object _rootObject;
	private String _rootProperty;
	private String _rootValue;
	private Severity _severity;
	private Throwable _throwable;

}