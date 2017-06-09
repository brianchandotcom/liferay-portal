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

package com.liferay.layout.admin.web.internal.portlet.configuration.icon;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES},
	service = PortletConfigurationIcon.class
)
public class CopyApplicationsPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	@Override
	public String getId() {
		return "copyApplications";
	}

	@Override
	public String getJspPath() {
		return "/configuration/icon/copy_applications.jsp";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getLocale(portletRequest), getClass());

		return LanguageUtil.get(resourceBundle, "copy-applications");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public double getWeight() {
		return 101.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			Layout layout = getLayout(portletRequest);

			if (layout == null) {
				return false;
			}

			// Check if layout is incomplete

			LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
				layout);

			boolean incomplete = false;

			if (layoutRevision != null) {
				long layoutSetBranchId = layoutRevision.getLayoutSetBranchId();

				incomplete = _staging.isIncomplete(layout, layoutSetBranchId);
			}

			if (incomplete) {
				return false;
			}

			// Check if layout is a layout prototype

			Group group = layout.getGroup();

			if (group.isLayoutPrototype()) {
				return false;
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.UPDATE)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.admin.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	protected Layout getLayout(PortletRequest portletRequest) throws Exception {
		long selPlid = ParamUtil.getLong(
			portletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _layoutLocalService.fetchLayout(selPlid);
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private LayoutLocalService _layoutLocalService;

	@Reference
	private Staging _staging;

}