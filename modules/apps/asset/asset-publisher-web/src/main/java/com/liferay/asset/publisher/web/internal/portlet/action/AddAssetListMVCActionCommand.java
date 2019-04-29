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

package com.liferay.asset.publisher.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.handler.AssetListExceptionRequestHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER,
		"mvc.command.name=/asset_publisher/add_asset_list"
	},
	service = MVCActionCommand.class
)
public class AddAssetListMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String title = ParamUtil.getString(actionRequest, "title");

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getExistingPortletSetup(
				themeDisplay.getLayout(), portletResource);

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		try {
			if (Objects.equals(selectionStyle, "dynamic")) {
				_saveDynamicAssetList(actionRequest, title, portletPreferences);
			}
			else if (Objects.equals(selectionStyle, "manual")) {
				_saveManualAssetList(actionRequest, title, portletPreferences);
			}

			JSONObject jsonObject = JSONUtil.put("redirectURL", redirect);

			hideDefaultSuccessMessage(actionRequest);

			MultiSessionMessages.add(
				actionRequest, portletResource + "requestProcessed");

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException pe) {
			hideDefaultErrorMessage(actionRequest);

			_assetListExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, pe);
		}
	}

	private void _saveDynamicAssetList(
			ActionRequest actionRequest, String title,
			PortletPreferences portletPreferences)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		UnicodeProperties properties = new UnicodeProperties(true);

		Enumeration<String> names = portletPreferences.getNames();

		while (names.hasMoreElements()) {
			String name = names.nextElement();

			String value = StringUtil.merge(
				portletPreferences.getValues(name, null));

			if (Validator.isNull(value)) {
				continue;
			}

			properties.put(name, value);
		}

		_assetListEntryService.addDynamicAssetListEntry(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), title,
			properties.toString(), serviceContext);
	}

	private void _saveManualAssetList(
			ActionRequest actionRequest, String title,
			PortletPreferences portletPreferences)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		long[] groupIds = _assetPublisherHelper.getGroupIds(
			portletPreferences, themeDisplay.getScopeGroupId(),
			themeDisplay.getLayout());

		List<AssetEntry> assetEntries = _assetPublisherHelper.getAssetEntries(
			actionRequest, portletPreferences,
			themeDisplay.getPermissionChecker(), groupIds, true, true);

		long[] assetEntryIds = ListUtil.toLongArray(
			assetEntries, AssetEntry::getEntryId);

		_assetListEntryService.addManualAssetListEntry(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), title,
			assetEntryIds, serviceContext);
	}

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private AssetListExceptionRequestHandler _assetListExceptionRequestHandler;

	@Reference
	private AssetPublisherHelper _assetPublisherHelper;

}