/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.views.web.internal.display.context;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marko Cikos
 */
public class DataSetItemSelectorDisplayContext {

	public DataSetItemSelectorDisplayContext(
		HttpServletRequest httpServletRequest) {

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getClassName() {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				_themeDisplay.getCompanyId(),
				FeatureFlagManagerUtil.isEnabled("LPD-15729") ? "DSMDataSet" :
					"FDSView");

		if (objectDefinition != null) {
			return objectDefinition.getClassName();
		}

		return ObjectDefinition.class.getName();
	}

	public long getClassNameId() {
		return PortalUtil.getClassNameId(getClassName());
	}

	private final ThemeDisplay _themeDisplay;

}