/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link TreeEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TreeEntry
 * @generated
 */
public class TreeEntryWrapper
	extends BaseModelWrapper<TreeEntry>
	implements ModelWrapper<TreeEntry>, TreeEntry {

	public TreeEntryWrapper(TreeEntry treeEntry) {
		super(treeEntry);
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

	/**
	 * Returns the group ID of this tree entry.
	 *
	 * @return the group ID of this tree entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the left tree entry ID of this tree entry.
	 *
	 * @return the left tree entry ID of this tree entry
	 */
	@Override
	public long getLeftTreeEntryId() {
		return model.getLeftTreeEntryId();
	}

	/**
	 * Returns the parent tree entry ID of this tree entry.
	 *
	 * @return the parent tree entry ID of this tree entry
	 */
	@Override
	public long getParentTreeEntryId() {
		return model.getParentTreeEntryId();
	}

	/**
	 * Returns the primary key of this tree entry.
	 *
	 * @return the primary key of this tree entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the right tree entry ID of this tree entry.
	 *
	 * @return the right tree entry ID of this tree entry
	 */
	@Override
	public long getRightTreeEntryId() {
		return model.getRightTreeEntryId();
	}

	/**
	 * Returns the tree entry ID of this tree entry.
	 *
	 * @return the tree entry ID of this tree entry
	 */
	@Override
	public long getTreeEntryId() {
		return model.getTreeEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the group ID of this tree entry.
	 *
	 * @param groupId the group ID of this tree entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the left tree entry ID of this tree entry.
	 *
	 * @param leftTreeEntryId the left tree entry ID of this tree entry
	 */
	@Override
	public void setLeftTreeEntryId(long leftTreeEntryId) {
		model.setLeftTreeEntryId(leftTreeEntryId);
	}

	/**
	 * Sets the parent tree entry ID of this tree entry.
	 *
	 * @param parentTreeEntryId the parent tree entry ID of this tree entry
	 */
	@Override
	public void setParentTreeEntryId(long parentTreeEntryId) {
		model.setParentTreeEntryId(parentTreeEntryId);
	}

	/**
	 * Sets the primary key of this tree entry.
	 *
	 * @param primaryKey the primary key of this tree entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the right tree entry ID of this tree entry.
	 *
	 * @param rightTreeEntryId the right tree entry ID of this tree entry
	 */
	@Override
	public void setRightTreeEntryId(long rightTreeEntryId) {
		model.setRightTreeEntryId(rightTreeEntryId);
	}

	/**
	 * Sets the tree entry ID of this tree entry.
	 *
	 * @param treeEntryId the tree entry ID of this tree entry
	 */
	@Override
	public void setTreeEntryId(long treeEntryId) {
		model.setTreeEntryId(treeEntryId);
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
	protected TreeEntryWrapper wrap(TreeEntry treeEntry) {
		return new TreeEntryWrapper(treeEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1410684749