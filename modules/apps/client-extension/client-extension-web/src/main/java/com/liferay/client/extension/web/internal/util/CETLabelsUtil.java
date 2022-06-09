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

package com.liferay.client.extension.web.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CamelCaseUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class CETLabelsUtil {

	public static String getAddLabel(
		HttpServletRequest httpServletRequest, String type) {

		return _getLabel(httpServletRequest, "add-", type);
	}

	public static String getNewLabel(
		HttpServletRequest httpServletRequest, String type) {

		return _getLabel(httpServletRequest, "new-", type);
	}

	private static String _getLabel(
		HttpServletRequest httpServletRequest, String prefix, String type) {

		return LanguageUtil.get(
			httpServletRequest,
			prefix + CamelCaseUtil.fromCamelCase(type, CharPool.DASH));
	}

}