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

package com.liferay.item.selector.criteria.folder.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Adolfo Pérez
 */
public class FolderItemSelectorCriterion extends BaseItemSelectorCriterion {

	public long getFolderId() {
		return _folderId;
	}

	public long getSelectedFolderId() {
		return _selectedFolderId;
	}

	public boolean isIgnoreRootFolder() {
		return _ignoreRootFolder;
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

	public void setSelectedFolderId(long selectedFolderId) {
		_selectedFolderId = selectedFolderId;
	}

	public void setShowMountFolder(boolean showMountFolder) {
		_showMountFolder = showMountFolder;
	}

	private long _folderId;
	private boolean _ignoreRootFolder;
	private long _selectedFolderId;
	private boolean _showMountFolder;

}