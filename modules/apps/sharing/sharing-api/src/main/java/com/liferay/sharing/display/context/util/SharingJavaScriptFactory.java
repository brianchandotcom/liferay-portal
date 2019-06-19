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

package com.liferay.sharing.display.context.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public interface SharingJavaScriptFactory {

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public default String createManageCollaboratorsJavaScript(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return StringPool.BLANK;
	}

	public String createManageCollaboratorsOnClickMethod(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public default String createSharingJavaScript(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return StringPool.BLANK;
	}

	public String createSharingOnClickMethod(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException;

}