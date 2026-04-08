/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.seo.studio.site.initializer.internal.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(
	property = {
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS,
		"panel.category.order:Integer=1500"
	},
	service = PanelCategory.class
)
public class SEOStudioPanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return _KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "visibility");
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

	private static final String _KEY =
		"applications_menu.applications.seo_studio";

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

}