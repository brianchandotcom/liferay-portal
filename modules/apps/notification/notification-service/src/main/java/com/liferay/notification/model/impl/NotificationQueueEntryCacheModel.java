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

package com.liferay.notification.model.impl;

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing NotificationQueueEntry in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationQueueEntryCacheModel
	implements CacheModel<NotificationQueueEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationQueueEntryCacheModel)) {
			return false;
		}

		NotificationQueueEntryCacheModel notificationQueueEntryCacheModel =
			(NotificationQueueEntryCacheModel)object;

		if ((notificationQueueEntryId ==
				notificationQueueEntryCacheModel.notificationQueueEntryId) &&
			(mvccVersion == notificationQueueEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationQueueEntryId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(45);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", notificationQueueEntryId=");
		sb.append(notificationQueueEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", notificationTemplateId=");
		sb.append(notificationTemplateId);
		sb.append(", bcc=");
		sb.append(bcc);
		sb.append(", body=");
		sb.append(body);
		sb.append(", cc=");
		sb.append(cc);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", from=");
		sb.append(from);
		sb.append(", fromName=");
		sb.append(fromName);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", sentDate=");
		sb.append(sentDate);
		sb.append(", subject=");
		sb.append(subject);
		sb.append(", to=");
		sb.append(to);
		sb.append(", toName=");
		sb.append(toName);
		sb.append(", type=");
		sb.append(type);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationQueueEntry toEntityModel() {
		NotificationQueueEntryImpl notificationQueueEntryImpl =
			new NotificationQueueEntryImpl();

		notificationQueueEntryImpl.setMvccVersion(mvccVersion);
		notificationQueueEntryImpl.setNotificationQueueEntryId(
			notificationQueueEntryId);
		notificationQueueEntryImpl.setCompanyId(companyId);
		notificationQueueEntryImpl.setUserId(userId);

		if (userName == null) {
			notificationQueueEntryImpl.setUserName("");
		}
		else {
			notificationQueueEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationQueueEntryImpl.setCreateDate(null);
		}
		else {
			notificationQueueEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationQueueEntryImpl.setModifiedDate(null);
		}
		else {
			notificationQueueEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		notificationQueueEntryImpl.setNotificationTemplateId(
			notificationTemplateId);

		if (bcc == null) {
			notificationQueueEntryImpl.setBcc("");
		}
		else {
			notificationQueueEntryImpl.setBcc(bcc);
		}

		if (body == null) {
			notificationQueueEntryImpl.setBody("");
		}
		else {
			notificationQueueEntryImpl.setBody(body);
		}

		if (cc == null) {
			notificationQueueEntryImpl.setCc("");
		}
		else {
			notificationQueueEntryImpl.setCc(cc);
		}

		notificationQueueEntryImpl.setClassNameId(classNameId);
		notificationQueueEntryImpl.setClassPK(classPK);

		if (from == null) {
			notificationQueueEntryImpl.setFrom("");
		}
		else {
			notificationQueueEntryImpl.setFrom(from);
		}

		if (fromName == null) {
			notificationQueueEntryImpl.setFromName("");
		}
		else {
			notificationQueueEntryImpl.setFromName(fromName);
		}

		notificationQueueEntryImpl.setPriority(priority);

		if (sentDate == Long.MIN_VALUE) {
			notificationQueueEntryImpl.setSentDate(null);
		}
		else {
			notificationQueueEntryImpl.setSentDate(new Date(sentDate));
		}

		if (subject == null) {
			notificationQueueEntryImpl.setSubject("");
		}
		else {
			notificationQueueEntryImpl.setSubject(subject);
		}

		if (to == null) {
			notificationQueueEntryImpl.setTo("");
		}
		else {
			notificationQueueEntryImpl.setTo(to);
		}

		if (toName == null) {
			notificationQueueEntryImpl.setToName("");
		}
		else {
			notificationQueueEntryImpl.setToName(toName);
		}

		if (type == null) {
			notificationQueueEntryImpl.setType("");
		}
		else {
			notificationQueueEntryImpl.setType(type);
		}

		notificationQueueEntryImpl.setStatus(status);

		notificationQueueEntryImpl.resetOriginalValues();

		return notificationQueueEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		notificationQueueEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		notificationTemplateId = objectInput.readLong();
		bcc = objectInput.readUTF();
		body = (String)objectInput.readObject();
		cc = objectInput.readUTF();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		from = objectInput.readUTF();
		fromName = objectInput.readUTF();

		priority = objectInput.readDouble();
		sentDate = objectInput.readLong();
		subject = objectInput.readUTF();
		to = objectInput.readUTF();
		toName = objectInput.readUTF();
		type = objectInput.readUTF();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(notificationQueueEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(notificationTemplateId);

		if (bcc == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(bcc);
		}

		if (body == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(body);
		}

		if (cc == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cc);
		}

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (from == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(from);
		}

		if (fromName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fromName);
		}

		objectOutput.writeDouble(priority);
		objectOutput.writeLong(sentDate);

		if (subject == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(subject);
		}

		if (to == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(to);
		}

		if (toName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(toName);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long notificationQueueEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long notificationTemplateId;
	public String bcc;
	public String body;
	public String cc;
	public long classNameId;
	public long classPK;
	public String from;
	public String fromName;
	public double priority;
	public long sentDate;
	public String subject;
	public String to;
	public String toName;
	public String type;
	public int status;

}