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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.seo.service.LayoutSEOEntryService;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/edit_layout"
	},
	service = MVCActionCommand.class
)
public class EditLayoutMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String type = ParamUtil.getString(uploadPortletRequest, "type");
		boolean hidden = ParamUtil.getBoolean(uploadPortletRequest, "hidden");
		Map<Locale, String> friendlyURLMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "friendlyURL");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] iconBytes = null;

		long fileEntryId = ParamUtil.getLong(
			uploadPortletRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			iconBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		Layout layout = _layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		String oldFriendlyURL = layout.getFriendlyURL();

		Collection<String> values = friendlyURLMap.values();

		values.removeIf(value -> Validator.isNull(value));

		if (friendlyURLMap.isEmpty()) {
			friendlyURLMap = layout.getFriendlyURLMap();
		}

		String currentType = layout.getType();

		if (StringUtil.equals(
				currentType, LayoutConstants.TYPE_ASSET_DISPLAY)) {

			serviceContext.setAttribute(
				"layout.instanceable.allowed", Boolean.TRUE);
		}

		layout = _layoutService.updateLayout(
			groupId, privateLayout, layoutId, layout.getParentLayoutId(),
			nameMap, layout.getTitleMap(), layout.getDescriptionMap(),
			layout.getKeywordsMap(), layout.getRobotsMap(), type, hidden,
			friendlyURLMap, !deleteLogo, iconBytes, serviceContext);

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if (draftLayout != null) {
			_layoutService.updateLayout(
				groupId, privateLayout, draftLayout.getLayoutId(),
				draftLayout.getParentLayoutId(), nameMap,
				draftLayout.getTitleMap(), draftLayout.getDescriptionMap(),
				draftLayout.getKeywordsMap(), draftLayout.getRobotsMap(), type,
				draftLayout.isHidden(), draftLayout.getFriendlyURLMap(),
				!deleteLogo, iconBytes, serviceContext);
		}

		themeDisplay.clearLayoutFriendlyURL(layout);

		UnicodeProperties layoutTypeSettingsProperties =
			layout.getTypeSettingsProperties();

		UnicodeProperties formTypeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		String linkToLayoutUuid = ParamUtil.getString(
			actionRequest, "linkToLayoutUuid");

		if (Validator.isNotNull(linkToLayoutUuid)) {
			Layout linkToLayout = _layoutService.getLayoutByUuidAndGroupId(
				linkToLayoutUuid, groupId, privateLayout);

			formTypeSettingsProperties.put(
				"linkToLayoutId", String.valueOf(linkToLayout.getLayoutId()));
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (type.equals(LayoutConstants.TYPE_PORTLET)) {
			String layoutTemplateId = ParamUtil.getString(
				uploadPortletRequest, "layoutTemplateId",
				PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

			layoutTypePortlet.setLayoutTemplateId(
				themeDisplay.getUserId(), layoutTemplateId);

			layoutTypeSettingsProperties.putAll(formTypeSettingsProperties);

			boolean layoutCustomizable = GetterUtil.getBoolean(
				layoutTypeSettingsProperties.get(
					LayoutConstants.CUSTOMIZABLE_LAYOUT));

			if (!layoutCustomizable) {
				layoutTypePortlet.removeCustomization(
					layoutTypeSettingsProperties);
			}

			layout = _layoutService.updateLayout(
				groupId, privateLayout, layoutId,
				layoutTypeSettingsProperties.toString());

			if (!currentType.equals(LayoutConstants.TYPE_PORTLET)) {
				_portletPreferencesLocalService.deletePortletPreferences(
					0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());
			}
		}
		else {
			layoutTypeSettingsProperties.putAll(formTypeSettingsProperties);

			layoutTypeSettingsProperties.putAll(
				layout.getTypeSettingsProperties());

			layout = _layoutService.updateLayout(
				groupId, privateLayout, layoutId,
				layoutTypeSettingsProperties.toString());
		}

		EventsProcessorUtil.process(
			PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
			layoutTypePortlet.getConfigurationActionUpdate(),
			uploadPortletRequest,
			_portal.getHttpServletResponse(actionResponse));

		_actionUtil.updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layout.getLayoutId(),
			layout.getTypeSettingsProperties());

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNull(redirect) ||
			(redirect.contains(oldFriendlyURL) &&
			 !Objects.equals(layout.getFriendlyURL(), oldFriendlyURL))) {

			redirect = _portal.getLayoutFullURL(layout, themeDisplay);
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		MultiSessionMessages.add(
			actionRequest, portletResource + "layoutUpdated", layout);

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	@Reference
	private ActionUtil _actionUtil;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSEOEntryService _layoutSEOEntryService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}