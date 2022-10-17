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

import com.liferay.notification.model.NotificationQueueEntryRecipient;
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
 * The cache model class for representing NotificationQueueEntryRecipient in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationQueueEntryRecipientCacheModel
	implements CacheModel<NotificationQueueEntryRecipient>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationQueueEntryRecipientCacheModel)) {
			return false;
		}

		NotificationQueueEntryRecipientCacheModel
			notificationQueueEntryRecipientCacheModel =
				(NotificationQueueEntryRecipientCacheModel)object;

		if ((notificationQueueEntryRecipientId ==
				notificationQueueEntryRecipientCacheModel.
					notificationQueueEntryRecipientId) &&
			(mvccVersion ==
				notificationQueueEntryRecipientCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationQueueEntryRecipientId);

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
		sb.append(", notificationQueueEntryRecipientId=");
		sb.append(notificationQueueEntryRecipientId);
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
		sb.append(", notificationQueueEntryId=");
		sb.append(notificationQueueEntryId);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationQueueEntryRecipient toEntityModel() {
		NotificationQueueEntryRecipientImpl
			notificationQueueEntryRecipientImpl =
				new NotificationQueueEntryRecipientImpl();

		notificationQueueEntryRecipientImpl.setMvccVersion(mvccVersion);
		notificationQueueEntryRecipientImpl.
			setNotificationQueueEntryRecipientId(
				notificationQueueEntryRecipientId);
		notificationQueueEntryRecipientImpl.setCompanyId(companyId);
		notificationQueueEntryRecipientImpl.setUserId(userId);

		if (userName == null) {
			notificationQueueEntryRecipientImpl.setUserName("");
		}
		else {
			notificationQueueEntryRecipientImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationQueueEntryRecipientImpl.setCreateDate(null);
		}
		else {
			notificationQueueEntryRecipientImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationQueueEntryRecipientImpl.setModifiedDate(null);
		}
		else {
			notificationQueueEntryRecipientImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		notificationQueueEntryRecipientImpl.setNotificationQueueEntryId(
			notificationQueueEntryId);

		if (type == null) {
			notificationQueueEntryRecipientImpl.setType("");
		}
		else {
			notificationQueueEntryRecipientImpl.setType(type);
		}

		notificationQueueEntryRecipientImpl.resetOriginalValues();

		return notificationQueueEntryRecipientImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		notificationQueueEntryRecipientId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		notificationQueueEntryId = objectInput.readLong();
		type = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(notificationQueueEntryRecipientId);

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

		objectOutput.writeLong(notificationQueueEntryId);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public long notificationQueueEntryRecipientId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long notificationQueueEntryId;
	public String type;

}