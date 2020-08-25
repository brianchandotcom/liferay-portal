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

import java.sql.Blob;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link EagerBlobEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntry
 * @generated
 */
public class EagerBlobEntryWrapper
	extends BaseModelWrapper<EagerBlobEntry>
	implements EagerBlobEntry, ModelWrapper<EagerBlobEntry> {

	public EagerBlobEntryWrapper(EagerBlobEntry eagerBlobEntry) {
		super(eagerBlobEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("eagerBlobEntryId", getEagerBlobEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("blob", getBlob());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long eagerBlobEntryId = (Long)attributes.get("eagerBlobEntryId");

		if (eagerBlobEntryId != null) {
			setEagerBlobEntryId(eagerBlobEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Blob blob = (Blob)attributes.get("blob");

		if (blob != null) {
			setBlob(blob);
		}
	}

	/**
	 * Returns the blob of this eager blob entry.
	 *
	 * @return the blob of this eager blob entry
	 */
	@Override
	public Blob getBlob() {
		return model.getBlob();
	}

	/**
	 * Returns the eager blob entry ID of this eager blob entry.
	 *
	 * @return the eager blob entry ID of this eager blob entry
	 */
	@Override
	public long getEagerBlobEntryId() {
		return model.getEagerBlobEntryId();
	}

	/**
	 * Returns the group ID of this eager blob entry.
	 *
	 * @return the group ID of this eager blob entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the primary key of this eager blob entry.
	 *
	 * @return the primary key of this eager blob entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this eager blob entry.
	 *
	 * @return the uuid of this eager blob entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the blob of this eager blob entry.
	 *
	 * @param blob the blob of this eager blob entry
	 */
	@Override
	public void setBlob(Blob blob) {
		model.setBlob(blob);
	}

	/**
	 * Sets the eager blob entry ID of this eager blob entry.
	 *
	 * @param eagerBlobEntryId the eager blob entry ID of this eager blob entry
	 */
	@Override
	public void setEagerBlobEntryId(long eagerBlobEntryId) {
		model.setEagerBlobEntryId(eagerBlobEntryId);
	}

	/**
	 * Sets the group ID of this eager blob entry.
	 *
	 * @param groupId the group ID of this eager blob entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the primary key of this eager blob entry.
	 *
	 * @param primaryKey the primary key of this eager blob entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this eager blob entry.
	 *
	 * @param uuid the uuid of this eager blob entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected EagerBlobEntryWrapper wrap(EagerBlobEntry eagerBlobEntry) {
		return new EagerBlobEntryWrapper(eagerBlobEntry);
	}

}