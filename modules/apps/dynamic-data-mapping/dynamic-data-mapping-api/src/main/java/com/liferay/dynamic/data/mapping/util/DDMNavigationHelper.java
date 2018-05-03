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

/**
 * @author Rafael Praxedes
 */
public interface DDMNavigationHelper {

	public static final String EDIT_STRUCTURE = "EDIT_STRUCTURE";

	public static final String EDIT_TEMPLATE = "EDIT_TEMPLATE";

	public static final String SELECT_STRUCTURE = "SELECT_STRUCTURE";

	public static final String SELECT_TEMPLATE = "SELECT_TEMPLATE";

	public static final String VIEW_STRUCTURES = "VIEW_STRUCTURES";

	public static final String VIEW_TEMPLATES = "VIEW_TEMPLATES";

	public boolean isNavigationStartsOnEditStructure(
		LiferayPortletRequest liferayPortletRequest);

	public boolean isNavigationStartsOnEditTemplate(
		LiferayPortletRequest liferayPortletRequest);

	public boolean isNavigationStartsOnSelectStructure(
		LiferayPortletRequest liferayPortletRequest);

	public boolean isNavigationStartsOnSelectTemplate(
		LiferayPortletRequest liferayPortletRequest);

	public boolean isNavigationStartsOnViewStructures(
		LiferayPortletRequest liferayPortletRequest);

	public boolean isNavigationStartsOnViewTemplates(
		LiferayPortletRequest liferayPortletRequest);

}