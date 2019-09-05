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

package com.liferay.portal.security.audit.storage.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.security.audit.storage.model.AuditEvent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AuditEvent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AuditEventCacheModel
	implements CacheModel<AuditEvent>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AuditEventCacheModel)) {
			return false;
		}

		AuditEventCacheModel auditEventCacheModel = (AuditEventCacheModel)obj;

		if (auditEventId == auditEventCacheModel.auditEventId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, auditEventId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{auditEventId=");
		sb.append(auditEventId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", eventType=");
		sb.append(eventType);
		sb.append(", className=");
		sb.append(className);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", message=");
		sb.append(message);
		sb.append(", clientHost=");
		sb.append(clientHost);
		sb.append(", clientIP=");
		sb.append(clientIP);
		sb.append(", serverName=");
		sb.append(serverName);
		sb.append(", serverPort=");
		sb.append(serverPort);
		sb.append(", sessionID=");
		sb.append(sessionID);
		sb.append(", additionalInfo=");
		sb.append(additionalInfo);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AuditEvent toEntityModel() {
		AuditEventImpl auditEventImpl = new AuditEventImpl();

		auditEventImpl.setAuditEventId(auditEventId);
		auditEventImpl.setCompanyId(companyId);
		auditEventImpl.setUserId(userId);

		if (userName == null) {
			auditEventImpl.setUserName("");
		}
		else {
			auditEventImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			auditEventImpl.setCreateDate(null);
		}
		else {
			auditEventImpl.setCreateDate(new Date(createDate));
		}

		if (eventType == null) {
			auditEventImpl.setEventType("");
		}
		else {
			auditEventImpl.setEventType(eventType);
		}

		if (className == null) {
			auditEventImpl.setClassName("");
		}
		else {
			auditEventImpl.setClassName(className);
		}

		if (classPK == null) {
			auditEventImpl.setClassPK("");
		}
		else {
			auditEventImpl.setClassPK(classPK);
		}

		if (message == null) {
			auditEventImpl.setMessage("");
		}
		else {
			auditEventImpl.setMessage(message);
		}

		if (clientHost == null) {
			auditEventImpl.setClientHost("");
		}
		else {
			auditEventImpl.setClientHost(clientHost);
		}

		if (clientIP == null) {
			auditEventImpl.setClientIP("");
		}
		else {
			auditEventImpl.setClientIP(clientIP);
		}

		if (serverName == null) {
			auditEventImpl.setServerName("");
		}
		else {
			auditEventImpl.setServerName(serverName);
		}

		auditEventImpl.setServerPort(serverPort);

		if (sessionID == null) {
			auditEventImpl.setSessionID("");
		}
		else {
			auditEventImpl.setSessionID(sessionID);
		}

		if (additionalInfo == null) {
			auditEventImpl.setAdditionalInfo("");
		}
		else {
			auditEventImpl.setAdditionalInfo(additionalInfo);
		}

		auditEventImpl.resetOriginalValues();

		return auditEventImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		auditEventId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		eventType = objectInput.readUTF();
		className = objectInput.readUTF();
		classPK = objectInput.readUTF();
		message = objectInput.readUTF();
		clientHost = objectInput.readUTF();
		clientIP = objectInput.readUTF();
		serverName = objectInput.readUTF();

		serverPort = objectInput.readInt();
		sessionID = objectInput.readUTF();
		additionalInfo = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(auditEventId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);

		if (eventType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(eventType);
		}

		if (className == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(className);
		}

		if (classPK == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(classPK);
		}

		if (message == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(message);
		}

		if (clientHost == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientHost);
		}

		if (clientIP == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientIP);
		}

		if (serverName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(serverName);
		}

		objectOutput.writeInt(serverPort);

		if (sessionID == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sessionID);
		}

		if (additionalInfo == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(additionalInfo);
		}
	}

	public long auditEventId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public String eventType;
	public String className;
	public String classPK;
	public String message;
	public String clientHost;
	public String clientIP;
	public String serverName;
	public int serverPort;
	public String sessionID;
	public String additionalInfo;

}