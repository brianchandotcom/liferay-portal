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

package com.liferay.document.library.web.portlet.configuration.icon;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.display.context.logic.UIItemsBuilder;
import com.liferay.document.library.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class OpenInMSOfficeFileEntryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getLocale(portletRequest), getClass());

		return LanguageUtil.get(resourceBundle, "open-in-ms-office");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		StringBundler sb = new StringBundler(4);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			FileEntry fileEntry = ActionUtil.getFileEntry(portletRequest);

			String webDavURL = DLUtil.getWebDavURL(
				themeDisplay, fileEntry.getFolder(), fileEntry,
				PropsValues.
					DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			sb.append(portletDisplay.getNamespace());

			sb.append("openDocument('");
			sb.append(webDavURL);
			sb.append("');");
		}
		catch (Exception e) {
		}

		return sb.toString();
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public double getWeight() {
		return 107;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				portletRequest);

			FileEntry fileEntry = ActionUtil.getFileEntry(portletRequest);

			FileVersion fileVersion = ActionUtil.getFileVersion(
				portletRequest, fileEntry);

			UIItemsBuilder uiItemsBuilder = new UIItemsBuilder(
				request, fileVersion);

			return uiItemsBuilder.isOpenInMsOfficeActionAvailable();
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
	public boolean isUseDialog() {
		return true;
	}

}