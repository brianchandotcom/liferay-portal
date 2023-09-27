/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.criteria.file.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Alejandro Tardín
 */
public class FileExtensionItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public long[] getSelectedGroupIds() {
		return _selectedGroupIds;
	}

	public void setSelectedGroupIds(long[] selectedGroupIds) {
		_selectedGroupIds = selectedGroupIds;
	}

	private long[] _selectedGroupIds;

}