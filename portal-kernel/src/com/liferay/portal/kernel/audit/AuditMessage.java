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

package com.liferay.portal.kernel.audit;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;

/**
 * @author Michael C. Han
 * @author Mika Koivisto
 * @author Bruno Farache
 */
public class AuditMessage implements Serializable {

	public AuditMessage(String message) throws JSONException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(message);

		_additionalInfoJSONObject = jsonObject.getJSONObject(_ADDITIONAL_INFO);
		_className = jsonObject.getString(_CLASS_NAME);
		_classPK = jsonObject.getString(_CLASS_PK);

		if (jsonObject.has(_CLIENT_HOST)) {
			_clientHost = jsonObject.getString(_CLIENT_HOST);
		}

		if (jsonObject.has(_CLIENT_IP)) {
			_clientIP = jsonObject.getString(_CLIENT_IP);
		}

		_companyId = jsonObject.getLong(_COMPANY_ID);
		_eventType = jsonObject.getString(_EVENT_TYPE);
		_message = jsonObject.getString(_MESSAGE);

		if (jsonObject.has(_SERVER_NAME)) {
			_serverName = jsonObject.getString(_SERVER_NAME);
		}

		if (jsonObject.has(_SERVER_PORT)) {
			_serverPort = jsonObject.getInt(_SERVER_PORT);
		}

		if (jsonObject.has(_SESSION_ID)) {
			_sessionID = jsonObject.getString(_SESSION_ID);
		}

		_timestamp = GetterUtil.getDate(
			jsonObject.getString(_TIMESTAMP), _getDateFormat());
		_userId = jsonObject.getLong(_USER_ID);
		_userName = jsonObject.getString(_USER_NAME);
	}

	public AuditMessage(
		String eventType, long companyId, long userId, String userName) {

		this(
			eventType, companyId, userId, userName, null, null, null, null,
			null);
	}

	public AuditMessage(
		String eventType, long companyId, long userId, String userName,
		String className, String classPK) {

		this(
			eventType, companyId, userId, userName, className, classPK, null,
			null, null);
	}

	public AuditMessage(
		String eventType, long companyId, long userId, String userName,
		String className, String classPK, String message) {

		this(
			eventType, companyId, userId, userName, className, classPK, message,
			null, null);
	}

	public AuditMessage(
		String eventType, long companyId, long userId, String userName,
		String className, String classPK, String message, Date timestamp,
		JSONObject additionalInfoJSONObject) {

		_eventType = eventType;
		_companyId = companyId;
		_userId = userId;
		_userName = userName;
		_className = className;
		_classPK = classPK;
		_message = message;

		AuditRequestThreadLocal auditRequestThreadLocal =
			AuditRequestThreadLocal.getAuditThreadLocal();

		_clientHost = auditRequestThreadLocal.getClientHost();
		_clientIP = auditRequestThreadLocal.getClientIP();
		_serverName = auditRequestThreadLocal.getServerName();
		_serverPort = auditRequestThreadLocal.getServerPort();
		_sessionID = auditRequestThreadLocal.getSessionID();

		_timestamp = timestamp;

		if (_timestamp == null) {
			_timestamp = new Date();
		}

		_additionalInfoJSONObject = additionalInfoJSONObject;

		if (_additionalInfoJSONObject == null) {
			JSONFactoryUtil.createJSONObject();
		}
	}

	public AuditMessage(
		String eventType, long companyId, long userId, String userName,
		String className, String classPK, String message,
		JSONObject additionalInfoJSONObject) {

		this(
			eventType, companyId, userId, userName, className, classPK, message,
			null, additionalInfoJSONObject);
	}

	public JSONObject getAdditionalInfo() {
		return _additionalInfoJSONObject;
	}

	public String getClassName() {
		return _className;
	}

	public String getClassPK() {
		return _classPK;
	}

	public String getClientHost() {
		return _clientHost;
	}

	public String getClientIP() {
		return _clientIP;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getEventType() {
		return _eventType;
	}

	public String getMessage() {
		return _message;
	}

	public String getServerName() {
		return _serverName;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public String getSessionID() {
		return _sessionID;
	}

	public Date getTimestamp() {
		return _timestamp;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setAdditionalInfo(JSONObject additionalInfoJSONObject) {
		_additionalInfoJSONObject = additionalInfoJSONObject;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = String.valueOf(classPK);
	}

	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	public void setClientHost(String clientHost) {
		_clientHost = clientHost;
	}

	public void setClientIP(String clientIP) {
		_clientIP = clientIP;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setEventType(String eventType) {
		_eventType = eventType;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setServerName(String serverName) {
		_serverName = serverName;
	}

	public void setServerPort(int serverPort) {
		_serverPort = serverPort;
	}

	public void setSessionID(String sessionID) {
		_sessionID = sessionID;
	}

	public void setTimestamp(Date timestamp) {
		_timestamp = timestamp;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			_ADDITIONAL_INFO, _additionalInfoJSONObject
		).put(
			_COMPANY_ID, _companyId
		).put(
			_CLASS_PK, _classPK
		).put(
			_CLASS_NAME, _className
		).put(
			_CLIENT_HOST, _clientHost
		).put(
			_CLIENT_IP, _clientIP
		).put(
			_MESSAGE, _message
		).put(
			_SERVER_PORT, _serverPort
		).put(
			_SERVER_NAME, _serverName
		).put(
			_SESSION_ID, _sessionID
		).put(
			_TIMESTAMP, _getDateFormat().format(new Date())
		).put(
			_EVENT_TYPE, _eventType
		).put(
			_USER_ID, _userId
		).put(
			_USER_NAME, _userName
		);
	}

	private DateFormat _getDateFormat() {
		return DateFormatFactoryUtil.getSimpleDateFormat(_DATE_FORMAT);
	}

	private static final String _ADDITIONAL_INFO = "additionalInfo";

	private static final String _CLASS_NAME = "className";

	private static final String _CLASS_PK = "classPK";

	private static final String _CLIENT_HOST = "clientHost";

	private static final String _CLIENT_IP = "clientIP";

	private static final String _COMPANY_ID = "companyId";

	private static final String _DATE_FORMAT = "yyyyMMddkkmmssSSS";

	private static final String _EVENT_TYPE = "eventType";

	private static final String _MESSAGE = "message";

	private static final String _SERVER_NAME = "serverName";

	private static final String _SERVER_PORT = "serverPort";

	private static final String _SESSION_ID = "sessionID";

	private static final String _TIMESTAMP = "timestamp";

	private static final String _USER_ID = "userId";

	private static final String _USER_NAME = "userName";

	private JSONObject _additionalInfoJSONObject;
	private String _className;
	private String _classPK;
	private String _clientHost;
	private String _clientIP;
	private long _companyId = -1;
	private String _eventType;
	private String _message;
	private String _serverName;
	private int _serverPort;
	private String _sessionID;
	private Date _timestamp;
	private long _userId = -1;
	private String _userName;

}