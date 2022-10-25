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

package com.liferay.dynamic.data.lists.web.internal.portlet.configuration.icon;

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.web.internal.security.permission.resource.DDLPermission;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS,
	service = PortletConfigurationIcon.class
)
public class DDMStructuresPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(
			getLocale(portletRequest), "manage-data-definitions");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				portletRequest,
				PortletProviderUtil.getPortletId(
					DDMStructure.class.getName(), PortletProvider.Action.VIEW),
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/view.jsp"
		).setBackURL(
			themeDisplay.getURLCurrent()
		).setParameter(
			"groupId", themeDisplay.getScopeGroupId()
		).setParameter(
			"refererPortletName", DDLPortletKeys.DYNAMIC_DATA_LISTS
		).setParameter(
			"refererWebDAVToken",
			() -> {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				Portlet portlet = _portletLocalService.getPortletById(
					portletDisplay.getId());

				return WebDAVUtil.getStorageToken(portlet);
			}
		).setParameter(
			"showAncestorScopes", true
		).buildString();
	}

	@Override
	public double getWeight() {
		return 102;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		if (user.isDefaultUser()) {
			return false;
		}

		return DDLPermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup(),
			DDLActionKeys.ADD_RECORD_SET);
	}

	@Reference
	private Language _language;

	@Reference
	private PortletLocalService _portletLocalService;

}