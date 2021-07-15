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

import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "service.ranking:Integer=500",
	service = ContentPageEditorSidebarPanel.class
)
public class MappingContentPageEditorSidebarPanel
	implements ContentPageEditorSidebarPanel {

	@Override
	public String getIcon() {
		return "bolt";
	}

	@Override
	public String getId() {
		return "mapping";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "mapping");
	}

	@Override
	public boolean isVisible(
		PermissionChecker permissionChecker, long plid, int layoutType) {

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if ((layout == null) ||
			((layoutType !=
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) &&
			 !Objects.equals(
				 layout.getType(), LayoutConstants.TYPE_COLLECTION))) {

			return false;
		}

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
		MappingContentPageEditorSidebarPanel.class);

	@Reference
	private LayoutLocalService _layoutLocalService;

}