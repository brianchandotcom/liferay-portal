/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.extender.internal;

import com.liferay.petra.string.StringPool;

import java.util.Dictionary;

import org.osgi.framework.Bundle;

/**
 * @author Raymond Augé
 */
public class WabUtil {

	public static String getWebContextPath(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		return headers.get("Web-ContextPath");
	}

}