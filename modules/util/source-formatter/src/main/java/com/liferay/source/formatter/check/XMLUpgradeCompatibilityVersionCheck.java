/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class XMLUpgradeCompatibilityVersionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		_upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION, absolutePath);

		if ((_upgradeToVersion == null) || !fileName.endsWith(".xml")) {
			return content;
		}

		return _checkNewCompatibilityVersion(content);
	}

	private String _checkNewCompatibilityVersion(String content) {
		Matcher matcher = _compatibilityVersionPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String versions = matcher.group(1);

		String[] upgradeToVersionParts = StringUtil.split(
			_upgradeToVersion, StringPool.PERIOD);

		String newVersion = StringBundler.concat(
			"<version>", upgradeToVersionParts[0], StringPool.PERIOD,
			upgradeToVersionParts[1], ".0+</version>");

		if (versions.contains(newVersion)) {
			return content;
		}

		return StringUtil.replace(
			content, versions, newVersion + StringPool.NEW_LINE);
	}

	private static final Pattern _compatibilityVersionPattern = Pattern.compile(
		"\\s<compatibility>\\s*((<version>.+<\\/version>\\s*)+)" +
			"\\s<\\/compatibility>");

	private String _upgradeToVersion;

}