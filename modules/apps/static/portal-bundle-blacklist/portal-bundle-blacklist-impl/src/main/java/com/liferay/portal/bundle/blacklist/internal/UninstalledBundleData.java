/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.bundle.blacklist.internal;

import com.liferay.petra.string.StringBundler;

/**
 * @author Matthew Tambara
 */
public class UninstalledBundleData {

	public UninstalledBundleData(String location, int startLevel) {
		_location = location;
		_startLevel = startLevel;
	}

	public String getLocation() {
		return _location;
	}

	public int getStartLevel() {
		return _startLevel;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{location=", _location, ", startLevel=", _startLevel, "}");
	}

	private final String _location;
	private final int _startLevel;

}