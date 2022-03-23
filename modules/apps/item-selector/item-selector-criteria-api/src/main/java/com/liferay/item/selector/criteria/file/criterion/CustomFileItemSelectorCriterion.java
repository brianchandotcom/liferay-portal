/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.criteria.file.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Roberto Díaz
 */
public class CustomFileItemSelectorCriterion extends BaseItemSelectorCriterion {

	public String[] getExtensions() {
		return _extensions;
	}

	public void setExtensions(String[] extensions) {
		_extensions = extensions;
	}

	private String[] _extensions;

}