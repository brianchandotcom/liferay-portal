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
 * This class is a wrapper for {@link VersionedEntryVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryVersion
 * @generated
 */
public class VersionedEntryVersionWrapper
	implements VersionedEntryVersion, ModelWrapper<VersionedEntryVersion> {

	public VersionedEntryVersionWrapper(
		VersionedEntryVersion versionedEntryVersion) {

		_versionedEntryVersion = versionedEntryVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return VersionedEntryVersion.class;
	}

	@Override
	public String getModelClassName() {
		return VersionedEntryVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("versionedEntryVersionId", getVersionedEntryVersionId());
		attributes.put("version", getVersion());
		attributes.put("versionedEntryId", getVersionedEntryId());
		attributes.put("groupId", getGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long versionedEntryVersionId = (Long)attributes.get(
			"versionedEntryVersionId");

		if (versionedEntryVersionId != null) {
			setVersionedEntryVersionId(versionedEntryVersionId);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
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
		return new VersionedEntryVersionWrapper(
			(VersionedEntryVersion)_versionedEntryVersion.clone());
	}

	@Override
	public int compareTo(VersionedEntryVersion versionedEntryVersion) {
		return _versionedEntryVersion.compareTo(versionedEntryVersion);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _versionedEntryVersion.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this versioned entry version.
	 *
	 * @return the group ID of this versioned entry version
	 */
	@Override
	public long getGroupId() {
		return _versionedEntryVersion.getGroupId();
	}

	/**
	 * Returns the primary key of this versioned entry version.
	 *
	 * @return the primary key of this versioned entry version
	 */
	@Override
	public long getPrimaryKey() {
		return _versionedEntryVersion.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _versionedEntryVersion.getPrimaryKeyObj();
	}

	/**
	 * Returns the version of this versioned entry version.
	 *
	 * @return the version of this versioned entry version
	 */
	@Override
	public int getVersion() {
		return _versionedEntryVersion.getVersion();
	}

	/**
	 * Returns the versioned entry ID of this versioned entry version.
	 *
	 * @return the versioned entry ID of this versioned entry version
	 */
	@Override
	public long getVersionedEntryId() {
		return _versionedEntryVersion.getVersionedEntryId();
	}

	/**
	 * Returns the versioned entry version ID of this versioned entry version.
	 *
	 * @return the versioned entry version ID of this versioned entry version
	 */
	@Override
	public long getVersionedEntryVersionId() {
		return _versionedEntryVersion.getVersionedEntryVersionId();
	}

	@Override
	public int hashCode() {
		return _versionedEntryVersion.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _versionedEntryVersion.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _versionedEntryVersion.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _versionedEntryVersion.isNew();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_versionedEntryVersion.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_versionedEntryVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_versionedEntryVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_versionedEntryVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this versioned entry version.
	 *
	 * @param groupId the group ID of this versioned entry version
	 */
	@Override
	public void setGroupId(long groupId) {
		_versionedEntryVersion.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_versionedEntryVersion.setNew(n);
	}

	/**
	 * Sets the primary key of this versioned entry version.
	 *
	 * @param primaryKey the primary key of this versioned entry version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_versionedEntryVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_versionedEntryVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the version of this versioned entry version.
	 *
	 * @param version the version of this versioned entry version
	 */
	@Override
	public void setVersion(int version) {
		_versionedEntryVersion.setVersion(version);
	}

	/**
	 * Sets the versioned entry ID of this versioned entry version.
	 *
	 * @param versionedEntryId the versioned entry ID of this versioned entry version
	 */
	@Override
	public void setVersionedEntryId(long versionedEntryId) {
		_versionedEntryVersion.setVersionedEntryId(versionedEntryId);
	}

	/**
	 * Sets the versioned entry version ID of this versioned entry version.
	 *
	 * @param versionedEntryVersionId the versioned entry version ID of this versioned entry version
	 */
	@Override
	public void setVersionedEntryVersionId(long versionedEntryVersionId) {
		_versionedEntryVersion.setVersionedEntryVersionId(
			versionedEntryVersionId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<VersionedEntryVersion>
		toCacheModel() {

		return _versionedEntryVersion.toCacheModel();
	}

	@Override
	public VersionedEntryVersion toEscapedModel() {
		return new VersionedEntryVersionWrapper(
			_versionedEntryVersion.toEscapedModel());
	}

	@Override
	public String toString() {
		return _versionedEntryVersion.toString();
	}

	@Override
	public VersionedEntryVersion toUnescapedModel() {
		return new VersionedEntryVersionWrapper(
			_versionedEntryVersion.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _versionedEntryVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof VersionedEntryVersionWrapper)) {
			return false;
		}

		VersionedEntryVersionWrapper versionedEntryVersionWrapper =
			(VersionedEntryVersionWrapper)obj;

		if (Objects.equals(
				_versionedEntryVersion,
				versionedEntryVersionWrapper._versionedEntryVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public long getVersionedModelId() {
		return _versionedEntryVersion.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		_versionedEntryVersion.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(VersionedEntry versionedEntry) {
		_versionedEntryVersion.populateVersionedModel(versionedEntry);
	}

	@Override
	public VersionedEntry toVersionedModel() {
		return _versionedEntryVersion.toVersionedModel();
	}

	@Override
	public VersionedEntryVersion getWrappedModel() {
		return _versionedEntryVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _versionedEntryVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _versionedEntryVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_versionedEntryVersion.resetOriginalValues();
	}

	private final VersionedEntryVersion _versionedEntryVersion;

}