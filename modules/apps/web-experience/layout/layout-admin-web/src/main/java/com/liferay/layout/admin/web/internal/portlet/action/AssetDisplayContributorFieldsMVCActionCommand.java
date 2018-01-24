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

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.display.contributor.AssetDisplayField;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

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
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/asset_display_contributor_fields"
	},
	service = MVCActionCommand.class
)
public class AssetDisplayContributorFieldsMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String className = ParamUtil.getString(actionRequest, "className");

		AssetDisplayContributor assetDisplayContributor =
			_assetDisplayContributorTracker.getAssetDisplayContributor(
				className);

		if (assetDisplayContributor == null) {
			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AssetDisplayField assetDisplayField :
				assetDisplayContributor.getAssetEntryFields(
					themeDisplay.getLocale())) {

			jsonArray.put(assetDisplayField.toJSONObject());
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonArray);
	}

	@Reference
	private AssetDisplayContributorTracker _assetDisplayContributorTracker;

}