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

package com.liferay.dynamic.data.mapping.form.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Eudaldo Alonso
 */
public class DDMUserPersonalFolderItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public DDMUserPersonalFolderItemSelectorCriterion() {
	}

	public DDMUserPersonalFolderItemSelectorCriterion(
		long folderId, long groupId) {

		_folderId = folderId;
		_groupId = groupId;
	}

	public long getFolderId() {
		return _folderId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	private long _folderId;
	private long _groupId;
	private long _repositoryId;

}