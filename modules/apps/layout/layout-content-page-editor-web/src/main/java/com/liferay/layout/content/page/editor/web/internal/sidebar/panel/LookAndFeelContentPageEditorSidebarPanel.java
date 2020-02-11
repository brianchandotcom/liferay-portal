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

package com.liferay.layout.content.page.editor.web.internal.sidebar.panel;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "service.ranking:Integer=200",
	service = ContentPageEditorSidebarPanel.class
)
public class LookAndFeelContentPageEditorSidebarPanel
	implements ContentPageEditorSidebarPanel {

	@Override
	public String getIcon() {
		return "format";
	}

	@Override
	public String getId() {
		return "lookAndFeel";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "look-and-feel");
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		PortletURL lookAndFeelURL = _portal.getControlPanelPortletURL(
			httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		lookAndFeelURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout");

		lookAndFeelURL.setParameter(
			"redirect",
			ParamUtil.getString(
				_portal.getOriginalServletRequest(httpServletRequest),
				"p_l_back_url"));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		lookAndFeelURL.setParameter("backURL", themeDisplay.getURLCurrent());

		Layout layout = themeDisplay.getLayout();

		lookAndFeelURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		lookAndFeelURL.setParameter(
			"selPlid", String.valueOf(layout.getPlid()));
		lookAndFeelURL.setParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		return lookAndFeelURL.toString();
	}

	@Override
	public boolean includeSeparator() {
		return true;
	}

	@Override
	public boolean isLink() {
		return true;
	}

	@Override
	public boolean isVisible(
		PermissionChecker permissionChecker, long plid,
		boolean pageIsDisplayPage) {

		try {
			if (LayoutPermissionUtil.contains(
					permissionChecker, plid, ActionKeys.UPDATE)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LookAndFeelContentPageEditorSidebarPanel.class);

	@Reference
	private Portal _portal;

}