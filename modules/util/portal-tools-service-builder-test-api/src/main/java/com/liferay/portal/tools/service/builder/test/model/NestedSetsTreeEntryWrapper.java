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
 * This class is a wrapper for {@link NestedSetsTreeEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see NestedSetsTreeEntry
 * @generated
 */
public class NestedSetsTreeEntryWrapper
	implements NestedSetsTreeEntry, ModelWrapper<NestedSetsTreeEntry> {

	public NestedSetsTreeEntryWrapper(NestedSetsTreeEntry nestedSetsTreeEntry) {
		_nestedSetsTreeEntry = nestedSetsTreeEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return NestedSetsTreeEntry.class;
	}

	@Override
	public String getModelClassName() {
		return NestedSetsTreeEntry.class.getName();
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

	@Override
	public Object clone() {
		return new NestedSetsTreeEntryWrapper(
			(NestedSetsTreeEntry)_nestedSetsTreeEntry.clone());
	}

	@Override
	public int compareTo(NestedSetsTreeEntry nestedSetsTreeEntry) {
		return _nestedSetsTreeEntry.compareTo(nestedSetsTreeEntry);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _nestedSetsTreeEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this nested sets tree entry.
	 *
	 * @return the group ID of this nested sets tree entry
	 */
	@Override
	public long getGroupId() {
		return _nestedSetsTreeEntry.getGroupId();
	}

	/**
	 * Returns the left nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @return the left nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public long getLeftNestedSetsTreeEntryId() {
		return _nestedSetsTreeEntry.getLeftNestedSetsTreeEntryId();
	}

	/**
	 * Returns the nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @return the nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public long getNestedSetsTreeEntryId() {
		return _nestedSetsTreeEntry.getNestedSetsTreeEntryId();
	}

	/**
	 * Returns the parent nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @return the parent nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public long getParentNestedSetsTreeEntryId() {
		return _nestedSetsTreeEntry.getParentNestedSetsTreeEntryId();
	}

	/**
	 * Returns the primary key of this nested sets tree entry.
	 *
	 * @return the primary key of this nested sets tree entry
	 */
	@Override
	public long getPrimaryKey() {
		return _nestedSetsTreeEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _nestedSetsTreeEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the right nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @return the right nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public long getRightNestedSetsTreeEntryId() {
		return _nestedSetsTreeEntry.getRightNestedSetsTreeEntryId();
	}

	@Override
	public int hashCode() {
		return _nestedSetsTreeEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _nestedSetsTreeEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _nestedSetsTreeEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _nestedSetsTreeEntry.isNew();
	}

	@Override
	public void persist() {
		_nestedSetsTreeEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_nestedSetsTreeEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_nestedSetsTreeEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_nestedSetsTreeEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_nestedSetsTreeEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this nested sets tree entry.
	 *
	 * @param groupId the group ID of this nested sets tree entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_nestedSetsTreeEntry.setGroupId(groupId);
	}

	/**
	 * Sets the left nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @param leftNestedSetsTreeEntryId the left nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public void setLeftNestedSetsTreeEntryId(long leftNestedSetsTreeEntryId) {
		_nestedSetsTreeEntry.setLeftNestedSetsTreeEntryId(
			leftNestedSetsTreeEntryId);
	}

	/**
	 * Sets the nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @param nestedSetsTreeEntryId the nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public void setNestedSetsTreeEntryId(long nestedSetsTreeEntryId) {
		_nestedSetsTreeEntry.setNestedSetsTreeEntryId(nestedSetsTreeEntryId);
	}

	@Override
	public void setNew(boolean n) {
		_nestedSetsTreeEntry.setNew(n);
	}

	/**
	 * Sets the parent nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @param parentNestedSetsTreeEntryId the parent nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public void setParentNestedSetsTreeEntryId(
		long parentNestedSetsTreeEntryId) {

		_nestedSetsTreeEntry.setParentNestedSetsTreeEntryId(
			parentNestedSetsTreeEntryId);
	}

	/**
	 * Sets the primary key of this nested sets tree entry.
	 *
	 * @param primaryKey the primary key of this nested sets tree entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_nestedSetsTreeEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_nestedSetsTreeEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the right nested sets tree entry ID of this nested sets tree entry.
	 *
	 * @param rightNestedSetsTreeEntryId the right nested sets tree entry ID of this nested sets tree entry
	 */
	@Override
	public void setRightNestedSetsTreeEntryId(long rightNestedSetsTreeEntryId) {
		_nestedSetsTreeEntry.setRightNestedSetsTreeEntryId(
			rightNestedSetsTreeEntryId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<NestedSetsTreeEntry>
		toCacheModel() {

		return _nestedSetsTreeEntry.toCacheModel();
	}

	@Override
	public NestedSetsTreeEntry toEscapedModel() {
		return new NestedSetsTreeEntryWrapper(
			_nestedSetsTreeEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _nestedSetsTreeEntry.toString();
	}

	@Override
	public NestedSetsTreeEntry toUnescapedModel() {
		return new NestedSetsTreeEntryWrapper(
			_nestedSetsTreeEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _nestedSetsTreeEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof NestedSetsTreeEntryWrapper)) {
			return false;
		}

		NestedSetsTreeEntryWrapper nestedSetsTreeEntryWrapper =
			(NestedSetsTreeEntryWrapper)obj;

		if (Objects.equals(
				_nestedSetsTreeEntry,
				nestedSetsTreeEntryWrapper._nestedSetsTreeEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public long getNestedSetsTreeNodeLeft() {
		return _nestedSetsTreeEntry.getNestedSetsTreeNodeLeft();
	}

	@Override
	public long getNestedSetsTreeNodeRight() {
		return _nestedSetsTreeEntry.getNestedSetsTreeNodeRight();
	}

	@Override
	public long getNestedSetsTreeNodeScopeId() {
		return _nestedSetsTreeEntry.getNestedSetsTreeNodeScopeId();
	}

	@Override
	public void setNestedSetsTreeNodeLeft(long nestedSetsTreeNodeLeft) {
		_nestedSetsTreeEntry.setNestedSetsTreeNodeLeft(nestedSetsTreeNodeLeft);
	}

	@Override
	public void setNestedSetsTreeNodeRight(long nestedSetsTreeNodeRight) {
		_nestedSetsTreeEntry.setNestedSetsTreeNodeRight(
			nestedSetsTreeNodeRight);
	}

	@Override
	public NestedSetsTreeEntry getWrappedModel() {
		return _nestedSetsTreeEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _nestedSetsTreeEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _nestedSetsTreeEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_nestedSetsTreeEntry.resetOriginalValues();
	}

	private final NestedSetsTreeEntry _nestedSetsTreeEntry;

}