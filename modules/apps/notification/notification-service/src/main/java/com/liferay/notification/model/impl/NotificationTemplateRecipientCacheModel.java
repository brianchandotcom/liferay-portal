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

import com.liferay.notification.model.NotificationTemplateRecipient;
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
 * The cache model class for representing NotificationTemplateRecipient in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationTemplateRecipientCacheModel
	implements CacheModel<NotificationTemplateRecipient>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationTemplateRecipientCacheModel)) {
			return false;
		}

		NotificationTemplateRecipientCacheModel
			notificationTemplateRecipientCacheModel =
				(NotificationTemplateRecipientCacheModel)object;

		if ((notificationTemplateRecipientId ==
				notificationTemplateRecipientCacheModel.
					notificationTemplateRecipientId) &&
			(mvccVersion ==
				notificationTemplateRecipientCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationTemplateRecipientId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", notificationTemplateRecipientId=");
		sb.append(notificationTemplateRecipientId);
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
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationTemplateRecipient toEntityModel() {
		NotificationTemplateRecipientImpl notificationTemplateRecipientImpl =
			new NotificationTemplateRecipientImpl();

		notificationTemplateRecipientImpl.setMvccVersion(mvccVersion);
		notificationTemplateRecipientImpl.setNotificationTemplateRecipientId(
			notificationTemplateRecipientId);
		notificationTemplateRecipientImpl.setCompanyId(companyId);
		notificationTemplateRecipientImpl.setUserId(userId);

		if (userName == null) {
			notificationTemplateRecipientImpl.setUserName("");
		}
		else {
			notificationTemplateRecipientImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationTemplateRecipientImpl.setCreateDate(null);
		}
		else {
			notificationTemplateRecipientImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationTemplateRecipientImpl.setModifiedDate(null);
		}
		else {
			notificationTemplateRecipientImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		notificationTemplateRecipientImpl.setNotificationTemplateId(
			notificationTemplateId);

		if (type == null) {
			notificationTemplateRecipientImpl.setType("");
		}
		else {
			notificationTemplateRecipientImpl.setType(type);
		}

		notificationTemplateRecipientImpl.resetOriginalValues();

		return notificationTemplateRecipientImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		notificationTemplateRecipientId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		notificationTemplateId = objectInput.readLong();
		type = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(notificationTemplateRecipientId);

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

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public long notificationTemplateRecipientId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long notificationTemplateId;
	public String type;

}