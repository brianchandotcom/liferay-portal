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

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.lar.ThemeExporter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.model.adapter.StagedTheme;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = ThemeExporter.class)
public class ThemeExporterImpl implements ThemeExporter {

	@Override
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

		Theme theme = layoutSet.getTheme();

		if (theme == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to export theme " + layoutSet.getThemeId());
			}

			return;
		}

		StagedTheme stagedTheme = ModelAdapterUtil.adapt(
			theme, Theme.class, StagedTheme.class);

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			Element layoutSetElement = portletDataContext.getExportDataElement(
				layoutSet);

			portletDataContext.addReferenceElement(
				layoutSet, layoutSetElement, stagedTheme,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}

		_exportThemeSettings(
			portletDataContext, stagedTheme.getThemeId(),
			layoutSet.getColorSchemeId(), layoutSet.getCss());
	}

	@Override
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

		StagedTheme stagedTheme = ModelAdapterUtil.adapt(
			layoutSetBranch.getTheme(), Theme.class, StagedTheme.class);

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			Element layoutSetBranchElement =
				portletDataContext.getExportDataElement(layoutSetBranch);

			portletDataContext.addReferenceElement(
				layoutSetBranch, layoutSetBranchElement, stagedTheme,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}

		_exportThemeSettings(
			portletDataContext, stagedTheme.getThemeId(),
			layoutSetBranch.getColorSchemeId(), layoutSetBranch.getCss());
	}

	private void _exportThemeSettings(
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

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeExporterImpl.class);

}