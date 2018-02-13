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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringPool;

/**
 * @author Peter Shin
 */
public class PropertiesServiceKeysCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

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