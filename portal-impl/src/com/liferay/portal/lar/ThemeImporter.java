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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;

/**
 * @author Mate Thurzo
 */
public class ThemeImporter {

	public static ThemeImporter getInstance() {
		return _instance;
	}

	public void importTheme(
			PortletDataContext portletDataContext, LayoutSet layoutSet)
		throws Exception {

		boolean importThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (_log.isDebugEnabled()) {
			_log.debug("Import theme settings " + importThemeSettings);
		}

		if (!importThemeSettings) {
			return;
		}

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();
		Element headerElement = importDataRootElement.element("header");

		String themeId = layoutSet.getThemeId();
		String colorSchemeId = layoutSet.getColorSchemeId();

		Attribute themeIdAttribute = headerElement.attribute("theme-id");

		if (themeIdAttribute != null) {
			themeId = themeIdAttribute.getValue();
		}

		Attribute colorSchemeIdAttribute = headerElement.attribute(
			"color-scheme-id");

		if (colorSchemeIdAttribute != null) {
			colorSchemeId = colorSchemeIdAttribute.getValue();
		}

		String css = GetterUtil.getString(headerElement.elementText("css"));

		LayoutSetLocalServiceUtil.updateLookAndFeel(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(), themeId,
			colorSchemeId, css, false);
	}

	private ThemeImporter() {
	}

	private static final Log _log = LogFactoryUtil.getLog(ThemeImporter.class);

	private static final ThemeImporter _instance = new ThemeImporter();

}