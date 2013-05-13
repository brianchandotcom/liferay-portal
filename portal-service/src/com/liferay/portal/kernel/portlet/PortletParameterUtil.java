/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Shuyang Zhou
 */
public class PortletParameterUtil {

	public static String addNamespace(String portletId, String queryString) {
		String[] parameters = StringUtil.split(queryString, CharPool.AMPERSAND);

		StringBundler sb = new StringBundler(2 + parameters.length * 4);

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