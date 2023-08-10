/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Stefan Tanasie
 */
public class AssetTagsItemSelectorCriterion extends BaseItemSelectorCriterion {

	public long[] getGroupIds() {
		return _groupIds;
	}

	public boolean isAllGroupIds() {
		return _allGroupIds;
	}

	public boolean isMultiSelection() {
		return _multiSelection;
	}

	public void setAllGroupIds(boolean allGroupIds) {
		_allGroupIds = allGroupIds;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setMultiSelection(boolean multiSelection) {
		_multiSelection = multiSelection;
	}

	private boolean _allGroupIds;
	private long[] _groupIds;
	private boolean _multiSelection;

}