/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public String getScopeGroupType() {
		return _scopeGroupType;
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

	public boolean isSingleSelect() {
		return _singleSelect;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setScopeGroupType(String scopeGroupType) {
		_scopeGroupType = scopeGroupType;
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

	public void setSingleSelect(boolean singleSelect) {
		_singleSelect = singleSelect;
	}

	public void setSubtypeSelectionId(long subtypeSelectionId) {
		_subtypeSelectionId = subtypeSelectionId;
	}

	public void setTypeSelection(String typeSelection) {
		_typeSelection = typeSelection;
	}

	private long _groupId;
	private String _scopeGroupType;
	private long[] _selectedGroupIds;
	private boolean _showNonindexable;
	private boolean _showScheduled;
	private boolean _singleSelect;
	private long _subtypeSelectionId = -1;
	private String _typeSelection;

}