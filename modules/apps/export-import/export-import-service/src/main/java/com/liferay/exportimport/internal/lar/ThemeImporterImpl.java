/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.lar.ThemeImporter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(service = ThemeImporter.class)
public class ThemeImporterImpl implements ThemeImporter {

	@Override
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

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long importGroupId = groupIds.get(layoutSet.getGroupId());

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

		_layoutSetLocalService.updateLookAndFeel(
			importGroupId, layoutSet.isPrivateLayout(), themeId, colorSchemeId,
			layoutSet.getCss());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeImporterImpl.class);

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}