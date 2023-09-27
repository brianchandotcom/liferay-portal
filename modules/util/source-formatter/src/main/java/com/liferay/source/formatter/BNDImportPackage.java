/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ImportPackage;

/**
 * @author Alan Huang
 */
public class BNDImportPackage extends ImportPackage {

	public BNDImportPackage(String importString, String line) {
		super(importString, false, line);

		_importString = importString;
	}

	@Override
	public int compareTo(ImportPackage importPackage) {
		String importPackageImportString = importPackage.getImportString();

		int value = _importString.compareTo(importPackageImportString);

		if (_importString.startsWith(StringPool.EXCLAMATION) ||
			importPackageImportString.startsWith(StringPool.EXCLAMATION)) {

			return value;
		}

		int startsWithWeight = StringUtil.startsWithWeight(
			_importString, importPackageImportString);

		String importStringPart1 = _importString.substring(startsWithWeight);
		String importStringPart2 = importPackageImportString.substring(
			startsWithWeight);

		if (importStringPart1.equals(StringPool.STAR) ||
			importStringPart2.equals(StringPool.STAR)) {

			return -value;
		}

		return value;
	}

	@Override
	public String getPackageLevel() {
		int pos = _importString.indexOf(StringPool.SLASH);

		if (pos != -1) {
			pos = _importString.indexOf(StringPool.SLASH, pos + 1);

			if (pos == -1) {
				return _importString;
			}

			return _importString.substring(0, pos);
		}

		pos = _importString.indexOf(StringPool.PERIOD);

		pos = _importString.indexOf(StringPool.PERIOD, pos + 1);

		if (pos == -1) {
			return _importString;
		}

		return _importString.substring(0, pos);
	}

	private final String _importString;

}