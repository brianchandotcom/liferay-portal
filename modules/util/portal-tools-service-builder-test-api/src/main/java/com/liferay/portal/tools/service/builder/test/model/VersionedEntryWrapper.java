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
 * This class is a wrapper for {@link VersionedEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntry
 * @generated
 */
public class VersionedEntryWrapper
	extends BaseModelWrapper<VersionedEntry>
	implements ModelWrapper<VersionedEntry>, VersionedEntry {

	public VersionedEntryWrapper(VersionedEntry versionedEntry) {
		super(versionedEntry);
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

	/**
	 * Returns the group ID of this versioned entry.
	 *
	 * @return the group ID of this versioned entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the head ID of this versioned entry.
	 *
	 * @return the head ID of this versioned entry
	 */
	@Override
	public long getHeadId() {
		return model.getHeadId();
	}

	/**
	 * Returns the mvcc version of this versioned entry.
	 *
	 * @return the mvcc version of this versioned entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this versioned entry.
	 *
	 * @return the primary key of this versioned entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the versioned entry ID of this versioned entry.
	 *
	 * @return the versioned entry ID of this versioned entry
	 */
	@Override
	public long getVersionedEntryId() {
		return model.getVersionedEntryId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a versioned entry model instance should use the <code>VersionedEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the group ID of this versioned entry.
	 *
	 * @param groupId the group ID of this versioned entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the head ID of this versioned entry.
	 *
	 * @param headId the head ID of this versioned entry
	 */
	@Override
	public void setHeadId(long headId) {
		model.setHeadId(headId);
	}

	/**
	 * Sets the mvcc version of this versioned entry.
	 *
	 * @param mvccVersion the mvcc version of this versioned entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this versioned entry.
	 *
	 * @param primaryKey the primary key of this versioned entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the versioned entry ID of this versioned entry.
	 *
	 * @param versionedEntryId the versioned entry ID of this versioned entry
	 */
	@Override
	public void setVersionedEntryId(long versionedEntryId) {
		model.setVersionedEntryId(versionedEntryId);
	}

	@Override
	public boolean isHead() {
		return model.isHead();
	}

	@Override
	public void populateVersionModel(
		VersionedEntryVersion versionedEntryVersion) {

		model.populateVersionModel(versionedEntryVersion);
	}

	@Override
	protected VersionedEntryWrapper wrap(VersionedEntry versionedEntry) {
		return new VersionedEntryWrapper(versionedEntry);
	}

}