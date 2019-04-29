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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Set;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_asset_mapping_fields"
	},
	service = MVCResourceCommand.class
)
public class GetAssetMappingFieldsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long classNameId = ParamUtil.getLong(resourceRequest, "classNameId");

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				_portal.getClassName(classNameId));

		if (infoDisplayContributor == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		long classPK = ParamUtil.getLong(resourceRequest, "classPK");

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			classNameId, classPK);

		if (assetEntry == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Set<InfoDisplayField> infoDisplayFields =
			infoDisplayContributor.getInfoDisplayFields(
				assetEntry.getClassTypeId(), themeDisplay.getLocale());

		for (InfoDisplayField infoDisplayField : infoDisplayFields) {
			JSONObject jsonObject = JSONUtil.put(
				"key", infoDisplayField.getKey()
			).put(
				"label", infoDisplayField.getLabel()
			).put(
				"type", infoDisplayField.getType()
			);

			jsonArray.put(jsonObject);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Portal _portal;

}