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

package com.liferay.content.dashboard.item.action.provider;

import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemVersionActionException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stefan Tanasie
 */
public interface ContentDashboardItemVersionActionProvider<T> {

	public ContentDashboardItemVersionAction
			getContentDashboardItemVersionAction(
				T t, HttpServletRequest httpServletRequest)
		throws ContentDashboardItemVersionActionException;

	public boolean isShow(T t, HttpServletRequest httpServletRequest);

}