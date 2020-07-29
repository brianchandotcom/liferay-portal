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

import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
		"mvc.command.name=/asset_list/update_asset_list_entry_manual"
	},
	service = MVCActionCommand.class
)
public class UpdateAssetListEntryManualMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long assetListEntryId = ParamUtil.getLong(
			actionRequest, "assetListEntryId");

		AssetListEntry assetListEntry =
			_assetListEntryService.fetchAssetListEntry(assetListEntryId);

		if (assetListEntry == null) {
			return;
		}

		long segmentsEntryId = ParamUtil.getLong(
			actionRequest, "segmentsEntryId");

		UnicodeProperties typeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		_assetListEntryService.updateAssetListEntryTypeSettings(
			assetListEntryId, segmentsEntryId,
			typeSettingsUnicodeProperties.toString());
	}

	@Reference
	private AssetListEntryService _assetListEntryService;

}