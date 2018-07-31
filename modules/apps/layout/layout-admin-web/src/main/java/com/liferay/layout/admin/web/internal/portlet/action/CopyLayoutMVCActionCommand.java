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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/copy_layout"
	},
	service = MVCActionCommand.class
)
public class CopyLayoutMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(uploadPortletRequest, "layoutId");
		String name = ParamUtil.getString(actionRequest, "name");

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(themeDisplay.getLocale(), name);

		if (!Objects.equals(
				themeDisplay.getLocale(), LocaleUtil.getSiteDefault())) {

			nameMap.put(LocaleUtil.getSiteDefault(), name);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			Layout copyLayout = _layoutLocalService.fetchLayout(
				groupId, privateLayout, layoutId);

			LayoutTypePortlet copyLayoutTypePortlet =
				(LayoutTypePortlet)copyLayout.getLayoutType();

			UnicodeProperties copyTypeSettingsProperties =
				copyLayout.getTypeSettingsProperties();

			copyTypeSettingsProperties.putAll(typeSettingsProperties);

			Layout layout = _layoutService.addLayout(
				groupId, privateLayout, copyLayout.getParentLayoutId(), nameMap,
				new HashMap<>(), new HashMap<>(), copyLayout.getKeywordsMap(),
				copyLayout.getRobotsMap(), copyLayout.getType(),
				copyTypeSettingsProperties.toString(), false, new HashMap<>(),
				serviceContext);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.setLayoutTemplateId(
				themeDisplay.getUserId(),
				copyLayoutTypePortlet.getLayoutTemplateId());

			_layoutService.updateLayout(
				groupId, privateLayout, layout.getLayoutId(),
				layout.getTypeSettings());

			com.liferay.portlet.sites.action.ActionUtil.copyPreferences(
				actionRequest, layout, copyLayout);

			SitesUtil.copyLookAndFeel(layout, copyLayout);

			long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
			long stagingGroupId = ParamUtil.getLong(
				actionRequest, "stagingGroupId");

			_actionUtil.updateLookAndFeel(
				actionRequest, themeDisplay.getCompanyId(), liveGroupId,
				stagingGroupId, privateLayout, layout.getLayoutId(),
				layout.getTypeSettingsProperties());

			List<FragmentEntryLink> fragmentEntryLinks =
				_fragmentEntryLinkLocalService.getFragmentEntryLinks(
					copyLayout.getGroupId(),
					_portal.getClassNameId(Layout.class), copyLayout.getPlid());

			if (ListUtil.isNotEmpty(fragmentEntryLinks)) {
				for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
					_copyFragmentEntryLink(
						layout.getPlid(), fragmentEntryLink, serviceContext);
				}
			}

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(actionResponse);

			PortletURL redirectURL = liferayPortletResponse.createRenderURL();

			redirectURL.setParameter(
				"navigation", privateLayout ? "private-pages" : "public-pages");
			redirectURL.setParameter(
				"selPlid", String.valueOf(copyLayout.getParentPlid()));
			redirectURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));

			jsonObject.put("redirectURL", redirectURL.toString());

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getLocale(), "an-unexpected-error-occurred"));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
	}

	private void _copyFragmentEntryLink(
			long newPlid, FragmentEntryLink fragmentEntryLink,
			ServiceContext serviceContext)
		throws PortalException {

		FragmentEntryLink newFragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
				fragmentEntryLink.getFragmentEntryId(),
				_portal.getClassNameId(Layout.class), newPlid,
				fragmentEntryLink.getCss(), fragmentEntryLink.getHtml(),
				fragmentEntryLink.getJs(),
				fragmentEntryLink.getEditableValues(),
				fragmentEntryLink.getPosition(), serviceContext);

		List<String> portletIds =
			_portletRegistry.getFragmentEntryLinkPortletIds(fragmentEntryLink);

		for (String portletId : portletIds) {
			long defaultPlid = _portal.getControlPanelPlid(
				serviceContext.getCompanyId());

			PortletPreferences portletPreferences =
				_portletPreferencesLocalService.fetchPreferences(
					serviceContext.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, defaultPlid,
					portletId);

			if (portletPreferences == null) {
				continue;
			}

			String newPortletId = StringUtil.replace(
				portletId, fragmentEntryLink.getNamespace(),
				newFragmentEntryLink.getNamespace());

			_portletPreferencesLocalService.addPortletPreferences(
				serviceContext.getCompanyId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, defaultPlid, newPortletId,
				null, PortletPreferencesFactoryUtil.toXML(portletPreferences));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyLayoutMVCActionCommand.class);

	@Reference
	private ActionUtil _actionUtil;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

}