/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.model.impl;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemFileEntry;
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
 * The cache model class for representing CommerceVirtualOrderItemFileEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceVirtualOrderItemFileEntryCacheModel
	implements CacheModel<CommerceVirtualOrderItemFileEntry>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceVirtualOrderItemFileEntryCacheModel)) {
			return false;
		}

		CommerceVirtualOrderItemFileEntryCacheModel
			commerceVirtualOrderItemFileEntryCacheModel =
				(CommerceVirtualOrderItemFileEntryCacheModel)object;

		if ((commerceVirtualOrderItemFileEntryId ==
				commerceVirtualOrderItemFileEntryCacheModel.
					commerceVirtualOrderItemFileEntryId) &&
			(mvccVersion ==
				commerceVirtualOrderItemFileEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceVirtualOrderItemFileEntryId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", commerceVirtualOrderItemFileEntryId=");
		sb.append(commerceVirtualOrderItemFileEntryId);
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
		sb.append(", commerceVirtualOrderItemId=");
		sb.append(commerceVirtualOrderItemId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", url=");
		sb.append(url);
		sb.append(", usages=");
		sb.append(usages);
		sb.append(", version=");
		sb.append(version);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceVirtualOrderItemFileEntry toEntityModel() {
		CommerceVirtualOrderItemFileEntryImpl
			commerceVirtualOrderItemFileEntryImpl =
				new CommerceVirtualOrderItemFileEntryImpl();

		commerceVirtualOrderItemFileEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceVirtualOrderItemFileEntryImpl.setUuid("");
		}
		else {
			commerceVirtualOrderItemFileEntryImpl.setUuid(uuid);
		}

		commerceVirtualOrderItemFileEntryImpl.
			setCommerceVirtualOrderItemFileEntryId(
				commerceVirtualOrderItemFileEntryId);
		commerceVirtualOrderItemFileEntryImpl.setGroupId(groupId);
		commerceVirtualOrderItemFileEntryImpl.setCompanyId(companyId);
		commerceVirtualOrderItemFileEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceVirtualOrderItemFileEntryImpl.setUserName("");
		}
		else {
			commerceVirtualOrderItemFileEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceVirtualOrderItemFileEntryImpl.setCreateDate(null);
		}
		else {
			commerceVirtualOrderItemFileEntryImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceVirtualOrderItemFileEntryImpl.setModifiedDate(null);
		}
		else {
			commerceVirtualOrderItemFileEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceVirtualOrderItemFileEntryImpl.setCommerceVirtualOrderItemId(
			commerceVirtualOrderItemId);
		commerceVirtualOrderItemFileEntryImpl.setFileEntryId(fileEntryId);

		if (url == null) {
			commerceVirtualOrderItemFileEntryImpl.setUrl("");
		}
		else {
			commerceVirtualOrderItemFileEntryImpl.setUrl(url);
		}

		commerceVirtualOrderItemFileEntryImpl.setUsages(usages);

		if (version == null) {
			commerceVirtualOrderItemFileEntryImpl.setVersion("");
		}
		else {
			commerceVirtualOrderItemFileEntryImpl.setVersion(version);
		}

		commerceVirtualOrderItemFileEntryImpl.resetOriginalValues();

		return commerceVirtualOrderItemFileEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		commerceVirtualOrderItemFileEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceVirtualOrderItemId = objectInput.readLong();

		fileEntryId = objectInput.readLong();
		url = objectInput.readUTF();

		usages = objectInput.readInt();
		version = objectInput.readUTF();
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

		objectOutput.writeLong(commerceVirtualOrderItemFileEntryId);

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

		objectOutput.writeLong(commerceVirtualOrderItemId);

		objectOutput.writeLong(fileEntryId);

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}

		objectOutput.writeInt(usages);

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long commerceVirtualOrderItemFileEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceVirtualOrderItemId;
	public long fileEntryId;
	public String url;
	public int usages;
	public String version;

}