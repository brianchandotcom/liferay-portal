/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.FileSizeException;

import java.io.File;
import java.io.InputStream;

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
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
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

			String closeRedirect = ParamUtil.getString(
				actionRequest, "closeRedirect");

			if (Validator.isNotNull(closeRedirect)) {
				SessionMessages.add(
					actionRequest,
					portletConfig.getPortletName() + ".doCloseRedirect",
					closeRedirect);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException ||
				e instanceof SystemException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.layouts_admin.error");
			}
			else if (e instanceof FileSizeException ||
					 e instanceof ImageTypeException ||
					 e instanceof UploadException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			checkPermissions(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.layouts_admin.error");
		}

		try {
			getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.edit_layouts"));
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
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			layoutSetId);

		updateLogo(
			actionRequest, liveGroupId, stagingGroupId, privateLayout,
			layoutSet.isLogo());

		updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layoutSet.getThemeId(),
			layoutSet.getSettingsProperties());

		updateMergePages(actionRequest, liveGroupId);

		updateSettings(
			actionRequest, liveGroupId, stagingGroupId, privateLayout,
			layoutSet.getSettingsProperties());
	}

	protected void updateLogo(
			ActionRequest actionRequest, long liveGroupId,
			long stagingGroupId, boolean privateLayout, boolean hasLogo)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		boolean useLogo = ParamUtil.getBoolean(actionRequest, "useLogo");

		InputStream inputStream = null;

		try {
			File file = uploadPortletRequest.getFile("logoFileName");

			inputStream = new ByteArrayFileInputStream(file, 1024);

			if (useLogo && (inputStream == null)) {
				if (hasLogo) {
					return;
				}

				throw new UploadException("No logo uploaded for use");
			}

			if (inputStream != null) {
				inputStream.mark(0);
			}

			LayoutSetServiceUtil.updateLogo(
				liveGroupId, privateLayout, useLogo, inputStream, false);

			if (inputStream != null) {
				inputStream.reset();
			}

			if (stagingGroupId > 0) {
				LayoutSetServiceUtil.updateLogo(
					stagingGroupId, privateLayout, useLogo, inputStream, false);
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void updateLookAndFeel(
			ActionRequest actionRequest, long companyId, long liveGroupId,
			long stagingGroupId, boolean privateLayout, String oldThemeId,
			UnicodeProperties typeSettingsProperties)
		throws Exception {

		String[] devices = StringUtil.split(
			ParamUtil.getString(actionRequest, "devices"));

		for (String device : devices) {
			String themeId = ParamUtil.getString(
				actionRequest, device + "ThemeId");
			String colorSchemeId = ParamUtil.getString(
				actionRequest, device + "ColorSchemeId");
			String css = ParamUtil.getString(actionRequest, device + "Css");
			boolean wapTheme = device.equals("wap");

			if (Validator.isNotNull(themeId)) {
				colorSchemeId = getColorSchemeId(
					actionRequest, companyId, typeSettingsProperties, device,
					themeId, colorSchemeId, wapTheme);
			}

			long groupId = liveGroupId;

			if (stagingGroupId > 0) {
				groupId = stagingGroupId;
			}

			LayoutSetServiceUtil.updateLookAndFeel(
				groupId, privateLayout, themeId, colorSchemeId, css, wapTheme);
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
			boolean privateLayout, UnicodeProperties settingsProperties)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		settingsProperties.putAll(typeSettingsProperties);

		LayoutSetServiceUtil.updateSettings(
			liveGroupId, privateLayout, settingsProperties.toString());

		if (stagingGroupId > 0) {
			LayoutSetServiceUtil.updateSettings(
				stagingGroupId, privateLayout,
				typeSettingsProperties.toString());
		}
	}

}