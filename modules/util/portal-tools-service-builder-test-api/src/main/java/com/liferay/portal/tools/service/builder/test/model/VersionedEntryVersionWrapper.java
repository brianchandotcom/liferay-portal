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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<VersionedEntryVersion>
	implements ModelWrapper<VersionedEntryVersion>, VersionedEntryVersion {

	public VersionedEntryVersionWrapper(
		VersionedEntryVersion versionedEntryVersion) {

		super(versionedEntryVersion);
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

	/**
	 * Returns the group ID of this versioned entry version.
	 *
	 * @return the group ID of this versioned entry version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the primary key of this versioned entry version.
	 *
	 * @return the primary key of this versioned entry version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the version of this versioned entry version.
	 *
	 * @return the version of this versioned entry version
	 */
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns the versioned entry ID of this versioned entry version.
	 *
	 * @return the versioned entry ID of this versioned entry version
	 */
	@Override
	public long getVersionedEntryId() {
		return model.getVersionedEntryId();
	}

	/**
	 * Returns the versioned entry version ID of this versioned entry version.
	 *
	 * @return the versioned entry version ID of this versioned entry version
	 */
	@Override
	public long getVersionedEntryVersionId() {
		return model.getVersionedEntryVersionId();
	}

	/**
	 * Sets the group ID of this versioned entry version.
	 *
	 * @param groupId the group ID of this versioned entry version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the primary key of this versioned entry version.
	 *
	 * @param primaryKey the primary key of this versioned entry version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the version of this versioned entry version.
	 *
	 * @param version the version of this versioned entry version
	 */
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	/**
	 * Sets the versioned entry ID of this versioned entry version.
	 *
	 * @param versionedEntryId the versioned entry ID of this versioned entry version
	 */
	@Override
	public void setVersionedEntryId(long versionedEntryId) {
		model.setVersionedEntryId(versionedEntryId);
	}

	/**
	 * Sets the versioned entry version ID of this versioned entry version.
	 *
	 * @param versionedEntryVersionId the versioned entry version ID of this versioned entry version
	 */
	@Override
	public void setVersionedEntryVersionId(long versionedEntryVersionId) {
		model.setVersionedEntryVersionId(versionedEntryVersionId);
	}

	@Override
	public long getVersionedModelId() {
		return model.getVersionedModelId();
	}

	@Override
	public void setVersionedModelId(long id) {
		model.setVersionedModelId(id);
	}

	@Override
	public void populateVersionedModel(VersionedEntry versionedEntry) {
		model.populateVersionedModel(versionedEntry);
	}

	@Override
	public VersionedEntry toVersionedModel() {
		return model.toVersionedModel();
	}

	@Override
	protected VersionedEntryVersionWrapper wrap(
		VersionedEntryVersion versionedEntryVersion) {

		return new VersionedEntryVersionWrapper(versionedEntryVersion);
	}

}