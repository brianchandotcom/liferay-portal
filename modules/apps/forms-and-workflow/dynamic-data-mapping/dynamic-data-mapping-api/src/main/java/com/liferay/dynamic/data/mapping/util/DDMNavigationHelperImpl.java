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

	@Override
	public boolean isNavigationStartsOnEditStructure(
		LiferayPortletRequest liferayPortletRequest) {

		return isNavigationStartsOn(liferayPortletRequest, EDIT_STRUCTURE);
	}

	@Override
	public boolean isNavigationStartsOnEditTemplate(
		LiferayPortletRequest liferayPortletRequest) {

		return isNavigationStartsOn(liferayPortletRequest, EDIT_TEMPLATE);
	}

	@Override
	public boolean isNavigationStartsOnSelectStructure(
		LiferayPortletRequest liferayPortletRequest) {

		return isNavigationStartsOn(liferayPortletRequest, SELECT_STRUCTURE);
	}

	@Override
	public boolean isNavigationStartsOnSelectTemplate(
		LiferayPortletRequest liferayPortletRequest) {

		return isNavigationStartsOn(liferayPortletRequest, SELECT_TEMPLATE);
	}

	@Override
	public boolean isNavigationStartsOnViewStructures(
		LiferayPortletRequest liferayPortletRequest) {

		return isNavigationStartsOn(liferayPortletRequest, VIEW_STRUCTURES);
	}

	@Override
	public boolean isNavigationStartsOnViewTemplates(
		LiferayPortletRequest liferayPortletRequest) {

		return isNavigationStartsOn(liferayPortletRequest, VIEW_TEMPLATES);
	}

	protected boolean isNavigationStartsOn(
		LiferayPortletRequest liferayPortletRequest, String startPoint) {

		String navigationStartsOn = ParamUtil.getString(
			liferayPortletRequest, "navigationStartsOn");

		return navigationStartsOn.equals(startPoint);
	}

}