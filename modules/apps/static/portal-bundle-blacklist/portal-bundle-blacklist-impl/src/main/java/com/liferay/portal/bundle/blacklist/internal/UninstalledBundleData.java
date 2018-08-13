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
		StringBundler sb = new StringBundler(5);

		sb.append("{location=");
		sb.append(_location);
		sb.append(", startLevel=");
		sb.append(_startLevel);
		sb.append("}");

		return sb.toString();
	}

	private final String _location;
	private final int _startLevel;

}