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

package com.liferay.change.tracking.engine.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.model.ChangeCollection;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ChangeCollection in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeCollection
 * @generated
 */
@ProviderType
public class ChangeCollectionCacheModel implements CacheModel<ChangeCollection>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangeCollectionCacheModel)) {
			return false;
		}

		ChangeCollectionCacheModel changeCollectionCacheModel = (ChangeCollectionCacheModel)obj;

		if (changeCollectionId == changeCollectionCacheModel.changeCollectionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, changeCollectionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{changeCollectionId=");
		sb.append(changeCollectionId);
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
	public ChangeCollection toEntityModel() {
		ChangeCollectionImpl changeCollectionImpl = new ChangeCollectionImpl();

		changeCollectionImpl.setChangeCollectionId(changeCollectionId);
		changeCollectionImpl.setCompanyId(companyId);
		changeCollectionImpl.setUserId(userId);

		if (userName == null) {
			changeCollectionImpl.setUserName("");
		}
		else {
			changeCollectionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			changeCollectionImpl.setCreateDate(null);
		}
		else {
			changeCollectionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			changeCollectionImpl.setModifiedDate(null);
		}
		else {
			changeCollectionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			changeCollectionImpl.setName("");
		}
		else {
			changeCollectionImpl.setName(name);
		}

		if (description == null) {
			changeCollectionImpl.setDescription("");
		}
		else {
			changeCollectionImpl.setDescription(description);
		}

		changeCollectionImpl.resetOriginalValues();

		return changeCollectionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		changeCollectionId = objectInput.readLong();

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
		objectOutput.writeLong(changeCollectionId);

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

	public long changeCollectionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
}