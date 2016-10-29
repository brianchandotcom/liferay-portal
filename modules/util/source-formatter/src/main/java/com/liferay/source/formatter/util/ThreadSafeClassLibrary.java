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

package com.liferay.source.formatter.util;

import com.thoughtworks.qdox.model.ClassLibrary;

import java.util.Collection;

/**
 * @author Shuyang Zhou
 */
public class ThreadSafeClassLibrary extends ClassLibrary {

	public ThreadSafeClassLibrary() {
		addDefaultLoader();
	}

	@Override
	public void add(String className) {
		synchronized (_classNamesLock) {
			super.add(className);
		}
	}

	@Override
	public Collection<?> all() {
		synchronized (_classNamesLock) {
			return super.all();
		}
	}

	@Override
	public boolean contains(String className) {
		synchronized (_classNamesLock) {
			return super.contains(className);
		}
	}

	@Override
	public Class<?> getClass(String className) {
		synchronized (_classNameToClassMapLock) {
			return super.getClass(className);
		}
	}

	private final Object _classNamesLock = new Object();
	private final Object _classNameToClassMapLock = new Object();

}