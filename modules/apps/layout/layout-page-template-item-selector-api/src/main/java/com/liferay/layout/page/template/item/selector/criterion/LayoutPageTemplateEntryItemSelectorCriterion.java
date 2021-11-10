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

package com.liferay.layout.page.template.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutPageTemplateEntryItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public LayoutPageTemplateEntryItemSelectorCriterion() {
		_groupId = 0;
		_layoutTypes = new int[0];
	}

	public long getGroupId() {
		return _groupId;
	}

	public int[] getLayoutTypes() {
		return _layoutTypes;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setLayoutTypes(int... layoutTypes) {
		_layoutTypes = layoutTypes;
	}

	private long _groupId;
	private int[] _layoutTypes;

}