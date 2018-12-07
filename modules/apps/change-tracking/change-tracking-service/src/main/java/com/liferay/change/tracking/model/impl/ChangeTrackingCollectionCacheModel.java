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

package com.liferay.change.tracking.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.model.ChangeTrackingCollection;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ChangeTrackingCollection in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingCollection
 * @generated
 */
@ProviderType
public class ChangeTrackingCollectionCacheModel implements CacheModel<ChangeTrackingCollection>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangeTrackingCollectionCacheModel)) {
			return false;
		}

		ChangeTrackingCollectionCacheModel changeTrackingCollectionCacheModel = (ChangeTrackingCollectionCacheModel)obj;

		if (changeTrackingCollectionId == changeTrackingCollectionCacheModel.changeTrackingCollectionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, changeTrackingCollectionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{changeTrackingCollectionId=");
		sb.append(changeTrackingCollectionId);
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
	public ChangeTrackingCollection toEntityModel() {
		ChangeTrackingCollectionImpl changeTrackingCollectionImpl = new ChangeTrackingCollectionImpl();

		changeTrackingCollectionImpl.setChangeTrackingCollectionId(changeTrackingCollectionId);
		changeTrackingCollectionImpl.setCompanyId(companyId);
		changeTrackingCollectionImpl.setUserId(userId);

		if (userName == null) {
			changeTrackingCollectionImpl.setUserName("");
		}
		else {
			changeTrackingCollectionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			changeTrackingCollectionImpl.setCreateDate(null);
		}
		else {
			changeTrackingCollectionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			changeTrackingCollectionImpl.setModifiedDate(null);
		}
		else {
			changeTrackingCollectionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			changeTrackingCollectionImpl.setName("");
		}
		else {
			changeTrackingCollectionImpl.setName(name);
		}

		if (description == null) {
			changeTrackingCollectionImpl.setDescription("");
		}
		else {
			changeTrackingCollectionImpl.setDescription(description);
		}

		changeTrackingCollectionImpl.setStatus(status);
		changeTrackingCollectionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			changeTrackingCollectionImpl.setStatusByUserName("");
		}
		else {
			changeTrackingCollectionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			changeTrackingCollectionImpl.setStatusDate(null);
		}
		else {
			changeTrackingCollectionImpl.setStatusDate(new Date(statusDate));
		}

		changeTrackingCollectionImpl.resetOriginalValues();

		return changeTrackingCollectionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		changeTrackingCollectionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(changeTrackingCollectionId);

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

	public long changeTrackingCollectionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}