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
 * This class is a wrapper for {@link TreeEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TreeEntry
 * @generated
 */
public class TreeEntryWrapper implements ModelWrapper<TreeEntry>, TreeEntry {

	public TreeEntryWrapper(TreeEntry treeEntry) {
		_treeEntry = treeEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return TreeEntry.class;
	}

	@Override
	public String getModelClassName() {
		return TreeEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("treeEntryId", getTreeEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("parentTreeEntryId", getParentTreeEntryId());
		attributes.put("leftTreeEntryId", getLeftTreeEntryId());
		attributes.put("rightTreeEntryId", getRightTreeEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long treeEntryId = (Long)attributes.get("treeEntryId");

		if (treeEntryId != null) {
			setTreeEntryId(treeEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long parentTreeEntryId = (Long)attributes.get("parentTreeEntryId");

		if (parentTreeEntryId != null) {
			setParentTreeEntryId(parentTreeEntryId);
		}

		Long leftTreeEntryId = (Long)attributes.get("leftTreeEntryId");

		if (leftTreeEntryId != null) {
			setLeftTreeEntryId(leftTreeEntryId);
		}

		Long rightTreeEntryId = (Long)attributes.get("rightTreeEntryId");

		if (rightTreeEntryId != null) {
			setRightTreeEntryId(rightTreeEntryId);
		}
	}

	@Override
	public Object clone() {
		return new TreeEntryWrapper((TreeEntry)_treeEntry.clone());
	}

	@Override
	public int compareTo(TreeEntry treeEntry) {
		return _treeEntry.compareTo(treeEntry);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _treeEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this tree entry.
	 *
	 * @return the group ID of this tree entry
	 */
	@Override
	public long getGroupId() {
		return _treeEntry.getGroupId();
	}

	/**
	 * Returns the left tree entry ID of this tree entry.
	 *
	 * @return the left tree entry ID of this tree entry
	 */
	@Override
	public long getLeftTreeEntryId() {
		return _treeEntry.getLeftTreeEntryId();
	}

	/**
	 * Returns the parent tree entry ID of this tree entry.
	 *
	 * @return the parent tree entry ID of this tree entry
	 */
	@Override
	public long getParentTreeEntryId() {
		return _treeEntry.getParentTreeEntryId();
	}

	/**
	 * Returns the primary key of this tree entry.
	 *
	 * @return the primary key of this tree entry
	 */
	@Override
	public long getPrimaryKey() {
		return _treeEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _treeEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the right tree entry ID of this tree entry.
	 *
	 * @return the right tree entry ID of this tree entry
	 */
	@Override
	public long getRightTreeEntryId() {
		return _treeEntry.getRightTreeEntryId();
	}

	/**
	 * Returns the tree entry ID of this tree entry.
	 *
	 * @return the tree entry ID of this tree entry
	 */
	@Override
	public long getTreeEntryId() {
		return _treeEntry.getTreeEntryId();
	}

	@Override
	public int hashCode() {
		return _treeEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _treeEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _treeEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _treeEntry.isNew();
	}

	@Override
	public void persist() {
		_treeEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_treeEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_treeEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_treeEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_treeEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this tree entry.
	 *
	 * @param groupId the group ID of this tree entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_treeEntry.setGroupId(groupId);
	}

	/**
	 * Sets the left tree entry ID of this tree entry.
	 *
	 * @param leftTreeEntryId the left tree entry ID of this tree entry
	 */
	@Override
	public void setLeftTreeEntryId(long leftTreeEntryId) {
		_treeEntry.setLeftTreeEntryId(leftTreeEntryId);
	}

	@Override
	public void setNew(boolean n) {
		_treeEntry.setNew(n);
	}

	/**
	 * Sets the parent tree entry ID of this tree entry.
	 *
	 * @param parentTreeEntryId the parent tree entry ID of this tree entry
	 */
	@Override
	public void setParentTreeEntryId(long parentTreeEntryId) {
		_treeEntry.setParentTreeEntryId(parentTreeEntryId);
	}

	/**
	 * Sets the primary key of this tree entry.
	 *
	 * @param primaryKey the primary key of this tree entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_treeEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_treeEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the right tree entry ID of this tree entry.
	 *
	 * @param rightTreeEntryId the right tree entry ID of this tree entry
	 */
	@Override
	public void setRightTreeEntryId(long rightTreeEntryId) {
		_treeEntry.setRightTreeEntryId(rightTreeEntryId);
	}

	/**
	 * Sets the tree entry ID of this tree entry.
	 *
	 * @param treeEntryId the tree entry ID of this tree entry
	 */
	@Override
	public void setTreeEntryId(long treeEntryId) {
		_treeEntry.setTreeEntryId(treeEntryId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<TreeEntry>
		toCacheModel() {

		return _treeEntry.toCacheModel();
	}

	@Override
	public TreeEntry toEscapedModel() {
		return new TreeEntryWrapper(_treeEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _treeEntry.toString();
	}

	@Override
	public TreeEntry toUnescapedModel() {
		return new TreeEntryWrapper(_treeEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _treeEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TreeEntryWrapper)) {
			return false;
		}

		TreeEntryWrapper treeEntryWrapper = (TreeEntryWrapper)object;

		if (Objects.equals(_treeEntry, treeEntryWrapper._treeEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public long getNestedSetsTreeNodeLeft() {
		return _treeEntry.getNestedSetsTreeNodeLeft();
	}

	@Override
	public long getNestedSetsTreeNodeRight() {
		return _treeEntry.getNestedSetsTreeNodeRight();
	}

	@Override
	public long getNestedSetsTreeNodeScopeId() {
		return _treeEntry.getNestedSetsTreeNodeScopeId();
	}

	@Override
	public void setNestedSetsTreeNodeLeft(long nestedSetsTreeNodeLeft) {
		_treeEntry.setNestedSetsTreeNodeLeft(nestedSetsTreeNodeLeft);
	}

	@Override
	public void setNestedSetsTreeNodeRight(long nestedSetsTreeNodeRight) {
		_treeEntry.setNestedSetsTreeNodeRight(nestedSetsTreeNodeRight);
	}

	@Override
	public TreeEntry getWrappedModel() {
		return _treeEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _treeEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _treeEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_treeEntry.resetOriginalValues();
	}

	private final TreeEntry _treeEntry;

}
// SB-Hash:717108191