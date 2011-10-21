/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Lock;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Lock in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Lock
 * @generated
 */
public class LockCacheModel implements CacheModel<Lock>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
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

	public Lock toEntityModel() {
		LockImpl lockImpl = new LockImpl();

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