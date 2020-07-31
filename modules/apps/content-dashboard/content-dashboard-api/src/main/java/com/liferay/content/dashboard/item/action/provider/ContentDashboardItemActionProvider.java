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

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemActionException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author David Arques
 */
public interface ContentDashboardItemActionProvider<T> {

	public ContentDashboardItemAction getContentDashboardItemAction(
			T t, HttpServletRequest httpServletRequest)
		throws ContentDashboardItemActionException;

	public String getKey();

	public ContentDashboardItemAction.Type getType();

	public boolean isShow(T t, HttpServletRequest httpServletRequest);

}