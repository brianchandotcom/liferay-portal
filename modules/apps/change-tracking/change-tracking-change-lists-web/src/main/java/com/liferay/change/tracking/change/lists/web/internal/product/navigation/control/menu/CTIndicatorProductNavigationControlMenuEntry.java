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

package com.liferay.change.tracking.change.lists.web.internal.product.navigation.control.menu;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.constants.CTProductNavigationControlMenuCategoryKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.taglib.aui.IconTag;

import java.io.IOException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + CTProductNavigationControlMenuCategoryKeys.CHANGE_TRACKING,
		"product.navigation.control.menu.entry.order:Integer=100"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class CTIndicatorProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), themeDisplay.getUserId());

		String ctCollectionName = StringPool.BLANK;

		if (ctPreferences == null) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				themeDisplay.getLocale(),
				CTIndicatorProductNavigationControlMenuEntry.class);

			ctCollectionName = _language.get(resourceBundle, "production");
		}
		else {
			CTCollection ctCollection =
				_ctCollectionLocalService.fetchCTCollection(
					ctPreferences.getCtCollectionId());

			if (ctCollection != null) {
				ctCollectionName = ctCollection.getName();
			}
		}

		Map<String, String> values = new HashMap<>();

		values.put("ctCollectionName", ctCollectionName);

		PortletURL changeTrackingURL = _portal.getControlPanelPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		try {
			changeTrackingURL.setWindowState(WindowState.MAXIMIZED);
		}
		catch (WindowStateException wse) {
			ReflectionUtil.throwException(wse);
		}

		values.put("changeTrackingURL", changeTrackingURL.toString());

		try {
			IconTag iconTag = new IconTag();

			iconTag.setCssClass("icon-monospaced");
			iconTag.setMarkupView("lexicon");

			if (ctPreferences == null) {
				iconTag.setImage("change-list-disabled");
			}
			else {
				iconTag.setImage("change-list");
			}

			values.put(
				"changeTrackingIcon",
				iconTag.doTagAsString(httpServletRequest, httpServletResponse));
		}
		catch (JspException je) {
			ReflectionUtil.throwException(je);
		}

		StringBundler sb = StringUtil.replaceToStringBundler(
			_TMPL_CONTENT, "${", "}", values);

		sb.writeTo(httpServletResponse.getWriter());

		return true;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), 0);

		if (ctPreferences == null) {
			return false;
		}

		return true;
	}

	private static final String _TMPL_CONTENT = StringUtil.read(
		CTIndicatorProductNavigationControlMenuEntry.class,
		"/META-INF/resources/control/menu/change_tracking_indicator_icon.tmpl");

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}