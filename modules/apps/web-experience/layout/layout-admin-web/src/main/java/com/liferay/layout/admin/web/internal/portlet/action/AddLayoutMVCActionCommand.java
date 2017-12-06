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

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.MDRActionLocalService;
import com.liferay.mobile.device.rules.service.MDRActionService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
		"mvc.command.name=/layout/add_layout"
	},
	service = MVCActionCommand.class
)
public class AddLayoutMVCActionCommand extends BaseMVCActionCommand {

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
		long parentLayoutId = ParamUtil.getLong(
			uploadPortletRequest, "parentLayoutId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		Map<Locale, String> keywordsMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "keywords");
		Map<Locale, String> robotsMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "robots");
		String type = ParamUtil.getString(uploadPortletRequest, "type");
		boolean hidden = ParamUtil.getBoolean(uploadPortletRequest, "hidden");
		Map<Locale, String> friendlyURLMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "friendlyURL");

		long layoutPrototypeId = ParamUtil.getLong(
			uploadPortletRequest, "layoutPrototypeId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		Layout layout = null;

		boolean inheritFromParentLayoutId = ParamUtil.getBoolean(
			uploadPortletRequest, "inheritFromParentLayoutId");

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		String linkToLayoutUuid = ParamUtil.getString(
			actionRequest, "linkToLayoutUuid");

		if (Validator.isNotNull(linkToLayoutUuid)) {
			Layout linkToLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
				linkToLayoutUuid, groupId, privateLayout);

			typeSettingsProperties.put(
				"linkToLayoutId", String.valueOf(linkToLayout.getLayoutId()));
		}

		if (inheritFromParentLayoutId && (parentLayoutId > 0)) {
			Layout parentLayout = _layoutLocalService.getLayout(
				groupId, privateLayout, parentLayoutId);

			layout = _layoutService.addLayout(
				groupId, privateLayout, parentLayoutId, nameMap, titleMap,
				parentLayout.getDescriptionMap(), parentLayout.getKeywordsMap(),
				parentLayout.getRobotsMap(), parentLayout.getType(),
				parentLayout.getTypeSettings(), hidden, friendlyURLMap,
				serviceContext);

			inheritMobileRuleGroups(layout, serviceContext);

			if (parentLayout.isTypePortlet()) {
				com.liferay.portlet.sites.action.ActionUtil.copyPreferences(
					actionRequest, layout, parentLayout);

				SitesUtil.copyLookAndFeel(layout, parentLayout);
			}
		}
		else if (layoutPrototypeId > 0) {
			LayoutPrototype layoutPrototype =
				_layoutPrototypeService.getLayoutPrototype(layoutPrototypeId);

			boolean layoutPrototypeLinkEnabled = ParamUtil.getBoolean(
				uploadPortletRequest,
				"layoutPrototypeLinkEnabled" + layoutPrototype.getUuid());

			serviceContext.setAttribute(
				"layoutPrototypeLinkEnabled", layoutPrototypeLinkEnabled);

			serviceContext.setAttribute(
				"layoutPrototypeUuid", layoutPrototype.getUuid());

			layout = _layoutService.addLayout(
				groupId, privateLayout, parentLayoutId, nameMap, titleMap,
				descriptionMap, keywordsMap, robotsMap,
				LayoutConstants.TYPE_PORTLET, typeSettingsProperties.toString(),
				hidden, friendlyURLMap, serviceContext);

			// Force propagation from page template to page. See LPS-48430.

			SitesUtil.mergeLayoutPrototypeLayout(layout.getGroup(), layout);
		}
		else {
			long copyLayoutId = ParamUtil.getLong(
				uploadPortletRequest, "copyLayoutId");

			Layout copyLayout = null;

			String layoutTemplateId = ParamUtil.getString(
				uploadPortletRequest, "layoutTemplateId",
				PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

			if (copyLayoutId > 0) {
				copyLayout = _layoutLocalService.fetchLayout(
					groupId, privateLayout, copyLayoutId);

				if ((copyLayout != null) && copyLayout.isTypePortlet()) {
					LayoutTypePortlet copyLayoutTypePortlet =
						(LayoutTypePortlet)copyLayout.getLayoutType();

					layoutTemplateId =
						copyLayoutTypePortlet.getLayoutTemplateId();

					typeSettingsProperties =
						copyLayout.getTypeSettingsProperties();
				}
			}

			layout = _layoutService.addLayout(
				groupId, privateLayout, parentLayoutId, nameMap, titleMap,
				descriptionMap, keywordsMap, robotsMap, type,
				typeSettingsProperties.toString(), hidden, friendlyURLMap,
				serviceContext);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.setLayoutTemplateId(
				themeDisplay.getUserId(), layoutTemplateId);

			_layoutService.updateLayout(
				groupId, privateLayout, layout.getLayoutId(),
				layout.getTypeSettings());

			if ((copyLayout != null) && copyLayout.isTypePortlet()) {
				com.liferay.portlet.sites.action.ActionUtil.copyPreferences(
					actionRequest, layout, copyLayout);

				SitesUtil.copyLookAndFeel(layout, copyLayout);
			}
		}

		_actionUtil.updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layout.getLayoutId(),
			layout.getTypeSettingsProperties());

		String portletResource = ParamUtil.getString(
			uploadPortletRequest, "portletResource");

		MultiSessionMessages.add(
			actionRequest, portletResource + "layoutAdded", layout);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = _portal.getLayoutFullURL(layout, themeDisplay);

			if (layout.isTypeURL()) {
				redirect = _portal.getGroupFriendlyURL(
					layout.getLayoutSet(), themeDisplay);
			}
		}

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	protected void inheritMobileRuleGroups(
			Layout layout, ServiceContext serviceContext)
		throws PortalException {

		List<MDRRuleGroupInstance> parentMDRRuleGroupInstances =
			_mdrRuleGroupInstanceLocalService.getRuleGroupInstances(
				Layout.class.getName(), layout.getParentPlid());

		for (MDRRuleGroupInstance parentMDRRuleGroupInstance :
				parentMDRRuleGroupInstances) {

			MDRRuleGroupInstance mdrRuleGroupInstance =
				_mdrRuleGroupInstanceService.addRuleGroupInstance(
					layout.getGroupId(), Layout.class.getName(),
					layout.getPlid(),
					parentMDRRuleGroupInstance.getRuleGroupId(),
					parentMDRRuleGroupInstance.getPriority(), serviceContext);

			List<MDRAction> parentMDRActions =
				_mdrActionLocalService.getActions(
					parentMDRRuleGroupInstance.getRuleGroupInstanceId());

			for (MDRAction mdrAction : parentMDRActions) {
				_mdrActionService.addAction(
					mdrRuleGroupInstance.getRuleGroupInstanceId(),
					mdrAction.getNameMap(), mdrAction.getDescriptionMap(),
					mdrAction.getType(), mdrAction.getTypeSettings(),
					serviceContext);
			}
		}
	}

	@Reference
	private ActionUtil _actionUtil;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPrototypeService _layoutPrototypeService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private MDRActionLocalService _mdrActionLocalService;

	@Reference
	private MDRActionService _mdrActionService;

	@Reference
	private MDRRuleGroupInstanceLocalService _mdrRuleGroupInstanceLocalService;

	@Reference
	private MDRRuleGroupInstanceService _mdrRuleGroupInstanceService;

	@Reference
	private Portal _portal;

}