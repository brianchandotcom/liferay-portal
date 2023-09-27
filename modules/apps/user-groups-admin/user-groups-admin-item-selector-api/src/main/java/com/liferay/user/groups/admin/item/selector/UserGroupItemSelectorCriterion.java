/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.groups.admin.item.selector;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Alessio Antonio Rendina
 */
public class UserGroupItemSelectorCriterion extends BaseItemSelectorCriterion {

	public boolean isFilterManageableUserGroups() {
		return _filterManageableUserGroups;
	}

	public void setFilterManageableUserGroups(
		boolean filterManageableUserGroups) {

		_filterManageableUserGroups = filterManageableUserGroups;
	}

	private boolean _filterManageableUserGroups;

}