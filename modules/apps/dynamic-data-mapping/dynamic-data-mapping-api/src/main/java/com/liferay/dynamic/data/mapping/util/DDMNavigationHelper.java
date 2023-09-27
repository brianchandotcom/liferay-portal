/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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