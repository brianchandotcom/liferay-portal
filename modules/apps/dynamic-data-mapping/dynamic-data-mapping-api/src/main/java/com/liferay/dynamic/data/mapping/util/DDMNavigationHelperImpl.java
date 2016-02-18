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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;

/**
 * @author Rafael Praxedes
 */
public class DDMNavigationHelperImpl implements DDMNavigationHelper {

	public boolean startsOnEditStructures(
		LiferayPortletRequest liferayPortletRequest) {

		return isStartedOn(
			liferayPortletRequest, _NAVIGATION_START_POINT_EDIT_STRUCTURE);
	}

	public boolean startsOnEditTemplates(
		LiferayPortletRequest liferayPortletRequest) {

		return isStartedOn(
			liferayPortletRequest, _NAVIGATION_START_POINT_EDIT_TEMPLATE);
	}

	public boolean startsOnSelectStructures(
		LiferayPortletRequest liferayPortletRequest) {

		return isStartedOn(
			liferayPortletRequest, _NAVIGATION_START_POINT_SELECT_STRUCTURE);
	}

	public boolean startsOnSelectTemplates(
		LiferayPortletRequest liferayPortletRequest) {

		return isStartedOn(
			liferayPortletRequest, _NAVIGATION_START_POINT_SELECT_TEMPLATE);
	}

	public boolean startsOnStructures(
		LiferayPortletRequest liferayPortletRequest) {

		return isStartedOn(
			liferayPortletRequest, _NAVIGATION_START_POINT_STRUCTURE);
	}

	public boolean startsOnTemplates(
		LiferayPortletRequest liferayPortletRequest) {

		return isStartedOn(
			liferayPortletRequest, _NAVIGATION_START_POINT_TEMPLATE);
	}

	private boolean isStartedOn(
		LiferayPortletRequest liferayPortletRequest, String startPoint) {

		String navStartsOn = ParamUtil.getString(
			liferayPortletRequest, "navStartsOn");

		return navStartsOn.equals(startPoint);
	}

	private static final String _NAVIGATION_START_POINT_EDIT_STRUCTURE =
		"editStructure";

	private static final String _NAVIGATION_START_POINT_EDIT_TEMPLATE =
		"editTemplate";

	private static final String _NAVIGATION_START_POINT_SELECT_STRUCTURE =
		"selectStructures";

	private static final String _NAVIGATION_START_POINT_SELECT_TEMPLATE =
		"selectTemplates";

	private static final String _NAVIGATION_START_POINT_STRUCTURE =
		"structures";

	private static final String _NAVIGATION_START_POINT_TEMPLATE = "templates";

}