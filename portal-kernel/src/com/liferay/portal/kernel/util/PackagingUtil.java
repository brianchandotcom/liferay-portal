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

import com.liferay.petra.string.StringPool;

import java.io.File;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * @author Raymond Augé
 */
public class PackagingUtil {

	public static List<String> getPackagesFromPath(File file) {
		Set<String> packages = new HashSet<>();
		Stack<String> packageStack = new Stack<>();

		subPackages(file, packages, packageStack);

		List<String> list = ListUtil.fromCollection(packages);

		Collections.sort(list);

		return list;
	}

	protected static void subPackages(
		File file, Set<String> packages, Stack<String> packageStack) {

		for (File subFile : file.listFiles()) {
			if (subFile.isDirectory()) {
				packageStack.push(subFile.getName());

				String packageName = StringUtil.merge(
					packageStack, StringPool.PERIOD);

				if (packageName.contains(StringPool.PERIOD)) {
					packages.add(packageName);
				}

				subPackages(subFile, packages, packageStack);

				packageStack.pop();
			}
		}
	}

}