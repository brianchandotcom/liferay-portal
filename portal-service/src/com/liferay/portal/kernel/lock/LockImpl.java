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

package com.liferay.portal.kernel.lock;

import java.util.Date;

/**
 * @author Tina Tian
 */
public class LockImpl implements Lock {

	public LockImpl(com.liferay.portal.model.Lock innerLock) {
		_innerLock = innerLock;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LockImpl)) {
			return false;
		}

		LockImpl lockImpl = (LockImpl)object;

		return _innerLock.equals(lockImpl._innerLock);
	}

	@Override
	public String getClassName() {
		return _innerLock.getClassName();
	}

	@Override
	public long getCompanyId() {
		return _innerLock.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _innerLock.getCreateDate();
	}

	@Override
	public Date getExpirationDate() {
		return _innerLock.getExpirationDate();
	}

	@Override
	public long getExpirationTime() {
		return _innerLock.getExpirationTime();
	}

	@Override
	public boolean getInheritable() {
		return _innerLock.getInheritable();
	}

	@Override
	public String getKey() {
		return _innerLock.getKey();
	}

	@Override
	public long getLockId() {
		return _innerLock.getLockId();
	}

	@Override
	public String getOwner() {
		return _innerLock.getOwner();
	}

	@Override
	public long getUserId() {
		return _innerLock.getUserId();
	}

	@Override
	public String getUserName() {
		return _innerLock.getUserName();
	}

	@Override
	public String getUserUuid() {
		return _innerLock.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _innerLock.getUuid();
	}

	@Override
	public int hashCode() {
		return _innerLock.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _innerLock.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _innerLock.isEscapedModel();
	}

	@Override
	public boolean isExpired() {
		return _innerLock.isExpired();
	}

	@Override
	public boolean isInheritable() {
		return _innerLock.isInheritable();
	}

	@Override
	public boolean isNeverExpires() {
		return _innerLock.isNeverExpires();
	}

	@Override
	public boolean isNew() {
		return _innerLock.isNew();
	}

	private final com.liferay.portal.model.Lock _innerLock;

}