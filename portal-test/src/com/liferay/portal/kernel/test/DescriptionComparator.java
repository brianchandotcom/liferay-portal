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

package com.liferay.portal.kernel.test;

import java.util.Comparator;

import org.junit.runner.Description;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class DescriptionComparator implements Comparator<Description> {

	@Override
	public int compare(Description description1, Description description2) {
		String displayName1 = description1.getDisplayName();
		String displayName2 = description2.getDisplayName();

		return displayName1.compareTo(displayName2);
	}

}