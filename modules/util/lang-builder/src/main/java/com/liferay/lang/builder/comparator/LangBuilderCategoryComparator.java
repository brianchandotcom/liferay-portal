/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.lang.builder.comparator;

import com.liferay.lang.builder.LangBuilderCategory;

import java.util.Comparator;

/**
 * @author Hugo Huijser
 */
public class LangBuilderCategoryComparator
	implements Comparator<LangBuilderCategory> {

	@Override
	public int compare(
		LangBuilderCategory langBuilderCategory1,
		LangBuilderCategory langBuilderCategory2) {

		return langBuilderCategory1.getIndex() -
			langBuilderCategory2.getIndex();
	}

}