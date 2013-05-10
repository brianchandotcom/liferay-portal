/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.comparator;

import com.thoughtworks.qdox.model.JavaMethod;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class JavaMethodComparator implements Comparator<JavaMethod> {

	@Override
	public int compare(JavaMethod method1, JavaMethod method2) {
		return method1.getName().compareTo(method2.getName());
	}

}