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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portlet.layoutsadmin.lar.StagedTheme;
import com.liferay.portlet.layoutsadmin.lar.StagedThemeImpl;

/**
 * @author Mate Thurzo
 */
public class ThemeExporter {

	public static ThemeExporter getInstance() {
		return _instance;
	}

	public void exportTheme(
			PortletDataContext portletDataContext, LayoutSet layoutSet)
		throws Exception {

		boolean exportThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (_log.isDebugEnabled()) {
			_log.debug("Export theme settings " + exportThemeSettings);
		}

		if (!exportThemeSettings) {
			return;
		}

		StagedTheme stagedTheme = new StagedThemeImpl(layoutSet.getTheme());

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			Element layoutSetElement = portletDataContext.getExportDataElement(
				layoutSet);

			portletDataContext.addReferenceElement(
				layoutSet, layoutSetElement, stagedTheme,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}

		exportThemeSettings(
			portletDataContext, stagedTheme.getThemeId(),
			layoutSet.getColorSchemeId(), layoutSet.getCss());
	}

	public void exportTheme(
			PortletDataContext portletDataContext,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		boolean exportThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (_log.isDebugEnabled()) {
			_log.debug("Export theme settings " + exportThemeSettings);
		}

		if (!exportThemeSettings) {
			return;
		}

		StagedTheme stagedTheme = new StagedThemeImpl(
			layoutSetBranch.getTheme());

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			Element layoutSetBranchElement =
				portletDataContext.getExportDataElement(layoutSetBranch);

			portletDataContext.addReferenceElement(
				layoutSetBranch, layoutSetBranchElement, stagedTheme,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}

		exportThemeSettings(
			portletDataContext, stagedTheme.getThemeId(),
			layoutSetBranch.getColorSchemeId(), layoutSetBranch.getCss());
	}

	protected void exportThemeSettings(
			PortletDataContext portletDataContext, String themeId,
			String colorSchemeId, String css)
		throws Exception {

		Element exportDataRootElement =
			portletDataContext.getExportDataRootElement();

		Element headerElement = exportDataRootElement.element("header");

		headerElement.addAttribute("theme-id", themeId);
		headerElement.addAttribute("color-scheme-id", colorSchemeId);

		Element cssElement = headerElement.addElement("css");

		cssElement.addCDATA(css);
	}

	private ThemeExporter() {
	}

	private static final Log _log = LogFactoryUtil.getLog(ThemeExporter.class);

	private static final ThemeExporter _instance = new ThemeExporter();

}