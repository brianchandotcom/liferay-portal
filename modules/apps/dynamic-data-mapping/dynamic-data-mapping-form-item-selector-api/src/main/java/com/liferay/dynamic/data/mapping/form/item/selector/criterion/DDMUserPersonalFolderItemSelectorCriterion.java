/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public long getObjectFieldId() {
		return _objectFieldId;
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

	public void setObjectFieldId(long objectFieldId) {
		_objectFieldId = objectFieldId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	private long _folderId;
	private long _groupId;
	private long _objectFieldId;
	private long _repositoryId;

}