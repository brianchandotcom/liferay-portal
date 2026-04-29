/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.jaxrs.exception.mapper;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class ExceptionMapperUtil {

	public static String getType(String className) {
		if (className == null) {
			return null;
		}

		List<String> segments = StringUtil.split(className, CharPool.PERIOD);

		if (segments.isEmpty()) {
			return className;
		}

		return StringUtil.replace(
			segments.get(segments.size() - 1), CharPool.DOLLAR,
			CharPool.PERIOD);
	}

}