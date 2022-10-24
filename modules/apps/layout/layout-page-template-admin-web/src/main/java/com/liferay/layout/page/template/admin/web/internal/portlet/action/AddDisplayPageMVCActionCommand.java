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

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.asset.kernel.NoSuchClassTypeException;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.handler.LayoutPageTemplateEntryExceptionRequestHandler;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.NoSuchClassNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template_admin/add_display_page"
	},
	service = MVCActionCommand.class
)
public class AddDisplayPageMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		JSONObject jsonObject = _addDisplayPage(actionRequest, serviceContext);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);

		if (SessionErrors.contains(
				actionRequest, "layoutPageTemplateEntryNameInvalid")) {

			addSuccessMessage(actionRequest, actionResponse);
		}

		MultiSessionMessages.add(actionRequest, "displayPageAdded");
	}

	protected String getRedirectURL(
			ActionRequest actionRequest,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		Layout draftLayout = _layoutLocalService.fetchDraftLayout(
			layoutPageTemplateEntry.getPlid());

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutFullURL = _portal.getLayoutFullURL(
			draftLayout, themeDisplay);

		layoutFullURL = HttpComponentsUtil.setParameter(
			layoutFullURL, "p_l_back_url",
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					actionRequest,
					LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
					PortletRequest.RENDER_PHASE)
			).setTabs1(
				"display-page-templates"
			).buildString());

		return HttpComponentsUtil.setParameter(
			layoutFullURL, "p_l_mode", Constants.EDIT);
	}

	private JSONObject _addDisplayPage(
		ActionRequest actionRequest, ServiceContext serviceContext) {

		JSONObject errorJSONObject = _jsonFactory.createJSONObject();

		long layoutPageTemplateCollectionId = ParamUtil.getLong(
			actionRequest, "layoutPageTemplateCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");
		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classTypeId = ParamUtil.getLong(actionRequest, "classTypeId");
		long masterLayoutPlid = ParamUtil.getLong(
			actionRequest, "masterLayoutPlid");

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			serviceContext.getLocale(), AddDisplayPageMVCActionCommand.class);

		try {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
					serviceContext.getScopeGroupId(),
					layoutPageTemplateCollectionId, classNameId, classTypeId,
					name, masterLayoutPlid, WorkflowConstants.STATUS_DRAFT,
					serviceContext);

			return JSONUtil.put(
				"redirectURL",
				getRedirectURL(actionRequest, layoutPageTemplateEntry));
		}
		catch (PortalException portalException) {
			if (portalException instanceof NoSuchClassNameException) {
				errorJSONObject = JSONUtil.put(
					"classNameId",
					ResourceBundleUtil.getString(
						resourceBundle, "invalid-content-type"));
			}
			else if (portalException instanceof NoSuchClassTypeException) {
				errorJSONObject = JSONUtil.put(
					"classTypeId",
					ResourceBundleUtil.getString(
						resourceBundle, "invalid-subtype"));
			}
			else {
				errorJSONObject = JSONUtil.put(
					"name",
					() -> {
						JSONObject jsonObject =
							_layoutPageTemplateEntryExceptionRequestHandler.
								createErrorJSONObject(
									actionRequest, portalException);

						return jsonObject.get("error");
					});
			}
		}

		return JSONUtil.put("error", errorJSONObject);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryExceptionRequestHandler
		_layoutPageTemplateEntryExceptionRequestHandler;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}