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

package com.liferay.asset.list.web.internal.util.comparator;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Jürgen Kappler
 */
public class ClassTypeNameComparator extends OrderByComparator<ClassType> {

	public ClassTypeNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(ClassType classType1, ClassType classType2) {
		String name1 = classType1.getName();
		String name2 = classType2.getName();

		int value = name1.compareTo(name2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;

}