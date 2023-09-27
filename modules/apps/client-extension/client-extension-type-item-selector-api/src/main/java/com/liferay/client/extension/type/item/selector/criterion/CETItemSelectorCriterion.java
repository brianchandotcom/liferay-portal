/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Víctor Galán
 */
public class CETItemSelectorCriterion extends BaseItemSelectorCriterion {

	public String getType() {
		return _type;
	}

	public boolean isMultipleSelection() {
		return _multipleSelection;
	}

	public void setMultipleSelection(boolean multipleSelection) {
		_multipleSelection = multipleSelection;
	}

	public void setType(String type) {
		_type = type;
	}

	private boolean _multipleSelection;
	private String _type;

}