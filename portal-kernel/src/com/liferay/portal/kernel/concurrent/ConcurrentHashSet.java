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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.util.MapBackedSet;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class ConcurrentHashSet<E> extends MapBackedSet<E> {

	public ConcurrentHashSet() {
		super(new ConcurrentHashMap<E, Boolean>());
	}

	public ConcurrentHashSet(int capacity) {
		super(new ConcurrentHashMap<E, Boolean>(capacity));
	}

	public ConcurrentHashSet(Set<E> set) {
		super(new ConcurrentHashMap<E, Boolean>());

		addAll(set);
	}

}