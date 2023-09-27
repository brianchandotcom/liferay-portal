/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.internal.registry;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.util.Objects;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
public class UpgradeInfo {

	public UpgradeInfo(
		String fromSchemaVersionString, String toSchemaVersionString,
		int buildNumber, UpgradeStep upgradeStep) {

		_fromSchemaVersionString = fromSchemaVersionString;
		_toSchemaVersionString = toSchemaVersionString;
		_buildNumber = buildNumber;
		_upgradeStep = upgradeStep;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UpgradeInfo)) {
			return false;
		}

		UpgradeInfo upgradeInfo = (UpgradeInfo)object;

		if (Objects.equals(
				_fromSchemaVersionString,
				upgradeInfo._fromSchemaVersionString) &&
			Objects.equals(
				_toSchemaVersionString, upgradeInfo._toSchemaVersionString) &&
			Objects.equals(_upgradeStep, upgradeInfo._upgradeStep)) {

			return true;
		}

		return false;
	}

	public int getBuildNumber() {
		return _buildNumber;
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
	public int hashCode() {
		int hash = HashUtil.hash(0, _fromSchemaVersionString);

		hash = HashUtil.hash(hash, _toSchemaVersionString);
		hash = HashUtil.hash(hash, _upgradeStep);

		return hash;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{fromSchemaVersionString=", _fromSchemaVersionString,
			", toSchemaVersionString=", _toSchemaVersionString,
			", upgradeStep=", _upgradeStep, "}");
	}

	private final int _buildNumber;
	private final String _fromSchemaVersionString;
	private final String _toSchemaVersionString;
	private final UpgradeStep _upgradeStep;

}