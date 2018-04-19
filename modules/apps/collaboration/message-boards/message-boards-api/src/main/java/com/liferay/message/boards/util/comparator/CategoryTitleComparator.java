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

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBCategory;

/**
 * @author David Zhang
 */
public class CategoryTitleComparator<T> extends MBEntryTitleComparator<T> {

	public CategoryTitleComparator(boolean ascending) {
		super(ascending);
	}

	/**
	 * @deprecated As of 4.0.0, Since 7.1.0, replaced by {@link #getMBEntryTitle(Object)}
	 */
	@Deprecated
	protected String getCategoryName(Object obj) {
		return getMBEntryTitle(obj);
	}

	@Override
	protected String getMBEntryTitle(Object obj) {
		if (obj instanceof MBCategory) {
			MBCategory mbCategory = (MBCategory)obj;

			return mbCategory.getName();
		}

		return null;
	}

}