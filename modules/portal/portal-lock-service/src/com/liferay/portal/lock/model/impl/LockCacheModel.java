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

package com.liferay.portal.lock.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lock.model.Lock;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Lock in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Lock
 * @generated
 */
@ProviderType
public class LockCacheModel implements CacheModel<Lock>, Externalizable,
	MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LockCacheModel)) {
			return false;
		}

		LockCacheModel lockCacheModel = (LockCacheModel)obj;

		if ((lockId == lockCacheModel.lockId) &&
				(mvccVersion == lockCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, lockId);

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
		sb.append(", lockId=");
		sb.append(lockId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", className=");
		sb.append(className);
		sb.append(", key=");
		sb.append(key);
		sb.append(", owner=");
		sb.append(owner);
		sb.append(", inheritable=");
		sb.append(inheritable);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Lock toEntityModel() {
		LockImpl lockImpl = new LockImpl();

		lockImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			lockImpl.setUuid(StringPool.BLANK);
		}
		else {
			lockImpl.setUuid(uuid);
		}

		lockImpl.setLockId(lockId);
		lockImpl.setCompanyId(companyId);
		lockImpl.setUserId(userId);

		if (userName == null) {
			lockImpl.setUserName(StringPool.BLANK);
		}
		else {
			lockImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			lockImpl.setCreateDate(null);
		}
		else {
			lockImpl.setCreateDate(new Date(createDate));
		}

		if (className == null) {
			lockImpl.setClassName(StringPool.BLANK);
		}
		else {
			lockImpl.setClassName(className);
		}

		if (key == null) {
			lockImpl.setKey(StringPool.BLANK);
		}
		else {
			lockImpl.setKey(key);
		}

		if (owner == null) {
			lockImpl.setOwner(StringPool.BLANK);
		}
		else {
			lockImpl.setOwner(owner);
		}

		lockImpl.setInheritable(inheritable);

		if (expirationDate == Long.MIN_VALUE) {
			lockImpl.setExpirationDate(null);
		}
		else {
			lockImpl.setExpirationDate(new Date(expirationDate));
		}

		lockImpl.resetOriginalValues();

		return lockImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		lockId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		className = objectInput.readUTF();
		key = objectInput.readUTF();
		owner = objectInput.readUTF();
		inheritable = objectInput.readBoolean();
		expirationDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(lockId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);

		if (className == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(className);
		}

		if (key == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (owner == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(owner);
		}

		objectOutput.writeBoolean(inheritable);
		objectOutput.writeLong(expirationDate);
	}

	public long mvccVersion;
	public String uuid;
	public long lockId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public String className;
	public String key;
	public String owner;
	public boolean inheritable;
	public long expirationDate;
}