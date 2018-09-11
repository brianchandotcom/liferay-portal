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

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

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
		"javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
		"mvc.command.name=/asset_list/remove_asset_entry_selection"
	},
	service = MVCActionCommand.class
)
public class RemoveAssetEntrySelectionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long assetListEntryId = ParamUtil.getLong(
			actionRequest, "assetListEntryId");

		int assetEntryOrder = ParamUtil.getInteger(
			actionRequest, "assetEntryOrder");

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(assetListEntryId);

		if (assetListEntry != null) {
			UnicodeProperties typeSettingsProperties = new UnicodeProperties(
				true);

			typeSettingsProperties.fastLoad(assetListEntry.getTypeSettings());

			_removeSelection(typeSettingsProperties, assetEntryOrder);

			assetListEntry.setTypeSettings(typeSettingsProperties.toString());

			_assetListEntryLocalService.updateAssetListEntry(assetListEntry);
		}
	}

	private void _removeSelection(
		UnicodeProperties typeSettingsProperties, int assetEntryOrder) {

		String assetEntryXmlProperty = typeSettingsProperties.getProperty(
			"assetEntryXml");

		List<String> assetEntryXmls = new ArrayList<>();

		if (Validator.isNotNull(assetEntryXmlProperty)) {
			assetEntryXmls = StringUtil.split(assetEntryXmlProperty);
		}

		if (ListUtil.isEmpty(assetEntryXmls) ||
			(assetEntryOrder >= assetEntryXmls.size())) {

			return;
		}

		assetEntryXmls.remove(assetEntryOrder);

		typeSettingsProperties.put(
			"assetEntryXml", String.join(StringPool.COMMA, assetEntryXmls));
	}

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}