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
import com.liferay.layout.utility.page.exception.LayoutUtilityPageEntryNameException;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/add_layout_utility_page_entry"
	},
	service = MVCActionCommand.class
)
public class AddLayoutUtilityPageEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String name = ParamUtil.getString(actionRequest, "name");
		int type = ParamUtil.getInteger(actionRequest, "type");
		long masterLayoutPlid = ParamUtil.getLong(
			actionRequest, "masterLayoutPlid");

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			LayoutUtilityPageEntry layoutUtilityPageEntry =
				_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
					null, serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), name, type,
					masterLayoutPlid);

			JSONObject jsonObject = JSONUtil.put(
				"redirectURL",
				_getRedirectURL(actionRequest, layoutUtilityPageEntry));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);

			MultiSessionMessages.add(
				actionRequest, "layoutUtilityPageEntryAdded");
		}
		catch (PortalException portalException) {
			hideDefaultErrorMessage(actionRequest);

			_handlePortalException(
				actionRequest, actionResponse, portalException);
		}
	}

	private JSONObject _createErrorJSONObject(
		ActionRequest actionRequest, PortalException portalException) {

		if (_log.isDebugEnabled()) {
			_log.debug(portalException);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String errorMessage = null;

		if (portalException instanceof
				LayoutUtilityPageEntryNameException.MustNotBeDuplicate) {

			errorMessage = _language.get(
				themeDisplay.getLocale(),
				"a-utility-page-with-that-name-already-exists");
		}
		else if (portalException instanceof
					LayoutUtilityPageEntryNameException.MustNotBeNull) {

			errorMessage = _language.get(
				themeDisplay.getLocale(), "name-must-not-be-empty");
		}
		else if (portalException instanceof
					LayoutUtilityPageEntryNameException.
						MustNotContainInvalidCharacters) {

			LayoutUtilityPageEntryNameException.MustNotContainInvalidCharacters
				lptene =
					(LayoutUtilityPageEntryNameException.
						MustNotContainInvalidCharacters)portalException;

			errorMessage = _language.format(
				themeDisplay.getLocale(),
				"name-cannot-contain-the-following-invalid-character-x",
				lptene.character);
		}
		else if (portalException instanceof
					LayoutUtilityPageEntryNameException.
						MustNotExceedMaximumSize) {

			int nameMaxLength = ModelHintsUtil.getMaxLength(
				LayoutUtilityPageEntry.class.getName(), "name");

			errorMessage = _language.format(
				themeDisplay.getLocale(),
				"please-enter-a-name-with-fewer-than-x-characters",
				nameMaxLength);
		}

		if (Validator.isNull(errorMessage)) {
			errorMessage = _language.get(
				themeDisplay.getLocale(), "an-unexpected-error-occurred");

			_log.error(portalException);
		}

		return JSONUtil.put("error", errorMessage);
	}

	private String _getRedirectURL(
			ActionRequest actionRequest,
			LayoutUtilityPageEntry layoutUtilityPageEntry)
		throws PortalException {

		Layout draftLayout = _layoutLocalService.fetchDraftLayout(
			layoutUtilityPageEntry.getPlid());

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutFullURL = _portal.getLayoutFullURL(
			draftLayout, themeDisplay);

		layoutFullURL = HttpComponentsUtil.setParameter(
			layoutFullURL, "p_l_back_url",
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					actionRequest, LayoutAdminPortletKeys.GROUP_PAGES,
					PortletRequest.RENDER_PHASE)
			).setTabs1(
				"utility-pages"
			).buildString());

		return HttpComponentsUtil.setParameter(
			layoutFullURL, "p_l_mode", Constants.EDIT);
	}

	private void _handlePortalException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			PortalException portalException)
		throws Exception {

		JSONObject errorJSONObject = _createErrorJSONObject(
			actionRequest, portalException);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, errorJSONObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddLayoutUtilityPageEntryMVCActionCommand.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Reference
	private Portal _portal;

}