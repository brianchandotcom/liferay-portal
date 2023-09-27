/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.item.selector.criterion;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.item.selector.BaseItemSelectorCriterion;

import java.util.HashSet;
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

	private Set<String> _inputTypes = new HashSet<>();
	private int _type = FragmentConstants.TYPE_COMPONENT;

}