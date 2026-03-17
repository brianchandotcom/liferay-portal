/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ArrayableEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ArrayableEntry
 * @generated
 */
public class ArrayableEntryWrapper
	implements ArrayableEntry, ModelWrapper<ArrayableEntry> {

	public ArrayableEntryWrapper(ArrayableEntry arrayableEntry) {
		_arrayableEntry = arrayableEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ArrayableEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ArrayableEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("arrayableEntryId", getArrayableEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("integer", getInteger());
		attributes.put("name", getName());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long arrayableEntryId = (Long)attributes.get("arrayableEntryId");

		if (arrayableEntryId != null) {
			setArrayableEntryId(arrayableEntryId);
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

		Integer integer = (Integer)attributes.get("integer");

		if (integer != null) {
			setInteger(integer);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public Object clone() {
		return new ArrayableEntryWrapper(
			(ArrayableEntry)_arrayableEntry.clone());
	}

	@Override
	public int compareTo(ArrayableEntry arrayableEntry) {
		return _arrayableEntry.compareTo(arrayableEntry);
	}

	/**
	 * Returns the arrayable entry ID of this arrayable entry.
	 *
	 * @return the arrayable entry ID of this arrayable entry
	 */
	@Override
	public long getArrayableEntryId() {
		return _arrayableEntry.getArrayableEntryId();
	}

	/**
	 * Returns the company ID of this arrayable entry.
	 *
	 * @return the company ID of this arrayable entry
	 */
	@Override
	public long getCompanyId() {
		return _arrayableEntry.getCompanyId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _arrayableEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this arrayable entry.
	 *
	 * @return the group ID of this arrayable entry
	 */
	@Override
	public long getGroupId() {
		return _arrayableEntry.getGroupId();
	}

	/**
	 * Returns the integer of this arrayable entry.
	 *
	 * @return the integer of this arrayable entry
	 */
	@Override
	public int getInteger() {
		return _arrayableEntry.getInteger();
	}

	/**
	 * Returns the name of this arrayable entry.
	 *
	 * @return the name of this arrayable entry
	 */
	@Override
	public String getName() {
		return _arrayableEntry.getName();
	}

	/**
	 * Returns the primary key of this arrayable entry.
	 *
	 * @return the primary key of this arrayable entry
	 */
	@Override
	public long getPrimaryKey() {
		return _arrayableEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _arrayableEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the type of this arrayable entry.
	 *
	 * @return the type of this arrayable entry
	 */
	@Override
	public String getType() {
		return _arrayableEntry.getType();
	}

	/**
	 * Returns the user ID of this arrayable entry.
	 *
	 * @return the user ID of this arrayable entry
	 */
	@Override
	public long getUserId() {
		return _arrayableEntry.getUserId();
	}

	/**
	 * Returns the user uuid of this arrayable entry.
	 *
	 * @return the user uuid of this arrayable entry
	 */
	@Override
	public String getUserUuid() {
		return _arrayableEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _arrayableEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _arrayableEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _arrayableEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _arrayableEntry.isNew();
	}

	@Override
	public void persist() {
		_arrayableEntry.persist();
	}

	/**
	 * Sets the arrayable entry ID of this arrayable entry.
	 *
	 * @param arrayableEntryId the arrayable entry ID of this arrayable entry
	 */
	@Override
	public void setArrayableEntryId(long arrayableEntryId) {
		_arrayableEntry.setArrayableEntryId(arrayableEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_arrayableEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this arrayable entry.
	 *
	 * @param companyId the company ID of this arrayable entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_arrayableEntry.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_arrayableEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_arrayableEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_arrayableEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this arrayable entry.
	 *
	 * @param groupId the group ID of this arrayable entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_arrayableEntry.setGroupId(groupId);
	}

	/**
	 * Sets the integer of this arrayable entry.
	 *
	 * @param integer the integer of this arrayable entry
	 */
	@Override
	public void setInteger(int integer) {
		_arrayableEntry.setInteger(integer);
	}

	/**
	 * Sets the name of this arrayable entry.
	 *
	 * @param name the name of this arrayable entry
	 */
	@Override
	public void setName(String name) {
		_arrayableEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_arrayableEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this arrayable entry.
	 *
	 * @param primaryKey the primary key of this arrayable entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_arrayableEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_arrayableEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the type of this arrayable entry.
	 *
	 * @param type the type of this arrayable entry
	 */
	@Override
	public void setType(String type) {
		_arrayableEntry.setType(type);
	}

	/**
	 * Sets the user ID of this arrayable entry.
	 *
	 * @param userId the user ID of this arrayable entry
	 */
	@Override
	public void setUserId(long userId) {
		_arrayableEntry.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this arrayable entry.
	 *
	 * @param userUuid the user uuid of this arrayable entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_arrayableEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ArrayableEntry>
		toCacheModel() {

		return _arrayableEntry.toCacheModel();
	}

	@Override
	public ArrayableEntry toEscapedModel() {
		return new ArrayableEntryWrapper(_arrayableEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _arrayableEntry.toString();
	}

	@Override
	public ArrayableEntry toUnescapedModel() {
		return new ArrayableEntryWrapper(_arrayableEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _arrayableEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ArrayableEntryWrapper)) {
			return false;
		}

		ArrayableEntryWrapper arrayableEntryWrapper =
			(ArrayableEntryWrapper)object;

		if (Objects.equals(
				_arrayableEntry, arrayableEntryWrapper._arrayableEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public ArrayableEntry getWrappedModel() {
		return _arrayableEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _arrayableEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _arrayableEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_arrayableEntry.resetOriginalValues();
	}

	private final ArrayableEntry _arrayableEntry;

}
// SB-Hash:705138044