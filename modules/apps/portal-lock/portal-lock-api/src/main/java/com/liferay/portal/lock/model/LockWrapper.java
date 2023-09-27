/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lock.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Lock}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Lock
 * @generated
 */
public class LockWrapper
	extends BaseModelWrapper<Lock> implements Lock, ModelWrapper<Lock> {

	public LockWrapper(Lock lock) {
		super(lock);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("lockId", getLockId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("className", getClassName());
		attributes.put("key", getKey());
		attributes.put("owner", getOwner());
		attributes.put("inheritable", isInheritable());
		attributes.put("expirationDate", getExpirationDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long lockId = (Long)attributes.get("lockId");

		if (lockId != null) {
			setLockId(lockId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		String owner = (String)attributes.get("owner");

		if (owner != null) {
			setOwner(owner);
		}

		Boolean inheritable = (Boolean)attributes.get("inheritable");

		if (inheritable != null) {
			setInheritable(inheritable);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}
	}

	@Override
	public Lock cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the class name of this lock.
	 *
	 * @return the class name of this lock
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the company ID of this lock.
	 *
	 * @return the company ID of this lock
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this lock.
	 *
	 * @return the create date of this lock
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the expiration date of this lock.
	 *
	 * @return the expiration date of this lock
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	@Override
	public long getExpirationTime() {
		return model.getExpirationTime();
	}

	/**
	 * Returns the inheritable of this lock.
	 *
	 * @return the inheritable of this lock
	 */
	@Override
	public boolean getInheritable() {
		return model.getInheritable();
	}

	/**
	 * Returns the key of this lock.
	 *
	 * @return the key of this lock
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the lock ID of this lock.
	 *
	 * @return the lock ID of this lock
	 */
	@Override
	public long getLockId() {
		return model.getLockId();
	}

	/**
	 * Returns the mvcc version of this lock.
	 *
	 * @return the mvcc version of this lock
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the owner of this lock.
	 *
	 * @return the owner of this lock
	 */
	@Override
	public String getOwner() {
		return model.getOwner();
	}

	/**
	 * Returns the primary key of this lock.
	 *
	 * @return the primary key of this lock
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this lock.
	 *
	 * @return the user ID of this lock
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this lock.
	 *
	 * @return the user name of this lock
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this lock.
	 *
	 * @return the user uuid of this lock
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this lock.
	 *
	 * @return the uuid of this lock
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this lock is inheritable.
	 *
	 * @return <code>true</code> if this lock is inheritable; <code>false</code> otherwise
	 */
	@Override
	public boolean isInheritable() {
		return model.isInheritable();
	}

	@Override
	public boolean isNeverExpires() {
		return model.isNeverExpires();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the class name of this lock.
	 *
	 * @param className the class name of this lock
	 */
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the company ID of this lock.
	 *
	 * @param companyId the company ID of this lock
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this lock.
	 *
	 * @param createDate the create date of this lock
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the expiration date of this lock.
	 *
	 * @param expirationDate the expiration date of this lock
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets whether this lock is inheritable.
	 *
	 * @param inheritable the inheritable of this lock
	 */
	@Override
	public void setInheritable(boolean inheritable) {
		model.setInheritable(inheritable);
	}

	/**
	 * Sets the key of this lock.
	 *
	 * @param key the key of this lock
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the lock ID of this lock.
	 *
	 * @param lockId the lock ID of this lock
	 */
	@Override
	public void setLockId(long lockId) {
		model.setLockId(lockId);
	}

	/**
	 * Sets the mvcc version of this lock.
	 *
	 * @param mvccVersion the mvcc version of this lock
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the owner of this lock.
	 *
	 * @param owner the owner of this lock
	 */
	@Override
	public void setOwner(String owner) {
		model.setOwner(owner);
	}

	/**
	 * Sets the primary key of this lock.
	 *
	 * @param primaryKey the primary key of this lock
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this lock.
	 *
	 * @param userId the user ID of this lock
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this lock.
	 *
	 * @param userName the user name of this lock
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this lock.
	 *
	 * @param userUuid the user uuid of this lock
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this lock.
	 *
	 * @param uuid the uuid of this lock
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected LockWrapper wrap(Lock lock) {
		return new LockWrapper(lock);
	}

}