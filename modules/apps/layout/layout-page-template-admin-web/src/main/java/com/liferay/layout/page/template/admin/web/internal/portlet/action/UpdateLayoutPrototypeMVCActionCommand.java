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

import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.handler.LayoutPageTemplateEntryExceptionRequestHandler;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutPrototypeService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_prototype/update_layout_prototype"
	},
	service = MVCActionCommand.class
)
public class UpdateLayoutPrototypeMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long layoutPrototypeId = ParamUtil.getLong(
				actionRequest, "layoutPrototypeId");

			Map<Locale, String> nameMap = HashMapBuilder.put(
				actionRequest.getLocale(),
				ParamUtil.getString(actionRequest, "name")
			).build();

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				LayoutPrototype.class.getName(), actionRequest);

			_layoutPrototypeService.updateLayoutPrototype(
				layoutPrototypeId, nameMap, new HashMap<>(), true,
				serviceContext);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("redirectURL", redirect));
		}
		catch (Throwable t) {
			if (_log.isDebugEnabled()) {
				_log.debug(t, t);
			}

			if (t instanceof LayoutPageTemplateEntryNameException) {
				LayoutPageTemplateEntryNameException lptene =
					(LayoutPageTemplateEntryNameException)t;

				_layoutPageTemplateEntryExceptionRequestHandler.
					handlePortalException(
						actionRequest, actionResponse, lptene);
			}
			else {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse,
					JSONUtil.put(
						"error",
						LanguageUtil.get(
							themeDisplay.getRequest(),
							"an-unexpected-error-occurred")));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateLayoutPrototypeMVCActionCommand.class);

	@Reference
	private LayoutPageTemplateEntryExceptionRequestHandler
		_layoutPageTemplateEntryExceptionRequestHandler;

	@Reference
	private LayoutPrototypeService _layoutPrototypeService;

}