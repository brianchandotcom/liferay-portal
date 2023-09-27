/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.scripting.util;

import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

/**
 * @author Carolina Barbosa
 */
public class ServerScriptingUtil {

	public static Set<String> getSupportedLanguages() {
		return SetUtil.fromArray(new String[] {"groovy"});
	}

}