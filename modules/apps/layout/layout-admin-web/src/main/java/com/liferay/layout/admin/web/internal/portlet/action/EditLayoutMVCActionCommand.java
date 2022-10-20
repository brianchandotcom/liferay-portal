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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.sites.kernel.util.Sites;

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
		"mvc.command.name=/layout_admin/edit_layout"
	},
	service = MVCActionCommand.class
)
public class EditLayoutMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
			long stagingGroupId = ParamUtil.getLong(
				actionRequest, "stagingGroupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long layoutId = ParamUtil.getLong(actionRequest, "layoutId");
			Map<Locale, String> nameMap = _localization.getLocalizationMap(
				actionRequest, "name");
			String type = ParamUtil.getString(uploadPortletRequest, "type");
			boolean hidden = ParamUtil.getBoolean(
				uploadPortletRequest, "hidden");
			Map<Locale, String> friendlyURLMap =
				_localization.getLocalizationMap(actionRequest, "friendlyURL");
			boolean deleteLogo = ParamUtil.getBoolean(
				actionRequest, "deleteLogo");

			byte[] iconBytes = null;

			long fileEntryId = ParamUtil.getLong(
				uploadPortletRequest, "fileEntryId");

			if (fileEntryId > 0) {
				FileEntry fileEntry = _dlAppLocalService.getFileEntry(
					fileEntryId);

				iconBytes = FileUtil.getBytes(fileEntry.getContentStream());
			}

			Layout layout = _layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			long styleBookEntryId = ParamUtil.getLong(
				uploadPortletRequest, "styleBookEntryId",
				layout.getStyleBookEntryId());
			long faviconFileEntryId = ParamUtil.getLong(
				uploadPortletRequest, "faviconFileEntryId",
				layout.getFaviconFileEntryId());
			long masterLayoutPlid = ParamUtil.getLong(
				uploadPortletRequest, "masterLayoutPlid",
				layout.getMasterLayoutPlid());

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Layout.class.getName(), actionRequest);

			if ((layout.isTypeAssetDisplay() || layout.isTypeContent()) &&
				(layout.fetchDraftLayout() == null)) {

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					Layout.class.getName(), layout.getPlid());

				serviceContext.setAssetCategoryIds(assetEntry.getCategoryIds());
			}

			String oldFriendlyURL = layout.getFriendlyURL();

			Collection<String> values = friendlyURLMap.values();

			values.removeIf(value -> Validator.isNull(value));

			if (friendlyURLMap.isEmpty()) {
				friendlyURLMap = layout.getFriendlyURLMap();
			}

			if (layout.isTypeAssetDisplay()) {
				serviceContext.setAttribute(
					"layout.instanceable.allowed", Boolean.TRUE);
			}

			layout = _layoutService.updateLayout(
				groupId, privateLayout, layoutId, layout.getParentLayoutId(),
				nameMap, layout.getTitleMap(), layout.getDescriptionMap(),
				layout.getKeywordsMap(), layout.getRobotsMap(), type, hidden,
				friendlyURLMap, !deleteLogo, iconBytes, styleBookEntryId,
				faviconFileEntryId, masterLayoutPlid, serviceContext);

			_updateClientExtensions(
				actionRequest, layout, themeDisplay.getUserId());

			UnicodeProperties formTypeSettingsUnicodeProperties =
				PropertiesParamUtil.getProperties(
					actionRequest, "TypeSettingsProperties--");

			Layout draftLayout = layout.fetchDraftLayout();

			if (draftLayout != null) {
				serviceContext.setAttribute(
					Sites.LAYOUT_UPDATEABLE,
					formTypeSettingsUnicodeProperties.get(
						Sites.LAYOUT_UPDATEABLE));

				_layoutService.updateLayout(
					groupId, privateLayout, draftLayout.getLayoutId(),
					draftLayout.getParentLayoutId(), nameMap,
					draftLayout.getTitleMap(), draftLayout.getDescriptionMap(),
					draftLayout.getKeywordsMap(), draftLayout.getRobotsMap(),
					type, draftLayout.isHidden(),
					draftLayout.getFriendlyURLMap(), !deleteLogo, iconBytes,
					styleBookEntryId, faviconFileEntryId,
					draftLayout.getMasterLayoutPlid(), serviceContext);

				_updateClientExtensions(
					actionRequest, layout, themeDisplay.getUserId());
			}

			themeDisplay.clearLayoutFriendlyURL(layout);

			UnicodeProperties layoutTypeSettingsUnicodeProperties =
				layout.getTypeSettingsProperties();

			String linkToLayoutUuid = ParamUtil.getString(
				actionRequest, "linkToLayoutUuid");

			if (Validator.isNotNull(linkToLayoutUuid)) {
				Layout linkToLayout = _layoutService.getLayoutByUuidAndGroupId(
					linkToLayoutUuid, groupId, privateLayout);

				formTypeSettingsUnicodeProperties.put(
					"linkToLayoutId",
					String.valueOf(linkToLayout.getLayoutId()));
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (type.equals(LayoutConstants.TYPE_PORTLET)) {
				String layoutTemplateId = ParamUtil.getString(
					uploadPortletRequest, "layoutTemplateId",
					PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

				layoutTypePortlet.setLayoutTemplateId(
					themeDisplay.getUserId(), layoutTemplateId);

				layoutTypeSettingsUnicodeProperties.putAll(
					formTypeSettingsUnicodeProperties);

				boolean layoutCustomizable = GetterUtil.getBoolean(
					layoutTypeSettingsUnicodeProperties.get(
						LayoutConstants.CUSTOMIZABLE_LAYOUT));

				if (!layoutCustomizable) {
					layoutTypePortlet.removeCustomization(
						layoutTypeSettingsUnicodeProperties);
				}
			}
			else {
				layoutTypeSettingsUnicodeProperties.putAll(
					formTypeSettingsUnicodeProperties);

				layoutTypeSettingsUnicodeProperties.putAll(
					layout.getTypeSettingsProperties());
			}

			layout = _layoutService.updateLayout(
				groupId, privateLayout, layoutId,
				layoutTypeSettingsUnicodeProperties.toString());

			EventsProcessorUtil.process(
				PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
				layoutTypePortlet.getConfigurationActionUpdate(),
				uploadPortletRequest,
				_portal.getHttpServletResponse(actionResponse));

			ActionUtil.updateLookAndFeel(
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
		catch (ModelListenerException modelListenerException) {
			if (modelListenerException.getCause() instanceof PortalException) {
				throw (PortalException)modelListenerException.getCause();
			}

			throw modelListenerException;
		}
	}

	private void _addClientExtensionEntryRel(
			String cetExternalReferenceCode, Layout layout, String type,
			long userId)
		throws PortalException {

		if (Validator.isNotNull(cetExternalReferenceCode)) {
			ClientExtensionEntryRel clientExtensionEntryRel =
				_clientExtensionEntryRelLocalService.
					fetchClientExtensionEntryRelByExternalReferenceCode(
						layout.getCompanyId(), cetExternalReferenceCode);

			if (clientExtensionEntryRel == null) {
				_clientExtensionEntryRelLocalService.
					deleteClientExtensionEntryRels(
						_portal.getClassNameId(Layout.class), layout.getPlid(),
						type);

				_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
					userId, layout.getGroupId(),
					_portal.getClassNameId(Layout.class), layout.getPlid(),
					cetExternalReferenceCode, type, StringPool.BLANK);
			}
		}
		else {
			_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRels(
				_portal.getClassNameId(Layout.class), layout.getPlid(), type);
		}
	}

	private void _updateClientExtensions(
			ActionRequest actionRequest, Layout layout, long userId)
		throws PortalException {

		String themeFaviconCETExternalReferenceCode = ParamUtil.getString(
			actionRequest, "themeFaviconCETExternalReferenceCode");

		_addClientExtensionEntryRel(
			themeFaviconCETExternalReferenceCode, layout,
			ClientExtensionEntryConstants.TYPE_THEME_FAVICON, userId);

		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRels(
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			ClientExtensionEntryConstants.TYPE_GLOBAL_CSS);

		String[] globalCSSCETExternalReferenceCodes = ParamUtil.getStringValues(
			actionRequest, "globalCSSCETExternalReferenceCodes");

		for (String globalCSSCETExternalReferenceCode :
				globalCSSCETExternalReferenceCodes) {

			_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
				userId, layout.getGroupId(),
				_portal.getClassNameId(Layout.class), layout.getPlid(),
				globalCSSCETExternalReferenceCode,
				ClientExtensionEntryConstants.TYPE_GLOBAL_CSS,
				StringPool.BLANK);
		}

		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRels(
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			ClientExtensionEntryConstants.TYPE_GLOBAL_JS);

		String[] globalJSCETExternalReferenceCodes = ParamUtil.getStringValues(
			actionRequest, "globalJSCETExternalReferenceCodes");

		for (String globalJSCETExternalReferenceCode :
				globalJSCETExternalReferenceCodes) {

			String[] typeSettings = StringUtil.split(
				globalJSCETExternalReferenceCode, StringPool.UNDERLINE);

			UnicodeProperties typeSettingsUnicodeProperties =
				UnicodePropertiesBuilder.create(
					true
				).put(
					"loadType", typeSettings[1]
				).put(
					"scriptLocation", typeSettings[2]
				).build();

			_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
				userId, layout.getGroupId(),
				_portal.getClassNameId(Layout.class), layout.getPlid(),
				typeSettings[0], ClientExtensionEntryConstants.TYPE_GLOBAL_JS,
				typeSettingsUnicodeProperties.toString());
		}

		String themeCSSCETExternalReferenceCode = ParamUtil.getString(
			actionRequest, "themeCSSCETExternalReferenceCode");

		_addClientExtensionEntryRel(
			themeCSSCETExternalReferenceCode, layout,
			ClientExtensionEntryConstants.TYPE_THEME_CSS, userId);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

}