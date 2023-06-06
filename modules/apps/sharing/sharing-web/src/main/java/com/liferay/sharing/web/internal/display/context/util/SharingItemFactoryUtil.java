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

package com.liferay.sharing.web.internal.display.context.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Joao Victor Alves
 */
public class SharingItemFactoryUtil {

	public static String getManageCollaboratorsLabel(
		HttpServletRequest httpServletRequest) {

		return _getLabel("manage-collaborators", httpServletRequest);
	}

	public static String getSharingLabel(
		HttpServletRequest httpServletRequest) {

		return _getLabel("share", httpServletRequest);
	}

	private static String _getLabel(
		String key, HttpServletRequest httpServletRequest) {

		return LanguageUtil.get(PortalUtil.getLocale(httpServletRequest), key);
	}

}