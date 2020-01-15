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

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import java.util.Comparator;

import javax.annotation.Priority;

/**
 * @author  Neil Griffin
 */
public class DescendingPriorityComparator<T> implements Comparator<Object> {

	public DescendingPriorityComparator() {
		this(0);
	}

	public DescendingPriorityComparator(int defaultPriority) {
		_defaultPriority = defaultPriority;
	}

	@Override
	public int compare(Object object1, Object object2) {
		Class<?> class1 = object1.getClass();

		Priority priority1 = class1.getAnnotation(Priority.class);

		Class<?> class2 = object2.getClass();

		Priority priority2 = class2.getAnnotation(Priority.class);

		if ((priority1 == null) && (priority2 == null)) {
			return 0;
		}
		else if (priority1 == null) {
			return Integer.compare(priority2.value(), _defaultPriority);
		}
		else if (priority2 == null) {
			return Integer.compare(_defaultPriority, priority1.value());
		}
		else {
			return Integer.compare(priority1.value(), priority2.value());
		}
	}

	private int _defaultPriority;

}