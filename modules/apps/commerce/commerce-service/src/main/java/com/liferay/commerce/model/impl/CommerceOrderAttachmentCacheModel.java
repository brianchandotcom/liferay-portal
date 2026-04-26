/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceOrderAttachment;
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
 * The cache model class for representing CommerceOrderAttachment in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderAttachmentCacheModel
	implements CacheModel<CommerceOrderAttachment>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceOrderAttachmentCacheModel)) {
			return false;
		}

		CommerceOrderAttachmentCacheModel commerceOrderAttachmentCacheModel =
			(CommerceOrderAttachmentCacheModel)object;

		if ((commerceOrderAttachmentId ==
				commerceOrderAttachmentCacheModel.commerceOrderAttachmentId) &&
			(mvccVersion == commerceOrderAttachmentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceOrderAttachmentId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceOrderAttachmentId=");
		sb.append(commerceOrderAttachmentId);
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
		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", restricted=");
		sb.append(restricted);
		sb.append(", title=");
		sb.append(title);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceOrderAttachment toEntityModel() {
		CommerceOrderAttachmentImpl commerceOrderAttachmentImpl =
			new CommerceOrderAttachmentImpl();

		commerceOrderAttachmentImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceOrderAttachmentImpl.setUuid("");
		}
		else {
			commerceOrderAttachmentImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commerceOrderAttachmentImpl.setExternalReferenceCode("");
		}
		else {
			commerceOrderAttachmentImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceOrderAttachmentImpl.setCommerceOrderAttachmentId(
			commerceOrderAttachmentId);
		commerceOrderAttachmentImpl.setGroupId(groupId);
		commerceOrderAttachmentImpl.setCompanyId(companyId);
		commerceOrderAttachmentImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderAttachmentImpl.setUserName("");
		}
		else {
			commerceOrderAttachmentImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderAttachmentImpl.setCreateDate(null);
		}
		else {
			commerceOrderAttachmentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderAttachmentImpl.setModifiedDate(null);
		}
		else {
			commerceOrderAttachmentImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceOrderAttachmentImpl.setCommerceOrderId(commerceOrderId);
		commerceOrderAttachmentImpl.setFileEntryId(fileEntryId);
		commerceOrderAttachmentImpl.setPriority(priority);
		commerceOrderAttachmentImpl.setRestricted(restricted);

		if (title == null) {
			commerceOrderAttachmentImpl.setTitle("");
		}
		else {
			commerceOrderAttachmentImpl.setTitle(title);
		}

		if (type == null) {
			commerceOrderAttachmentImpl.setType("");
		}
		else {
			commerceOrderAttachmentImpl.setType(type);
		}

		commerceOrderAttachmentImpl.resetOriginalValues();

		return commerceOrderAttachmentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commerceOrderAttachmentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceOrderId = objectInput.readLong();

		fileEntryId = objectInput.readLong();

		priority = objectInput.readDouble();

		restricted = objectInput.readBoolean();
		title = objectInput.readUTF();
		type = objectInput.readUTF();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceOrderAttachmentId);

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

		objectOutput.writeLong(commerceOrderId);

		objectOutput.writeLong(fileEntryId);

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(restricted);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long commerceOrderAttachmentId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceOrderId;
	public long fileEntryId;
	public double priority;
	public boolean restricted;
	public String title;
	public String type;

}
// LIFERAY-SERVICE-BUILDER-HASH:94374581