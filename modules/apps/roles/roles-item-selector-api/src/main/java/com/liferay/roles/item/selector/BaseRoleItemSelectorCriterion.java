/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.item.selector;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Roberto Díaz
 */
public class BaseRoleItemSelectorCriterion extends BaseItemSelectorCriterion {

	public long[] getCheckedRoleIds() {
		return _checkedRoleIds;
	}

	public String[] getExcludedRoleNames() {
		return _excludedRoleNames;
	}

	public void setCheckedRoleIds(long[] checkedRoleIds) {
		_checkedRoleIds = checkedRoleIds;
	}

	public void setExcludedRoleNames(String[] excludedRoleNames) {
		_excludedRoleNames = excludedRoleNames;
	}

	private long[] _checkedRoleIds = new long[0];
	private String[] _excludedRoleNames = new String[0];

}