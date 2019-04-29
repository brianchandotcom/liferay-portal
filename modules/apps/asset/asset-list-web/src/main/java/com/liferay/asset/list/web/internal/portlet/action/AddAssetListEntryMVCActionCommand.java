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

package com.liferay.asset.list.web.internal.portlet.action;

import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.asset.list.web.internal.handler.AssetListEntryExceptionRequestHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
		"mvc.command.name=/asset_list/add_asset_list_entry"
	},
	service = MVCActionCommand.class
)
public class AddAssetListEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String title = ParamUtil.getString(actionRequest, "title");
		int type = ParamUtil.getInteger(actionRequest, "type");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			AssetListEntry assetListEntry = null;

			if (type == AssetListEntryTypeConstants.TYPE_DYNAMIC) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				UnicodeProperties properties = new UnicodeProperties(true);

				properties.setProperty(
					"groupIds", String.valueOf(themeDisplay.getScopeGroupId()));

				assetListEntry =
					_assetListEntryService.addDynamicAssetListEntry(
						serviceContext.getUserId(),
						serviceContext.getScopeGroupId(), title,
						properties.toString(), serviceContext);
			}
			else {
				assetListEntry = _assetListEntryService.addAssetListEntry(
					serviceContext.getScopeGroupId(), title, type,
					serviceContext);
			}

			JSONObject jsonObject = JSONUtil.put(
				"redirectURL", getRedirectURL(actionResponse, assetListEntry));

			if (SessionErrors.contains(
					actionRequest, "assetListEntryNameInvalid")) {

				addSuccessMessage(actionRequest, actionResponse);
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, "assetListEntryNameInvalid");

			hideDefaultErrorMessage(actionRequest);

			_assetListEntryExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, pe);
		}
	}

	protected String getRedirectURL(
		ActionResponse actionResponse, AssetListEntry assetListEntry) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/edit_asset_list_entry.jsp");
		portletURL.setParameter(
			"assetListEntryId",
			String.valueOf(assetListEntry.getAssetListEntryId()));

		return portletURL.toString();
	}

	@Reference
	private AssetListEntryExceptionRequestHandler
		_assetListEntryExceptionRequestHandler;

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private Portal _portal;

}