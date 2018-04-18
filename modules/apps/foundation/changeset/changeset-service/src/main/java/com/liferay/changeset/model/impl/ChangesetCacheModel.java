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

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.model.Changeset;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Changeset in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Changeset
 * @generated
 */
@ProviderType
public class ChangesetCacheModel implements CacheModel<Changeset>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangesetCacheModel)) {
			return false;
		}

		ChangesetCacheModel changesetCacheModel = (ChangesetCacheModel)obj;

		if (changesetId == changesetCacheModel.changesetId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, changesetId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{changesetId=");
		sb.append(changesetId);
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
	public Changeset toEntityModel() {
		ChangesetImpl changesetImpl = new ChangesetImpl();

		changesetImpl.setChangesetId(changesetId);
		changesetImpl.setGroupId(groupId);
		changesetImpl.setCompanyId(companyId);
		changesetImpl.setUserId(userId);

		if (userName == null) {
			changesetImpl.setUserName("");
		}
		else {
			changesetImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			changesetImpl.setCreateDate(null);
		}
		else {
			changesetImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			changesetImpl.setModifiedDate(null);
		}
		else {
			changesetImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			changesetImpl.setName("");
		}
		else {
			changesetImpl.setName(name);
		}

		if (description == null) {
			changesetImpl.setDescription("");
		}
		else {
			changesetImpl.setDescription(description);
		}

		changesetImpl.resetOriginalValues();

		return changesetImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		changesetId = objectInput.readLong();

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
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(changesetId);

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

	public long changesetId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
}