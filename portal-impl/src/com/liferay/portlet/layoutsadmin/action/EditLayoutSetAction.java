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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.ImageTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.ThemeSetting;
import com.liferay.portal.model.impl.ThemeSettingImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class EditLayoutSetAction extends EditLayoutsAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			checkPermissions(actionRequest);
		}
		catch (PrincipalException pe) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.UPDATE)) {
				updateLayoutSet(actionRequest, actionResponse);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException ||
				e instanceof SystemException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.layouts_admin.error");
			}
			else if (e instanceof FileSizeException ||
					 e instanceof ImageTypeException ||
					 e instanceof UploadException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			checkPermissions(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return actionMapping.findForward("portlet.layouts_admin.error");
		}

		try {
			getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.edit_layouts"));
	}

	@Override
	protected void setThemeSettingProperties(
		ActionRequest actionRequest, UnicodeProperties typeSettingsProperties,
		Map<String, ThemeSetting> themeSettings, String device,
		String deviceThemeId) {

		for (String key : themeSettings.keySet()) {
			ThemeSetting themeSetting = themeSettings.get(key);

			String property =
				device + "ThemeSettingsProperties--" + key +
					StringPool.DOUBLE_DASH;

			String value = ParamUtil.getString(
				actionRequest, property, themeSetting.getValue());

			if (!value.equals(themeSetting.getValue())) {
				typeSettingsProperties.setProperty(
					ThemeSettingImpl.namespaceProperty(device, key), value);
			}
		}
	}

	protected void updateLayoutSet(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long layoutSetId = ParamUtil.getLong(actionRequest, "layoutSetId");

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			layoutSetId);

		updateLogo(actionRequest, liveGroupId, stagingGroupId);

		updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, layoutSet.getSettingsProperties());

		updateMergePages(actionRequest, liveGroupId);

		updateSettings(
			actionRequest, liveGroupId, stagingGroupId,
			layoutSet.getSettingsProperties());
	}

	protected void updateLogo(
			ActionRequest actionRequest, long liveGroupId, long stagingGroupId)
		throws Exception {

		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		long groupId = liveGroupId;

		if (stagingGroupId > 0) {
			groupId = stagingGroupId;
		}

		LayoutSetServiceUtil.updateLogo(groupId, !deleteLogo, logoBytes);
	}

	protected void updateLookAndFeel(
			ActionRequest actionRequest, long companyId, long liveGroupId,
			long stagingGroupId, UnicodeProperties typeSettingsProperties)
		throws Exception {

		String[] devices = StringUtil.split(
			ParamUtil.getString(actionRequest, "devices"));

		for (String device : devices) {
			String deviceThemeId = ParamUtil.getString(
				actionRequest, device + "ThemeId");
			String deviceColorSchemeId = ParamUtil.getString(
				actionRequest, device + "ColorSchemeId");
			String deviceCss = ParamUtil.getString(
				actionRequest, device + "Css");
			boolean deviceWapTheme = device.equals("wap");

			if (Validator.isNotNull(deviceThemeId)) {
				deviceColorSchemeId = getColorSchemeId(
					companyId, deviceThemeId, deviceColorSchemeId,
					deviceWapTheme);

				updateThemeSettingsProperties(
					actionRequest, companyId, typeSettingsProperties, device,
					deviceThemeId, deviceWapTheme);
			}

			long groupId = liveGroupId;

			if (stagingGroupId > 0) {
				groupId = stagingGroupId;
			}

			LayoutSetServiceUtil.updateLookAndFeel(
				groupId, deviceThemeId, deviceColorSchemeId, deviceCss,
				deviceWapTheme);
		}
	}

	protected void updateMergePages(
			ActionRequest actionRequest, long liveGroupId)
		throws Exception {

		boolean mergeGuestPublicPages = ParamUtil.getBoolean(
			actionRequest, "mergeGuestPublicPages");

		Group liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"mergeGuestPublicPages", String.valueOf(mergeGuestPublicPages));

		GroupServiceUtil.updateGroup(liveGroupId, liveGroup.getTypeSettings());
	}

	protected void updateSettings(
			ActionRequest actionRequest, long liveGroupId, long stagingGroupId,
			UnicodeProperties settingsProperties)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		settingsProperties.putAll(typeSettingsProperties);

		long groupId = liveGroupId;

		if (stagingGroupId > 0) {
			groupId = stagingGroupId;
		}

		LayoutSetServiceUtil.updateSettings(
			groupId, settingsProperties.toString());
	}

}