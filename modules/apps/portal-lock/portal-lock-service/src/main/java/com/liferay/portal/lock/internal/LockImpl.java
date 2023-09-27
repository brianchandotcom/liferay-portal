/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lock.internal;

import com.liferay.portal.kernel.lock.Lock;

import java.util.Date;

/**
 * @author Tina Tian
 */
public class LockImpl implements Lock {

	public LockImpl(com.liferay.portal.lock.model.Lock lock) {
		_lock = lock;
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

		return _lock.equals(lockImpl._lock);
	}

	@Override
	public String getClassName() {
		return _lock.getClassName();
	}

	@Override
	public long getCompanyId() {
		return _lock.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _lock.getCreateDate();
	}

	@Override
	public Date getExpirationDate() {
		return _lock.getExpirationDate();
	}

	@Override
	public long getExpirationTime() {
		return _lock.getExpirationTime();
	}

	@Override
	public boolean getInheritable() {
		return _lock.getInheritable();
	}

	@Override
	public String getKey() {
		return _lock.getKey();
	}

	@Override
	public long getLockId() {
		return _lock.getLockId();
	}

	@Override
	public String getOwner() {
		return _lock.getOwner();
	}

	@Override
	public long getUserId() {
		return _lock.getUserId();
	}

	@Override
	public String getUserName() {
		return _lock.getUserName();
	}

	@Override
	public String getUserUuid() {
		return _lock.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _lock.getUuid();
	}

	@Override
	public int hashCode() {
		return _lock.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _lock.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _lock.isEscapedModel();
	}

	@Override
	public boolean isExpired() {
		return _lock.isExpired();
	}

	@Override
	public boolean isInheritable() {
		return _lock.isInheritable();
	}

	@Override
	public boolean isNeverExpires() {
		return _lock.isNeverExpires();
	}

	@Override
	public boolean isNew() {
		return _lock.isNew();
	}

	private final com.liferay.portal.lock.model.Lock _lock;

}