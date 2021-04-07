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