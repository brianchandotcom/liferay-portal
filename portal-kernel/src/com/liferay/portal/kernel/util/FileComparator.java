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

package com.liferay.portal.kernel.util;

import java.io.File;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class FileComparator implements Comparator<File> {

	@Override
	public int compare(File file1, File file2) {
		String name1 = file1.getName();
		String name2 = file2.getName();

		return name1.compareTo(name2);
	}

}