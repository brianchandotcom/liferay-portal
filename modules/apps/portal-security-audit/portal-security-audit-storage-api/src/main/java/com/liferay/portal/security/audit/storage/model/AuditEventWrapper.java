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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AuditEvent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AuditEvent
 * @generated
 */
@ProviderType
public class AuditEventWrapper implements AuditEvent, ModelWrapper<AuditEvent> {
	public AuditEventWrapper(AuditEvent auditEvent) {
		_auditEvent = auditEvent;
	}

	@Override
	public Class<?> getModelClass() {
		return AuditEvent.class;
	}

	@Override
	public String getModelClassName() {
		return AuditEvent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("auditEventId", getAuditEventId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("eventType", getEventType());
		attributes.put("className", getClassName());
		attributes.put("classPK", getClassPK());
		attributes.put("message", getMessage());
		attributes.put("clientHost", getClientHost());
		attributes.put("clientIP", getClientIP());
		attributes.put("serverName", getServerName());
		attributes.put("serverPort", getServerPort());
		attributes.put("sessionID", getSessionID());
		attributes.put("additionalInfo", getAdditionalInfo());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long auditEventId = (Long)attributes.get("auditEventId");

		if (auditEventId != null) {
			setAuditEventId(auditEventId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String eventType = (String)attributes.get("eventType");

		if (eventType != null) {
			setEventType(eventType);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		String classPK = (String)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String message = (String)attributes.get("message");

		if (message != null) {
			setMessage(message);
		}

		String clientHost = (String)attributes.get("clientHost");

		if (clientHost != null) {
			setClientHost(clientHost);
		}

		String clientIP = (String)attributes.get("clientIP");

		if (clientIP != null) {
			setClientIP(clientIP);
		}

		String serverName = (String)attributes.get("serverName");

		if (serverName != null) {
			setServerName(serverName);
		}

		Integer serverPort = (Integer)attributes.get("serverPort");

		if (serverPort != null) {
			setServerPort(serverPort);
		}

		String sessionID = (String)attributes.get("sessionID");

		if (sessionID != null) {
			setSessionID(sessionID);
		}

		String additionalInfo = (String)attributes.get("additionalInfo");

		if (additionalInfo != null) {
			setAdditionalInfo(additionalInfo);
		}
	}

	@Override
	public Object clone() {
		return new AuditEventWrapper((AuditEvent)_auditEvent.clone());
	}

	@Override
	public int compareTo(AuditEvent auditEvent) {
		return _auditEvent.compareTo(auditEvent);
	}

	/**
	* Returns the additional info of this audit event.
	*
	* @return the additional info of this audit event
	*/
	@Override
	public String getAdditionalInfo() {
		return _auditEvent.getAdditionalInfo();
	}

	/**
	* Returns the audit event ID of this audit event.
	*
	* @return the audit event ID of this audit event
	*/
	@Override
	public long getAuditEventId() {
		return _auditEvent.getAuditEventId();
	}

	/**
	* Returns the class name of this audit event.
	*
	* @return the class name of this audit event
	*/
	@Override
	public String getClassName() {
		return _auditEvent.getClassName();
	}

	/**
	* Returns the class pk of this audit event.
	*
	* @return the class pk of this audit event
	*/
	@Override
	public String getClassPK() {
		return _auditEvent.getClassPK();
	}

	/**
	* Returns the client host of this audit event.
	*
	* @return the client host of this audit event
	*/
	@Override
	public String getClientHost() {
		return _auditEvent.getClientHost();
	}

	/**
	* Returns the client ip of this audit event.
	*
	* @return the client ip of this audit event
	*/
	@Override
	public String getClientIP() {
		return _auditEvent.getClientIP();
	}

	/**
	* Returns the company ID of this audit event.
	*
	* @return the company ID of this audit event
	*/
	@Override
	public long getCompanyId() {
		return _auditEvent.getCompanyId();
	}

	/**
	* Returns the create date of this audit event.
	*
	* @return the create date of this audit event
	*/
	@Override
	public Date getCreateDate() {
		return _auditEvent.getCreateDate();
	}

	/**
	* Returns the event type of this audit event.
	*
	* @return the event type of this audit event
	*/
	@Override
	public String getEventType() {
		return _auditEvent.getEventType();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _auditEvent.getExpandoBridge();
	}

	/**
	* Returns the message of this audit event.
	*
	* @return the message of this audit event
	*/
	@Override
	public String getMessage() {
		return _auditEvent.getMessage();
	}

	/**
	* Returns the primary key of this audit event.
	*
	* @return the primary key of this audit event
	*/
	@Override
	public long getPrimaryKey() {
		return _auditEvent.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _auditEvent.getPrimaryKeyObj();
	}

	/**
	* Returns the server name of this audit event.
	*
	* @return the server name of this audit event
	*/
	@Override
	public String getServerName() {
		return _auditEvent.getServerName();
	}

	/**
	* Returns the server port of this audit event.
	*
	* @return the server port of this audit event
	*/
	@Override
	public int getServerPort() {
		return _auditEvent.getServerPort();
	}

	/**
	* Returns the session ID of this audit event.
	*
	* @return the session ID of this audit event
	*/
	@Override
	public String getSessionID() {
		return _auditEvent.getSessionID();
	}

	/**
	* Returns the user ID of this audit event.
	*
	* @return the user ID of this audit event
	*/
	@Override
	public long getUserId() {
		return _auditEvent.getUserId();
	}

	/**
	* Returns the user name of this audit event.
	*
	* @return the user name of this audit event
	*/
	@Override
	public String getUserName() {
		return _auditEvent.getUserName();
	}

	/**
	* Returns the user uuid of this audit event.
	*
	* @return the user uuid of this audit event
	*/
	@Override
	public String getUserUuid() {
		return _auditEvent.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _auditEvent.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _auditEvent.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _auditEvent.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _auditEvent.isNew();
	}

	@Override
	public void persist() {
		_auditEvent.persist();
	}

	/**
	* Sets the additional info of this audit event.
	*
	* @param additionalInfo the additional info of this audit event
	*/
	@Override
	public void setAdditionalInfo(String additionalInfo) {
		_auditEvent.setAdditionalInfo(additionalInfo);
	}

	/**
	* Sets the audit event ID of this audit event.
	*
	* @param auditEventId the audit event ID of this audit event
	*/
	@Override
	public void setAuditEventId(long auditEventId) {
		_auditEvent.setAuditEventId(auditEventId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_auditEvent.setCachedModel(cachedModel);
	}

	/**
	* Sets the class name of this audit event.
	*
	* @param className the class name of this audit event
	*/
	@Override
	public void setClassName(String className) {
		_auditEvent.setClassName(className);
	}

	/**
	* Sets the class pk of this audit event.
	*
	* @param classPK the class pk of this audit event
	*/
	@Override
	public void setClassPK(String classPK) {
		_auditEvent.setClassPK(classPK);
	}

	/**
	* Sets the client host of this audit event.
	*
	* @param clientHost the client host of this audit event
	*/
	@Override
	public void setClientHost(String clientHost) {
		_auditEvent.setClientHost(clientHost);
	}

	/**
	* Sets the client ip of this audit event.
	*
	* @param clientIP the client ip of this audit event
	*/
	@Override
	public void setClientIP(String clientIP) {
		_auditEvent.setClientIP(clientIP);
	}

	/**
	* Sets the company ID of this audit event.
	*
	* @param companyId the company ID of this audit event
	*/
	@Override
	public void setCompanyId(long companyId) {
		_auditEvent.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this audit event.
	*
	* @param createDate the create date of this audit event
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_auditEvent.setCreateDate(createDate);
	}

	/**
	* Sets the event type of this audit event.
	*
	* @param eventType the event type of this audit event
	*/
	@Override
	public void setEventType(String eventType) {
		_auditEvent.setEventType(eventType);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_auditEvent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_auditEvent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_auditEvent.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the message of this audit event.
	*
	* @param message the message of this audit event
	*/
	@Override
	public void setMessage(String message) {
		_auditEvent.setMessage(message);
	}

	@Override
	public void setNew(boolean n) {
		_auditEvent.setNew(n);
	}

	/**
	* Sets the primary key of this audit event.
	*
	* @param primaryKey the primary key of this audit event
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_auditEvent.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_auditEvent.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the server name of this audit event.
	*
	* @param serverName the server name of this audit event
	*/
	@Override
	public void setServerName(String serverName) {
		_auditEvent.setServerName(serverName);
	}

	/**
	* Sets the server port of this audit event.
	*
	* @param serverPort the server port of this audit event
	*/
	@Override
	public void setServerPort(int serverPort) {
		_auditEvent.setServerPort(serverPort);
	}

	/**
	* Sets the session ID of this audit event.
	*
	* @param sessionID the session ID of this audit event
	*/
	@Override
	public void setSessionID(String sessionID) {
		_auditEvent.setSessionID(sessionID);
	}

	/**
	* Sets the user ID of this audit event.
	*
	* @param userId the user ID of this audit event
	*/
	@Override
	public void setUserId(long userId) {
		_auditEvent.setUserId(userId);
	}

	/**
	* Sets the user name of this audit event.
	*
	* @param userName the user name of this audit event
	*/
	@Override
	public void setUserName(String userName) {
		_auditEvent.setUserName(userName);
	}

	/**
	* Sets the user uuid of this audit event.
	*
	* @param userUuid the user uuid of this audit event
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_auditEvent.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AuditEvent> toCacheModel() {
		return _auditEvent.toCacheModel();
	}

	@Override
	public AuditEvent toEscapedModel() {
		return new AuditEventWrapper(_auditEvent.toEscapedModel());
	}

	@Override
	public String toString() {
		return _auditEvent.toString();
	}

	@Override
	public AuditEvent toUnescapedModel() {
		return new AuditEventWrapper(_auditEvent.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _auditEvent.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AuditEventWrapper)) {
			return false;
		}

		AuditEventWrapper auditEventWrapper = (AuditEventWrapper)obj;

		if (Objects.equals(_auditEvent, auditEventWrapper._auditEvent)) {
			return true;
		}

		return false;
	}

	@Override
	public AuditEvent getWrappedModel() {
		return _auditEvent;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _auditEvent.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _auditEvent.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_auditEvent.resetOriginalValues();
	}

	private final AuditEvent _auditEvent;
}