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

import java.io.IOException;

/**
 * @author Kevin Lee
 */
public class XMLUpgradeDTDVersionCheck extends XMLDTDVersionCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		_upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION, absolutePath);

		if (_upgradeToVersion == null) {
			return content;
		}

		return super.doProcess(fileName, absolutePath, content);
	}

	@Override
	protected String getLPVersion() {
		String[] upgradeToVersionParts = StringUtil.split(
			_upgradeToVersion, StringPool.PERIOD);

		if (upgradeToVersionParts == null) {
			return null;
		}

		return StringBundler.concat(
			upgradeToVersionParts[0], ".", upgradeToVersionParts[1], ".0");
	}

	@Override
	protected String getLPVersionDTD() {
		String[] upgradeToVersionParts = StringUtil.split(
			_upgradeToVersion, StringPool.PERIOD);

		if (upgradeToVersionParts == null) {
			return null;
		}

		return StringBundler.concat(
			upgradeToVersionParts[0], "_", upgradeToVersionParts[1], "_0");
	}

	private String _upgradeToVersion;

}