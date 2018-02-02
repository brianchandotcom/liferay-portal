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

package com.liferay.knowledge.base.web.internal.portlet.configuration.icon;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"path=/admin/view_folders.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class EditKBFolderPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "edit");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			portletRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/admin/common/edit_folder.jsp");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(portletRequest));

		KBFolder kbFolder = (KBFolder)portletRequest.getAttribute(
			KBWebKeys.KNOWLEDGE_BASE_PARENT_KB_FOLDER);

		portletURL.setParameter(
			"kbFolderId", String.valueOf(kbFolder.getKbFolderId()));

		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 104;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		KBFolder kbFolder = (KBFolder)portletRequest.getAttribute(
			KBWebKeys.KNOWLEDGE_BASE_PARENT_KB_FOLDER);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		try {
			return _kbFolderModelResourcePermission.contains(
				permissionChecker, kbFolder, KBActionKeys.UPDATE);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditKBFolderPortletConfigurationIcon.class);

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBFolder)"
	)
	private ModelResourcePermission<KBFolder> _kbFolderModelResourcePermission;

	@Reference
	private Portal _portal;

}