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

package com.liferay.asset.publisher.web.servlet.taglib.ui;

import com.liferay.asset.publisher.web.constants.AssetPublisherConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletPreferences;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseConfigurationFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return StringPool.BLANK;
	}

	@Override
	public String getFormNavigatorId() {
		return AssetPublisherConstants.FORM_NAVIGATOR_ID_CONFIGURATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, getKey());
	}

	protected boolean isDynamicAssetSelection() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
				themeDisplay.getLayout(), portletDisplay.getPortletResource());

		String selectionStyle = GetterUtil.getString(
			portletSetup.getValue("selectionStyle", null), "dynamic");

		if (Objects.equals(selectionStyle, "dynamic")) {
			return true;
		}

		return false;
	}

}