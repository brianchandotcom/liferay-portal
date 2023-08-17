/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

/**
 * @author Kevin Lee
 */
public class PropertiesUpgradeLiferayPluginPackageLiferayVersionsCheck
	extends PropertiesLiferayPluginPackageLiferayVersionsCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return false;
	}

	protected String getLiferayVersion(String absolutePath) {
		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION, absolutePath);

		String[] upgradeToVersionParts = StringUtil.split(
			upgradeToVersion, StringPool.PERIOD);

		if (upgradeToVersionParts.length < 2) {
			return null;
		}

		return StringBundler.concat(
			upgradeToVersionParts[0], ".", upgradeToVersionParts[1], ".0");
	}

	@Override
	protected boolean isSkipFix(String absolutePath) {
		if (!absolutePath.contains("/modules/") &&
			!absolutePath.contains("/themes/")) {

			return true;
		}

		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION, absolutePath);

		if (upgradeToVersion == null) {
			return true;
		}

		return false;
	}

}