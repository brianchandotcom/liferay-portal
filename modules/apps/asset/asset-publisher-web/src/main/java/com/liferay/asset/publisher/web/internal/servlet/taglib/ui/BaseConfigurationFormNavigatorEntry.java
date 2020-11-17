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

package com.liferay.asset.publisher.web.internal.servlet.taglib.ui;

import com.liferay.asset.publisher.constants.AssetPublisherConstants;
import com.liferay.frontend.taglib.form.navigator.BaseJSPFormNavigatorEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletPreferences;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseConfigurationFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<Object> {

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

	protected String getSelectionStyle() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletPreferences portletSetup =
			themeDisplay.getStrictLayoutPortletSetup(
				themeDisplay.getLayout(), portletDisplay.getPortletResource());

		return GetterUtil.getString(
			portletSetup.getValue("selectionStyle", null), "dynamic");
	}

	protected boolean isAssetListSelection() {
		if (Objects.equals(getSelectionStyle(), "asset-list")) {
			return true;
		}

		return false;
	}

	protected boolean isDynamicAssetSelection() {
		if (Objects.equals(getSelectionStyle(), "dynamic")) {
			return true;
		}

		return false;
	}

	protected boolean isManualSelection() {
		if (Objects.equals(getSelectionStyle(), "manual")) {
			return true;
		}

		return false;
	}

}