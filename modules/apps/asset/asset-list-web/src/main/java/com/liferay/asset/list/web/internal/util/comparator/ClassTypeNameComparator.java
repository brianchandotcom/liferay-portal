/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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