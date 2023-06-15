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

package com.liferay.portal.vulcan.action;

/**
 * @author Carlos Correa
 */
public class ActionInfo {

	public ActionInfo(
		String actionName, Class<?> resourceClass, String resourceMethodName) {

		_actionName = actionName;
		_resourceClass = resourceClass;
		_resourceMethodName = resourceMethodName;
	}

	public String getActionName() {
		return _actionName;
	}

	public Class<?> getResourceClass() {
		return _resourceClass;
	}

	public String getResourceMethodName() {
		return _resourceMethodName;
	}

	private final String _actionName;
	private final Class<?> _resourceClass;
	private final String _resourceMethodName;

}