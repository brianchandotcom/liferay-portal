/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;

/**
 * @author Peter Shin
 */
public class PropertiesServiceKeysCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("/service.properties")) {
			return content;
		}

		for (String legacyServiceKey : _LEGACY_SERVICE_KEYS) {
			content = content.replaceAll(
				"(\\A|\n)\\s*" + legacyServiceKey + "=.*(\\Z|\n)",
				StringPool.NEW_LINE);
		}

		return content;
	}

	private static final String[] _LEGACY_SERVICE_KEYS = {"build.auto.upgrade"};

}