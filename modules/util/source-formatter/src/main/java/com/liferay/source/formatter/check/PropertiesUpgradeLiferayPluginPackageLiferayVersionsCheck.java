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

	protected boolean skipFix(String absolutePath) {
		if (!absolutePath.contains("/modules/")) {
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