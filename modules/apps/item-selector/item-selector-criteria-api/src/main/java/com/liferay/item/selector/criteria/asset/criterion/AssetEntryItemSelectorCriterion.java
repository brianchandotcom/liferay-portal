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

package com.liferay.item.selector.criteria.asset.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Adolfo Pérez
 */
public class AssetEntryItemSelectorCriterion extends BaseItemSelectorCriterion {

	public long getGroupId() {
		return _groupId;
	}

	public long[] getSelectedGroupIds() {
		return _selectedGroupIds;
	}

	public long getSubtypeSelectionId() {
		return _subtypeSelectionId;
	}

	public String getTypeSelection() {
		return _typeSelection;
	}

	public boolean isShowNonindexable() {
		return _showNonindexable;
	}

	public boolean isShowScheduled() {
		return _showScheduled;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setSelectedGroupIds(long[] selectedGroupIds) {
		_selectedGroupIds = selectedGroupIds;
	}

	public void setShowNonindexable(boolean showNonindexable) {
		_showNonindexable = showNonindexable;
	}

	public void setShowScheduled(boolean showScheduled) {
		_showScheduled = showScheduled;
	}

	public void setSubtypeSelectionId(long subtypeSelectionId) {
		_subtypeSelectionId = subtypeSelectionId;
	}

	public void setTypeSelection(String typeSelection) {
		_typeSelection = typeSelection;
	}

	private long _groupId;
	private long[] _selectedGroupIds;
	private boolean _showNonindexable;
	private boolean _showScheduled;
	private long _subtypeSelectionId;
	private String _typeSelection;

}