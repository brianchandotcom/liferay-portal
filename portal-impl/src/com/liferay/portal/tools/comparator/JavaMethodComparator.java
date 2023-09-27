/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.comparator;

import com.thoughtworks.qdox.model.JavaMethod;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class JavaMethodComparator implements Comparator<JavaMethod> {

	@Override
	public int compare(JavaMethod method1, JavaMethod method2) {
		String name1 = method1.getName();
		String name2 = method2.getName();

		return name1.compareTo(name2);
	}

}