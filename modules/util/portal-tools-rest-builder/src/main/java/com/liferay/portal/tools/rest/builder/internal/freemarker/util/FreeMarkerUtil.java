/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.freemarker.util;

import com.liferay.portal.tools.rest.builder.internal.freemarker.FreeMarker;

import java.io.File;

import java.util.Map;

/**
 * @author Peter Shin
 */
public class FreeMarkerUtil {

	public static String processTemplate(
			File copyrightFile, String copyrightYear, String name,
			Map<String, Object> context)
		throws Exception {

		return _freeMarker.processTemplate(
			copyrightFile, copyrightYear,
			"com/liferay/portal/tools/rest/builder/dependencies/" + name +
				".ftl",
			context);
	}

	private static final FreeMarker _freeMarker = new FreeMarker();

}