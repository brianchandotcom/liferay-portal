/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.criteria.folder.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Adolfo Pérez
 */
public class FolderItemSelectorCriterion extends BaseItemSelectorCriterion {

	public long getFolderId() {
		return _folderId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public long getSelectedFolderId() {
		return _selectedFolderId;
	}

	public long getSelectedRepositoryId() {
		return _selectedRepositoryId;
	}

	public boolean isIgnoreRootFolder() {
		return _ignoreRootFolder;
	}

	public boolean isShowGroupSelector() {
		return _showGroupSelector;
	}

	public boolean isShowMountFolder() {
		return _showMountFolder;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public void setIgnoreRootFolder(boolean ignoreRootFolder) {
		_ignoreRootFolder = ignoreRootFolder;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setSelectedFolderId(long selectedFolderId) {
		_selectedFolderId = selectedFolderId;
	}

	public void setSelectedRepositoryId(long selectedRepositoryId) {
		_selectedRepositoryId = selectedRepositoryId;
	}

	public void setShowGroupSelector(boolean showGroupSelector) {
		_showGroupSelector = showGroupSelector;
	}

	public void setShowMountFolder(boolean showMountFolder) {
		_showMountFolder = showMountFolder;
	}

	private long _folderId;
	private boolean _ignoreRootFolder;
	private long _repositoryId;
	private long _selectedFolderId;
	private long _selectedRepositoryId;
	private boolean _showGroupSelector;
	private boolean _showMountFolder;

}