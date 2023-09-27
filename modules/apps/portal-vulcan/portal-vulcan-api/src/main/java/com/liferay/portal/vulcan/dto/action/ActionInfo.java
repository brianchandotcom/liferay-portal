/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.dto.action;

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