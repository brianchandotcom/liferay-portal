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

package com.liferay.staging.configuration.web.internal.portlet.configuration.icon;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.staging.constants.StagingConfigurationPortletKeys;
import com.liferay.staging.constants.StagingProcessesPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = PortletConfigurationIcon.class)
public class StagingPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getCssClass() {
		return "portlet-export-import portlet-export-import-icon";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "staging");
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		StringBundler sb = new StringBundler(12);

		sb.append("Liferay.Portlet.openWindow({bodyCssClass: ");
		sb.append("'dialog-with-footer', namespace: '");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		sb.append(portletDisplay.getNamespace());

		sb.append("', portlet: '#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("', title: '");
		sb.append(LanguageUtil.get(themeDisplay.getLocale(), "staging"));
		sb.append("', uri: '");
		sb.append(HtmlUtil.escapeJS(portletDisplay.getURLStaging()));
		sb.append("'}); return false;");

		return sb.toString();
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getURLStaging();
	}

	@Override
	public double getWeight() {
		return 16.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getSiteGroup();

		if (group.hasLocalOrRemoteStagingGroup() &&
			!PropsValues.STAGING_LIVE_GROUP_REMOTE_STAGING_ENABLED) {

			return false;
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String rootPortletId = portletDisplay.getRootPortletId();

		if (rootPortletId.equals(ExportImportPortletKeys.EXPORT) ||
			rootPortletId.equals(ExportImportPortletKeys.EXPORT_IMPORT) ||
			rootPortletId.equals(ExportImportPortletKeys.IMPORT) ||
			rootPortletId.equals(
				StagingConfigurationPortletKeys.STAGING_CONFIGURATION) ||
			rootPortletId.equals(
				StagingProcessesPortletKeys.STAGING_PROCESSES)) {

			return false;
		}

		if (!portletDisplay.isShowStagingIcon()) {
			return false;
		}

		Portlet portlet = _portletLocalService.getPortletById(
			portletDisplay.getId());

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if ((portletDataHandler == null) ||
			!portletDataHandler.isConfigurationEnabled()) {

			return false;
		}

		try {
			return GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup(), ActionKeys.PUBLISH_PORTLET_INFO);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return false;
		}
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingPortletConfigurationIcon.class);

	@Reference
	private PortletLocalService _portletLocalService;

}