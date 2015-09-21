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

import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
public class UpgradeInfo {

	public UpgradeInfo(
		String fromSchemaVersionString, String toSchemaVersionString,
		UpgradeStep upgradeStep) {

		_fromSchemaVersionString = fromSchemaVersionString;
		_toSchemaVersionString = toSchemaVersionString;
		_upgradeStep = upgradeStep;
	}

	public String getFromSchemaVersionString() {
		return _fromSchemaVersionString;
	}

	public String getToSchemaVersionString() {
		return _toSchemaVersionString;
	}

	public UpgradeStep getUpgradeStep() {
		return _upgradeStep;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{fromSchemaVersionString=");
		sb.append(_fromSchemaVersionString);
		sb.append(", toSchemaVersionString=");
		sb.append(_toSchemaVersionString);
		sb.append(", upgradeStep=");
		sb.append(_upgradeStep);
		sb.append("}");

		return sb.toString();
	}

	private final String _fromSchemaVersionString;
	private final String _toSchemaVersionString;
	private final UpgradeStep _upgradeStep;

}