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

package com.liferay.portal.settings.portlet.action;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;

/**
 * @author Michael C. Han
 */
public class PortalSettingsParameterUtil {

	public static boolean getBoolean(
		ActionRequest actionRequest,
		PortalSettingsFormContributor portalSettingsFormContributor,
		String name) {

		return ParamUtil.getBoolean(
			actionRequest,
			portalSettingsFormContributor.getParameterNamespace() + name);
	}

	public static String getString(
		ActionRequest actionRequest,
		PortalSettingsFormContributor portalSettingsFormContributor,
		String name) {

		return ParamUtil.getString(
			actionRequest,
			portalSettingsFormContributor.getParameterNamespace() + name);
	}

}