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

package com.liferay.asset.tags.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Stefan Tanasie
 */
public class AssetTagsItemSelectorCriterion extends BaseItemSelectorCriterion {

	public long[] getGroupIds() {
		return _groupIds;
	}

	public boolean getIsMultiple() {
		return _multiple;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setMultiple(boolean multiple) {
		_multiple = multiple;
	}

	private long[] _groupIds;
	private boolean _multiple;

}