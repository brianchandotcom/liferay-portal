/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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