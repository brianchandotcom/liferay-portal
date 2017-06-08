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

package com.liferay.knowledge.base.web.portlet.configuration.icon;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.permission.KBTemplatePermission;
import com.liferay.knowledge.base.web.constants.KBWebKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"path=/admin/view_template.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class PermissionsKBTemplatePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "permissions");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String url = StringPool.BLANK;

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		KBTemplate kbTemplate = (KBTemplate)portletRequest.getAttribute(
			KBWebKeys.KNOWLEDGE_BASE_KB_TEMPLATE);

		try {
			String modelResource = KBTemplate.class.getName();
			String modelResourceDescription = kbTemplate.getTitle();
			String resourcePrimKey = String.valueOf(
				kbTemplate.getKbTemplateId());

			url = PermissionsURLTag.doTag(
				StringPool.BLANK, modelResource, modelResourceDescription, null,
				resourcePrimKey, LiferayWindowState.POP_UP.toString(), null,
				themeDisplay.getRequest());
		}
		catch (Exception e) {
		}

		return url;
	}

	@Override
	public double getWeight() {
		return 102;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		KBTemplate kbTemplate = (KBTemplate)portletRequest.getAttribute(
			KBWebKeys.KNOWLEDGE_BASE_KB_TEMPLATE);

		if (KBTemplatePermission.contains(
				permissionChecker, kbTemplate, KBActionKeys.PERMISSIONS)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

}