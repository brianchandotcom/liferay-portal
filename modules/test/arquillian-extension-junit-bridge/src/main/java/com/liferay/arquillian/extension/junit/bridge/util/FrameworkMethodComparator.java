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

package com.liferay.arquillian.extension.junit.bridge.util;

import java.util.Comparator;

import org.junit.runners.model.FrameworkMethod;

/**
 * @author Shuyang Zhou
 */
public class FrameworkMethodComparator implements Comparator<FrameworkMethod> {

	public static final Comparator<FrameworkMethod> INSTANCE =
		new FrameworkMethodComparator();

	@Override
	public int compare(
		FrameworkMethod frameworkMethod1, FrameworkMethod frameworkMethod2) {

		String name = frameworkMethod1.getName();

		return name.compareTo(frameworkMethod2.getName());
	}

	private FrameworkMethodComparator() {
	}

}