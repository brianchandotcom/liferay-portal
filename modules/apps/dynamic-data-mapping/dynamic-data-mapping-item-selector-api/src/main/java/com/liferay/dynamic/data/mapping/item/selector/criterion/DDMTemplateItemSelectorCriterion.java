/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Eudaldo Alonso
 */
public class DDMTemplateItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public long getClassNameId() {
		return _classNameId;
	}

	public long getDDMStructureId() {
		return _ddmStructureId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setDDMStructureId(long ddmStructureId) {
		_ddmStructureId = ddmStructureId;
	}

	private long _classNameId;
	private long _ddmStructureId;

}