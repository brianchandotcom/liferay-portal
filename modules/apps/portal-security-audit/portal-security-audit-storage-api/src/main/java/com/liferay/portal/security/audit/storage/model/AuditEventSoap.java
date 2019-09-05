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

package com.liferay.portal.security.audit.storage.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.security.audit.storage.service.http.AuditEventServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AuditEventSoap implements Serializable {

	public static AuditEventSoap toSoapModel(AuditEvent model) {
		AuditEventSoap soapModel = new AuditEventSoap();

		soapModel.setAuditEventId(model.getAuditEventId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setEventType(model.getEventType());
		soapModel.setClassName(model.getClassName());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setMessage(model.getMessage());
		soapModel.setClientHost(model.getClientHost());
		soapModel.setClientIP(model.getClientIP());
		soapModel.setServerName(model.getServerName());
		soapModel.setServerPort(model.getServerPort());
		soapModel.setSessionID(model.getSessionID());
		soapModel.setAdditionalInfo(model.getAdditionalInfo());

		return soapModel;
	}

	public static AuditEventSoap[] toSoapModels(AuditEvent[] models) {
		AuditEventSoap[] soapModels = new AuditEventSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AuditEventSoap[][] toSoapModels(AuditEvent[][] models) {
		AuditEventSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AuditEventSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AuditEventSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AuditEventSoap[] toSoapModels(List<AuditEvent> models) {
		List<AuditEventSoap> soapModels = new ArrayList<AuditEventSoap>(
			models.size());

		for (AuditEvent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AuditEventSoap[soapModels.size()]);
	}

	public AuditEventSoap() {
	}

	public long getPrimaryKey() {
		return _auditEventId;
	}

	public void setPrimaryKey(long pk) {
		setAuditEventId(pk);
	}

	public long getAuditEventId() {
		return _auditEventId;
	}

	public void setAuditEventId(long auditEventId) {
		_auditEventId = auditEventId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public String getEventType() {
		return _eventType;
	}

	public void setEventType(String eventType) {
		_eventType = eventType;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getClassPK() {
		return _classPK;
	}

	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	public String getMessage() {
		return _message;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public String getClientHost() {
		return _clientHost;
	}

	public void setClientHost(String clientHost) {
		_clientHost = clientHost;
	}

	public String getClientIP() {
		return _clientIP;
	}

	public void setClientIP(String clientIP) {
		_clientIP = clientIP;
	}

	public String getServerName() {
		return _serverName;
	}

	public void setServerName(String serverName) {
		_serverName = serverName;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public void setServerPort(int serverPort) {
		_serverPort = serverPort;
	}

	public String getSessionID() {
		return _sessionID;
	}

	public void setSessionID(String sessionID) {
		_sessionID = sessionID;
	}

	public String getAdditionalInfo() {
		return _additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		_additionalInfo = additionalInfo;
	}

	private long _auditEventId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private String _eventType;
	private String _className;
	private String _classPK;
	private String _message;
	private String _clientHost;
	private String _clientIP;
	private String _serverName;
	private int _serverPort;
	private String _sessionID;
	private String _additionalInfo;

}