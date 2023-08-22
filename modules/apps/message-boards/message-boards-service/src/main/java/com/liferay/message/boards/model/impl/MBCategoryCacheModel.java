/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model.impl;

import com.liferay.message.boards.model.MBCategory;
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
 * The cache model class for representing MBCategory in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBCategoryCacheModel
	implements CacheModel<MBCategory>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MBCategoryCacheModel)) {
			return false;
		}

		MBCategoryCacheModel mbCategoryCacheModel =
			(MBCategoryCacheModel)object;

		if ((categoryId == mbCategoryCacheModel.categoryId) &&
			(mvccVersion == mbCategoryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, categoryId);

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", categoryId=");
		sb.append(categoryId);
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
		sb.append(", parentCategoryId=");
		sb.append(parentCategoryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", displayStyle=");
		sb.append(displayStyle);
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MBCategory toEntityModel() {
		MBCategoryImpl mbCategoryImpl = new MBCategoryImpl();

		mbCategoryImpl.setMvccVersion(mvccVersion);
		mbCategoryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			mbCategoryImpl.setUuid("");
		}
		else {
			mbCategoryImpl.setUuid(uuid);
		}

		mbCategoryImpl.setCategoryId(categoryId);
		mbCategoryImpl.setGroupId(groupId);
		mbCategoryImpl.setCompanyId(companyId);
		mbCategoryImpl.setUserId(userId);

		if (userName == null) {
			mbCategoryImpl.setUserName("");
		}
		else {
			mbCategoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbCategoryImpl.setCreateDate(null);
		}
		else {
			mbCategoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbCategoryImpl.setModifiedDate(null);
		}
		else {
			mbCategoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbCategoryImpl.setParentCategoryId(parentCategoryId);

		if (name == null) {
			mbCategoryImpl.setName("");
		}
		else {
			mbCategoryImpl.setName(name);
		}

		if (description == null) {
			mbCategoryImpl.setDescription("");
		}
		else {
			mbCategoryImpl.setDescription(description);
		}

		if (displayStyle == null) {
			mbCategoryImpl.setDisplayStyle("");
		}
		else {
			mbCategoryImpl.setDisplayStyle(displayStyle);
		}

		if (friendlyURL == null) {
			mbCategoryImpl.setFriendlyURL("");
		}
		else {
			mbCategoryImpl.setFriendlyURL(friendlyURL);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			mbCategoryImpl.setLastPublishDate(null);
		}
		else {
			mbCategoryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		mbCategoryImpl.setStatus(status);
		mbCategoryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			mbCategoryImpl.setStatusByUserName("");
		}
		else {
			mbCategoryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			mbCategoryImpl.setStatusDate(null);
		}
		else {
			mbCategoryImpl.setStatusDate(new Date(statusDate));
		}

		mbCategoryImpl.resetOriginalValues();

		return mbCategoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		categoryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentCategoryId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		displayStyle = objectInput.readUTF();
		friendlyURL = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(categoryId);

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

		objectOutput.writeLong(parentCategoryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (displayStyle == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(displayStyle);
		}

		if (friendlyURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(friendlyURL);
		}

		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long categoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentCategoryId;
	public String name;
	public String description;
	public String displayStyle;
	public String friendlyURL;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}