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

package com.liferay.osb.faro.engine.client.model;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class ErrorResponse {

	public Map<String, Object> getDebugInfo() {
		return _debugInfo;
	}

	public String getError() {
		return _error;
	}

	public String getException() {
		return _exception;
	}

	public String getField() {
		return _field;
	}

	public String getLocalizedMessage() {
		return _localizedMessage;
	}

	public String getMessage() {
		return _message;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public String getPath() {
		return _path;
	}

	public int getStatus() {
		return _status;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	public void setDebugInfo(Map<String, Object> debugInfo) {
		_debugInfo = debugInfo;
	}

	public void setError(String error) {
		_error = error;
	}

	public void setException(String exception) {
		_exception = exception;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setLocalizedMessage(String localizedMessage) {
		_localizedMessage = localizedMessage;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setMessageKey(String messageKey) {
		_messageKey = messageKey;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	private Map<String, Object> _debugInfo;
	private String _error;
	private String _exception;
	private String _field;
	private String _localizedMessage;
	private String _message;
	private String _messageKey;
	private String _path;
	private int _status;
	private long _timestamp;

}