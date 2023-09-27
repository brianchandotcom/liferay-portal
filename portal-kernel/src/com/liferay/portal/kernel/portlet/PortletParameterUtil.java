/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Shuyang Zhou
 */
public class PortletParameterUtil {

	public static String addNamespace(String portletId, String queryString) {
		String[] parameters = StringUtil.split(queryString, CharPool.AMPERSAND);

		if (parameters.length == 0) {
			return "p_p_id=".concat(portletId);
		}

		StringBundler sb = new StringBundler(2 + (parameters.length * 4));

		sb.append("p_p_id=");
		sb.append(portletId);

		for (String parameter : parameters) {
			sb.append("&_");
			sb.append(portletId);
			sb.append("_");
			sb.append(parameter);
		}

		return sb.toString();
	}

}