/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.filter;

/**
 * @author Pablo Molina
 */
public class TagsInfoFilter implements InfoFilter {

	public static final String FILTER_TYPE_NAME = "tags";

	@Override
	public String getFilterTypeName() {
		return FILTER_TYPE_NAME;
	}

	public String[][] getTagNames() {
		return _tagNames;
	}

	public void setTagNames(String[][] tagNames) {
		_tagNames = tagNames;
	}

	private String[][] _tagNames;

}