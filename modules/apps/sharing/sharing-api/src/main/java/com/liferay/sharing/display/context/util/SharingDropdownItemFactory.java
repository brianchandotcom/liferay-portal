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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public interface SharingDropdownItemFactory {

	public DropdownItem createManageCollaboratorsDropdownItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public DropdownItem createShareDropdownItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException;

}