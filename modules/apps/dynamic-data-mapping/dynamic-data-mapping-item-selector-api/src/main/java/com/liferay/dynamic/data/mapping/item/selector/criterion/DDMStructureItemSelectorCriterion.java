/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Eudaldo Alonso
 */
public class DDMStructureItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public long getClassNameId() {
		return _classNameId;
	}

	public boolean isMultiSelection() {
		return _multiSelection;
	}

	public boolean isSelectAncestorScopes() {
		return _selectAncestorScopes;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setMultiSelection(boolean multiSelection) {
		_multiSelection = multiSelection;
	}

	public void setSelectAncestorScopes(boolean selectAncestorScopes) {
		_selectAncestorScopes = selectAncestorScopes;
	}

	private long _classNameId;
	private boolean _multiSelection;
	private boolean _selectAncestorScopes = true;

}