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

package com.liferay.modern.site.building.fragment.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MSBFragmentCollection in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentCollection
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionCacheModel implements CacheModel<MSBFragmentCollection>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MSBFragmentCollectionCacheModel)) {
			return false;
		}

		MSBFragmentCollectionCacheModel msbFragmentCollectionCacheModel = (MSBFragmentCollectionCacheModel)obj;

		if (msbFragmentCollectionId == msbFragmentCollectionCacheModel.msbFragmentCollectionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, msbFragmentCollectionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{msbFragmentCollectionId=");
		sb.append(msbFragmentCollectionId);
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
	public MSBFragmentCollection toEntityModel() {
		MSBFragmentCollectionImpl msbFragmentCollectionImpl = new MSBFragmentCollectionImpl();

		msbFragmentCollectionImpl.setMsbFragmentCollectionId(msbFragmentCollectionId);
		msbFragmentCollectionImpl.setGroupId(groupId);
		msbFragmentCollectionImpl.setCompanyId(companyId);
		msbFragmentCollectionImpl.setUserId(userId);

		if (userName == null) {
			msbFragmentCollectionImpl.setUserName(StringPool.BLANK);
		}
		else {
			msbFragmentCollectionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			msbFragmentCollectionImpl.setCreateDate(null);
		}
		else {
			msbFragmentCollectionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			msbFragmentCollectionImpl.setModifiedDate(null);
		}
		else {
			msbFragmentCollectionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			msbFragmentCollectionImpl.setName(StringPool.BLANK);
		}
		else {
			msbFragmentCollectionImpl.setName(name);
		}

		if (description == null) {
			msbFragmentCollectionImpl.setDescription(StringPool.BLANK);
		}
		else {
			msbFragmentCollectionImpl.setDescription(description);
		}

		msbFragmentCollectionImpl.resetOriginalValues();

		return msbFragmentCollectionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		msbFragmentCollectionId = objectInput.readLong();

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
		objectOutput.writeLong(msbFragmentCollectionId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}
	}

	public long msbFragmentCollectionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
}