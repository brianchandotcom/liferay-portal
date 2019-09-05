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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link VersionedEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntry
 * @generated
 */
public class VersionedEntryWrapper
	implements VersionedEntry, ModelWrapper<VersionedEntry> {

	public VersionedEntryWrapper(VersionedEntry versionedEntry) {
		_versionedEntry = versionedEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return VersionedEntry.class;
	}

	@Override
	public String getModelClassName() {
		return VersionedEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("headId", getHeadId());
		attributes.put("versionedEntryId", getVersionedEntryId());
		attributes.put("groupId", getGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long headId = (Long)attributes.get("headId");

		if (headId != null) {
			setHeadId(headId);
		}

		Long versionedEntryId = (Long)attributes.get("versionedEntryId");

		if (versionedEntryId != null) {
			setVersionedEntryId(versionedEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}
	}

	@Override
	public Object clone() {
		return new VersionedEntryWrapper(
			(VersionedEntry)_versionedEntry.clone());
	}

	@Override
	public int compareTo(VersionedEntry versionedEntry) {
		return _versionedEntry.compareTo(versionedEntry);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _versionedEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this versioned entry.
	 *
	 * @return the group ID of this versioned entry
	 */
	@Override
	public long getGroupId() {
		return _versionedEntry.getGroupId();
	}

	/**
	 * Returns the head ID of this versioned entry.
	 *
	 * @return the head ID of this versioned entry
	 */
	@Override
	public long getHeadId() {
		return _versionedEntry.getHeadId();
	}

	/**
	 * Returns the mvcc version of this versioned entry.
	 *
	 * @return the mvcc version of this versioned entry
	 */
	@Override
	public long getMvccVersion() {
		return _versionedEntry.getMvccVersion();
	}

	/**
	 * Returns the primary key of this versioned entry.
	 *
	 * @return the primary key of this versioned entry
	 */
	@Override
	public long getPrimaryKey() {
		return _versionedEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _versionedEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the versioned entry ID of this versioned entry.
	 *
	 * @return the versioned entry ID of this versioned entry
	 */
	@Override
	public long getVersionedEntryId() {
		return _versionedEntry.getVersionedEntryId();
	}

	@Override
	public int hashCode() {
		return _versionedEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _versionedEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _versionedEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _versionedEntry.isNew();
	}

	@Override
	public void persist() {
		_versionedEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_versionedEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_versionedEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_versionedEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_versionedEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this versioned entry.
	 *
	 * @param groupId the group ID of this versioned entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_versionedEntry.setGroupId(groupId);
	}

	/**
	 * Sets the head ID of this versioned entry.
	 *
	 * @param headId the head ID of this versioned entry
	 */
	@Override
	public void setHeadId(long headId) {
		_versionedEntry.setHeadId(headId);
	}

	/**
	 * Sets the mvcc version of this versioned entry.
	 *
	 * @param mvccVersion the mvcc version of this versioned entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_versionedEntry.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_versionedEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this versioned entry.
	 *
	 * @param primaryKey the primary key of this versioned entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_versionedEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_versionedEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the versioned entry ID of this versioned entry.
	 *
	 * @param versionedEntryId the versioned entry ID of this versioned entry
	 */
	@Override
	public void setVersionedEntryId(long versionedEntryId) {
		_versionedEntry.setVersionedEntryId(versionedEntryId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<VersionedEntry>
		toCacheModel() {

		return _versionedEntry.toCacheModel();
	}

	@Override
	public VersionedEntry toEscapedModel() {
		return new VersionedEntryWrapper(_versionedEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _versionedEntry.toString();
	}

	@Override
	public VersionedEntry toUnescapedModel() {
		return new VersionedEntryWrapper(_versionedEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _versionedEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof VersionedEntryWrapper)) {
			return false;
		}

		VersionedEntryWrapper versionedEntryWrapper =
			(VersionedEntryWrapper)obj;

		if (Objects.equals(
				_versionedEntry, versionedEntryWrapper._versionedEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isHead() {
		return _versionedEntry.isHead();
	}

	@Override
	public void populateVersionModel(
		VersionedEntryVersion versionedEntryVersion) {

		_versionedEntry.populateVersionModel(versionedEntryVersion);
	}

	@Override
	public VersionedEntry getWrappedModel() {
		return _versionedEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _versionedEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _versionedEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_versionedEntry.resetOriginalValues();
	}

	private final VersionedEntry _versionedEntry;

}