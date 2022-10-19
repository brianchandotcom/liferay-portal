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

import com.liferay.notification.model.NotificationQueueEntryRecipientSetting;
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
 * The cache model class for representing NotificationQueueEntryRecipientSetting in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationQueueEntryRecipientSettingCacheModel
	implements CacheModel<NotificationQueueEntryRecipientSetting>,
			   Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				NotificationQueueEntryRecipientSettingCacheModel)) {

			return false;
		}

		NotificationQueueEntryRecipientSettingCacheModel
			notificationQueueEntryRecipientSettingCacheModel =
				(NotificationQueueEntryRecipientSettingCacheModel)object;

		if ((notificationQueueEntryRecipientSettingId ==
				notificationQueueEntryRecipientSettingCacheModel.
					notificationQueueEntryRecipientSettingId) &&
			(mvccVersion ==
				notificationQueueEntryRecipientSettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(
			0, notificationQueueEntryRecipientSettingId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", notificationQueueEntryRecipientSettingId=");
		sb.append(notificationQueueEntryRecipientSettingId);
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
		sb.append(", notificationQueueEntryRecipientId=");
		sb.append(notificationQueueEntryRecipientId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationQueueEntryRecipientSetting toEntityModel() {
		NotificationQueueEntryRecipientSettingImpl
			notificationQueueEntryRecipientSettingImpl =
				new NotificationQueueEntryRecipientSettingImpl();

		notificationQueueEntryRecipientSettingImpl.setMvccVersion(mvccVersion);
		notificationQueueEntryRecipientSettingImpl.
			setNotificationQueueEntryRecipientSettingId(
				notificationQueueEntryRecipientSettingId);
		notificationQueueEntryRecipientSettingImpl.setCompanyId(companyId);
		notificationQueueEntryRecipientSettingImpl.setUserId(userId);

		if (userName == null) {
			notificationQueueEntryRecipientSettingImpl.setUserName("");
		}
		else {
			notificationQueueEntryRecipientSettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationQueueEntryRecipientSettingImpl.setCreateDate(null);
		}
		else {
			notificationQueueEntryRecipientSettingImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationQueueEntryRecipientSettingImpl.setModifiedDate(null);
		}
		else {
			notificationQueueEntryRecipientSettingImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		notificationQueueEntryRecipientSettingImpl.
			setNotificationQueueEntryRecipientId(
				notificationQueueEntryRecipientId);

		if (name == null) {
			notificationQueueEntryRecipientSettingImpl.setName("");
		}
		else {
			notificationQueueEntryRecipientSettingImpl.setName(name);
		}

		if (value == null) {
			notificationQueueEntryRecipientSettingImpl.setValue("");
		}
		else {
			notificationQueueEntryRecipientSettingImpl.setValue(value);
		}

		notificationQueueEntryRecipientSettingImpl.resetOriginalValues();

		return notificationQueueEntryRecipientSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		notificationQueueEntryRecipientSettingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		notificationQueueEntryRecipientId = objectInput.readLong();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(notificationQueueEntryRecipientSettingId);

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

		objectOutput.writeLong(notificationQueueEntryRecipientId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long mvccVersion;
	public long notificationQueueEntryRecipientSettingId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long notificationQueueEntryRecipientId;
	public String name;
	public String value;

}