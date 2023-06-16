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

package com.liferay.fragment.item.selector.criterion;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.item.selector.BaseItemSelectorCriterion;

import java.util.Set;

/**
 * @author Pavel Savinov
 */
public class FragmentEntryItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public Set<String> getInputTypes() {
		return _inputTypes;
	}

	public int getType() {
		return _type;
	}

	public void setInputTypes(Set<String> inputTypes) {
		_inputTypes = inputTypes;
	}

	public void setType(int type) {
		_type = type;
	}

	private Set<String> _inputTypes;
	private int _type = FragmentConstants.TYPE_COMPONENT;

}