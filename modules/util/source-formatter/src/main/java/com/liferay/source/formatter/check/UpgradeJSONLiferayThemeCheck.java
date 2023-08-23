/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJSONLiferayThemeCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		Matcher matcher = _liferayThemeVersionPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION, absolutePath);

		String[] upgradeToVersionParts = StringUtil.split(
			upgradeToVersion, StringPool.PERIOD);

		String newVersion = StringBundler.concat(
			upgradeToVersionParts[0], ".", upgradeToVersionParts[1]);

		if (Objects.equals(newVersion, matcher.group(2))) {
			return content;
		}

		return StringUtil.replace(content, matcher.group(2), newVersion);
	}

	@Override
	protected String[] getValidExtensions() {
		return new String[] {"json"};
	}

	private static final Pattern _liferayThemeVersionPattern = Pattern.compile(
		"\"liferayTheme\":\\s*\\{(\\s*[^}]+)\"version\":\\s*\"(.+)\"");

}