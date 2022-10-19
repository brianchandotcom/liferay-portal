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

import com.liferay.notification.model.NotificationRecipientSetting;
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
 * The cache model class for representing NotificationRecipientSetting in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationRecipientSettingCacheModel
	implements CacheModel<NotificationRecipientSetting>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationRecipientSettingCacheModel)) {
			return false;
		}

		NotificationRecipientSettingCacheModel
			notificationRecipientSettingCacheModel =
				(NotificationRecipientSettingCacheModel)object;

		if ((notificationRecipientSettingId ==
				notificationRecipientSettingCacheModel.
					notificationRecipientSettingId) &&
			(mvccVersion ==
				notificationRecipientSettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationRecipientSettingId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", notificationRecipientSettingId=");
		sb.append(notificationRecipientSettingId);
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
		sb.append(", notificationRecipientId=");
		sb.append(notificationRecipientId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationRecipientSetting toEntityModel() {
		NotificationRecipientSettingImpl notificationRecipientSettingImpl =
			new NotificationRecipientSettingImpl();

		notificationRecipientSettingImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			notificationRecipientSettingImpl.setUuid("");
		}
		else {
			notificationRecipientSettingImpl.setUuid(uuid);
		}

		notificationRecipientSettingImpl.setNotificationRecipientSettingId(
			notificationRecipientSettingId);
		notificationRecipientSettingImpl.setCompanyId(companyId);
		notificationRecipientSettingImpl.setUserId(userId);

		if (userName == null) {
			notificationRecipientSettingImpl.setUserName("");
		}
		else {
			notificationRecipientSettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationRecipientSettingImpl.setCreateDate(null);
		}
		else {
			notificationRecipientSettingImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationRecipientSettingImpl.setModifiedDate(null);
		}
		else {
			notificationRecipientSettingImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		notificationRecipientSettingImpl.setNotificationRecipientId(
			notificationRecipientId);

		if (name == null) {
			notificationRecipientSettingImpl.setName("");
		}
		else {
			notificationRecipientSettingImpl.setName(name);
		}

		if (value == null) {
			notificationRecipientSettingImpl.setValue("");
		}
		else {
			notificationRecipientSettingImpl.setValue(value);
		}

		notificationRecipientSettingImpl.resetOriginalValues();

		return notificationRecipientSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		notificationRecipientSettingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		notificationRecipientId = objectInput.readLong();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(notificationRecipientSettingId);

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

		objectOutput.writeLong(notificationRecipientId);

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
	public String uuid;
	public long notificationRecipientSettingId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long notificationRecipientId;
	public String name;
	public String value;

}