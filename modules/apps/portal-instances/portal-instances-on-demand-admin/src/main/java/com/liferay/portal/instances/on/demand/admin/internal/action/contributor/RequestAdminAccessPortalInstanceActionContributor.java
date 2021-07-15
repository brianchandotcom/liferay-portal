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

package com.liferay.portal.instances.on.demand.admin.internal.action.contributor;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.instances.action.contributor.PortalInstanceActionContributor;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = PortalInstanceActionContributor.class)
public class RequestAdminAccessPortalInstanceActionContributor
	implements PortalInstanceActionContributor {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(), getClass()),
			"request-administrator-access");
	}

	@Override
	public String getURL(
		Company company, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		return PortletURLBuilder.createActionURL(
			_portal.getLiferayPortletResponse(portletResponse)
		).setActionName(
			"/portal_instances/request_admin_access"
		).setParameter(
			"companyId", company.getCompanyId()
		).buildString();
	}

	@Override
	public boolean isShow(Company company, PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.getCompanyId() == company.getCompanyId()) {
			return false;
		}

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		return permissionChecker.isOmniadmin();
	}

	@Reference
	private Portal _portal;

}