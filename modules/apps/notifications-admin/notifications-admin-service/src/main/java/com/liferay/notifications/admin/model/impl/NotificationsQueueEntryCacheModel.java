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

package com.liferay.notifications.admin.model.impl;

import com.liferay.notifications.admin.model.NotificationsQueueEntry;
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
 * The cache model class for representing NotificationsQueueEntry in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationsQueueEntryCacheModel
	implements CacheModel<NotificationsQueueEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationsQueueEntryCacheModel)) {
			return false;
		}

		NotificationsQueueEntryCacheModel notificationsQueueEntryCacheModel =
			(NotificationsQueueEntryCacheModel)object;

		if ((notificationsQueueEntryId ==
				notificationsQueueEntryCacheModel.notificationsQueueEntryId) &&
			(mvccVersion == notificationsQueueEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationsQueueEntryId);

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
		sb.append(", notificationsQueueEntryId=");
		sb.append(notificationsQueueEntryId);
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
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", notificationsTemplateId=");
		sb.append(notificationsTemplateId);
		sb.append(", from=");
		sb.append(from);
		sb.append(", fromName=");
		sb.append(fromName);
		sb.append(", to=");
		sb.append(to);
		sb.append(", toName=");
		sb.append(toName);
		sb.append(", cc=");
		sb.append(cc);
		sb.append(", bcc=");
		sb.append(bcc);
		sb.append(", subject=");
		sb.append(subject);
		sb.append(", body=");
		sb.append(body);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", sent=");
		sb.append(sent);
		sb.append(", sentDate=");
		sb.append(sentDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationsQueueEntry toEntityModel() {
		NotificationsQueueEntryImpl notificationsQueueEntryImpl =
			new NotificationsQueueEntryImpl();

		notificationsQueueEntryImpl.setMvccVersion(mvccVersion);
		notificationsQueueEntryImpl.setNotificationsQueueEntryId(
			notificationsQueueEntryId);
		notificationsQueueEntryImpl.setGroupId(groupId);
		notificationsQueueEntryImpl.setCompanyId(companyId);
		notificationsQueueEntryImpl.setUserId(userId);

		if (userName == null) {
			notificationsQueueEntryImpl.setUserName("");
		}
		else {
			notificationsQueueEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationsQueueEntryImpl.setCreateDate(null);
		}
		else {
			notificationsQueueEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationsQueueEntryImpl.setModifiedDate(null);
		}
		else {
			notificationsQueueEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		notificationsQueueEntryImpl.setClassNameId(classNameId);
		notificationsQueueEntryImpl.setClassPK(classPK);
		notificationsQueueEntryImpl.setNotificationsTemplateId(
			notificationsTemplateId);

		if (from == null) {
			notificationsQueueEntryImpl.setFrom("");
		}
		else {
			notificationsQueueEntryImpl.setFrom(from);
		}

		if (fromName == null) {
			notificationsQueueEntryImpl.setFromName("");
		}
		else {
			notificationsQueueEntryImpl.setFromName(fromName);
		}

		if (to == null) {
			notificationsQueueEntryImpl.setTo("");
		}
		else {
			notificationsQueueEntryImpl.setTo(to);
		}

		if (toName == null) {
			notificationsQueueEntryImpl.setToName("");
		}
		else {
			notificationsQueueEntryImpl.setToName(toName);
		}

		if (cc == null) {
			notificationsQueueEntryImpl.setCc("");
		}
		else {
			notificationsQueueEntryImpl.setCc(cc);
		}

		if (bcc == null) {
			notificationsQueueEntryImpl.setBcc("");
		}
		else {
			notificationsQueueEntryImpl.setBcc(bcc);
		}

		if (subject == null) {
			notificationsQueueEntryImpl.setSubject("");
		}
		else {
			notificationsQueueEntryImpl.setSubject(subject);
		}

		if (body == null) {
			notificationsQueueEntryImpl.setBody("");
		}
		else {
			notificationsQueueEntryImpl.setBody(body);
		}

		notificationsQueueEntryImpl.setPriority(priority);
		notificationsQueueEntryImpl.setSent(sent);

		if (sentDate == Long.MIN_VALUE) {
			notificationsQueueEntryImpl.setSentDate(null);
		}
		else {
			notificationsQueueEntryImpl.setSentDate(new Date(sentDate));
		}

		notificationsQueueEntryImpl.resetOriginalValues();

		return notificationsQueueEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		notificationsQueueEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		notificationsTemplateId = objectInput.readLong();
		from = objectInput.readUTF();
		fromName = objectInput.readUTF();
		to = objectInput.readUTF();
		toName = objectInput.readUTF();
		cc = objectInput.readUTF();
		bcc = objectInput.readUTF();
		subject = objectInput.readUTF();
		body = objectInput.readUTF();

		priority = objectInput.readDouble();

		sent = objectInput.readBoolean();
		sentDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(notificationsQueueEntryId);

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
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(notificationsTemplateId);

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

		if (cc == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cc);
		}

		if (bcc == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(bcc);
		}

		if (subject == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(subject);
		}

		if (body == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(body);
		}

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(sent);
		objectOutput.writeLong(sentDate);
	}

	public long mvccVersion;
	public long notificationsQueueEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long notificationsTemplateId;
	public String from;
	public String fromName;
	public String to;
	public String toName;
	public String cc;
	public String bcc;
	public String subject;
	public String body;
	public double priority;
	public boolean sent;
	public long sentDate;

}