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
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutPrototypeService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
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

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long layoutPageTemplateEntryId = ParamUtil.getLong(
			actionRequest, "layoutPageTemplateEntryId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			actionRequest, "parentLayoutId");

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(),
			ParamUtil.getString(actionRequest, "name")
		).build();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Layout layout = null;

		try {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);

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
					new HashMap<>(), LayoutConstants.TYPE_PORTLET, null, false,
					new HashMap<>(), serviceContext);

				// Force propagation from page template to page. See LPS-48430.

				SitesUtil.mergeLayoutPrototypeLayout(layout.getGroup(), layout);
			}
			else {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				if (layoutPageTemplateEntryId > 0) {
					LayoutPageTemplateEntryPermission.check(
						themeDisplay.getPermissionChecker(),
						layoutPageTemplateEntryId, ActionKeys.VIEW);
				}

				layout = _layoutService.addLayout(
					groupId, privateLayout, parentLayoutId,
					portal.getClassNameId(LayoutPageTemplateEntry.class),
					layoutPageTemplateEntryId, nameMap, new HashMap<>(),
					new HashMap<>(), new HashMap<>(), new HashMap<>(),
					LayoutConstants.TYPE_CONTENT, null, false, false,
					new HashMap<>(), serviceContext);
			}

			String redirectURL = getRedirectURL(
				actionRequest, actionResponse, layout);

			if (Objects.equals(
					layout.getType(), LayoutConstants.TYPE_CONTENT)) {

				redirectURL = getContentRedirectURL(actionRequest, layout);
			}

			jsonObject.put("redirectURL", redirectURL);

			MultiSessionMessages.add(actionRequest, "layoutAdded", layout);

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
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPrototypeService _layoutPrototypeService;

	@Reference
	private LayoutService _layoutService;

}