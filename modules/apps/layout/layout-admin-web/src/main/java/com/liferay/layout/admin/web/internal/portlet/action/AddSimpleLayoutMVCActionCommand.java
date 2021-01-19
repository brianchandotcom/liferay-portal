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
import com.liferay.layout.admin.web.internal.handler.LayoutExceptionRequestHandler;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
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
		"mvc.command.name=/layout_admin/add_simple_layout"
	},
	service = MVCActionCommand.class
)
public class AddSimpleLayoutMVCActionCommand
	extends BaseAddLayoutMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			actionRequest, "parentLayoutId");
		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(),
			ParamUtil.getString(actionRequest, "name")
		).build();
		String type = ParamUtil.getString(actionRequest, "type");
		UnicodeProperties typeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		long masterLayoutPlid = ParamUtil.getLong(
			actionRequest, "masterLayoutPlid");

		if (!Objects.equals(type, LayoutConstants.TYPE_CONTENT)) {
			LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
				_layoutPageTemplateEntryService.
					fetchDefaultLayoutPageTemplateEntry(
						groupId,
						LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
						WorkflowConstants.STATUS_APPROVED);

			if (defaultLayoutPageTemplateEntry != null) {
				masterLayoutPlid = defaultLayoutPageTemplateEntry.getPlid();
			}
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		try {
			Layout layout = _layoutService.addLayout(
				groupId, privateLayout, parentLayoutId, nameMap,
				new HashMap<>(), new HashMap<>(), new HashMap<>(),
				new HashMap<>(), type, typeSettingsUnicodeProperties.toString(),
				false, new HashMap<>(), masterLayoutPlid, serviceContext);

			if (!Objects.equals(type, LayoutConstants.TYPE_CONTENT)) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				layoutTypePortlet.setLayoutTemplateId(
					themeDisplay.getUserId(),
					PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

				_layoutService.updateLayout(
					groupId, privateLayout, layout.getLayoutId(),
					layout.getTypeSettings());
			}

			ActionUtil.updateLookAndFeel(
				actionRequest, themeDisplay.getCompanyId(), liveGroupId,
				stagingGroupId, privateLayout, layout.getLayoutId(),
				layout.getTypeSettingsProperties());

			Layout draftLayout = layout.fetchDraftLayout();

			if (draftLayout != null) {
				_layoutLocalService.updateLayout(
					groupId, privateLayout, layout.getLayoutId(),
					draftLayout.getModifiedDate());
			}

			MultiSessionMessages.add(actionRequest, "layoutAdded", layout);

			String redirectURL = getRedirectURL(
				actionRequest, actionResponse, layout);

			if (Objects.equals(type, LayoutConstants.TYPE_CONTENT)) {
				redirectURL = getContentRedirectURL(actionRequest, layout);
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("redirectURL", redirectURL));
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, "layoutNameInvalid");

			hideDefaultErrorMessage(actionRequest);

			_layoutExceptionRequestHandler.handleException(
				actionRequest, actionResponse, exception);
		}
	}

	@Reference
	private LayoutExceptionRequestHandler _layoutExceptionRequestHandler;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}