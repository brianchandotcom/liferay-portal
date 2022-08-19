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
 * This class is a wrapper for {@link RenameFinderColumnEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RenameFinderColumnEntry
 * @generated
 */
public class RenameFinderColumnEntryWrapper
	extends BaseModelWrapper<RenameFinderColumnEntry>
	implements ModelWrapper<RenameFinderColumnEntry>, RenameFinderColumnEntry {

	public RenameFinderColumnEntryWrapper(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		super(renameFinderColumnEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"renameFinderColumnEntryId", getRenameFinderColumnEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("columnToRename", getColumnToRename());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long renameFinderColumnEntryId = (Long)attributes.get(
			"renameFinderColumnEntryId");

		if (renameFinderColumnEntryId != null) {
			setRenameFinderColumnEntryId(renameFinderColumnEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String columnToRename = (String)attributes.get("columnToRename");

		if (columnToRename != null) {
			setColumnToRename(columnToRename);
		}
	}

	@Override
	public RenameFinderColumnEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the column to rename of this rename finder column entry.
	 *
	 * @return the column to rename of this rename finder column entry
	 */
	@Override
	public String getColumnToRename() {
		return model.getColumnToRename();
	}

	/**
	 * Returns the group ID of this rename finder column entry.
	 *
	 * @return the group ID of this rename finder column entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the primary key of this rename finder column entry.
	 *
	 * @return the primary key of this rename finder column entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the rename finder column entry ID of this rename finder column entry.
	 *
	 * @return the rename finder column entry ID of this rename finder column entry
	 */
	@Override
	public long getRenameFinderColumnEntryId() {
		return model.getRenameFinderColumnEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the column to rename of this rename finder column entry.
	 *
	 * @param columnToRename the column to rename of this rename finder column entry
	 */
	@Override
	public void setColumnToRename(String columnToRename) {
		model.setColumnToRename(columnToRename);
	}

	/**
	 * Sets the group ID of this rename finder column entry.
	 *
	 * @param groupId the group ID of this rename finder column entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the primary key of this rename finder column entry.
	 *
	 * @param primaryKey the primary key of this rename finder column entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the rename finder column entry ID of this rename finder column entry.
	 *
	 * @param renameFinderColumnEntryId the rename finder column entry ID of this rename finder column entry
	 */
	@Override
	public void setRenameFinderColumnEntryId(long renameFinderColumnEntryId) {
		model.setRenameFinderColumnEntryId(renameFinderColumnEntryId);
	}

	@Override
	protected RenameFinderColumnEntryWrapper wrap(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		return new RenameFinderColumnEntryWrapper(renameFinderColumnEntry);
	}

}