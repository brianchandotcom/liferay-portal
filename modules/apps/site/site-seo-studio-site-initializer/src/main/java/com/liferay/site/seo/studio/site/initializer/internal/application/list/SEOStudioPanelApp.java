/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.seo.studio.site.initializer.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLWrapper;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.PortletImpl;

import jakarta.portlet.PortletURL;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(
	property = {
		"panel.app.order:Integer=1000",
		"panel.category.key=applications_menu.applications.seo_studio"
	},
	service = PanelApp.class
)
public class SEOStudioPanelApp extends BasePanelApp {

	@Override
	public String getIcon() {
		return "seo";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "seo-studio");
	}

	@Override
	public int getNotificationsCount(User user) {
		return 0;
	}

	@Override
	public Portlet getPortlet() {
		return new PortletImpl();
	}

	@Override
	public String getPortletId() {
		return "applications_menu.applications.seo_studio";
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new PortletURLWrapper(null) {

			@Override
			public String toString() {
				return themeDisplay.getPathFriendlyURLPublic() +
					GroupConstants.SEO_STUDIO_FRIENDLY_URL;
			}

		};
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled(
				permissionChecker.getCompanyId(), "LPD-44511")) {

			return false;
		}

		Group seoGroup = _groupLocalService.fetchGroup(
			group.getCompanyId(), GroupConstants.SEO_STUDIO);

		if ((seoGroup == null) || !seoGroup.isActive()) {
			return false;
		}

		return PortalPermissionUtil.contains(
			permissionChecker, ActionKeys.VIEW_CONTROL_PANEL);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

}