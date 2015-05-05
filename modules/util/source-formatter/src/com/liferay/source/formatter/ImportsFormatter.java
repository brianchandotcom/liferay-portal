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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author André de Oliveira
 */
public abstract class ImportsFormatter {

	public String format(String imports) throws IOException {
		if (imports.contains("/*") || imports.contains("*/") ||
			imports.contains("//")) {

			return imports + "\n";
		}

		Set<ImportPackage> importPackages = new TreeSet<>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(imports));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			ImportPackage importPackage = createImportPackage(line);

			if (importPackage != null) {
				importPackages.add(importPackage);
			}
		}

		StringBundler sb = new StringBundler(3 * importPackages.size());

		ImportPackage previousImportPackage = null;

		for (ImportPackage importPackage : importPackages) {
			if ((previousImportPackage != null) &&
				!importPackage.isGroupedWith(previousImportPackage)) {

				sb.append("\n");
			}

			sb.append(importPackage.getLine());
			sb.append("\n");

			previousImportPackage = importPackage;
		}

		return sb.toString();
	}

	protected abstract ImportPackage createImportPackage(String line);

}