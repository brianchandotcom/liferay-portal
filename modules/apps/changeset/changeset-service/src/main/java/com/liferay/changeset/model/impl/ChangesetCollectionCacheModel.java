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

package com.liferay.changeset.model.impl;

import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ChangesetCollection in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ChangesetCollectionCacheModel
	implements CacheModel<ChangesetCollection>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangesetCollectionCacheModel)) {
			return false;
		}

		ChangesetCollectionCacheModel changesetCollectionCacheModel =
			(ChangesetCollectionCacheModel)obj;

		if (changesetCollectionId ==
				changesetCollectionCacheModel.changesetCollectionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, changesetCollectionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{changesetCollectionId=");
		sb.append(changesetCollectionId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ChangesetCollection toEntityModel() {
		ChangesetCollectionImpl changesetCollectionImpl =
			new ChangesetCollectionImpl();

		changesetCollectionImpl.setChangesetCollectionId(changesetCollectionId);
		changesetCollectionImpl.setGroupId(groupId);
		changesetCollectionImpl.setCompanyId(companyId);
		changesetCollectionImpl.setUserId(userId);

		if (userName == null) {
			changesetCollectionImpl.setUserName("");
		}
		else {
			changesetCollectionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			changesetCollectionImpl.setCreateDate(null);
		}
		else {
			changesetCollectionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			changesetCollectionImpl.setModifiedDate(null);
		}
		else {
			changesetCollectionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			changesetCollectionImpl.setName("");
		}
		else {
			changesetCollectionImpl.setName(name);
		}

		if (description == null) {
			changesetCollectionImpl.setDescription("");
		}
		else {
			changesetCollectionImpl.setDescription(description);
		}

		changesetCollectionImpl.resetOriginalValues();

		return changesetCollectionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		changesetCollectionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(changesetCollectionId);

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
	}

	public long changesetCollectionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;

}