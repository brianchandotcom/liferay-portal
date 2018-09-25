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
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutPrototypeService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sites.kernel.util.SitesUtil;

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
		"mvc.command.name=/layout/add_content_layout"
	},
	service = MVCActionCommand.class
)
public class AddContentLayoutMVCActionCommand
	extends BaseAddLayoutMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		long layoutPageTemplateEntryId = GetterUtil.getLong(
			typeSettingsProperties.getProperty("layoutPageTemplateEntryId"));

		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			actionRequest, "parentLayoutId");
		String name = ParamUtil.getString(actionRequest, "name");

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), name);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Layout layout = null;

		try {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.fetchLayoutPageTemplateEntry(
					layoutPageTemplateEntryId);

			if ((layoutPageTemplateEntry != null) &&
				(layoutPageTemplateEntry.getLayoutPrototypeId() > 0)) {

				LayoutPrototype layoutPrototype =
					_layoutPrototypeService.getLayoutPrototype(
						layoutPageTemplateEntry.getLayoutPrototypeId());

				serviceContext.setAttribute(
					"layoutPrototypeUuid", layoutPrototype.getUuid());

				layout = _layoutService.addLayout(
					groupId, privateLayout, parentLayoutId, nameMap,
					new HashMap<>(), new HashMap<>(), new HashMap<>(),
					new HashMap<>(), LayoutConstants.TYPE_PORTLET,
					typeSettingsProperties.toString(), false, new HashMap<>(),
					serviceContext);

				// Force propagation from page template to page. See LPS-48430.

				SitesUtil.mergeLayoutPrototypeLayout(layout.getGroup(), layout);
			}
			else {
				layout = _layoutService.addLayout(
					groupId, privateLayout, parentLayoutId, nameMap,
					new HashMap<>(), new HashMap<>(), new HashMap<>(),
					new HashMap<>(), "content",
					typeSettingsProperties.toString(), false, new HashMap<>(),
					serviceContext);
			}

			String redirectURL = getRedirectURL(
				actionRequest, actionResponse, layout);

			if (Objects.equals(layout.getType(), "content")) {
				redirectURL = getContentRedirectURL(themeDisplay, layout);
			}

			jsonObject.put("redirectURL", redirectURL);

			SessionMessages.add(actionRequest, "layoutAdded", layout);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			SessionErrors.add(actionRequest, "layoutNameInvalid");

			hideDefaultErrorMessage(actionRequest);

			_layoutExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddContentLayoutMVCActionCommand.class);

	@Reference
	private LayoutExceptionRequestHandler _layoutExceptionRequestHandler;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPrototypeService _layoutPrototypeService;

	@Reference
	private LayoutService _layoutService;

}