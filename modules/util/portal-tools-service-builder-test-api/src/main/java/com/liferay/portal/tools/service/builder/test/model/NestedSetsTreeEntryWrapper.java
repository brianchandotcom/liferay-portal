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
 * This class is a wrapper for {@link NestedSetsTreeEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see NestedSetsTreeEntry
 * @generated
 */
public class NestedSetsTreeEntryWrapper
	extends BaseModelWrapper<NestedSetsTreeEntry>
	implements ModelWrapper<NestedSetsTreeEntry>, NestedSetsTreeEntry {

	public NestedSetsTreeEntryWrapper(NestedSetsTreeEntry nestedSetsTreeEntry) {
		super(nestedSetsTreeEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("nestedSetsTreeEntryId", getNestedSetsTreeEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put(
			"parentNestedSetsTreeEntryId", getParentNestedSetsTreeEntryId());
		attributes.put(
			"leftNestedSetsTreeEntryId", getLeftNestedSetsTreeEntryId());
		attributes.put(
			"rightNestedSetsTreeEntryId", getRightNestedSetsTreeEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long nestedSetsTreeEntryId = (Long)attributes.get(
			"nestedSetsTreeEntryId");

		if (nestedSetsTreeEntryId != null) {
			setNestedSetsTreeEntryId(nestedSetsTreeEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long parentNestedSetsTreeEntryId = (Long)attributes.get(
			"parentNestedSetsTreeEntryId");

		if (parentNestedSetsTreeEntryId != null) {
			setParentNestedSetsTreeEntryId(parentNestedSetsTreeEntryId);
		}

		Long leftNestedSetsTreeEntryId = (Long)attributes.get(
			"leftNestedSetsTreeEntryId");

		if (leftNestedSetsTreeEntryId != null) {
			setLeftNestedSetsTreeEntryId(leftNestedSetsTreeEntryId);
		}

		Long rightNestedSetsTreeEntryId = (Long)attributes.get(
			"rightNestedSetsTreeEntryId");

		if (rightNestedSetsTreeEntryId != null) {
			setRightNestedSetsTreeEntryId(rightNestedSetsTreeEntryId);
		}
	}

	/**
	 * Returns the group ID of this nested sets tree entry.
	 *
	 * @return the group ID of this nested sets tree entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the left nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @return the left nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public long getLeftNestedSetsTreeEntryId() {
		return model.getLeftNestedSetsTreeEntryId();
	}

	/**
	 * Returns the nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @return the nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public long getNestedSetsTreeEntryId() {
		return model.getNestedSetsTreeEntryId();
	}

	/**
	 * Returns the parent nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @return the parent nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public long getParentNestedSetsTreeEntryId() {
		return model.getParentNestedSetsTreeEntryId();
	}

	/**
	 * Returns the primary key of this nested sets tree entry.
	 *
	 * @return the primary key of this nested sets tree entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the right nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @return the right nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public long getRightNestedSetsTreeEntryId() {
		return model.getRightNestedSetsTreeEntryId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a nested sets tree entry model instance should use the <code>NestedSetsTreeEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the group ID of this nested sets tree entry.
	 *
	 * @param groupId the group ID of this nested sets tree entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the left nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @param leftNestedSetsTreeEntryId the left nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public void setLeftNestedSetsTreeEntryId(long leftNestedSetsTreeEntryId) {
		model.setLeftNestedSetsTreeEntryId(leftNestedSetsTreeEntryId);
	}

	/**
	 * Sets the nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @param nestedSetsTreeEntryId the nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public void setNestedSetsTreeEntryId(long nestedSetsTreeEntryId) {
		model.setNestedSetsTreeEntryId(nestedSetsTreeEntryId);
	}

	/**
	 * Sets the parent nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @param parentNestedSetsTreeEntryId the parent nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public void setParentNestedSetsTreeEntryId(
		long parentNestedSetsTreeEntryId) {

		model.setParentNestedSetsTreeEntryId(parentNestedSetsTreeEntryId);
	}

	/**
	 * Sets the primary key of this nested sets tree entry.
	 *
	 * @param primaryKey the primary key of this nested sets tree entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the right nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @param rightNestedSetsTreeEntryId the right nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public void setRightNestedSetsTreeEntryId(long rightNestedSetsTreeEntryId) {
		model.setRightNestedSetsTreeEntryId(rightNestedSetsTreeEntryId);
	}

	@Override
	public long getNestedSetsTreeNodeLeft() {
		return model.getNestedSetsTreeNodeLeft();
	}

	@Override
	public long getNestedSetsTreeNodeRight() {
		return model.getNestedSetsTreeNodeRight();
	}

	@Override
	public long getNestedSetsTreeNodeScopeId() {
		return model.getNestedSetsTreeNodeScopeId();
	}

	@Override
	public void setNestedSetsTreeNodeLeft(long nestedSetsTreeNodeLeft) {
		model.setNestedSetsTreeNodeLeft(nestedSetsTreeNodeLeft);
	}

	@Override
	public void setNestedSetsTreeNodeRight(long nestedSetsTreeNodeRight) {
		model.setNestedSetsTreeNodeRight(nestedSetsTreeNodeRight);
	}

	@Override
	protected NestedSetsTreeEntryWrapper wrap(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		return new NestedSetsTreeEntryWrapper(nestedSetsTreeEntry);
	}

}