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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

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
public class AuditEventWrapper extends BaseModelWrapper<AuditEvent>
	implements AuditEvent, ModelWrapper<AuditEvent> {
	public AuditEventWrapper(AuditEvent auditEvent) {
		super(auditEvent);
	}

	/**
	* Returns the additional info of this audit event.
	*
	* @return the additional info of this audit event
	*/
	@Override
	public String getAdditionalInfo() {
		return model.getAdditionalInfo();
	}

	/**
	* Returns the audit event ID of this audit event.
	*
	* @return the audit event ID of this audit event
	*/
	@Override
	public long getAuditEventId() {
		return model.getAuditEventId();
	}

	/**
	* Returns the class name of this audit event.
	*
	* @return the class name of this audit event
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class pk of this audit event.
	*
	* @return the class pk of this audit event
	*/
	@Override
	public String getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the client host of this audit event.
	*
	* @return the client host of this audit event
	*/
	@Override
	public String getClientHost() {
		return model.getClientHost();
	}

	/**
	* Returns the client ip of this audit event.
	*
	* @return the client ip of this audit event
	*/
	@Override
	public String getClientIP() {
		return model.getClientIP();
	}

	/**
	* Returns the company ID of this audit event.
	*
	* @return the company ID of this audit event
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this audit event.
	*
	* @return the create date of this audit event
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the event type of this audit event.
	*
	* @return the event type of this audit event
	*/
	@Override
	public String getEventType() {
		return model.getEventType();
	}

	/**
	* Returns the message of this audit event.
	*
	* @return the message of this audit event
	*/
	@Override
	public String getMessage() {
		return model.getMessage();
	}

	/**
	* Returns the primary key of this audit event.
	*
	* @return the primary key of this audit event
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the server name of this audit event.
	*
	* @return the server name of this audit event
	*/
	@Override
	public String getServerName() {
		return model.getServerName();
	}

	/**
	* Returns the server port of this audit event.
	*
	* @return the server port of this audit event
	*/
	@Override
	public int getServerPort() {
		return model.getServerPort();
	}

	/**
	* Returns the session ID of this audit event.
	*
	* @return the session ID of this audit event
	*/
	@Override
	public String getSessionID() {
		return model.getSessionID();
	}

	/**
	* Returns the user ID of this audit event.
	*
	* @return the user ID of this audit event
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this audit event.
	*
	* @return the user name of this audit event
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this audit event.
	*
	* @return the user uuid of this audit event
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the additional info of this audit event.
	*
	* @param additionalInfo the additional info of this audit event
	*/
	@Override
	public void setAdditionalInfo(String additionalInfo) {
		model.setAdditionalInfo(additionalInfo);
	}

	/**
	* Sets the audit event ID of this audit event.
	*
	* @param auditEventId the audit event ID of this audit event
	*/
	@Override
	public void setAuditEventId(long auditEventId) {
		model.setAuditEventId(auditEventId);
	}

	/**
	* Sets the class name of this audit event.
	*
	* @param className the class name of this audit event
	*/
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class pk of this audit event.
	*
	* @param classPK the class pk of this audit event
	*/
	@Override
	public void setClassPK(String classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the client host of this audit event.
	*
	* @param clientHost the client host of this audit event
	*/
	@Override
	public void setClientHost(String clientHost) {
		model.setClientHost(clientHost);
	}

	/**
	* Sets the client ip of this audit event.
	*
	* @param clientIP the client ip of this audit event
	*/
	@Override
	public void setClientIP(String clientIP) {
		model.setClientIP(clientIP);
	}

	/**
	* Sets the company ID of this audit event.
	*
	* @param companyId the company ID of this audit event
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this audit event.
	*
	* @param createDate the create date of this audit event
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the event type of this audit event.
	*
	* @param eventType the event type of this audit event
	*/
	@Override
	public void setEventType(String eventType) {
		model.setEventType(eventType);
	}

	/**
	* Sets the message of this audit event.
	*
	* @param message the message of this audit event
	*/
	@Override
	public void setMessage(String message) {
		model.setMessage(message);
	}

	/**
	* Sets the primary key of this audit event.
	*
	* @param primaryKey the primary key of this audit event
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the server name of this audit event.
	*
	* @param serverName the server name of this audit event
	*/
	@Override
	public void setServerName(String serverName) {
		model.setServerName(serverName);
	}

	/**
	* Sets the server port of this audit event.
	*
	* @param serverPort the server port of this audit event
	*/
	@Override
	public void setServerPort(int serverPort) {
		model.setServerPort(serverPort);
	}

	/**
	* Sets the session ID of this audit event.
	*
	* @param sessionID the session ID of this audit event
	*/
	@Override
	public void setSessionID(String sessionID) {
		model.setSessionID(sessionID);
	}

	/**
	* Sets the user ID of this audit event.
	*
	* @param userId the user ID of this audit event
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this audit event.
	*
	* @param userName the user name of this audit event
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this audit event.
	*
	* @param userUuid the user uuid of this audit event
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected AuditEventWrapper wrap(AuditEvent auditEvent) {
		return new AuditEventWrapper(auditEvent);
	}
}