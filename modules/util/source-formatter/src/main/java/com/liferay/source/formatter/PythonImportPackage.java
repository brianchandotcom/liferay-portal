/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.tools.ImportPackage;

/**
 * @author Alan Huang
 */
public class PythonImportPackage extends ImportPackage {

	public PythonImportPackage(String importString, String line) {
		super(importString, false, line);

		_importString = importString;
	}

	@Override
	public String getPackageLevel() {
		int pos = _importString.indexOf(StringPool.PERIOD);

		if (pos == -1) {
			return _importString;
		}

		return _importString.substring(0, pos);
	}

	private final String _importString;

}