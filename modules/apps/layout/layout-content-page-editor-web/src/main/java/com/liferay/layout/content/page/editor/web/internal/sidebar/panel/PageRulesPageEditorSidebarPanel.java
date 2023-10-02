/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.sidebar.panel;

import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutPermission;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sandro Chinea
 */
@Component(
	property = "service.ranking:Integer=500",
	service = ContentPageEditorSidebarPanel.class
)
public class PageRulesPageEditorSidebarPanel
	implements ContentPageEditorSidebarPanel {

	@Override
	public String getIcon() {
		return "rules";
	}

	@Override
	public String getId() {
		return "page_rules";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "page-rules");
	}

	@Override
	public boolean isVisible(
		PermissionChecker permissionChecker, long plid, int layoutType) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-169837")) {
			return false;
		}

		try {
			if ((_layoutPermission.containsLayoutUpdatePermission(
					permissionChecker, plid) &&
				 _layoutPermission.contains(
					 permissionChecker, plid,
					 ActionKeys.LAYOUT_RULE_BUILDER)) ||
				_modelResourcePermission.contains(
					permissionChecker, plid, ActionKeys.UPDATE)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageRulesPageEditorSidebarPanel.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private LayoutContentModelResourcePermission _modelResourcePermission;

}