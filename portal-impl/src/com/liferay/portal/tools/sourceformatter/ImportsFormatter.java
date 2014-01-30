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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author André de Oliveira
 */
public class ImportsFormatter {

	public static String format(String imports, int classStartPos)
		throws IOException {

		if (imports.contains("/*") || imports.contains("*/") ||
			imports.contains("//")) {

			return imports + "\n";
		}

		Set<ImportPackage> importPackages = new HashSet<ImportPackage>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(imports));

		try {
			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				ImportPackage importPackage = ImportPackageFactoryUtil.create(
					line);

				if (importPackage != null) {
					importPackages.add(importPackage);
				}
			}
		}
		finally {
			unsyncBufferedReader.close();
		}

		List<ImportPackage> importPackagesSorted = ListUtil.sort(
			new ArrayList<ImportPackage>(importPackages));

		StringBundler sb = new StringBundler(3 * importPackagesSorted.size());

		String temp = null;

		for (int i = 0; i < importPackagesSorted.size(); i++) {
			ImportPackage importPackage = importPackagesSorted.get(i);

			String s = importPackage.getLine();

			int pos = s.indexOf(".");

			pos = s.indexOf(".", pos + 1);

			if (pos == -1) {
				pos = s.indexOf(".");
			}

			String packageLevel = s.substring(classStartPos, pos);

			if ((i != 0) && !packageLevel.equals(temp)) {
				sb.append("\n");
			}

			temp = packageLevel;

			sb.append(s);
			sb.append("\n");
		}

		return sb.toString();
	}

}