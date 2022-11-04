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

import com.liferay.notification.model.NotificationRecipient;
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
 * The cache model class for representing NotificationRecipient in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationRecipientCacheModel
	implements CacheModel<NotificationRecipient>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationRecipientCacheModel)) {
			return false;
		}

		NotificationRecipientCacheModel notificationRecipientCacheModel =
			(NotificationRecipientCacheModel)object;

		if ((notificationRecipientId ==
				notificationRecipientCacheModel.notificationRecipientId) &&
			(mvccVersion == notificationRecipientCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationRecipientId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", notificationRecipientId=");
		sb.append(notificationRecipientId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationRecipient toEntityModel() {
		NotificationRecipientImpl notificationRecipientImpl =
			new NotificationRecipientImpl();

		notificationRecipientImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			notificationRecipientImpl.setUuid("");
		}
		else {
			notificationRecipientImpl.setUuid(uuid);
		}

		notificationRecipientImpl.setNotificationRecipientId(
			notificationRecipientId);
		notificationRecipientImpl.setCompanyId(companyId);
		notificationRecipientImpl.setUserId(userId);

		if (userName == null) {
			notificationRecipientImpl.setUserName("");
		}
		else {
			notificationRecipientImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationRecipientImpl.setCreateDate(null);
		}
		else {
			notificationRecipientImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationRecipientImpl.setModifiedDate(null);
		}
		else {
			notificationRecipientImpl.setModifiedDate(new Date(modifiedDate));
		}

		notificationRecipientImpl.setClassNameId(classNameId);
		notificationRecipientImpl.setClassPK(classPK);

		notificationRecipientImpl.resetOriginalValues();

		return notificationRecipientImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		notificationRecipientId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
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

		objectOutput.writeLong(notificationRecipientId);

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
	}

	public long mvccVersion;
	public String uuid;
	public long notificationRecipientId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;

}