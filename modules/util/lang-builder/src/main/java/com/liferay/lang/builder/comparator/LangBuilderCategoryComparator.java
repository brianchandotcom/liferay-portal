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