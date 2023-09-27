/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.lock;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Tina Tian
 */
@ProviderType
public interface LockManager {

	public Lock createLock(
		long lockId, long companyId, long userId, String userName);

	public Lock fetchLock(String className, long key);

	public Lock fetchLock(String className, String key);

	public Lock getLock(String className, long key) throws PortalException;

	public Lock getLock(String className, String key) throws PortalException;

	public Lock getLockByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException;

	public boolean hasLock(long userId, String className, long key);

	public boolean hasLock(long userId, String className, String key);

	public boolean isLocked(String className, long key);

	public boolean isLocked(String className, String key);

	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException;

	public Lock lock(
			long userId, String className, long key, String owner,
			boolean inheritable, long expirationTime, boolean renew)
		throws PortalException;

	public Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime)
		throws PortalException;

	public Lock lock(
			long userId, String className, String key, String owner,
			boolean inheritable, long expirationTime, boolean renew)
		throws PortalException;

	public Lock lock(String className, String key, String owner);

	public Lock lock(
		String className, String key, String expectedOwner,
		String updatedOwner);

	public Lock refresh(String uuid, long companyId, long expirationTime)
		throws PortalException;

	public void unlock(String className, long key);

	public void unlock(String className, String key);

	public void unlock(String className, String key, String owner);

}