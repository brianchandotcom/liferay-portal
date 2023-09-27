/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model.impl;

import com.liferay.commerce.notification.model.CommerceNotificationAttachment;
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
 * The cache model class for representing CommerceNotificationAttachment in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceNotificationAttachmentCacheModel
	implements CacheModel<CommerceNotificationAttachment>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceNotificationAttachmentCacheModel)) {
			return false;
		}

		CommerceNotificationAttachmentCacheModel
			commerceNotificationAttachmentCacheModel =
				(CommerceNotificationAttachmentCacheModel)object;

		if ((commerceNotificationAttachmentId ==
				commerceNotificationAttachmentCacheModel.
					commerceNotificationAttachmentId) &&
			(mvccVersion ==
				commerceNotificationAttachmentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceNotificationAttachmentId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", commerceNotificationAttachmentId=");
		sb.append(commerceNotificationAttachmentId);
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
		sb.append(", commerceNotificationQueueEntryId=");
		sb.append(commerceNotificationQueueEntryId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", deleteOnSend=");
		sb.append(deleteOnSend);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceNotificationAttachment toEntityModel() {
		CommerceNotificationAttachmentImpl commerceNotificationAttachmentImpl =
			new CommerceNotificationAttachmentImpl();

		commerceNotificationAttachmentImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceNotificationAttachmentImpl.setUuid("");
		}
		else {
			commerceNotificationAttachmentImpl.setUuid(uuid);
		}

		commerceNotificationAttachmentImpl.setCommerceNotificationAttachmentId(
			commerceNotificationAttachmentId);
		commerceNotificationAttachmentImpl.setGroupId(groupId);
		commerceNotificationAttachmentImpl.setCompanyId(companyId);
		commerceNotificationAttachmentImpl.setUserId(userId);

		if (userName == null) {
			commerceNotificationAttachmentImpl.setUserName("");
		}
		else {
			commerceNotificationAttachmentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceNotificationAttachmentImpl.setCreateDate(null);
		}
		else {
			commerceNotificationAttachmentImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceNotificationAttachmentImpl.setModifiedDate(null);
		}
		else {
			commerceNotificationAttachmentImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceNotificationAttachmentImpl.setCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);
		commerceNotificationAttachmentImpl.setFileEntryId(fileEntryId);
		commerceNotificationAttachmentImpl.setDeleteOnSend(deleteOnSend);

		commerceNotificationAttachmentImpl.resetOriginalValues();

		return commerceNotificationAttachmentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		commerceNotificationAttachmentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceNotificationQueueEntryId = objectInput.readLong();

		fileEntryId = objectInput.readLong();

		deleteOnSend = objectInput.readBoolean();
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

		objectOutput.writeLong(commerceNotificationAttachmentId);

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

		objectOutput.writeLong(commerceNotificationQueueEntryId);

		objectOutput.writeLong(fileEntryId);

		objectOutput.writeBoolean(deleteOnSend);
	}

	public long mvccVersion;
	public String uuid;
	public long commerceNotificationAttachmentId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceNotificationQueueEntryId;
	public long fileEntryId;
	public boolean deleteOnSend;

}