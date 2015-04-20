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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Carlos Sierra Andrés
 */
public class ImportPackage implements Comparable<ImportPackage> {

	@Override
	public int compareTo(ImportPackage importPackage) {
		if (_isStatic != importPackage.isStatic()) {
			if (_isStatic) {
				return -1;
			}
			else {
				return 1;
			}
		}

		return _importString.compareTo(importPackage.getImportString());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ImportPackage)) {
			return false;
		}

		ImportPackage importPackage = (ImportPackage)obj;

		if ((_isStatic == importPackage.isStatic()) &&
			_importString.equals(importPackage.getImportString())) {

			return true;
		}

		return false;
	}

	public String getImportString() {
		return _importString;
	}

	public String getLine() {
		return _line;
	}

	public String getPackageLevel() {
		int pos = _importString.indexOf(StringPool.PERIOD);

		pos = _importString.indexOf(StringPool.PERIOD, pos + 1);

		if (pos == -1) {
			pos = _importString.indexOf(StringPool.PERIOD);
		}

		return _importString.substring(0, pos);
	}

	@Override
	public int hashCode() {
		return _importString.hashCode();
	}

	public boolean isGroupedWith(ImportPackage importPackage) {
		if (_isStatic != importPackage.isStatic()) {
			return false;
		}

		String packageLevel = getPackageLevel();

		if (packageLevel.equals(importPackage.getPackageLevel())) {
			return true;
		}

		return false;
	}

	public boolean isStatic() {
		return _isStatic;
	}

	protected ImportPackage(
		String importString, boolean isStatic, String line) {

		_importString = importString;
		_isStatic = isStatic;
		_line = line;
	}

	private final String _importString;
	private boolean _isStatic;
	private final String _line;

}