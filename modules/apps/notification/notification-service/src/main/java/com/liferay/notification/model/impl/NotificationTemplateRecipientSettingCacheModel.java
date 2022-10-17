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

import com.liferay.notification.model.NotificationTemplateRecipientSetting;
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
 * The cache model class for representing NotificationTemplateRecipientSetting in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationTemplateRecipientSettingCacheModel
	implements CacheModel<NotificationTemplateRecipientSetting>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				NotificationTemplateRecipientSettingCacheModel)) {

			return false;
		}

		NotificationTemplateRecipientSettingCacheModel
			notificationTemplateRecipientSettingCacheModel =
				(NotificationTemplateRecipientSettingCacheModel)object;

		if ((notificationTemplateRecipientSettingId ==
				notificationTemplateRecipientSettingCacheModel.
					notificationTemplateRecipientSettingId) &&
			(mvccVersion ==
				notificationTemplateRecipientSettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationTemplateRecipientSettingId);

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
		sb.append(", notificationTemplateRecipientSettingId=");
		sb.append(notificationTemplateRecipientSettingId);
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
		sb.append(", notificationTemplateRecipientId=");
		sb.append(notificationTemplateRecipientId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationTemplateRecipientSetting toEntityModel() {
		NotificationTemplateRecipientSettingImpl
			notificationTemplateRecipientSettingImpl =
				new NotificationTemplateRecipientSettingImpl();

		notificationTemplateRecipientSettingImpl.setMvccVersion(mvccVersion);
		notificationTemplateRecipientSettingImpl.
			setNotificationTemplateRecipientSettingId(
				notificationTemplateRecipientSettingId);
		notificationTemplateRecipientSettingImpl.setCompanyId(companyId);
		notificationTemplateRecipientSettingImpl.setUserId(userId);

		if (userName == null) {
			notificationTemplateRecipientSettingImpl.setUserName("");
		}
		else {
			notificationTemplateRecipientSettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationTemplateRecipientSettingImpl.setCreateDate(null);
		}
		else {
			notificationTemplateRecipientSettingImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationTemplateRecipientSettingImpl.setModifiedDate(null);
		}
		else {
			notificationTemplateRecipientSettingImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		notificationTemplateRecipientSettingImpl.
			setNotificationTemplateRecipientId(notificationTemplateRecipientId);

		if (name == null) {
			notificationTemplateRecipientSettingImpl.setName("");
		}
		else {
			notificationTemplateRecipientSettingImpl.setName(name);
		}

		if (value == null) {
			notificationTemplateRecipientSettingImpl.setValue("");
		}
		else {
			notificationTemplateRecipientSettingImpl.setValue(value);
		}

		notificationTemplateRecipientSettingImpl.resetOriginalValues();

		return notificationTemplateRecipientSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		notificationTemplateRecipientSettingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		notificationTemplateRecipientId = objectInput.readLong();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(notificationTemplateRecipientSettingId);

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

		objectOutput.writeLong(notificationTemplateRecipientId);

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
	public long notificationTemplateRecipientSettingId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long notificationTemplateRecipientId;
	public String name;
	public String value;

}