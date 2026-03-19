/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link TrashEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntry
 * @generated
 */
public class TrashEntryWrapper implements ModelWrapper<TrashEntry>, TrashEntry {

	public TrashEntryWrapper(TrashEntry trashEntry) {
		_trashEntry = trashEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return TrashEntry.class;
	}

	@Override
	public String getModelClassName() {
		return TrashEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("trashEntryId", getTrashEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long trashEntryId = (Long)attributes.get("trashEntryId");

		if (trashEntryId != null) {
			setTrashEntryId(trashEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public Object clone() {
		return new TrashEntryWrapper((TrashEntry)_trashEntry.clone());
	}

	@Override
	public int compareTo(TrashEntry trashEntry) {
		return _trashEntry.compareTo(trashEntry);
	}

	/**
	 * Returns the company ID of this trash entry.
	 *
	 * @return the company ID of this trash entry
	 */
	@Override
	public long getCompanyId() {
		return _trashEntry.getCompanyId();
	}

	/**
	 * Returns the create date of this trash entry.
	 *
	 * @return the create date of this trash entry
	 */
	@Override
	public Date getCreateDate() {
		return _trashEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _trashEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this trash entry.
	 *
	 * @return the group ID of this trash entry
	 */
	@Override
	public long getGroupId() {
		return _trashEntry.getGroupId();
	}

	/**
	 * Returns the modified date of this trash entry.
	 *
	 * @return the modified date of this trash entry
	 */
	@Override
	public Date getModifiedDate() {
		return _trashEntry.getModifiedDate();
	}

	/**
	 * Returns the name of this trash entry.
	 *
	 * @return the name of this trash entry
	 */
	@Override
	public String getName() {
		return _trashEntry.getName();
	}

	/**
	 * Returns the primary key of this trash entry.
	 *
	 * @return the primary key of this trash entry
	 */
	@Override
	public long getPrimaryKey() {
		return _trashEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _trashEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the status of this trash entry.
	 *
	 * @return the status of this trash entry
	 */
	@Override
	public int getStatus() {
		return _trashEntry.getStatus();
	}

	/**
	 * Returns the trash entry created when this trash entry was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this trash entry.
	 *
	 * @return the trash entry created when this trash entry was moved to the Recycle Bin
	 */
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntry.getTrashEntry();
	}

	/**
	 * Returns the class primary key of the trash entry for this trash entry.
	 *
	 * @return the class primary key of the trash entry for this trash entry
	 */
	@Override
	public long getTrashEntryClassPK() {
		return _trashEntry.getTrashEntryClassPK();
	}

	/**
	 * Returns the trash entry ID of this trash entry.
	 *
	 * @return the trash entry ID of this trash entry
	 */
	@Override
	public long getTrashEntryId() {
		return _trashEntry.getTrashEntryId();
	}

	/**
	 * Returns the trash handler for this trash entry.
	 *
	 * @return the trash handler for this trash entry
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _trashEntry.getTrashHandler();
	}

	/**
	 * Returns the user ID of this trash entry.
	 *
	 * @return the user ID of this trash entry
	 */
	@Override
	public long getUserId() {
		return _trashEntry.getUserId();
	}

	/**
	 * Returns the user name of this trash entry.
	 *
	 * @return the user name of this trash entry
	 */
	@Override
	public String getUserName() {
		return _trashEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this trash entry.
	 *
	 * @return the user uuid of this trash entry
	 */
	@Override
	public String getUserUuid() {
		return _trashEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _trashEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _trashEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _trashEntry.isEscapedModel();
	}

	/**
	 * Returns <code>true</code> if this trash entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this trash entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return _trashEntry.isInTrash();
	}

	/**
	 * Returns <code>true</code> if the parent of this trash entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if the parent of this trash entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrashContainer() {
		return _trashEntry.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _trashEntry.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _trashEntry.isInTrashImplicitly();
	}

	@Override
	public boolean isNew() {
		return _trashEntry.isNew();
	}

	@Override
	public void persist() {
		_trashEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_trashEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this trash entry.
	 *
	 * @param companyId the company ID of this trash entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_trashEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this trash entry.
	 *
	 * @param createDate the create date of this trash entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_trashEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_trashEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_trashEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_trashEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this trash entry.
	 *
	 * @param groupId the group ID of this trash entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_trashEntry.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this trash entry.
	 *
	 * @param modifiedDate the modified date of this trash entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_trashEntry.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this trash entry.
	 *
	 * @param name the name of this trash entry
	 */
	@Override
	public void setName(String name) {
		_trashEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_trashEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this trash entry.
	 *
	 * @param primaryKey the primary key of this trash entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_trashEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_trashEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the trash entry ID of this trash entry.
	 *
	 * @param trashEntryId the trash entry ID of this trash entry
	 */
	@Override
	public void setTrashEntryId(long trashEntryId) {
		_trashEntry.setTrashEntryId(trashEntryId);
	}

	/**
	 * Sets the user ID of this trash entry.
	 *
	 * @param userId the user ID of this trash entry
	 */
	@Override
	public void setUserId(long userId) {
		_trashEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this trash entry.
	 *
	 * @param userName the user name of this trash entry
	 */
	@Override
	public void setUserName(String userName) {
		_trashEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this trash entry.
	 *
	 * @param userUuid the user uuid of this trash entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_trashEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<TrashEntry>
		toCacheModel() {

		return _trashEntry.toCacheModel();
	}

	@Override
	public TrashEntry toEscapedModel() {
		return new TrashEntryWrapper(_trashEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _trashEntry.toString();
	}

	@Override
	public TrashEntry toUnescapedModel() {
		return new TrashEntryWrapper(_trashEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _trashEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TrashEntryWrapper)) {
			return false;
		}

		TrashEntryWrapper trashEntryWrapper = (TrashEntryWrapper)object;

		if (Objects.equals(_trashEntry, trashEntryWrapper._trashEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public TrashEntry getWrappedModel() {
		return _trashEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _trashEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _trashEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_trashEntry.resetOriginalValues();
	}

	private final TrashEntry _trashEntry;

}
// LIFERAY-SERVICE-BUILDER-HASH:223942572