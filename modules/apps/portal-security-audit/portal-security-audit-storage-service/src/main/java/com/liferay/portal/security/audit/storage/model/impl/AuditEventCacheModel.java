/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AuditEventCacheModel)) {
			return false;
		}

		AuditEventCacheModel auditEventCacheModel =
			(AuditEventCacheModel)object;

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
		StringBundler sb = new StringBundler(33);

		sb.append("{auditEventId=");
		sb.append(auditEventId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		auditEventImpl.setGroupId(groupId);
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
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		auditEventId = objectInput.readLong();

		groupId = objectInput.readLong();

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
		additionalInfo = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(auditEventId);

		objectOutput.writeLong(groupId);

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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(additionalInfo);
		}
	}

	public long auditEventId;
	public long groupId;
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