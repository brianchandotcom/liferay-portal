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

import com.liferay.notifications.admin.model.NotificationsTemplate;
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
 * The cache model class for representing NotificationsTemplate in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationsTemplateCacheModel
	implements CacheModel<NotificationsTemplate>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationsTemplateCacheModel)) {
			return false;
		}

		NotificationsTemplateCacheModel notificationsTemplateCacheModel =
			(NotificationsTemplateCacheModel)object;

		if ((notificationsTemplateId ==
				notificationsTemplateCacheModel.notificationsTemplateId) &&
			(mvccVersion == notificationsTemplateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationsTemplateId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", notificationsTemplateId=");
		sb.append(notificationsTemplateId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationsTemplate toEntityModel() {
		NotificationsTemplateImpl notificationsTemplateImpl =
			new NotificationsTemplateImpl();

		notificationsTemplateImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			notificationsTemplateImpl.setUuid("");
		}
		else {
			notificationsTemplateImpl.setUuid(uuid);
		}

		notificationsTemplateImpl.setNotificationsTemplateId(
			notificationsTemplateId);
		notificationsTemplateImpl.setCompanyId(companyId);
		notificationsTemplateImpl.setUserId(userId);

		if (userName == null) {
			notificationsTemplateImpl.setUserName("");
		}
		else {
			notificationsTemplateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationsTemplateImpl.setCreateDate(null);
		}
		else {
			notificationsTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationsTemplateImpl.setModifiedDate(null);
		}
		else {
			notificationsTemplateImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			notificationsTemplateImpl.setName("");
		}
		else {
			notificationsTemplateImpl.setName(name);
		}

		notificationsTemplateImpl.resetOriginalValues();

		return notificationsTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		notificationsTemplateId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
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

		objectOutput.writeLong(notificationsTemplateId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long notificationsTemplateId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;

}