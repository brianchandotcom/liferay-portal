/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		StringBundler sb = new StringBundler(33);

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
		sb.append(", body=");
		sb.append(body);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", sentDate=");
		sb.append(sentDate);
		sb.append(", subject=");
		sb.append(subject);
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

		if (body == null) {
			notificationQueueEntryImpl.setBody("");
		}
		else {
			notificationQueueEntryImpl.setBody(body);
		}

		notificationQueueEntryImpl.setClassNameId(classNameId);
		notificationQueueEntryImpl.setClassPK(classPK);
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
		body = (String)objectInput.readObject();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		priority = objectInput.readDouble();
		sentDate = objectInput.readLong();
		subject = (String)objectInput.readObject();
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

		if (body == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(body);
		}

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeDouble(priority);
		objectOutput.writeLong(sentDate);

		if (subject == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(subject);
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
	public String body;
	public long classNameId;
	public long classPK;
	public double priority;
	public long sentDate;
	public String subject;
	public String type;
	public int status;

}