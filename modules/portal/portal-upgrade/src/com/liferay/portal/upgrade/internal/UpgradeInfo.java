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

package com.liferay.portal.upgrade.internal;

import com.liferay.portal.Upgrade;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
public class UpgradeInfo {

	public UpgradeInfo(
		String fromVersionString, String toVersionString, Upgrade upgrade) {

		_fromVersionString = fromVersionString;
		_toVersionString = toVersionString;
		_upgrade = upgrade;
	}

	public String getFromVersionString() {
		return _fromVersionString;
	}

	public String getToVersionString() {
		return _toVersionString;
	}

	public Upgrade getUpgrade() {
		return _upgrade;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{fromVersionString=");
		sb.append(_fromVersionString);
		sb.append(", toVersionString=");
		sb.append(_toVersionString);
		sb.append(", upgrade=");
		sb.append(_upgrade);
		sb.append("}");

		return sb.toString();
	}

	private final String _fromVersionString;
	private final String _toVersionString;
	private final Upgrade _upgrade;

}